package com.tiy.cli;

import com.tiy.starship.Fighter;
import com.tiy.starship.ShipChassis;
import com.tiy.starsys.StarSystem;
import com.tiy.starsys.StarSystemGraph;

/**
 * Created by erronius on 12/20/2016.
 */
public class CLIRunner {

    public static void main(String[] args) {
        new CLIRunner().sandbox();
    }

    public void sandbox () {
        System.out.println("Welcome to Simplicity 4x Sandbox");
        StarSystemGraph simpleSystemGraph = new StarSystemGraph();
        System.out.println(simpleSystemGraph);
        for (StarSystem starSystem : simpleSystemGraph.getStarSystems()) {
            System.out.println(starSystem);
        }
    }

    public void simpleGame (){
        System.out.println("Welcome to Simplicity 4x Simple Game");
        StarSystemGraph simpleSystemGraph = new StarSystemGraph();
        StarSystem homeSystem = simpleSystemGraph.getStarSystems().get(0);

        Player tester = new Player(homeSystem, "Abelard");
        homeSystem.getPlanets().get(0).setOwner(tester);
        Fighter startingFighter = new Fighter(homeSystem, new StarshipSetup(ShipChassis.FIGHTER));
        startingFighter.setOwner(tester);
        tester.addShip(startingFighter);

        System.out.println(simpleSystemGraph);

    }
}
