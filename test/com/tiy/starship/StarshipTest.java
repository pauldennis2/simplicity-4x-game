package com.tiy.starship;

import com.tiy.starsys.StarSystem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by erronius on 12/26/2016.
 */
public class StarshipTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testTakeDamage () {
        Fighter f1 = new Fighter(new StarSystem("Beetlejuice"));
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
}