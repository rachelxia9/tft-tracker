package model;

import java.util.HashMap;
import java.util.Map.Entry;

// a list of games, each game has a unique id associated with it

public class MatchHistory {
    private final HashMap<Integer, Game> games;
    private int id = 0;

    // EFFECTS: constructs match history, a list of games with associated ids
    public MatchHistory() {
        this.games = new HashMap<>();
    }

    // REQUIRES: non-empty list
    // MODIFIES: this
    // EFFECTS: clears all games in match history
    public void clearHistory() {
        games.clear();
    }

    // MODIFIES: this
    // EFFECTS: adds given game to match history and increments associated id
    public void addGame(Game g) {
        id++;
        games.put(id, g);
    }

    // MODIFIES: this
    // EFFECTS: removes game from match history using given id
    public void removeGame(int id) {
        games.remove(id);
    }

    // EFFECTS: uses given id to access associated game in match history
    public Game accessGame(int id) {
        return games.get(id);
    }

    // EFFECTS: return number of games in match history
    public int getNumGames() {
        return games.size();
    }

    // EFFECTS: return percentage of winning games in match history, return 0 if empty
    public int getWinRate() {
        double count = 0;
        double numGames = this.getNumGames();

        if (numGames == 0) {
            return 0;
        } else {
            for (Game g : games.values()) {
                if (g.getWinStatus()) {
                    count++;
                }
            }
            return (int) Math.round(count / numGames * 100);
        }


    }

    // REQUIRES: valid rank [1-8]
    // EFFECTS: return all games with given rank
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

    // REQUIRES: non-empty string
    // EFFECTS: return all games with given comp
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

    // EFFECTS: gets all games in match history without returning ids
    public HashMap<Integer, Game> getGames() {
        return games;
    }

    // EFFECTS: returns id key of corresponding game, returns -1 if not found
    public int getID(Game g) {
        for (Entry<Integer, Game> entry : games.entrySet()) {
            if (entry.getValue() == g) {
                return entry.getKey();
            }
        }
        return -1;

    }

}

