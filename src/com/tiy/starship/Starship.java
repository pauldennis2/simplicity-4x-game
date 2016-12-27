package com.tiy.starship;

import com.tiy.IllegalMoveException;
import com.tiy.cli.Player;
import com.tiy.starsys.SpaceTunnel;
import com.tiy.starsys.StarSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erronius on 12/20/2016.
 */
public abstract class Starship {

    private String name;

    Shield shield;
    Generator generator;

    int fighterBerths;
    private List<Weapon> weapons;

    int health;

    boolean isDestroyed; //This will be replaced later by logic

    Player owner;


    private List<Fighter> attachedFighters;
    private StarSystem immediateDestination;
    private StarSystem finalDestination; //for later
    private int turnsToDestination;

    StarSystem currentSystem;

    private boolean inTunnel;

    public Starship(StarSystem location) {
        this.currentSystem = location;
        attachedFighters = new ArrayList<>();
        isDestroyed = false;
    }

    public void startTurn () {
        generator.generatePower();
        int availablePower = generator.getAvailablePower();
        if (shield.shieldsUp()) {
            //Check to make sure we have enough power to run shields
            if (availablePower < shield.getMaxDamageAbsorb()) {
                shield.lowerShields();
            } else {
                //Pay for running shields
                availablePower -= shield.getMaxDamageAbsorb();
            }
        }
        int powerUsedToRegen = shield.regenerate(availablePower);
        availablePower -= powerUsedToRegen;
        generator.returnUnusedPower(availablePower);
    }

    public static Starship buildSpaceShip () {
        return null;
    }

    public boolean isInTunnel () {
        return inTunnel;
    }

    public void enterTunnel (SpaceTunnel tunnel) throws IllegalMoveException {
        if (inTunnel) {
            throw new IllegalMoveException("Cannot enter a tunnel, already in one");
        }
        inTunnel = true;
        turnsToDestination = tunnel.getLength();
        immediateDestination = tunnel.getOtherSystem(currentSystem);
        if (!tunnel.getOtherSystem(immediateDestination).equals(currentSystem)) {
            throw new IllegalMoveException("This tunnel is not connected to the current system");
        }
        currentSystem = null;
    }

    public boolean attach (Fighter fighter) {
        //This should have already been checked
        if (attachedFighters.size() < fighterBerths) {
            attachedFighters.add(fighter);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Fire as many weapons as possible, returning the total damage to apply to the enemy ship. Won't work for later
     * since we'll need to know the type of each attack (to see if the enemy ship can dodge it for example).
     * @return total weapon damage
     */
    public int fireAllWeapons () {
        int availablePower = generator.getAvailablePower();
        int totalDamage = 0;
        for (Weapon weapon : weapons) {
            if (weapon.getWeaponClass() == WeaponClass.BEAM) {
                //requires power
                int weaponPower = weapon.getFiredWeaponDamage();
                if (availablePower > weaponPower) {
                    totalDamage += weaponPower;
                    availablePower -= weaponPower;
                }
            }
            if (weapon.getWeaponClass() == WeaponClass.MISSILE) {
                //no power required, just ammo (checked in Weapon class)
                totalDamage += weapon.getFiredWeaponDamage();
            }
        }
        generator.returnUnusedPower(availablePower);
        return totalDamage;
    }

    public StarSystem getImmediateDestination() {
        return immediateDestination;
    }

    public StarSystem getFinalDestination() {
        return finalDestination;
    }

    public int getTurnsToDestination() {
        return turnsToDestination;
    }

    public StarSystem getCurrentSystem() {
        return currentSystem;
    }

    public void moveToDestination () {
        if (inTunnel) {
            turnsToDestination--;
            if (turnsToDestination == 0) {
                //pop out
                inTunnel = false;
                currentSystem = immediateDestination;
            }
        } else {
            System.out.println("Not in a tunnel. Not moving.");
        }
    }

    public List<Fighter> getAttachedFighters () {
        return attachedFighters;
    }

    public void setWeapons (List<Weapon> weaponList) {
        this.weapons = weaponList;
    }

    /**
     * Possibly temporary. Written for testing
     */
    public void drainPower () {
        generator.getAvailablePower();
    }

    public void takeDamage (int damage) {
        if (shield.shieldsUp()) {
            int maxDamageAbsorb = shield.getMaxDamageAbsorb();
            int powerAvailable = generator.getAvailablePower();

            if (powerAvailable >= maxDamageAbsorb) {
                int returnedDamage = shield.takeDamage(damage);
                health -= returnedDamage;
                powerAvailable -= maxDamageAbsorb;
                generator.returnUnusedPower(powerAvailable);
            } else { //powerAvailable < maxDamageAbsorb
                int returnedDamage = shield.takeDamage(powerAvailable);
                health -= returnedDamage;
                //powerAvailable = 0;
            }
        } else {
            health -= damage;
        }
        if (health <= 0) {
            isDestroyed = true;
        }
    }

    public int getHealth () {
        return health;
    }

    public boolean isDestroyed () {
        return isDestroyed;
    }

    public void setOwner (Player owner) {
        this.owner = owner;
    }

    public Shield getShield () {
        return shield;
    }

}
