package com.tiy.starship;

import com.tiy.IllegalMoveException;
import com.tiy.cli.Player;
import com.tiy.starsys.Location;
import com.tiy.starsys.Planet;
import com.tiy.starsys.StarSystem;

/**
 * Created by erronius on 12/21/2016.
 */
public class Colonizer extends Starship {

    public static final int POPULATION = 2;

    public Colonizer (Location location, Player owner) {
        super(location, owner);
        this.fighterBerths = 1;
    }

    public void createColony (Planet planet) throws IllegalMoveException {
        try {
            StarSystem sys = (StarSystem) location;
        } catch (ClassCastException ex) {
            throw new IllegalMoveException("Cannot colonize planet. Not in a system.");
        }
        if (!planet.getSystem().equals((StarSystem)location)) {
            throw new IllegalMoveException("Cannot colonize planet. Not in system.");
        }
        if (!(planet.getOwner() == null)) {
            throw new IllegalMoveException("Cannot colonize planet. Already owned.");
        }
        //check for habitability, etc
        planet.setOwner(owner);
        planet.setPopulation(POPULATION);
        owner.addPlanet(planet);
        owner.removeShip(this);
    }
}
