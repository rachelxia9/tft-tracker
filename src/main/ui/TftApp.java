package ui;

import model.MatchHistory;
import model.Game;

import java.util.Scanner;

public class TftApp {
    private MatchHistory allGames;
    private Scanner input;
    private Game game; // ?? accessed game?

    // EFFECTS: Runs TFT app
    public TftApp() {
        runApp();
    }

    // MODIFIES: this
    // EFFECTS: process user input to run app or leave
    private void runApp() {
        boolean isRunning = true;
        String userAction = null;

        initializeApp();

        while (isRunning) {
            displayStart();
            userAction = input.next();
            userAction = userAction.toLowerCase();

            if (userAction.equals("leave")) {
                isRunning = false;
            } else {
                runAction(userAction);
            }
        }

        System.out.println("\n bye! see you next time! ");
    }

    // MODIFIES: this
    // EFFECTS: initializes match history
    private void initializeApp() {
        allGames = new MatchHistory();
        input = new Scanner(System.in); // ?
        input.useDelimiter("\n"); // ?
    }


    // EFFECTS: display navigation menu to user
    private void displayStart() {
        System.out.println("\n Select an option:");
        System.out.println("\t input 'open' to open your match history ");
        System.out.println("\t input 'add' to add a tft game to your current match history");
        System.out.println("\t input 'remove' to remove a tft game from your match history");
        System.out.println("\t input 'stats' to view your stats from games in your match history so far");

    }

    // EFFECTS: processes user action input
    private void runAction(String userAction) {
        switch (userAction) {
            case "open":
                showMatchHistory();
                break;
            case "add":
                addTo();
                break;
            case "remove":
                removeFrom();
                break;
            case "stats":
                displayStats();
                break;
            default:
                System.out.println("input not valid :(");
                break;
        }
    }


    private void displayStats() {

    }

    private void removeFrom() {
    }

    private void addTo() {
    }

    private void showMatchHistory() {
    }
 // TODO: add way to edit games in match history

}
