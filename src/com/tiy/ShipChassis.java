package com.tiy;

/**
 * Created by erronius on 12/20/2016.
 */
public enum ShipChassis {
    //ShipChassis Constructor:
    //smallWeap, largeWeap, fighterBerths, upgrades, fighterSlots, health, shieldStr
    FIGHTER (2, 0, 0, 0, 0, 25, 5),
    DESTROYER (2, 1, 2, 0, 0, 100, 10);
    //CRUISER (data),
    //BATTLESHIP (data),
    //CAPITOL_SHIP (data);

    private final int smallWeaponSlots;
    private final int largeWeaponSlots;
    private final int fighterBerths;
    private final int upgradeSlots;
    private final int fighterSlots;
    private final int health;
    private final int shieldStrength;

    ShipChassis(int smallWeaponSlots, int largeWeaponSlots, int fighterBerths, int upgradeSlots, int fighterSlots,
                int health, int shieldStrength) {
        this.smallWeaponSlots = smallWeaponSlots;
        this.largeWeaponSlots = largeWeaponSlots;
        this.fighterBerths = fighterBerths;
        this.upgradeSlots = upgradeSlots;
        this.fighterSlots = fighterSlots;
        this.health = health;
        this.shieldStrength = shieldStrength;
    }
    //getters
}
