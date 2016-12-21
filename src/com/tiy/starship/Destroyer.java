package com.tiy.starship;

import com.tiy.starsys.StarSystem;

/**
 * Created by erronius on 12/20/2016.
 */
public class Destroyer extends Spaceship {

    public Destroyer (StarSystem location) {
        super(location);
        this.fighterBerths = 2;
    }
}
