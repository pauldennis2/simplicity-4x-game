package com.tiy.starsys;

/**
 * Created by erronius on 12/20/2016.
 */
public class SpaceTunnel {

    private int length;
    private StarSystem firstSystem;
    private StarSystem secondSystem;

    public SpaceTunnel (int length, StarSystem firstSystem, StarSystem secondSystem) {
        this.length = length;
        this.firstSystem = firstSystem;
        this.secondSystem = secondSystem;
    }

    public int getLength () {
        return length;
    }

    /**
     *
     * @param notThisOne
     * @return the OTHER star system this tunnel connects to. NULL if it doesn't connect to the specified system
     */
    public StarSystem getOtherSystem (StarSystem notThisOne) {
        if (firstSystem.equals(notThisOne)) {
            return secondSystem;
        }
        if (secondSystem.equals(notThisOne)) {
            return firstSystem;
        }
        return null;
    }
}
