package com.tiy.cli;

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
        System.out.println("Welcome to Simplicity 4x");
        StarSystemGraph simpleSystemGraph = new StarSystemGraph();
        System.out.println(simpleSystemGraph);
        for (StarSystem starSystem : simpleSystemGraph.getStarSystems()) {
            System.out.println(starSystem);
        }
    }
}
