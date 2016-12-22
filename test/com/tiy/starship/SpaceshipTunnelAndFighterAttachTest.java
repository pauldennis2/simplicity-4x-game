package com.tiy.starship;

import com.tiy.IllegalMoveException;
import com.tiy.starsys.SpaceTunnel;
import com.tiy.starsys.StarSystem;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

/**
 * Created by erronius on 12/20/2016.
 */
public class SpaceshipTunnelAndFighterAttachTest {

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    StarSystem startSystem;
    StarSystem endSystem;
    SpaceTunnel tunnel;
    Destroyer destrier;
    Fighter charlie;

    StarSystem uncon1;
    StarSystem uncon2;
    SpaceTunnel unconTunnel;

    @Before
    public void setUp() throws Exception {
        startSystem = new StarSystem("Valefor");
        endSystem = new StarSystem("Ifrit");
        tunnel = new SpaceTunnel(3, startSystem, endSystem);
        destrier = new Destroyer(startSystem);
        charlie = new Fighter(startSystem);

        uncon1 = new StarSystem("Alpha");
        uncon2 = new StarSystem("Beta"); //This is probably unnecessary; we could use null/null instead
        unconTunnel = new SpaceTunnel(2, uncon1, uncon2);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testFighterCannotEnter () throws IllegalMoveException {
        expectedException.expect(IllegalMoveException.class);
        charlie.enterTunnel(tunnel);
    }

    @Test
    public void testStarTunnelEnter () throws IllegalMoveException {
        destrier.enterTunnel(tunnel);
        assertEquals(null, destrier.getCurrentSystem());
        assertEquals(tunnel.getLength(), destrier.getTurnsToDestination());

        expectedException.expect(IllegalMoveException.class);
        destrier.enterTunnel(tunnel);
    }


    @Test
    public void testStarTunnelMovement () throws IllegalMoveException {
        destrier.enterTunnel(tunnel);
        destrier.moveToDestination();
        destrier.moveToDestination();
        assertEquals(1, destrier.getTurnsToDestination());
        destrier.moveToDestination();
        assertEquals(endSystem, destrier.getCurrentSystem());
    }

    @Test
    public void testWrongFunctionFighterAttach () {
        //This function should not be called externally (internal call in fighter.attachTo(Spaceship)
        assertEquals(true, destrier.attach(charlie));
        assertEquals(true, destrier.attach(new Fighter(null)));
        assertEquals(false, destrier.attach(new Fighter(null)));
    }

    @Test
    public void testWrongSystemFighterAttach () throws IllegalMoveException {
        expectedException.expect(IllegalMoveException.class);
        new Fighter(uncon1).attachTo(destrier);
    }

    @Test
    public void testTooManyFightersAttach () throws IllegalMoveException {
        new Fighter(startSystem).attachTo(destrier);
        new Fighter(startSystem).attachTo(destrier);
        expectedException.expect(IllegalMoveException.class);
        new Fighter(startSystem).attachTo(destrier);
    }

    @Test
    public void testStarTunnelWithAttachedFighters () throws IllegalMoveException {
        charlie.attachTo(destrier);
        assertEquals(charlie.getCurrentSystem(), destrier.getCurrentSystem());
        destrier.enterTunnel(tunnel);
        assertEquals(null, charlie.getCurrentSystem());
        destrier.moveToDestination();
        destrier.moveToDestination();
        destrier.moveToDestination();
        assertEquals(endSystem, charlie.getCurrentSystem());
    }

}