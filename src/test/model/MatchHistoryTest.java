package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class MatchHistoryTest {
    private MatchHistory test1;
    //    private MatchHistory test2;
    private Game game1;
    private Game game2;


    @BeforeEach
    void runBefore() {
        test1 = new MatchHistory();
        game1 = new Game(4, "3", "2", "2023", "brawlers");
        game2 = new Game(8, "1", "1", "2023", "yuumi");

    }

    @Test
    void testConstructor() {
        HashMap<Integer, Game> empty = new HashMap<>();
        assertEquals(0, test1.getNumGames());
        assertEquals(0, test1.getWinRate());
    }

    @Test
    void testAddGameOnce() {
        test1.addGame(game1);
        assertEquals(1, test1.getNumGames());
        assertEquals(100, test1.getWinRate());
        assertEquals(1, test1.getGamesByComp("brawlers"));
        assertEquals(0, test1.getGamesByComp("yuumi"));
        assertEquals(1, test1.getGamesByRank(4));
        assertEquals(0, test1.getGamesByRank(8));
    }

    @Test
    void testAddGameMultiple() {
        test1.addGame(game1);
        assertEquals(1, test1.getNumGames());
        assertEquals(100, test1.getWinRate());
        assertEquals(1, test1.getGamesByComp("brawlers"));
        assertEquals(0, test1.getGamesByComp("yuumi"));
        assertEquals(1, test1.getGamesByRank(4));
        assertEquals(0, test1.getGamesByRank(8));

        test1.addGame(game2); // work on tests
        assertEquals(2, test1.getNumGames());
        assertEquals(50, test1.getWinRate());
        assertEquals(1, test1.getGamesByComp("brawlers"));
        assertEquals(1, test1.getGamesByComp("yuumi"));
        assertEquals(1, test1.getGamesByRank(4));
        assertEquals(1, test1.getGamesByRank(8));
    }

    @Test
    void testRemoveGame() {
        test1.addGame(game1);
        test1.removeGame(1);
        assertEquals(0, test1.getNumGames());
        assertEquals(0, test1.getWinRate());
        assertEquals(0, test1.getGamesByComp("brawlers"));
        assertEquals(0, test1.getGamesByRank(4));
    }

    @Test
    void testRemoveLastGame() {
        test1.addGame(game1);
        test1.addGame(game2);
        test1.removeGame(2);
        assertEquals(1, test1.getNumGames());
        assertEquals(100, test1.getWinRate());
        assertEquals(1, test1.getGamesByComp("brawlers"));
        assertEquals(1, test1.getGamesByRank(4));
        assertEquals(0, test1.getGamesByComp("yuumi"));
        assertEquals(1, test1.getGamesByRank(4));
        assertEquals(0, test1.getGamesByRank(8));

    }

    @Test
    void testRemoveMultipleGames() {
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
    void testAccessGame() {
        assertNull(test1.accessGame(1));
        test1.addGame(game1);
        test1.addGame(game2);
        assertEquals(game1, test1.accessGame(1));
        assertEquals(game2, test1.accessGame(2));
    }

    @Test
    void testClearHistory() {
        test1.addGame(game1);
        test1.clearHistory();
        assertEquals(0, test1.getNumGames());
    }

    @Test
    void testClearLargerHistory(){
        test1.addGame(game1);
        test1.addGame(game2);
        assertEquals(2, test1.getNumGames());
    }

}
