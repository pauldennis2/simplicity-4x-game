package com.tiy.starship;

import com.tiy.starsys.StarSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erronius on 12/20/2016.
 */
public class Shipyard {

    private StarSystem location;
    private List<Project> projects;
    private Project currentProject;
    private int surplusProduction;

    public Shipyard (StarSystem location) {
        this.location = location;
        projects = new ArrayList<>();
    }

    public void addProjectTopPrio (Project project) {
        currentProject = project;
        projects.add(project);
    }

    public void addProductionToCurrentProject (int production) {
        int surplusReturned = currentProject.addProduction(production);
        if (surplusReturned > 0) {
            surplusProduction += surplusReturned;
            projects.remove(currentProject);
            if (projects.size() > 0) {
                currentProject = projects.get(0); //get the next project, arbitrarily at the beginning
            } else {
                currentProject = null;
            }
        }
    }

    public int getSurplusProduction () {
        return surplusProduction;
    }

    public Project getCurrentProject () {
        return currentProject;
    }
}
