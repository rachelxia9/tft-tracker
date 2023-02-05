package ui;

import model.MatchHistory;

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
        System.out.println("\t open");
        System.out.println("\t add game");
        System.out.println("\t remove game");
        System.out.println("\t see stats");

    }

    // EFFECTS: processes user action input
    private void runAction(String userAction) {
        if (userAction.equals("open")) {
            showMatchHistory();
        } else if (userAction.equals("add game")) {
            addTo();
        } else if (userAction.equals("remove game")) {
            removeFrom();
        } else if (userAction.equals("see stats")) {
            displayStats();
        } else {
            System.out.println("input not valid :(");
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
