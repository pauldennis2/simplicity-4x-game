package com.tiy.cli;

import com.tiy.starship.Shipyard;
import com.tiy.starship.Starship;
import com.tiy.starsys.Planet;
import com.tiy.starsys.StarSystem;
import com.tiy.starsys.Technology;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erronius on 12/20/2016.
 */
public class Player {
    List<Planet> planets;
    List<Starship> ships;
    Shipyard shipyard;

    int totalResearch;

    private Technology tech;

    String name;

    private StarSystem homeSystem;

    public Player (StarSystem homeSystem, String name) {
        planets = new ArrayList<>();
        ships = new ArrayList<>();
        shipyard = new Shipyard(homeSystem, this);

        planets.add(homeSystem.getPlanets().get(0));//BAD (we shoudln't be assuming this. works for now) todo fix
        totalResearch = 0;
        this.name = name;
        this.homeSystem = homeSystem;
        tech = Technology.HEPHAESTUS_I;
    }

    public int getCurrentProductionPerTurn () {
        System.out.println("getCurrentProductionPerTurn() returning a hard-coded value");
        return 5;
    }

    public void addShip (Starship starship) {
        ships.add(starship);
    }

    public void addPlanet (Planet planet) {
        if (planet.getOwner().equals(this)) {
            planets.add(planet);
        } else {
            throw new AssertionError("Attempted to add a planet I don't own.");
        }
    }

    public void getResearchAndProductionFromPlanets () {
        int production = 0;
        int research = 0;
        for (Planet planet : planets) {
            production += planet.getProduction();
            research += planet.getResearch();
        }
        System.out.println("Adding " + research + " research to pool.");
        totalResearch += research;
        if (totalResearch > tech.getResearchCost()) {
            tech.setComplete(true);
        }
        System.out.println("Adding " + production + " production to " + shipyard.getName() + " Shipyard.");
        shipyard.addProductionToCurrentProject(production);
    }

    public void moveShips () {
        for (Starship starship : ships) {
            starship.moveToDestination();
        }
    }

    public String getName () {
        return name;
    }

    public void removeShip (Starship ship) {
        ships.remove(ship);
    }

    public StarSystem getHomeSystem () {
        return homeSystem;
    }

    public List<Planet> getPlanets () {
        return planets;
    }

    public List<Starship> getShips () {
        return ships;
    }

    public Technology getTech () {
        return tech;
    }

    public Shipyard getShipyard () {
        return shipyard;
    }

    public List<String> getAvailableSmallWeaps () {
        List<String> smallWeaps = new ArrayList<>();
        smallWeaps.add("Small Beam");
        smallWeaps.add("Small Missile");
        return smallWeaps;
    }

    public List<String> getAvailableLargeWeaps () {
        List<String> largeWeaps = new ArrayList<>();
        largeWeaps.add("Large Beam");
        largeWeaps.add("Large Missile");
        return largeWeaps;
    }
}
