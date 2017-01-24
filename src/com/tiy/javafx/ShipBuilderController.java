package com.tiy.javafx;

import com.sun.org.apache.xpath.internal.SourceTree;
import com.tiy.cli.Player;
import com.tiy.starship.*;
import com.tiy.starsys.StarSystem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Paul Dennis on 1/17/2017.
 */
public class ShipBuilderController implements Initializable {

    @FXML
    ComboBox<Weapon> firstSmallWeapSlot;
    @FXML
    ComboBox<Weapon> secondSmallWeapSlot;
    @FXML
    ComboBox<Weapon> thirdSmallWeapSlot;
    @FXML
    ComboBox<Weapon> fourthSmallWeapSlot;
    @FXML
    ComboBox<Weapon> fifthSmallWeapSlot;

    @FXML
    ComboBox<Weapon> firstLargeWeapSlot;
    @FXML
    ComboBox<Weapon> secondLargeWeapSlot;
    @FXML
    ComboBox<Weapon> thirdLargeWeapSlot;
    @FXML
    ComboBox<Weapon> fourthLargeWeapSlot;
    @FXML
    ComboBox<Weapon> fifthLargeWeapSlot;

    @FXML
    ComboBox<String> firstUpgradeSlot;
    @FXML
    ComboBox<String> secondUpgradeSlot;

    @FXML
    ComboBox<String> shipSizeComboBox;

    @FXML
    TextField shipNameTextField;

    @FXML
    Label shipHealthLabel;
    @FXML
    Label shieldHealthLabel;
    @FXML
    Label shieldStrengthLabel;
    @FXML
    Label shieldRegenLabel;
    @FXML
    Label generatorReserveLabel;
    @FXML
    Label generatorPerTurnLabel;
    @FXML
    Label fighterBerthsLabel;

    @FXML
    Button addShipTopButton;
    @FXML
    Button addShipBottomButton;

    @FXML
    Button removeShipButton;

    @FXML
    Label totalProductionLabel;
    @FXML
    Label estimatedTimeLabel;
    @FXML
    Label currentProductionLabel;

    @FXML
    ListView productionQueueListView;

    @FXML
    Label shipyardLabel;

    List<ComboBox> smallWeapComboBoxes;
    List<ComboBox> largeWeapComboBoxes;
    List<ComboBox> upgradeComboBoxes;

    Player player;
    ObservableList<Project> productionQueue;
    Shipyard shipyard;

    int productionCost;

    ShipChassis chassis;
    int smallWeapSlots;
    int largeWeapSlots;
    int upgradeSlots;

    //Todo change the combo boxes to actually hold weapons. It'll be way easier that way trust me

