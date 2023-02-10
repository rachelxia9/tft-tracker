package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Game {
    private int rank; // final placement from 1st to 8th
    private final String date;
    private String comp; // name of comp played
    private boolean winStatus; // whether rank correlates to a win or not

    /*
     * REQUIRES: rank is [1, 8], d is [1, 31], m is [1, 12], y has length 4 and comp has a non-zero length
     * EFFECTS: rank of game is set to rank, day/month/year are set to m, d, y and name of game comp is set to comp
     * if the rank is [1, 4], the winStatus is set to true; otherwise, winStatus is false.
     */
    public Game(int rank, String comp) {
        LocalDate basic = LocalDate.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        this.date = basic.format(format);

        this.rank = rank;
        this.comp = comp;
        this.winStatus = (rank <= 4);

    }

    // getter methods
    // EFFECTS: return formatted date
    public String getLocalDate() {
        return date;
    }

    // EFFECTS: return rank
    public int getRank() {
        return rank;
    }

    // EFFECTS: return comp name
    public String getComp() {
        return comp;
    }

    // EFFECTS: return winStatus - true for win, false for loss
    public boolean getWinStatus() {
        return winStatus;
    }

    // REQUIRES: newRank is [1,8]
    // MODIFIES: this
    // EFFECTS: replaces current rank with new rank and updates winStatus based on new rank

    public void updateRank(int newRank) {
        this.rank = newRank;
        this.winStatus = (rank <= 4);
    }

    // REQUIRES: newComp has a non-zero length
    // MODIFIES: this
    // EFFECTS: replaces current rank with new rank

    public void updateComp(String newComp) {
        this.comp = newComp;
    }


}
