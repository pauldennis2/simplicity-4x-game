package com.tiy.starsys;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by erronius on 12/20/2016.
 */
public class StarSystem extends Location {

    String name;
    List<Planet> planets;
    List<SpaceTunnel> tunnels;

    private boolean discoveredByPlayer = false;
    private boolean[] discoveredByPlayers;

    public static final String[] ROMAN_NUMERALS = {"I", "II", "III", "IV", "V"};

    public static final int DEFAULT_PLANET_SIZE = 10;

    //Create one habitable planet of default size
    public StarSystem (String name) {
        this.name = name;
        planets = new ArrayList<>();
        tunnels = new ArrayList<>();
        planets.add(new Planet(name + " " + ROMAN_NUMERALS[0], DEFAULT_PLANET_SIZE, true, this));
    }

    public StarSystem (String name, Random random) {
        this.name = name;
        planets = new ArrayList<>();
        tunnels = new ArrayList<>();
        //decide how many planets to create, 0-4
        int numPlanets = random.nextInt(5);
        for (int index = 0; index < numPlanets; index++) {
            boolean habitable = random.nextBoolean();
            int size = random.nextInt(3);
            planets.add(new Planet(name + " " + ROMAN_NUMERALS[index], DEFAULT_PLANET_SIZE + size, habitable, this));
        }
    }

    public void addTunnel (SpaceTunnel tunnel) {
        tunnels.add(tunnel);
    }

    public String getName () {
        System.out.println("Returning name");
        return name;
    }

    public List<SpaceTunnel> getTunnels () {
        return tunnels;
    }

    @Override
    public String toString() {
        String response = name + "\nPlanets:\n";
        for (Planet planet : planets) {
            response += planet.toString() + "\n";
        }
        response += "Tunnels:\n";
        for (SpaceTunnel tunnel : tunnels) {
            response += tunnel.getLength() + " days to " + tunnel.getOtherSystem(this).getName() + "\n";
        }
        return response;
    }

    public List<Planet> getPlanets () {
        return planets;
    }

}
