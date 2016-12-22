package com.tiy.starship;

import com.tiy.IllegalMoveException;
import com.tiy.cli.StarshipSetup;
import com.tiy.starsys.SpaceTunnel;
import com.tiy.starsys.StarSystem;

import java.util.List;

/**
 * Created by erronius on 12/20/2016.
 */
public class Fighter extends Spaceship {

    Spaceship attachedTo;
    boolean attached;

    public Fighter (StarSystem location) {
        super(location);
    }

    public Fighter (StarSystem location, StarshipSetup setup) {
        super(location);
        this.setWeapons(setup.getWeaponList());
    }

    @Override
    public void enterTunnel (SpaceTunnel tunnel) throws IllegalMoveException {
        throw new IllegalMoveException("Fighters can't enter tunnels");
    }

    public void attachTo (Spaceship spaceship) throws IllegalMoveException {
        int berths = spaceship.fighterBerths;

        if (!this.getCurrentSystem().equals(spaceship.getCurrentSystem())) {
            throw new IllegalMoveException("Cannot attach - not in same system");
        }

        if (berths > spaceship.getAttachedFighters().size()) {
            //Room at the inn
            spaceship.attach(this);
            attachedTo = spaceship;
            attached = true;
        } else {
            throw new IllegalMoveException("Cannot attach; no berths available.");
        }
    }


    @Override
    public StarSystem getCurrentSystem () {
        if (attached) {
            return attachedTo.getCurrentSystem();
        }
        //return this.getCurrentSystem();
        return this.currentSystem;
    }

}
