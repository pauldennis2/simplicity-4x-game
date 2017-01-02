package com.tiy.gui;

import com.tiy.ImproperFunctionInputException;
import com.tiy.cli.Player;
import com.tiy.starship.Fighter;
import com.tiy.starship.Generator;
import com.tiy.starship.Shield;
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
    private JRadioButton radioButton1;
    private JRadioButton radioButton2;
    Fighter fighter;

    public MainGui() {
        StarSystem betelgeuse = new StarSystem("Beetlejuice");
        Player owner = new Player(betelgeuse, "Paul");
        fighter = new Fighter(betelgeuse, owner);

        shipHealthProgressBar.setForeground(Color.green);
        shipShieldProgressBar.setForeground(Color.cyan);
        shipGeneratorProgressBar.setForeground(Color.yellow);

        shipHealthProgressBar.setMaximum(fighter.getMaxHealth());
        shipShieldProgressBar.setMaximum(fighter.getShield().getMaxShieldHealth());
        shipGeneratorProgressBar.setMaximum(fighter.getGenerator().getMaxReservePower());

        /*shipHealthProgressBar.setStringPainted(true);
        shipShieldProgressBar.setStringPainted(true);
        shipGeneratorProgressBar.setStringPainted(true);*/

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
                int damage;
                try {
                    damage = Integer.parseInt(zapAmountTextField.getText());
                } catch (NumberFormatException ex) {
                    damage = 10;
                }
                try {
                    fighter.takeDamage(damage);
                } catch (ImproperFunctionInputException ex) {
                    ex.printStackTrace();
                }

                shipHealthProgressBar.setValue(fighter.getHealth());
                shipHealthProgressBar.setString(fighter.getHealth() + "/" + fighter.getMaxHealth());

                Shield shield = fighter.getShield();
                shipShieldProgressBar.setValue(shield.getShieldHealth());
                shipShieldProgressBar.setString(shield.getShieldHealth() + "/" + shield.getMaxShieldHealth());
                //System.out.println(shield);

                Generator generator = fighter.getGenerator();
                shipGeneratorProgressBar.setValue(generator.getCurrentReservePower());
                shipGeneratorProgressBar.setString(generator.getCurrentReservePower() + "/" + generator.getMaxReservePower());

                shipStatusLabel.setText(fighter.toString());
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
