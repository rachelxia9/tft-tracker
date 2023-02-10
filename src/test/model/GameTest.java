package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class GameTest {
    private Game testplace4th;
    private Game testplace2nd;
    private Game testplace8th;
    private Game testplace1st;
    private String todaysDate;

    @BeforeEach
    public void runBefore(){
        testplace4th = new Game (4,  "brawlers");
        testplace2nd = new Game (2, "star guardian");
        testplace8th = new Game (8,  "yuumi");
        testplace1st = new Game (1,  "duelists");
        todaysDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    @Test
    public void testConstructor(){
        assertEquals(4, testplace4th.getRank());
        assertEquals(2, testplace2nd.getRank());
        assertEquals(8, testplace8th.getRank());
        assertEquals(1, testplace1st.getRank());

        assertEquals(todaysDate, testplace4th.getLocalDate());
        assertEquals(todaysDate, testplace2nd.getLocalDate());
        assertEquals(todaysDate, testplace8th.getLocalDate());
        assertEquals(todaysDate, testplace1st.getLocalDate());


        assertEquals("star guardian", testplace2nd.getComp());
        assertEquals("yuumi", testplace8th.getComp());
        assertEquals("duelists", testplace1st.getComp());


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


    @Test
    public void testUpdateComp(){
        testplace4th.updateComp("yuumi");
        assertEquals("yuumi", testplace4th.getComp());
        assertEquals(4, testplace4th.getRank());
        assertTrue(testplace4th.getWinStatus());
    }

    @Test
    public void testUpdateCompSame(){
        testplace4th.updateComp("brawlers");
        assertEquals("brawlers", testplace4th.getComp());
        assertEquals(4, testplace4th.getRank());
        assertTrue(testplace4th.getWinStatus());
    }
}