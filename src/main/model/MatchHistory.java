package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


public class MatchHistory {
//    private ArrayList<Game> games;
    private HashMap<Integer, Game> games;
    private int id = 0;


    public MatchHistory() {
    //    games = new ArrayList<>();
        games = new HashMap<Integer, Game>();
    }

    // Requires: non-empty list
    // Modifies: this
    // Effects: clears all games in match history
    public void clearHistory() {
        games.clear();
    }

    public void addGame(Game g) {
        id++;
        games.put(id, g);
    }

    public void removeGame(int id) {
        games.remove(id);
    }

    public Game accessGame(int id) {
        games.get(id);
//        for (Game g : games) {
//            if (id == g.getID()) {
//                return g;
//            }
//        }
//        return null;
    }

    public ArrayList<Game> getHistory() {
        return games;
    }

    // TODO: change to int??
    public double getNumGames() {
        return games.size();
    }


    public double getWinRate() {
        int count;
        count = 0;

        for (Game g : games) {
            if (g.getWinStatus()) {
                count++;
            }
        }
        return count / this.getNumGames() * 100;

    }

    public int getGamesByRank(int rank) {
        int count;
        count = 0;

        for (Game g : games) {
            if (g.getRank() == rank) {
                count++;
            }
        }
        return count;

    }

    public int getGamesByComp(String comp) {
        int count;
        count = 0;

        for (Game g : games) {
            if (g.getComp().equals(comp)) {
                count++;
            }
        }
        return count;
    }

    // Modifies: this
    // Effects: maps unique IDS to each game in list of games
    public void mapIDs() {
        int defaultID = 0;
        for (Game g : games) {
            defaultID = defaultID++;
            g.setID(defaultID);
        }
    }

}

