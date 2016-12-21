package com.tiy.starship;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by erronius on 12/20/2016.
 */
public class ShipyardTest {

    Shipyard shipyard;
    @Before
    public void setUp() throws Exception {
        shipyard = new Shipyard(null);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testBasicProductionHandling () {
        shipyard.addProjectTopPrio(new Project(25, null));
        shipyard.addProductionToCurrentProject(30);
        assertEquals(5, shipyard.getSurplusProduction());

        assertEquals(null, shipyard.getCurrentProject());

        shipyard.addProjectTopPrio(new Project(10, null));
        shipyard.addProjectTopPrio(new Project(50, null));
        shipyard.addProductionToCurrentProject(20);
        assertEquals(5, shipyard.getSurplusProduction());
        shipyard.addProductionToCurrentProject(40);
        assertEquals(15, shipyard.getSurplusProduction());
        assertEquals(10, shipyard.getCurrentProject().getRequiredProduction());
    }
}