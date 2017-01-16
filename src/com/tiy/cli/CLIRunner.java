package com.tiy.cli;

import com.tiy.IllegalMoveException;
import com.tiy.ImproperFunctionInputException;
import com.tiy.starship.*;
import com.tiy.starsys.*;
import sun.security.krb5.internal.crypto.Des;

import java.util.Scanner;

/**
 * Created by erronius on 12/20/2016.
 */
public class CLIRunner {

    Scanner scanner;
    Player tester;
    StarSystemGraph simpleSystemGraph;

    public static void main(String[] args) {
        //new CLIRunner().simpleGame();
        new CLIRunner().ringMapTest();
    }

    public CLIRunner () {
        scanner = new Scanner(System.in);
    }

    public void sandbox () {
        System.out.println("Welcome to Simplicity 4x Sandbox");
        StarSystemGraph simpleSystemGraph = new StarSystemGraph();
        System.out.println(simpleSystemGraph);
        for (StarSystem starSystem : simpleSystemGraph.getStarSystems()) {
            System.out.println(starSystem);
        }
    }

    public void ringMapTest () {
        StarSystemGraph ringSystemGraph = new StarSystemGraph(0, MapSize.SMALL);
        ringSystemGraph.printInfo();
    }

    public void simpleGame () {
        System.out.println("Welcome to Simplicity 4x Simple Game");
        simpleSystemGraph = new StarSystemGraph();
        StarSystem homeSystem = simpleSystemGraph.getStarSystems().get(0);

        tester = new Player(homeSystem, "Abelard");
        homeSystem.getPlanets().get(0).setOwner(tester);
        Fighter startingFighter = new Fighter(homeSystem, tester, new StarshipSetup(ShipChassis.FIGHTER));
        startingFighter.setOwner(tester);
        tester.addShip(startingFighter);

        System.out.println(simpleSystemGraph);

        Shipyard shipyard = new Shipyard(homeSystem, tester);

        mainMenu();
    }

    public void mainMenu () {
        boolean playing = true;
        while (playing) {
            System.out.println("What action to take?");
            System.out.println("1. Change a planet's output priorities");
            System.out.println("2. Move/take action with a ship");
            System.out.println("3. Add to shipyard queue");
            System.out.println("4. End turn");
            System.out.println("5. Exit testing");

            int userChoice = Integer.parseInt(scanner.nextLine());

            switch (userChoice) {
                case 1:
                    for (Planet planet : tester.getPlanets()) {
                        System.out.println(planet);
                    }
                    System.out.println("Which planet? (First planet is 0)");
                    int planetNumber = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter desired research %?");
                    float researchPct = Float.parseFloat(scanner.nextLine());
                    try {
                        tester.getPlanets().get(planetNumber).setResearchPct(researchPct);
                    } catch (ImproperFunctionInputException ex) {
                        ex.printStackTrace();
                    }
                    break;
                case 2:
                    for (Starship starship : tester.getShips()) {
                        System.out.println(starship);
                    }
                    System.out.println("Which ship? (First ship is 0)");
                    int shipNumber = Integer.parseInt(scanner.nextLine());
                    takeActionWithShip(shipNumber);
                    break;
                case 3:
                    changeShipyardProduction();
                    break;
                case 4:
                    tester.moveShips();
                    tester.getResearchAndProductionFromPlanets();
                    break;
                case 5:
                    playing = false;
                    break;
                default:
                    System.out.println("Not a valid choice");
                    break;
            }
        }
    }

    public void takeActionWithShip (int shipNumber) {
        Starship starship = tester.getShips().get(shipNumber);
        System.out.println("Action to take? Please only choose from valid items");
        System.out.println("0. Nevermind");
        if (starship.getClass() == Colonizer.class) {
            System.out.println("1. Create colony");
        }
        if (starship.getClass() != Fighter.class) {
            System.out.println("2. Enter tunnel");
        } else {
            System.out.println("3. Attach to nearest bigger ship");
        }
        int userChoice = Integer.parseInt(scanner.nextLine());

        StarSystem currentSystem = (StarSystem) starship.getLocation();
        switch (userChoice) {
            case 0:
                break;
            case 1:
                System.out.println("Attempting to colonize.");
                Planet planetToColonize = currentSystem.getPlanets().get(0);
                System.out.println("Target = " + planetToColonize);
                try {
                    ((Colonizer) starship).createColony(planetToColonize);
                } catch (IllegalMoveException ex) {
                    ex.printStackTrace();
                }
                break;
            case 2:
                //Enter tunnel
                //starship.enterTunnel();
                for (SpaceTunnel tunnel : currentSystem.getTunnels()) {
                    System.out.println(tunnel);
                }
                System.out.println("Which tunnel to enter? (First tunnel is 0)");
                int tunnelChoice = Integer.parseInt(scanner.nextLine());
                try {
                    starship.enterTunnel(currentSystem.getTunnels().get(tunnelChoice));
                } catch (IllegalMoveException ex) {
                    ex.printStackTrace();
                }
                break;
            case 3:
                //attach
                int shipIndex = 0;
                boolean shipAvailable = false;
                for (Starship starship1 : tester.getShips()) {
                    if (starship1.getLocation().equals(currentSystem)) {
                        if (starship1.getClass() != Fighter.class) {
                            System.out.println(shipIndex + ". " + starship1);
                            shipAvailable = true;
                        }
                    }
                    shipIndex++;
                }
                if (shipAvailable) {
                    int starshipChoice = Integer.parseInt(scanner.nextLine());
                    try {
                        Fighter starFighter = (Fighter) starship;
                        starFighter.attachTo(tester.getShips().get(starshipChoice));
                    } catch (IllegalMoveException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    System.out.println("No big ships nearby");
                }
                break;
            default:
                System.out.println("Bad choice");
        }
    }

    public void changeShipyardProduction () {
        Shipyard shipyard = tester.getShipyard();
        for (Project project : shipyard.getProjects()) {
            System.out.println(project);
        }
        System.out.println("What ship would you like to add?");
        System.out.println("0. Nevermind");
        System.out.println("1. Fighter");
        System.out.println("2. Colonizer");
        boolean destroyersAllowed = tester.getTech().isComplete();
        if (destroyersAllowed) {
            System.out.println("3. Destroyer");
        }
        int userChoice = Integer.parseInt(scanner.nextLine());

        if (userChoice == 3) {
            if (!destroyersAllowed) {
                System.out.println("Bad choice");
                userChoice = 0;
            }
        }

        switch (userChoice) {
            case 0:
                break;
            case 1:
                //add fighter
                Fighter fighter = new Fighter(shipyard, tester, new StarshipSetup(ShipChassis.FIGHTER));
                Project fighterProject = new Project(20, fighter, shipyard);
                shipyard.addProject(fighterProject);
                break;
            case 2:
                //add colonizer
                Colonizer colonizer = new Colonizer(shipyard, tester);
                Project colonizerProject = new Project(40, colonizer, shipyard);
                shipyard.addProject(colonizerProject);
                break;
            case 3:
                //add destwoyeh
                Destroyer destroyer = new Destroyer(shipyard, tester, new StarshipSetup(ShipChassis.DESTROYER));
                Project destroyerProject = new Project(30, destroyer, shipyard);
                shipyard.addProject(destroyerProject);
                break;
            default:
                System.out.println("Bad choice");
                break;
        }
    }
}
