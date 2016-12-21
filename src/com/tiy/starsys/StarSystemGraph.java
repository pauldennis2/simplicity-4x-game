package com.tiy.starsys;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erronius on 12/20/2016.
 */
public class StarSystemGraph {

    List<StarSystem> starSystems;

    //Create a super simple graph, A <=> B <=> C
    public StarSystemGraph () {
        starSystems = new ArrayList<StarSystem>();
        StarSystem homeSystem = new StarSystem("Home");
        StarSystem emptySystem = new StarSystem("Alpha Centauri");
        StarSystem scarySystem = new StarSystem("ScaryTown");
        starSystems.add(homeSystem);
        starSystems.add(emptySystem);
        starSystems.add(scarySystem);
        SpaceTunnel homeToEmpty = new SpaceTunnel(2, homeSystem, emptySystem);
        SpaceTunnel emptyToScary = new SpaceTunnel(3, emptySystem, scarySystem);

        homeSystem.addTunnel(homeToEmpty);
        emptySystem.addTunnel(homeToEmpty);
        emptySystem.addTunnel(emptyToScary);
        scarySystem.addTunnel(emptyToScary);

        Planet homePlanet = homeSystem.getPlanets().get(0);
        homePlanet.setOwner("Player");
        homePlanet.setPopulation(4);

    }

    public String toString () {
        //This assumes a simple simple graph A <=> B <=> C
        StringBuilder response = new StringBuilder("");
        List<SpaceTunnel> paintedTunnels = new ArrayList<>();
        for (StarSystem starSystem : starSystems) {
            response.append(starSystem.getName());
            for (SpaceTunnel tunnel : starSystem.getTunnels()) {
                if (!paintedTunnels.contains(tunnel)) { //if we haven't drawn it yet
                    paintedTunnels.add(tunnel); //Add it to the list of drawn tunnels
                    int length = tunnel.getLength();
                    response.append("<");
                    for (int index = 0; index < length; index++) {
                        response.append("=");
                    }
                    response.append(">");
                }
            }
        }
        return response.toString();
    }

    public List<StarSystem> getStarSystems () {
        return starSystems;
    }

    private boolean isConnected () {
        //Logic to search through a proposed system and ensure it is connected.
        //Suggestion: start at a given node, and start adding to a SET (not a list) all nodes we can find connected to it
        //Then compare that to the overall set of systems; if they match, it's connected
        return false;
    }
}
