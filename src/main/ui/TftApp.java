package ui;

import model.MatchHistory;
import model.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class TftApp {

    private static final String OPEN_COMMAND = "open";
    private static final String ADD_COMMAND = "add";
    private static final String REMOVE_COMMAND = "remove";
    private static final String STATS_COMMAND = "stats";
    private static final String QUIT_COMMAND = "quit";
    private static final String EDIT_COMMAND = "edit";

    private final MatchHistory allGames;
    private final Scanner input;
    private boolean isRunning;


    // EFFECTS: Runs TFT app
    public TftApp() {
        this.allGames = new MatchHistory();
        input = new Scanner(System.in);
        isRunning = true;

        runApp();
    }

    // MODIFIES: this
    // EFFECTS: process user input to run app or leave
    private void runApp() {
        System.out.println("What would you like to do in the app today?");
        displayStart();
        String userAction;

        while (isRunning) {
            if (input.hasNext()) {
                userAction = input.nextLine();
                userAction = userAction.toLowerCase();
                userAction = userAction.trim();
                runInput(userAction);
            }
        }
        System.out.println("Bye! See you next time!");
    }

    // EFFECTS: processes user action input
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void runInput(String userIn) {
        if (userIn.length() > 0) {
            switch (userIn) {
                case OPEN_COMMAND:
                    showMatchHistory();
                    break;
                case ADD_COMMAND:
                    addTo();
                    break;
                case REMOVE_COMMAND:
                    removeFrom();
                    break;
                case STATS_COMMAND:
                    displayStats();
                    break;
                case EDIT_COMMAND:
                    editMatchHistory();
                    break;
                case QUIT_COMMAND:
                    isRunning = false;
                    break;
                default:
                    System.out.println("input not valid :(");
                    break;
            }
        }
    }

    // EFFECTS: display navigation menu to user
    private void displayStart() {
        System.out.println("\nSelect an option:");
        if (allGames.getNumGames() > 0) {
            System.out.println("\t Enter '" + OPEN_COMMAND + "' to open your match history ");
            System.out.println("\t Enter '" + ADD_COMMAND + "' to add a tft game to your current match history");
            System.out.println("\t Enter '" + REMOVE_COMMAND + "' to remove a tft game from your match history");
            System.out.println("\t Enter '" + EDIT_COMMAND + "' to edit a game in your match history");
            System.out.println("\t Enter '" + STATS_COMMAND + "' to view your stats so far");
            System.out.println("\t Enter '" + QUIT_COMMAND + "' to quit the app");
        } else {
            System.out.println("\t Enter '" + ADD_COMMAND + "' to add a tft game to your current match history");
            System.out.println("\t Enter '" + QUIT_COMMAND + "' to quit the app");
        }
    }

    // REQUIRES: non-empty input
    // EFFECTS: display all stats for match history, takes in input
    private void displayStats() {
        System.out.println("Your win rate is: " + allGames.getWinRate() + "%");
        if (allGames.getWinRate() >= 50) {
            System.out.println(":)))))\n");
        } else {
            System.out.println(":(((((\n");
        }
        System.out.println("Your placements so far...");
        System.out.println("1st: " + allGames.getGamesByRank(1));
        System.out.println("2nd: " + allGames.getGamesByRank(2));
        System.out.println("3rd: " + allGames.getGamesByRank(3));
        System.out.println("4th: " + allGames.getGamesByRank(4));
        System.out.println("5th: " + allGames.getGamesByRank(5));
        System.out.println("6th: " + allGames.getGamesByRank(6));
        System.out.println("7th: " + allGames.getGamesByRank(7));
        System.out.println("8th: " + allGames.getGamesByRank(8) + "\n");

        countComps();

        displayStart();
    }

    // EFFECTS: returns counts of all instances of each comp name in match history
    private void countComps() {
        HashMap<Integer, Game> games = allGames.getGames();
        ArrayList<String> comps = new ArrayList<>();
        for (Game g : games.values()) {
            String currComp = g.getComp();
            if (!(comps.contains(currComp))) {
                comps.add(currComp);
                int count = allGames.getGamesByComp(currComp);
                System.out.println(currComp + " has been played " + count + " times.");
            } else {
                comps.add(currComp);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: removes given game from match history in app
    private void removeFrom() {
        HashMap<Integer, Game> games = allGames.getGames();

        System.out.println("Please enter the id of the game you want to remove:");
        String num = input.nextLine().trim();
        try {
            int id = Integer.parseInt(num);
            for (int i : games.keySet()) {
                if (id == i) {
                    allGames.removeGame(i);
                    System.out.println("Game " + id + " has been removed.");
                    break;
                }
                System.out.println("Game " + id + " was not found :(");
            }
        } catch (NumberFormatException nfe) {

            System.out.println("NumberFormat Exception: invalid input string :(");
        }

        displayStart();

    }


//        HashMap<Integer, Game> games = allGames.getGames();
//        for (int i : games.keySet()) {
//            if (id == i) {
//                allGames.removeGame(i);
//                System.out.println("Game " + id + " has been removed.");
//                break;
//            }
//            System.out.println("Game " + id + " was not found :(");
//        }


    // MODIFIES: this
    // EFFECTS: add game with inputted info into match history
    private void addTo() {
        System.out.println("Please enter the placement of the game you want to add:");
        String num = input.nextLine().trim();

        try {
            int rank = Integer.parseInt(num);

            if (rank < 1 | rank > 8) {
                System.out.println("Invalid placement. Enter a number from 1-8.");
                String num2 = input.nextLine().trim();
                rank = Integer.parseInt(num2);
            }
            System.out.println("Please enter the name of your comp:");
            String comp = input.nextLine().toLowerCase().trim();

            if (comp.isEmpty()) {
                System.out.println("Please input an actual name...");
                comp = input.nextLine().toLowerCase().trim();
            }

            Game game = new Game(rank, comp);
            allGames.addGame(game);

            System.out.println("Your " + comp + " game has been added.");
        } catch (NumberFormatException nfe) {
            System.out.println("NumberFormat Exception: invalid input string :(");
        }

        displayStart();

    }

    // EFFECTS: display a list of all games played and their info
    private void showMatchHistory() {
        HashMap<Integer, Game> games = allGames.getGames();
        for (Game g : games.values()) {
            int id = allGames.getID(g);
            int rank = g.getRank();
            String comp = g.getComp();
            String date = g.getLocalDate();
            System.out.println("Date: " + date + " | Game ID: " + id + " | Rank: " + rank + " | Comp: " + comp + "\n");
        }
        displayStart();
    }

    // MODIFIES: this
    // EFFECTS: edit game with given id in match history with inputs
    private void editMatchHistory() {
        System.out.println("Please enter the id of the game you want to edit:");
        String num = input.nextLine().trim();

        try {
            int id = Integer.parseInt(num);
            HashMap<Integer, Game> games = allGames.getGames();
            for (Game g : games.values()) {
                if (id == allGames.getID(g)) {
                    System.out.println("Enter new rank: ");
                    String num2 = input.nextLine().trim();
                    int rank = Integer.parseInt(num2);
                    g.updateRank(rank);
                    System.out.println("Enter new comp name: ");
                    String name = input.nextLine().toLowerCase().trim();
                    g.updateComp(name);
                    System.out.println("Game has been edited.");
                }
                System.out.println("Game not found");
            }
        } catch (NumberFormatException nfe) {
            System.out.println("NumberFormat Exception: invalid input string :(");
        }

        displayStart();
    }
}


