package com.tiy.gui;

import com.tiy.ImproperFunctionInputException;
import com.tiy.cli.Player;
import com.tiy.starship.*;
import com.tiy.starsys.StarSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by erronius on 1/2/2017.
 */
public class MainGui {
    private JButton gameOptionsButton;
    private JButton advanceTurnButton;
    private JButton planetViewButton;
    private JButton shipsShipyardViewButton;
    private JButton researchViewButton;
    private JButton diplomacyViewButton;
    private JButton helpButton;
    private JButton goToSelectedButton;
    private JPanel mainPanel;
    private JProgressBar shipHealthProgressBar;
    private JButton zapShipButton;
    private JLabel shipStatusLabel;
    private JProgressBar shipShieldProgressBar;
    private JFormattedTextField zapAmountTextField;
    private JProgressBar shipGeneratorProgressBar;
    private JPanel optionsPanel;
    private JPanel shipDisplayPanel;
    private JPanel actionAndDisplayPanel;
    private JPanel actionSubPanel;
    private JPanel mainDisplayPanel;
    private JComboBox shipSelectionComboBox;
    
    Fighter fighter;
    Destroyer destroyer;
    Starship selectedShip;

    public MainGui() {
        StarSystem betelgeuse = new StarSystem("Beetlejuice");
        Player owner = new Player(betelgeuse, "Paul");
        fighter = new Fighter(betelgeuse, owner);
        destroyer = new Destroyer(betelgeuse, owner);
        owner.addShip(fighter);
        owner.addShip(destroyer);
        for (Starship starship : owner.getShips()) {
            System.out.println(starship);
        }

        shipHealthProgressBar.setForeground(Color.green);
        shipShieldProgressBar.setForeground(Color.cyan);
        shipGeneratorProgressBar.setForeground(Color.yellow);

        DefaultComboBoxModel<String> shipSelectionModel = new DefaultComboBoxModel<>();
        for (Starship starship : owner.getShips()) {
            shipSelectionModel.addElement(starship.getName());
        }
        shipSelectionComboBox.setModel(shipSelectionModel);

        gameOptionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Game options");
            }
        });
        advanceTurnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Advancing turn");
            }
        });
        zapShipButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shipHealthProgressBar.setMaximum(selectedShip.getMaxHealth());
                shipShieldProgressBar.setMaximum(selectedShip.getShield().getMaxShieldHealth());
                shipGeneratorProgressBar.setMaximum(selectedShip.getGenerator().getMaxReservePower());

                int damage;
                try {
                    damage = Integer.parseInt(zapAmountTextField.getText());
                } catch (NumberFormatException ex) {
                    damage = 10;
                }
                try {
                    selectedShip.takeDamage(damage);
                } catch (ImproperFunctionInputException ex) {
                    ex.printStackTrace();
                }

                shipHealthProgressBar.setValue(selectedShip.getHealth());
                shipHealthProgressBar.setString(selectedShip.getHealth() + "/" + selectedShip.getMaxHealth());

                Shield shield = selectedShip.getShield();
                shipShieldProgressBar.setValue(shield.getShieldHealth());
                shipShieldProgressBar.setString(shield.getShieldHealth() + "/" + shield.getMaxShieldHealth());
                //System.out.println(shield);

                Generator generator = selectedShip.getGenerator();
                shipGeneratorProgressBar.setValue(generator.getCurrentReservePower());
                shipGeneratorProgressBar.setString(generator.getCurrentReservePower() + "/" + generator.getMaxReservePower());

                shipStatusLabel.setText(selectedShip.toString());
            }
        });
        goToSelectedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = shipSelectionComboBox.getSelectedIndex();
                selectedShip = owner.getShips().get(index);
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Simplicity 4X");
        frame.setContentPane(new MainGui().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();
        frame.setVisible(true);
    }
}
