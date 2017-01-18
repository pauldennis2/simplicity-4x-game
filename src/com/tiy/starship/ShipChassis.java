package com.tiy.starship;

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

    public int getSmallWeaponSlots() {
        return smallWeaponSlots;
    }

    public int getLargeWeaponSlots() {
        return largeWeaponSlots;
    }

    public int getFighterBerths() {
        return fighterBerths;
    }

    public int getUpgradeSlots() {
        return upgradeSlots;
    }

    public int getFighterSlots() {
        return fighterSlots;
    }

    public int getHealth() {
        return health;
    }

    public int getShieldStrength() {
        return shieldStrength;
    }

    public static ShipChassis getShipChassis (String name) {
        name = name.toLowerCase();
        switch (name) {
            case "fighter":
                return FIGHTER;
            case "destroyer":
                return DESTROYER;
            /*case "cruiser":
                return CRUISER;
            case "battleship":
                return BATTLESHIP;
            case "capitol ship":
                return CAPITOL_SHIP;*/
            default:
                return null;
        }
    }
}
