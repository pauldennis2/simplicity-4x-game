package com.tiy.starship;

import com.tiy.cli.Player;
import com.tiy.starsys.StarSystem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by erronius on 12/20/2016.
 */
public class ShipyardTest {

    StarSystem homeSystem;
    Player owner;
    Shipyard shipyard;

    @Before
    public void setUp() throws Exception {
        homeSystem = new StarSystem("Maryland");
        owner = new Player(homeSystem, "Jane Crosson");
        shipyard = new Shipyard(homeSystem, owner);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testBasicFakeProductionHandling () {
        //Tests the math associated with projects
        Fighter project = new Fighter(shipyard, owner);
        shipyard.addProject(new Project(25, project, shipyard));
        shipyard.addProductionToCurrentProject(30);
        assertEquals(5, shipyard.getSurplusProduction());

        assertEquals(null, shipyard.getCurrentProject());

        /* These tests are out of date. Can't have null projects anymore.
        shipyard.addProject(new Project(10, null, shipyard));
        shipyard.addProject(new Project(50, null, shipyard));
        shipyard.addProductionToCurrentProject(20);
        assertEquals(5, shipyard.getSurplusProduction());
        shipyard.addProductionToCurrentProject(40);
        assertEquals(15, shipyard.getSurplusProduction());
        assertEquals(10, shipyard.getCurrentProject().getRequiredProduction());*/
    }

    @Test
    public void testBasicActualProduction () {
        //TODO add production queue tests
        Fighter fighter = new Fighter(shipyard, owner);
        shipyard.addProject(new Project(25, fighter, shipyard));
        shipyard.addProductionToCurrentProject(20);
        assertEquals(shipyard, fighter.getLocation());
        shipyard.addProductionToCurrentProject(5);
        assertEquals(homeSystem, fighter.getLocation());
    }

}