    public void initialize (URL url, ResourceBundle resourceBundle) {
        player = new Player(new StarSystem("Beetlejuice"), null);
        initializeComboBoxes();

        String secondSize = "Destroyer";
        String firstSize = "Fighter";
        ObservableList<String> shipSizeList = FXCollections.observableArrayList(firstSize, secondSize);

        System.out.println("Adding some starter projects to queue");
        shipyard = player.getShipyard();
        shipyardLabel.setText(shipyard.toString());
        shipyard.addProject(new Project(30, new Destroyer(shipyard, player), shipyard));
        shipyard.addProject(new Project(10, new Fighter(shipyard, player), shipyard));
        shipyard.addProject(new Project(25, new Destroyer(shipyard, player), shipyard));
        productionQueue = FXCollections.observableArrayList(shipyard.getProjects());
        productionQueueListView.setItems(productionQueue);
        shipSizeComboBox.setItems(shipSizeList);

        addListenersToComponentComboBoxes();

        shipSizeComboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String t, String t1) {
                String shipSize = shipSizeComboBox.getSelectionModel().getSelectedItem();
                chassis = ShipChassis.getShipChassis(shipSize);
                productionCost = chassis.getBaseProductionCost();
                setShipInfoLabels(chassis);
                setProductionInfoLabels();
                resetComboBoxes();
                smallWeapSlots = chassis.getSmallWeaponSlots();
                largeWeapSlots = chassis.getLargeWeaponSlots();
                upgradeSlots = chassis.getUpgradeSlots();
                int index = 0;

                for (ComboBox box : smallWeapComboBoxes) {
                    if (index < smallWeapSlots) {
                        box.setDisable(false);
                    } else {
                        box.setDisable(true);
                    }
                    index++;
                }
                index = 0;
                for (ComboBox box : largeWeapComboBoxes) {
                    if (index < largeWeapSlots) {
                        box.setDisable(false);
                    } else {
                        box.setDisable(true);
                    }
                    index++;
                }
                index = 0;
                for (ComboBox box : upgradeComboBoxes) {
                    if (index < upgradeSlots) {
                        box.setDisable(false);
                    } else {
                        box.setDisable(true);
                    }
                    index++;
                }
            }
        });
    }

    public void addShipBottom () {
        addShipToQueue(false);
    }

    public void addShipTop () {
        addShipToQueue(true);
    }

    public void addShipToQueue (boolean topPriority) {
        String shipName = shipNameTextField.getText();
        String shipSize = shipSizeComboBox.getSelectionModel().getSelectedItem();
        if (shipSize == null) {
            System.out.println("Must select ship size");
            return;
        }
        Starship starship;
        if (!shipName.equals("")) {
            switch (shipSize) {
                case "Fighter":
                    starship = new Fighter(shipyard, player, shipName);
                    break;
                case "Destroyer":
                    starship = new Destroyer(shipyard, player, shipName);
                    break;
                default:
                    throw new AssertionError("Error in addShipBottom");
            }
            List<Weapon> weapons = new ArrayList<>();
            for (int index = 0; index < smallWeapSlots; index++) {
                weapons.add((Weapon) smallWeapComboBoxes.get(index).getSelectionModel().getSelectedItem());
            }
            for (int index = 0; index < largeWeapSlots; index++) {
                weapons.add((Weapon) largeWeapComboBoxes.get(index).getSelectionModel().getSelectedItem());
            }
            starship.setWeapons(weapons);
            Project project = new Project(productionCost, starship, shipyard);
            if (topPriority) {
                System.out.println("Adding " + shipName + " to top of queue.");
                shipyard.addProjectTopPriority(project);
                productionQueue.add(0, project);
            } else {
                System.out.println("Adding " + shipName + " to bottom of queue.");
                shipyard.addProject(project);
                productionQueue.add(project);
                shipSizeComboBox.setSelectionModel(null);
            }
        } else {
            System.out.println("Name cannot be blank");
        }
    }

    public void setShipInfoLabels (ShipChassis chassis) {
        Shield shield = chassis.getShield();
        Generator generator = chassis.getGenerator();
        shipHealthLabel.setText("" + chassis.getHealth());
        shieldHealthLabel.setText("" + shield.getShieldHealth());
        shieldStrengthLabel.setText("" + shield.getMaxDamageAbsorb());
        shieldRegenLabel.setText("" + shield.getRegenRate());
        generatorReserveLabel.setText("" + generator.getMaxReservePower());
        generatorPerTurnLabel.setText("" + generator.getPowerPerTurn());
        fighterBerthsLabel.setText("" + chassis.getFighterBerths());
    }

    public void setProductionInfoLabels () {
        int production = player.getCurrentProductionPerTurn();
        currentProductionLabel.setText("" + production);
        totalProductionLabel.setText("" + productionCost);
        estimatedTimeLabel.setText("" + productionCost / production);
    }

    public void initializeComboBoxes () {
        ObservableList<Weapon> smallWeaponList = FXCollections.observableArrayList(player.getAvailableSmallWeaps());
        ObservableList<Weapon> largeWeaponList = FXCollections.observableArrayList(player.getAvailableLargeWeaps());

        smallWeapComboBoxes = new ArrayList<>();
        largeWeapComboBoxes = new ArrayList<>();
        upgradeComboBoxes = new ArrayList<>();

        smallWeapComboBoxes.add(firstSmallWeapSlot);
        smallWeapComboBoxes.add(secondSmallWeapSlot);
        smallWeapComboBoxes.add(thirdSmallWeapSlot);
        smallWeapComboBoxes.add(fourthSmallWeapSlot);
        smallWeapComboBoxes.add(fifthSmallWeapSlot);

        largeWeapComboBoxes.add(firstLargeWeapSlot);
        largeWeapComboBoxes.add(secondLargeWeapSlot);
        largeWeapComboBoxes.add(thirdLargeWeapSlot);
        largeWeapComboBoxes.add(fourthLargeWeapSlot);
        largeWeapComboBoxes.add(fifthLargeWeapSlot);

        upgradeComboBoxes.add(firstUpgradeSlot);
        upgradeComboBoxes.add(secondUpgradeSlot);

        for (ComboBox box : smallWeapComboBoxes) {
            box.setItems(smallWeaponList);
        }
        for (ComboBox box : largeWeapComboBoxes) {
            box.setItems(largeWeaponList);
        }
    }

    public void removeSelectedShip () {
        Project project = (Project) productionQueueListView.getSelectionModel().getSelectedItem();
        if (project != null) {
            System.out.println("Removing " + project);
            productionQueue.remove(project);
            shipyard.removeProject(project);
        }
    }

    public void addListenersToComponentComboBoxes() {
        for (ComboBox box : smallWeapComboBoxes) {
            box.valueProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                    refreshInfo();
                }
            });
        }
        for (ComboBox box : largeWeapComboBoxes) {
            box.valueProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                    refreshInfo();
                }
            });
        }
        for (ComboBox box : upgradeComboBoxes) {
            box.valueProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                    refreshInfo();
                }
            });
        }
    }

    public void resetComboBoxes () {
        for (ComboBox box : smallWeapComboBoxes) {
            box.getSelectionModel().clearSelection();
        }
        for (ComboBox box : largeWeapComboBoxes) {
            box.getSelectionModel().clearSelection();
        }
        for (ComboBox box : upgradeComboBoxes) {
            box.getSelectionModel().clearSelection();
        }
        refreshInfo();
    }

    public void refreshInfo () {
        System.out.println("Refreshing info");
        productionCost = chassis.getBaseProductionCost();
        for (int index = 0; index < smallWeapSlots; index++) {
            Weapon selection = (Weapon) smallWeapComboBoxes.get(index).getSelectionModel().getSelectedItem();
            if (selection != null) {
                System.out.println("Small Weap selected = " + selection + " at index" + index);
                productionCost += selection.getBaseProductionCost();
            }
        }
        for (int index = 0; index < largeWeapSlots; index++) {
            Weapon selection = (Weapon) largeWeapComboBoxes.get(index).getSelectionModel().getSelectedItem();
            if (selection != null) {
                System.out.println("Large Weap selected = " + selection + " at index" + index);
                productionCost += selection.getBaseProductionCost();
            }
        }
        for (int index = 0; index < upgradeSlots; index++) {
            System.out.println("Not doin' nothin'");
        }
        totalProductionLabel.setText("" + productionCost);
    }
}
