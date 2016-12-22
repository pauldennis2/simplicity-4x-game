package com.tiy.cli;

import com.tiy.starship.Shipyard;
import com.tiy.starship.Spaceship;
import com.tiy.starsys.Planet;
import com.tiy.starsys.SpaceTunnel;
import com.tiy.starsys.StarSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erronius on 12/20/2016.
 */
public class Player {
    List<Planet> planets;
    List<Spaceship> ships;
    Shipyard shipyard;

    int totalResearch;

    String name;

    public Player (StarSystem homeSystem, String name) {
        planets = new ArrayList<>();
        ships = new ArrayList<>();
        shipyard = new Shipyard(homeSystem);

        planets.add(homeSystem.getPlanets().get(0));//BAD (we shoudln't be assuming this. works for now) todo fix
        totalResearch = 0;
        this.name = name;
    }

    public void getResearchAndProductionFromPlanets () {
        int production = 0;
        int research = 0;
        for (Planet planet : planets) {
            production += planet.getProduction();
            research += planet.getResearch();
        }
        totalResearch += research;
        shipyard.addProductionToCurrentProject(production);
    }

    public void moveShips () {

    }

    public String getName () {
        return name;
    }

    public void removeShip (Spaceship ship) {
        ships.remove(ship);
    }
}
