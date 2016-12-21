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

    public Player (StarSystem homeSystem) {
        planets = new ArrayList<>();
        ships = new ArrayList<>();
        shipyard = new Shipyard(homeSystem);

        planets.add(homeSystem.getPlanets().get(0));
        totalResearch = 0;
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
}
