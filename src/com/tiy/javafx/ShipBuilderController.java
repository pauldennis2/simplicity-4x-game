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
    ComboBox<String> firstSmallWeapSlot;
    @FXML
    ComboBox<String> secondSmallWeapSlot;
    @FXML
    ComboBox<String> thirdSmallWeapSlot;
    @FXML
    ComboBox<String> fourthSmallWeapSlot;
    @FXML
    ComboBox<String> fifthSmallWeapSlot;

    @FXML
    ComboBox<String> firstLargeWeapSlot;
    @FXML
    ComboBox<String> secondLargeWeapSlot;
    @FXML
    ComboBox<String> thirdLargeWeapSlot;
    @FXML
    ComboBox<String> fourthLargeWeapSlot;
    @FXML
    ComboBox<String> fifthLargeWeapSlot;

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

    List<ComboBox> smallWeapComboBoxes;
    List<ComboBox> largeWeapComboBoxes;
    List<ComboBox> upgradeComboBoxes;

    Player player;
    ObservableList<Project> productionQueue;
    Shipyard shipyard;

    public void initialize (URL url, ResourceBundle resourceBundle) {
        player = new Player(new StarSystem("Beetlejuice"), null);
        initializeComboBoxes();

        String secondSize = "Destroyer";
        String firstSize = "Fighter";
        ObservableList<String> shipSizeList = FXCollections.observableArrayList(firstSize, secondSize);

        System.out.println("Adding some starter projects to queue");
        shipyard = player.getShipyard();
        shipyard.addProject(new Project(30, new Destroyer(shipyard, player), shipyard));
        shipyard.addProject(new Project(10, new Fighter(shipyard, player), shipyard));
        shipyard.addProject(new Project(25, new Destroyer(shipyard, player), shipyard));
        productionQueue = FXCollections.observableArrayList(shipyard.getProjects());
        if (productionQueueListView == null) {
            System.out.println("about to throw null pointer ex");
        }
        productionQueueListView.setItems(productionQueue);
        shipSizeComboBox.setItems(shipSizeList);

        shipSizeComboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String t, String t1) {
                String shipSize = shipSizeComboBox.getSelectionModel().getSelectedItem();
                ShipChassis chassis = ShipChassis.getShipChassis(shipSize);
                setShipInfoLabels(chassis);
                setProductionInfoLabels();
                int smallWeapSlots = chassis.getSmallWeaponSlots();
                int largeWeapSlots = chassis.getLargeWeaponSlots();
                int upgradeSlots = chassis.getUpgradeSlots();
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
        if (topPriority) {
            System.out.println("Adding " + shipNameTextField.getText() + " to top of queue.");
        } else {
            System.out.println("Adding " + shipNameTextField.getText() + " to bottom of queue.");
        }
        String shipSize = shipSizeComboBox.getSelectionModel().getSelectedItem();
        if (shipSize == null) {
            System.out.println("Must select ship size");
            return;
        }
        String shipName = shipNameTextField.getText();
        int productionAmt = Integer.parseInt(totalProductionLabel.getText());
        Starship starship;
        if (!shipName.equals("")) {
            switch (shipSize) {
                case "Fighter":
                    starship = new Fighter(shipyard, player);
                    break;
                case "Destroyer":
                    starship = new Destroyer(shipyard, player);
                    break;
                default:
                    starship = null;
                    throw new AssertionError("Error in addShipBottom");
            }
            Project project = new Project(productionAmt, starship, shipyard);
            if (topPriority) {
                shipyard.addProjectTopPriority(project);
                productionQueue.add(0, project);
            } else {
                shipyard.addProject(project);
                productionQueue.add(project);
            }
        } else {
            System.out.println("Name cannot be blank");
        }
    }

    public void setShipInfoLabels (ShipChassis chassis) {
        shipHealthLabel.setText("" + chassis.getHealth());
        shieldHealthLabel.setText("100?");
        shieldStrengthLabel.setText("" + chassis.getShieldStrength());
        shieldRegenLabel.setText("1?");
        generatorReserveLabel.setText("200?");
        generatorPerTurnLabel.setText("20?");
        fighterBerthsLabel.setText("" + chassis.getFighterBerths());
    }

    public void setProductionInfoLabels () {
        System.out.println("Using hardcoded values to set Production Info Labels");
        int production = player.getCurrentProductionPerTurn();
        currentProductionLabel.setText("" + production);
        totalProductionLabel.setText("50");
        estimatedTimeLabel.setText("" + 50 / production);
    }

    public void initializeComboBoxes () {
        ObservableList<String> smallWeaponList = FXCollections.observableArrayList(player.getAvailableSmallWeaps());
        ObservableList<String> largeWeaponList = FXCollections.observableArrayList(player.getAvailableLargeWeaps());

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
}
