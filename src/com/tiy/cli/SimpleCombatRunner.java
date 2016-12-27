package com.tiy.cli;

import com.tiy.starship.Starship;

import java.util.List;

/**
 * Created by erronius on 12/22/2016.
 */
public class SimpleCombatRunner {
    private List<Starship> firstFleet; //larger fleet
    private List<Starship> secondFleet;

    boolean firstFleetFirst;

    SimpleCombatResult results;

    public SimpleCombatRunner (List<Starship> firstFleet, List<Starship> secondFleet) {
        this.firstFleet = firstFleet;
        this.secondFleet = secondFleet;
        results = new SimpleCombatResult();
    }

    public SimpleCombatResult doCombat () {
        int index = 0;
        for (; index < secondFleet.size(); index++) {

        }

        return null;
        //todo fix
    }
    /*
    if (firstFleet.size() == secondFleet.size()) {
        Random coinFlip = new Random();
        if (coinFlip.nextBoolean()) {
            firstFleetFirst = true;
        } else {
            firstFleetFirst = false;
        }
    } else if (firstFleet.size() > secondFleet.size()) {
        firstFleetFirst = true;
    } else {
        firstFleetFirst = false;
    }*/
}
