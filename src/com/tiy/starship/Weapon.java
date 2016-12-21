package com.tiy.starship;

/**
 * Created by erronius on 12/20/2016.
 */

//Weapon will probably be abstract later. for now we just have 2 possibilities
public /*abstract*/ class Weapon extends ShipTech {

    private int damage;
    private WeaponType type;

    public Weapon (int damage, boolean large) {

    }
}
