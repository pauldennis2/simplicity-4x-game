package com.tiy.starship;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by erronius on 12/22/2016.
 */
public class ShieldTest {

    Shield shield;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testBasicFighterShield () {
        shield = Shield.getTemplateShield(ShipChassis.FIGHTER);
        int damageReturnedToShip = shield.takeDamage(15);
        assertEquals(5, damageReturnedToShip);
        assertEquals(30-10, shield.getShieldHealth());
    }

    @Test
    public void testBasicShieldRegen () {
        shield = Shield.getTemplateShield(ShipChassis.DESTROYER);
        shield.takeDamage(20);
        int powerUsed = shield.regenerate(10);
        assertEquals(1, powerUsed);
        assertEquals(100-12+1, shield.getShieldHealth());
    }

    @Test
    public void testInsufficientPowerShieldRegen () {
        shield = new Shield(ShieldType.BASIC, 20, 50, 5);
        shield.takeDamage(20);
        int powerUsed = shield.regenerate(2);
        assertEquals (2, powerUsed);
        assertEquals(50-20+2, shield.getShieldHealth());
    }

}