package com.tiy.starship;

import com.tiy.IllegalMoveException;
import com.tiy.cli.Player;

/**
 * Created by erronius on 12/20/2016.
 */
public class Project {

    private int requiredProduction;
    private int currentProduction;

    private Starship goal;
    private Shipyard shipyard;

    public Project (int requiredProduction, Starship goal, Shipyard shipyard) {
        this.requiredProduction = requiredProduction;
        this.goal = goal;
        this.shipyard = shipyard;
        currentProduction = 0;
    }

    /**
     *
     * @param production to be added
     * @return 0 if project incomplete; surplus production if complete (min 1)
     */
    public int addProduction (int production) {
        if (currentProduction + production >= requiredProduction) {
            //make the spaceship
            shipyard.getOwner().addShip(goal);
            try {
                goal.setLocation(shipyard.getSystem());
            } catch (IllegalMoveException ex) {
                ex.printStackTrace();
            }
            return (currentProduction + production - requiredProduction); //Return the extra
        } else if ((currentProduction + production) == requiredProduction) {
            //make spaceship
            shipyard.getOwner().addShip(goal);
            return 1;
        } else {
            currentProduction += production;
            return 0;
        }
    }

    public int getRequiredProduction () {
        return requiredProduction;
    }

    @Override
    public String toString () {
        String longClass = goal.getClass().toString();
        //String[] shipClass = goal.getClass().toString().split(".");
        //String shortenedClass = shipClass[shipClass.length-1];
        return goal + " " + longClass + " " + currentProduction + "/" + requiredProduction;
    }
}
