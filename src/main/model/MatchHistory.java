package model;

import java.util.HashMap;
import java.util.Map.Entry;


public class MatchHistory {
    //    private ArrayList<Game> games;
    private HashMap<Integer, Game> games;
    private int id = 0;


    public MatchHistory() {
        //    games = new ArrayList<>();
        this.games = new HashMap<>();
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
        return games.get(id);
    }

//    public HashMap<Integer, Game> getHistory() {
//        return games;
//    }

    // TODO: change to int??
    public int getNumGames() {
        return games.size();
    }


    public int getWinRate() {
        double count = 0;
        double numGames = (double) this.getNumGames();

        if (numGames == 0) {
            return 0;
        } else {
            for (Game g : games.values()) {
                if (g.getWinStatus()) {
                    count++;
                }
            }
            // return count / numGames * 100;
            double result = (count / numGames);
            int percentage = (int) Math.round(count / numGames * 100);
            return percentage;
        }


    }

    public int getGamesByRank(int rank) {
        int count;
        count = 0;

        for (Game g : games.values()) {
            if (g.getRank() == rank) {
                count++;
            }
        }
        return count;

    }

    public int getGamesByComp(String comp) {
        int count;
        count = 0;

        for (Game g : games.values()) {
            if (g.getComp().equals(comp)) {
                count++;
            }
        }
        return count;
    }

    public HashMap<Integer, Game> getGames() {
        return games;
    }

    // EFFECTS: returns id key of corresponding game
    public int getID(Game g) {
        for (Entry<Integer, Game> entry : games.entrySet()) {
            if (entry.getValue() == g) {
                return entry.getKey();
            }
        }
        return -1;

    }

}

