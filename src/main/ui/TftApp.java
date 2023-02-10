package ui;

import model.MatchHistory;
import model.Game;

import java.util.HashMap;
import java.util.Scanner;

public class TftApp {

    private static final String OPEN_COMMAND = "open";
    private static final String ADD_COMMAND = "add";
    private static final String REMOVE_COMMAND = "remove";
    private static final String STATS_COMMAND = "stats";
    private static final String QUIT_COMMAND = "quit";

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
                runInput(userAction);
            }
        }
        System.out.println("Bye! See you next time!");
    }

    // EFFECTS: processes user action input
    private void runInput(String userAction) {
        if (userAction.length() > 0) {
            switch (userAction) {
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
        System.out.println("\n Select an option:");
        System.out.println("\t Enter " + OPEN_COMMAND + " to open your match history ");
        System.out.println("\t Enter " + ADD_COMMAND + " to add a tft game to your current match history");
        System.out.println("\t Enter " + REMOVE_COMMAND + " to remove a tft game from your match history");
        System.out.println("\t Enter " + STATS_COMMAND + " to view your stats from games in your match history so far");
        System.out.println("\t Enter " + QUIT_COMMAND + " to quit the app");
    }

    private void displayStats() {
        System.out.println("Your win rate is: " + allGames.getWinRate() + "%");
        if (allGames.getWinRate() >= 50) {
            System.out.println("Keep up the good work!\n");
        } else {
            System.out.println("stop losing!\n");
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

        System.out.println("To find a count of a specific comp, enter the name:");
        String name = input.nextLine();
        String num = Integer.toString(allGames.getGamesByComp(name));
        System.out.println(name + " was played " + num + " times");

        displayStart();
    }

    private void removeFrom() {
        System.out.println("Please enter the id of the game you want to remove:");
        String num = input.nextLine();
        int id = Integer.parseInt(num);
        allGames.removeGame(id);


        System.out.println("Game " + id + " has been removed.");
        displayStart();
    }

    private void addTo() {
        System.out.println("Please enter the placement of the game you want to add:");
        String num = input.nextLine();
        int rank = Integer.parseInt(num);
        System.out.println("Please enter the day the game was played on:");
        String d = input.nextLine();
        System.out.println("Please enter the month the game was played on:");
        String m = input.nextLine();
        System.out.println("Please enter the year the game was played on:");
        String y = input.nextLine();
        System.out.println("Please enter the name of your comp:");
        String comp = input.nextLine();
        Game game = new Game(rank, d, m, y, comp);
        allGames.addGame(game);

        System.out.println("Your " + comp + " game has been added.");
        displayStart();
    }

    private void showMatchHistory() {
        HashMap<Integer, Game> games = allGames.getGames();
        for (Game g : games.values()) {
            int id = g.getID();
            int rank = g.getRank();
            String comp = g.getComp();
            System.out.println("Game ID: " + id + " rank: " + rank + " comp: " + comp + "\n");
        }
    }


}
