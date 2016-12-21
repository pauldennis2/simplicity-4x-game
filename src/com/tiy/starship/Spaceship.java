package com.tiy.starship;

import com.tiy.IllegalMoveException;
import com.tiy.starsys.SpaceTunnel;
import com.tiy.starsys.StarSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erronius on 12/20/2016.
 */
public abstract class Spaceship {

    private String name;
    private int maxShieldStrength;
    private int curShieldStrength;
    private int maxPower;
    private int curPower;

    int fighterBerths;
    private List<Weapon> weapons;


    private List<Fighter> attachedFighters;
    private StarSystem immediateDestination;
    private StarSystem finalDestination; //for later
    private int turnsToDestination;
    private StarSystem currentSystem;

    private boolean inTunnel;

    public Spaceship (StarSystem location) {
        this.currentSystem = location;
        attachedFighters = new ArrayList<>();
    }

    public static Spaceship buildSpaceShip () {
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

}
