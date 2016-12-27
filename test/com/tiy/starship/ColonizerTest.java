package com.tiy.starship;

import com.tiy.IllegalMoveException;
import com.tiy.cli.Player;
import com.tiy.starsys.SpaceTunnel;
import com.tiy.starsys.StarSystem;
import org.junit.After;
import org.junit.Before;
import com.tiy.starsys.Planet;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

/**
 * Created by erronius on 12/21/2016.
 */
public class ColonizerTest {

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    Colonizer colonizer;
    Planet planetToColonize;
    Player player;
    StarSystem systemToColonize;

    StarSystem homeSystem;



    @Before
    public void setUp() throws Exception {
        systemToColonize = new StarSystem("Victoria");
        homeSystem = new StarSystem("Baltimore");
        player = new Player(homeSystem, "Paul");
        planetToColonize = new Planet("Victoria I", 10, true, systemToColonize);
        //colonizer = new Colonizer();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testBasicColonization() throws IllegalMoveException {
        colonizer = new Colonizer(systemToColonize, player);
        colonizer.createColony(planetToColonize);
        assertEquals(player, planetToColonize.getOwner());
        assertEquals(Colonizer.POPULATION, planetToColonize.getPopulation());
        //add test to make sure that the colonizer has been used up

    }

    @Test
    public void testNotInSystemColonization() throws IllegalMoveException {
        colonizer = new Colonizer(homeSystem, player);
        expectedException.expect(IllegalMoveException.class);
        colonizer.createColony(planetToColonize);
    }

    @Test
    public void testGotoSystemAndColonize() throws IllegalMoveException {
        colonizer = new Colonizer(homeSystem, player);
        SpaceTunnel tunnel = new SpaceTunnel(2, homeSystem, systemToColonize);
        colonizer.enterTunnel(tunnel);
        colonizer.moveToDestination();
        colonizer.moveToDestination();
        colonizer.createColony(planetToColonize);
        assertEquals(player, planetToColonize.getOwner());
    }
}