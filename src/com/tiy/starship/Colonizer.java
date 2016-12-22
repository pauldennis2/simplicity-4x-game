package com.tiy.starship;

import com.tiy.IllegalMoveException;
import com.tiy.cli.Player;
import com.tiy.starsys.Planet;
import com.tiy.starsys.StarSystem;

/**
 * Created by erronius on 12/21/2016.
 */
public class Colonizer extends Spaceship {

    public static final int POPULATION = 2;
    public Player owner;

    public Colonizer (StarSystem system, Player owner) {
        super(system);
        this.owner = owner;
        this.fighterBerths = 1;
    }

    public void createColony (Planet planet) throws IllegalMoveException {
        if (!planet.getSystem().equals(this.currentSystem)) {
            throw new IllegalMoveException("Cannot colonize planet. Not in system.");
        }
        if (!planet.getOwner().equals("none")) {
            throw new IllegalMoveException("Cannot colonize planet. Already owned.");
        }
        //check for habitability, etc
        planet.setOwner(owner.getName());
        planet.setPopulation(POPULATION);
        owner.removeShip(this);
    }
}
