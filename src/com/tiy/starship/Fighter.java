package com.tiy.starship;

import com.tiy.IllegalMoveException;
import com.tiy.cli.StarshipSetup;
import com.tiy.starsys.SpaceTunnel;
import com.tiy.starsys.StarSystem;

/**
 * Created by erronius on 12/20/2016.
 */
public class Fighter extends Starship {

    Starship attachedTo;
    boolean attached;

    public Fighter (StarSystem location) {
        super(location);
        shield = Shield.getTemplateShield(ShipChassis.FIGHTER);
        generator = Generator.getTemplateGenerator(ShipChassis.FIGHTER);
        health = 30;
    }

    public Fighter (StarSystem location, StarshipSetup setup) {
        super(location);
        this.setWeapons(setup.getWeaponList());
        shield = Shield.getTemplateShield(ShipChassis.FIGHTER);
        generator = Generator.getTemplateGenerator(ShipChassis.FIGHTER);
        health = 30;
    }

    @Override
    public void enterTunnel (SpaceTunnel tunnel) throws IllegalMoveException {
        throw new IllegalMoveException("Fighters can't enter tunnels");
    }

    public void attachTo (Starship starship) throws IllegalMoveException {
        int berths = starship.fighterBerths;

        if (!this.getCurrentSystem().equals(starship.getCurrentSystem())) {
            throw new IllegalMoveException("Cannot attach - not in same system");
        }

        if (berths > starship.getAttachedFighters().size()) {
            //Room at the inn
            starship.attach(this);
            attachedTo = starship;
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
        return this.currentSystem;
    }

}
