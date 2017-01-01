package com.tiy.starship;

import com.tiy.cli.Player;
import com.tiy.cli.StarshipSetup;
import com.tiy.starsys.StarSystem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by erronius on 12/26/2016.
 */
public class StarshipWeaponTest {

    Destroyer dest = new Destroyer(new StarSystem("Lindblum"), null, new StarshipSetup(ShipChassis.DESTROYER));
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testBasicWeaponFiring () {
        int damage = dest.fireAllWeapons();
        assertEquals(35 + 30 + 15, damage);

        //Test rockets running out
        dest.fireAllWeapons();
        dest.fireAllWeapons();

        damage = dest.fireAllWeapons();
        assertEquals(35 + 15, damage);
    }

    @Test
    public void testPowerShortage () {
        Fighter fighter = new Fighter(new StarSystem("Lindblum"), null);
        Weapon expensiveWeapon = new Weapon(60, WeaponClass.BEAM, false);
        List<Weapon> weaponList = new ArrayList<Weapon>();
        weaponList.add(expensiveWeapon);
        fighter.setWeapons(weaponList);

        //Should be able to fire it twice
        int damage = fighter.fireAllWeapons();
        assertEquals(60, damage);
        fighter.fireAllWeapons();
        damage = fighter.fireAllWeapons();
        assertEquals(0, damage);
    }
}