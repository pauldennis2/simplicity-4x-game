package com.tiy.starship;

import com.tiy.cli.StarshipSetup;
import com.tiy.starsys.StarSystem;

/**
 * Created by erronius on 12/20/2016.
 */
public class Destroyer extends Starship {

    public Destroyer (StarSystem location) {
        super(location);
        this.fighterBerths = 2;
        shield = Shield.getTemplateShield(ShipChassis.DESTROYER);
        generator = Generator.getTemplateGenerator(ShipChassis.DESTROYER);
        health = 100;
    }

    public Destroyer (StarSystem location, StarshipSetup setup) {
        super(location);
        this.fighterBerths = 2;
        this.setWeapons(setup.getWeaponList());
        shield = Shield.getTemplateShield(ShipChassis.DESTROYER);
        generator = Generator.getTemplateGenerator(ShipChassis.DESTROYER);
        health = 100;
    }
}
