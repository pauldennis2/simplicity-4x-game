package com.tiy.starship;

/**
 * Created by erronius on 12/20/2016.
 */
public class Project {

    private int requiredProduction;
    private int currentProduction;

    private Starship goal;

    public Project (int requiredProduction, Starship goal) {
        this.requiredProduction = requiredProduction;
        this.goal = goal;
        currentProduction = 0;
    }

    /**
     *
     * @param production to be added
     * @return 0 if project incomplete; surplus production if complete (min 1)
     */
    public int addProduction (int production) {
        if (currentProduction + production > requiredProduction) {
            //make the spaceship
            return (currentProduction + production - requiredProduction); //Return the extra
        } else if ((currentProduction + production) == requiredProduction) {
            //make spaceship
            return 1;
        } else {
            currentProduction += production;
            return 0;
        }
    }

    public int getRequiredProduction () {
        return requiredProduction;
    }
}
