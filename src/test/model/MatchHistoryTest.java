package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class MatchHistoryTest {
    private MatchHistory test1;
    private Game game1;
    private Game game2;


    @BeforeEach
    void runBefore() {
        test1 = new MatchHistory();
        game1 = new Game(4, "brawlers");
        game2 = new Game(8, "yuumi");

    }

    @Test
    public void testConstructor() {
        HashMap<Integer, Game> empty = new HashMap<>();
        assertEquals(0, test1.getNumGames());
        assertEquals(0, test1.getWinRate());
        assertEquals(empty, test1.getGames());
    }

    @Test
    public void testAddGameOnce() {
        test1.addGame(game1);
        assertEquals(1, test1.getID(game1));
        assertEquals(1, test1.getNumGames());
        assertEquals(100, test1.getWinRate());
        assertEquals(1, test1.getGamesByComp("brawlers"));
        assertEquals(0, test1.getGamesByComp("yuumi"));
        assertEquals(1, test1.getGamesByRank(4));
        assertEquals(0, test1.getGamesByRank(8));
    }

    @Test
    public void testAddGameMultiple() {
        test1.addGame(game1);
        assertEquals(1, test1.getID(game1));
        assertEquals(1, test1.getNumGames());
        assertEquals(100, test1.getWinRate());
        assertEquals(1, test1.getGamesByComp("brawlers"));
        assertEquals(0, test1.getGamesByComp("yuumi"));
        assertEquals(1, test1.getGamesByRank(4));
        assertEquals(0, test1.getGamesByRank(8));

        test1.addGame(game2); // work on tests
        assertEquals(2, test1.getID(game2));
        assertEquals(2, test1.getNumGames());
        assertEquals(50, test1.getWinRate());
        assertEquals(1, test1.getGamesByComp("brawlers"));
        assertEquals(1, test1.getGamesByComp("yuumi"));
        assertEquals(1, test1.getGamesByRank(4));
        assertEquals(1, test1.getGamesByRank(8));
    }

    @Test
    public void testRemoveGame() {
        test1.addGame(game1);
        assertEquals(1, test1.getNumGames());
        assertEquals(1, test1.getID(game1));
        test1.removeGame(1);
        assertEquals(-1, test1.getID(game1));
        assertEquals(0, test1.getNumGames());
        assertEquals(0, test1.getWinRate());
        assertEquals(0, test1.getGamesByComp("brawlers"));
        assertEquals(0, test1.getGamesByRank(4));
    }

    @Test
    public void testRemoveLastGame() {
        test1.addGame(game1);
        test1.addGame(game2);
        test1.removeGame(2);
        assertEquals(1, test1.getNumGames());
        assertEquals(100, test1.getWinRate());
        assertEquals(1, test1.getGamesByComp("brawlers"));
        assertEquals(1, test1.getGamesByRank(4));
        assertEquals(0, test1.getGamesByComp("yuumi"));
        assertEquals(0, test1.getGamesByRank(8));

    }

    @Test
    public void testRemoveMultipleGames() {
        test1.addGame(game1);
        test1.addGame(game2);
        test1.removeGame(1);
        assertEquals(1, test1.getNumGames());
        assertEquals(0, test1.getWinRate());
        assertEquals(0, test1.getGamesByComp("brawlers"));
        assertEquals(1, test1.getGamesByComp("yuumi"));
        assertEquals(0, test1.getGamesByRank(4));
        assertEquals(1, test1.getGamesByRank(8));
        test1.removeGame(2);
        assertEquals(0, test1.getNumGames());
        assertEquals(0, test1.getWinRate());
        assertEquals(0, test1.getGamesByComp("brawlers"));
        assertEquals(0, test1.getGamesByRank(4));
        assertEquals(0, test1.getGamesByComp("yuumi"));
        assertEquals(0, test1.getGamesByRank(4));
        assertEquals(0, test1.getGamesByRank(8));

    }

    @Test
    public void testAccessGame() {
        assertNull(test1.accessGame(1));
        test1.addGame(game1);
        test1.addGame(game2);
        assertEquals(game1, test1.accessGame(1));
        assertEquals(game2, test1.accessGame(2));
    }

    @Test
    public void testClearHistory() {
        test1.addGame(game1);
        test1.clearHistory();
        assertEquals(0, test1.getNumGames());
    }

    @Test
    public void testClearLargerHistory() {
        test1.addGame(game1);
        test1.addGame(game2);
        assertEquals(2, test1.getNumGames());
    }

}
