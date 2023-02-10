package model;

public class Game {
    // private int id; // game id
    private int rank; // final placement from 1st to 8th
    private String day;  // numerical date from 1-31
    private String month; // numerical month from 1-12
    private String year; // year
    private String comp; // name of comp played
    private boolean winStatus; // whether rank correlates to a win or not

    /*
     * REQUIRES: rank is [1, 8], d is [1, 31], m is [1, 12], y has length 4 and comp has a non-zero length
     * EFFECTS: rank of game is set to rank, day/month/year are set to m, d, y and name of game comp is set to comp
     * if the rank is [1, 4], the winStatus is set to true; otherwise, winStatus is false.
     */
    public Game(int rank, String d, String m, String y, String comp) {
        // int defaultID = 1;
        // id = defaultID++;
        this.rank = rank;
        this.day = d;
        this.month = m;
        this.year = y;
        this.comp = comp;
        this.winStatus = (rank <= 4);

    }

    // getter methods
//    public int getID() {
//        return id;
//    }

    public int getRank() {
        return rank;
    }

    public String getDate() {
        return month + "/" + day + "/" + year;
    }


    public String getComp() {
        return comp;
    }

    public boolean getWinStatus() {
        return winStatus;
    }

    // update methods

    /*
     * REQUIRES: newRank is [1,8]
     * MODIFIES: this
     * EFFECTS: replaces current rank with new rank and updates winStatus based on new rank
     */

    public int updateRank(int newRank) {
        this.rank = newRank;
        this.winStatus = (rank <= 4);
        return newRank;
    }

    /*
     * REQUIRES: newComp has a non-zero length
     * MODIFIES: this
     * EFFECTS: replaces current rank with new rank
     */
    public String updateComp(String newComp) {
        this.comp = newComp;
        return newComp;
    }


//    public int setID(int num) {
//        this.id = num;
//        return num;
//    }


}
