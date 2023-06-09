package model;

import org.json.JSONObject;
import persistence.Writable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// Each individual game with a rank, local date, comp name, and if a win occurred

public class Game implements Writable {
    private int rank; // final placement from 1st to 8th
    private final String date; // local date
    private String comp; // name of comp played
    private boolean winStatus; // whether rank correlates to a win or not

    /*
     * REQUIRES: rank is [1, 8] and comp has a non-zero length
     * EFFECTS: constructs a game; rank of game is set to rank, and name of game comp is set to comp
     * if the rank is [1, 4], the winStatus is set to true; otherwise, winStatus is false. date is set to local date;
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
        EventLog.getInstance().logEvent(new Event("Updated rank of game"));
        this.rank = newRank;
        this.winStatus = (rank <= 4);

    }

    // REQUIRES: newComp has a non-zero length
    // MODIFIES: this
    // EFFECTS: replaces current name with new name of comp

    public void updateComp(String newComp) {
        EventLog.getInstance().logEvent(new Event("Updated name of comp"));
        this.comp = newComp;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("rank", rank);
        jsonObj.put("comp", comp);
        return jsonObj;
    }
}
