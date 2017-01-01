package com.tiy.starship;

import com.tiy.cli.Player;
import com.tiy.cli.StarshipSetup;
import com.tiy.starsys.Location;

/**
 * Created by erronius on 12/20/2016.
 */
public class Destroyer extends Starship {

    public Destroyer (Location location, Player owner) {
        super(location, owner);
        this.fighterBerths = 2;
        shield = Shield.getTemplateShield(ShipChassis.DESTROYER);
        generator = Generator.getTemplateGenerator(ShipChassis.DESTROYER);
        health = 100;
    }

    public Destroyer (Location location, Player owner, StarshipSetup setup) {
        super(location, owner);
        this.fighterBerths = 2;
        this.setWeapons(setup.getWeaponList());
        shield = Shield.getTemplateShield(ShipChassis.DESTROYER);
        generator = Generator.getTemplateGenerator(ShipChassis.DESTROYER);
        health = 100;
    }

    @Override
    public String toString () {
        return this.getName() + " (Destroyer) @" + location.getName();
    }
}
