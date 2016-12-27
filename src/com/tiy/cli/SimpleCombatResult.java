package com.tiy.cli;

import com.tiy.starship.Starship;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erronius on 12/22/2016.
 */
public class SimpleCombatResult {
    List<Starship> firstFleet;
    List<Starship> secondFleet;

    List<String> log;

    public SimpleCombatResult () {
        log = new ArrayList<String>();
    }

    public void addToLog (String logItem) {
        log.add(logItem);
    }
}
