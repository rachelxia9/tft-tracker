package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Game testplace4th;
    private Game testplace2nd;
    private Game testplace8th;
    private Game testplace1st;

    @BeforeEach
    void runBefore(){
        testplace4th = new Game (4, "3", "2", "2023", "brawlers");
        testplace2nd = new Game (2, "1", "1", "2023", "star guardian");
        testplace8th = new Game (8, "1", "1", "2023", "yuumi");
        testplace1st = new Game (1, "14", "8", "2022", "duelists");
    }

    @Test
    void testConstructor(){
        assertEquals(4, testplace4th.getRank());
        assertEquals(2, testplace2nd.getRank());
        assertEquals(8, testplace8th.getRank());
        assertEquals(1, testplace1st.getRank());

        assertEquals("2/3/2023", testplace4th.getDate());
        assertEquals("1/1/2023", testplace2nd.getDate());
        assertEquals("1/1/2023", testplace8th.getDate());
        assertEquals("8/14/2022", testplace1st.getDate());


        assertEquals("star guardian", testplace2nd.getComp());
        assertEquals("yuumi", testplace8th.getComp());
        assertEquals("duelists", testplace1st.getComp());

        testplace4th.setID(1); // generate id in app? based on match history??
        testplace2nd.setID(2);
        testplace8th.setID(3);
        testplace1st.setID(4);

        assertEquals(1, testplace4th.getID());
        assertEquals(2, testplace2nd.getID());
        assertEquals(3, testplace8th.getID());
        assertEquals(4, testplace1st.getID());

        assertTrue(testplace4th.getWinStatus());
        assertTrue(testplace2nd.getWinStatus());
        assertFalse(testplace8th.getWinStatus());
        assertTrue(testplace1st.getWinStatus());
    }

    @Test
    void testUpdateRank(){
        testplace4th.updateRank(2);
        assertEquals(2, testplace4th.getRank());
        assertTrue(testplace4th.getWinStatus());

        testplace2nd.updateRank(7);
        assertEquals(7, testplace2nd.getRank());
        assertFalse(testplace2nd.getWinStatus());

        testplace2nd.updateRank(2);
        assertEquals(2, testplace2nd.getRank());
        assertTrue(testplace2nd.getWinStatus());

        testplace8th.updateRank(1);
        assertEquals(1, testplace8th.getRank());
        assertTrue(testplace8th.getWinStatus());
    }

    @Test
    void testUpdateRankTwice(){
        // 4 -> 2 -> 1 true -> true -> true
        testplace4th.updateRank(2);
        assertEquals(2, testplace4th.getRank());
        assertTrue(testplace4th.getWinStatus());
        testplace4th.updateRank(1);
        assertEquals(1, testplace4th.getRank());
        assertTrue(testplace4th.getWinStatus());

        // 4 -> 2 -> 8 true -> true -> false
        testplace4th.updateRank(2);
        assertEquals(2, testplace4th.getRank());
        assertTrue(testplace4th.getWinStatus());
        testplace4th.updateRank(8);
        assertEquals(8, testplace4th.getRank());
        assertFalse(testplace4th.getWinStatus());

        // 2 -> 7 -> 5 true -> false -> false
        assertTrue(testplace2nd.getWinStatus());
        testplace2nd.updateRank(7);
        assertEquals(7, testplace2nd.getRank());
        assertFalse(testplace2nd.getWinStatus());
        testplace2nd.updateRank(5);
        assertEquals(5, testplace2nd.getRank());
        assertFalse(testplace2nd.getWinStatus());


        // 8 -> 6 -> 1 false -> false -> true
        assertFalse(testplace8th.getWinStatus());
        testplace8th.updateRank(6);
        assertEquals(6, testplace8th.getRank());
        assertFalse(testplace8th.getWinStatus());
        testplace8th.updateRank(1);
        assertEquals(1, testplace8th.getRank());
        assertTrue(testplace8th.getWinStatus());
    }
}