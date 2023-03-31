package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.HashMap;
import java.util.Map.Entry;

// a list of games, each game has a unique id associated with it

public class MatchHistory implements Writable {
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
        EventLog.getInstance().logEvent(new Event("cleared match history"));
    }

    // MODIFIES: this
    // EFFECTS: adds given game to match history and increments associated id
    public void addGame(Game g) {
        id++;
        games.put(id, g);
        EventLog.getInstance().logEvent(new Event("added game to match history"));
    }

    // MODIFIES: this
    // EFFECTS: removes game from match history using given id
    public void removeGame(int id) {
        games.remove(id);
        EventLog.getInstance().logEvent(new Event("removed game to match history"));
    }

    // EFFECTS: uses given id to access associated game in match history
    public Game accessGame(int id) {
        //EventLog.getInstance().logEvent(new Event("accessed game from match history"));
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

    @Override
    public JSONObject toJson() {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("games", gamesToJson());
        return jsonObj;

    }

    // EFFECTS: returns games in this match history as a JSON array
    private JSONArray gamesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Game g : games.values()) {
            jsonArray.put(g.toJson());
        }

        return jsonArray;
    }

}

