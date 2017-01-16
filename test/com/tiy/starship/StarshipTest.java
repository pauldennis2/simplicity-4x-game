package com.tiy.starship;

import com.tiy.IllegalMoveException;
import com.tiy.ImproperFunctionInputException;
import com.tiy.cli.Player;
import com.tiy.starsys.SpaceTunnel;
import com.tiy.starsys.StarSystem;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

/**
 * Created by erronius on 12/26/2016.
 */
public class StarshipTest {

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    Destroyer doge;
    StarSystem bravos;
    StarSystem kitteh;
    Player owner;

    @Before
    public void setUp() throws Exception {
        kitteh = new StarSystem("Kitteh");
        bravos = new StarSystem("Bravos");
        doge = new Destroyer(bravos, owner);
        owner = new Player(bravos, "Jon Snow");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testTakeDamage () throws ImproperFunctionInputException {
        Fighter f1 = new Fighter(new StarSystem("Beetlejuice"), owner);
        //100 Reserve power, shields absorb 10, shield max health = 30, health = 30
        f1.takeDamage(10); //shield 20, health 30
        assertEquals(30, f1.getHealth());
        f1.takeDamage(15); //shield 10, health 25
        assertEquals(25, f1.getHealth());
        f1.takeDamage(5); //shield 5, health 25
        assertEquals(25, f1.getHealth());
        f1.takeDamage(5); //shield 0, health 25
        //Shields should be emptied out now
        assertEquals (0, f1.getShield().getShieldHealth());
        f1.takeDamage(20);
        assertEquals(5, f1.getHealth());
    }

    @Test
    public void testIllegalSetLocationCall () throws IllegalMoveException {
        expectedException.expect(IllegalMoveException.class);
        doge.setLocation(kitteh);
    }

    @Test
    public void testAnotherIllegalSetLocationCall () throws IllegalMoveException {
        SpaceTunnel tunnel = new SpaceTunnel(3, bravos, kitteh);
        expectedException.expect(IllegalMoveException.class);
        doge.setLocation(tunnel);
    }

    @Test
    public void testTakeDamageManySmallInstances () throws ImproperFunctionInputException {
        Fighter f1 = new Fighter(new StarSystem("Beetlejuice"), owner);
        for (int i = 0; i < 25; i++) {
            f1.takeDamage(1);
        }
        assertEquals(5, f1.getShield().getShieldHealth());
        assertEquals(30, f1.getHealth());
    }
}