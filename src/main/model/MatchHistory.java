package model;

import java.util.ArrayList;

public class MatchHistory {
    private ArrayList<Game> games;

    public MatchHistory() {
        games = new ArrayList<>();
    }

    public void addGame(Game g) {
        games.add(g);
    }
 /*
    public void removeGame(int id) {
        games.removeIf(g -> g.getID() == id);
    }
 */

    public void removeGame(Game g) {
        games.remove(g);
    }

    public Game accessGame(Game g) {
        if (games.contains(g)) {
            return g;
        }
        return null;
    }

    public ArrayList<Game> getHistory() {
        return games;
    }

    public int getNumGames() {
        return games.size();
    }

}

