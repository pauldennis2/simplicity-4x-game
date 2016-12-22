package com.tiy.starsys;

import com.sun.xml.internal.ws.policy.AssertionSet;
import com.tiy.ImproperFunctionInputException;

/**
 * Created by erronius on 12/20/2016.
 */
public class Planet {

    private boolean habitable;
    private int mineralLevel;
    private int size;
    private int population;
    private String name;
    //private Race owner;
    private String owner;

    private float researchPct;
    private float productionPct;

    private double partialResearch;
    private double partialProduction;

    private StarSystem system;

    public Planet (String name, int size, boolean habitable, StarSystem system) {
        this.name = name;
        this.size = size;
        this.habitable = habitable;
        owner = "none";
        population = 0;
        researchPct = 0.4f;
        productionPct = 0.6f;
        this.system = system;
    }

    @Override
    public String toString () {
        //Example:
        //Alpha Centauri Owned by Player 4/8
        String response = name + " ";
        if (!owner.equals("none")) {
            response += "Owned by " + owner + " ";
        }
        response += population + "/" + size;
        return response;
    }

    public String getOwner () {
        return owner;
    }

    public StarSystem getSystem () {
        return system;
    }

    public void setPopulation (int pop) {
        population = pop;
    }

    public void setOwner (String owner) {
        this.owner = owner;
    }

    public void setResearchPct (float researchPct) throws ImproperFunctionInputException {
        if (researchPct > 1.0f || researchPct < 0.0f) {
            throw new ImproperFunctionInputException("Value must be between 0.0 and 1.0 inclusive.");
        }
        this.researchPct = researchPct;
        productionPct = 1.0f - researchPct;
    }

    public int getResearch () {
        //for now always round to nearest number.
        //Later go back and add to partialResearch
        float researchProduced = researchPct * population;
        return Math.round(researchProduced);
    }

    public int getProduction () {
        float productionProduced = productionPct * population;
        return Math.round(productionProduced);
    }

    public float getResearchPct () {
        return researchPct;
    }

    public float getProductionPct () {
        return productionPct;
    }

    public int getPopulation () {
        return population;
    }
}
