package com.tiy.starship;

import com.tiy.IllegalMoveException;
import com.tiy.starsys.SpaceTunnel;
import com.tiy.starsys.StarSystem;

/**
 * Created by erronius on 12/20/2016.
 */
public class Fighter extends Spaceship {

    Spaceship attachedTo;
    boolean attached;

    public Fighter (StarSystem location) {
        super(location);
    }

    @Override
    public void enterTunnel (SpaceTunnel tunnel) throws IllegalMoveException {
        throw new IllegalMoveException("Fighters can't enter tunnels");
    }

    public void attachTo (Spaceship spaceship) {
        int berths = spaceship.fighterBerths;
        if (berths > spaceship.getAttachedFighters().size()) {
            //Room at the inn
        }
    }


    @Override
    public StarSystem getCurrentSystem () {
        if (attached) {
            return attachedTo.getCurrentSystem();
        }
        return this.getCurrentSystem();
    }

}
