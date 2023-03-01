package persistence;

import model.Game;
import model.MatchHistory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistent() {
        JsonReader reader = new JsonReader("./data/random.json");
        try {
            MatchHistory mh = reader.read();
            fail("Exception expected");
        } catch (IOException e) {
            // good!
        }
    }

    @Test
    void testReaderEmpty() {
        JsonReader reader = new JsonReader("./data/testReaderEmpty");
        try {
            MatchHistory mh = reader.read();
            assertEquals(0, mh.getNumGames());
        } catch (IOException e){
            fail("Unexpected IOException, can't read");
        }
    }

    @Test
    void testReaderGeneral() {
        JsonReader reader = new JsonReader("./data/testReaderGeneral");
        try {
            MatchHistory mh = reader.read();
            HashMap<Integer, Game> games = mh.getGames();
            Game g1 = new Game(1, "yuumi");
            Game g2 = new Game (5, "jax");
            Game g3 = new Game (8, "star guardian");
            assertEquals(3, mh.getNumGames());

            checkGame(1, "yuumi", g1);
            checkGame(5, "jax",  g2);
            checkGame(8, "star guardian", g3);

            checkGame(1, "yuumi", games.get(1));
            checkGame(5, "jax",  games.get(2));
            checkGame(8, "star guardian", games.get(3));
        } catch (IOException e){
            fail("Unexpected IOException, can't read");
        }
    }
}
