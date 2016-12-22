package com.tiy.starship;

/**
 * Created by erronius on 12/20/2016.
 */

//Weapon will probably be abstract later. for now we just have 2 possibilities
public /*abstract*/ class Weapon extends ShipTech {

    private int damage;
    private WeaponClass type;
    private int shots;

    public static final int DEFAULT_NUM_MISSILES = 3;

    public Weapon (int damage, WeaponClass type, boolean large) {
        this.damage = damage;
        this.type = type;
        if (type == WeaponClass.BEAM) {
            shots = -1;
        }
        if (type == WeaponClass.MISSILE) {
            shots = 3;
        }
    }

    //Note: this is NOT an accessor method. It actually "fires a shot" returning the
    //damage to be applied to the enemy ship
    public int getFiredWeaponDamage () {
        if (shots == -1) {
            //Beam weapon, simply return damage
            return damage;
        }
        if (shots > 0) {
            shots--;
            return damage;
        }
        return 0;
    }
}
