package persistence;

import model.Game;
import model.MatchHistory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

// Source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo, JsonWriterTest

public class JsonWriterTest extends JsonTest{

    @Test
    void testWriterBadFile(){
        try {
        MatchHistory mh = new MatchHistory();
        JsonWriter writer = new JsonWriter("./data/??:\0badfile!.json");
        writer.open();
        fail("IOException expected, file is bad");
        } catch (IOException e){
            // good!
        }
    }

    @Test
    void testWriterEmpty() {
        try {
            MatchHistory mh = new MatchHistory();
            JsonWriter writer = new JsonWriter("./data/testWriterEmpty.json");
            writer.open();
            writer.write(mh);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmpty.json");
            mh = reader.read();
            assertEquals(0, mh.getNumGames());
        } catch (IOException e) {
            fail("Unexpected IOException, can't write");
        }
    }

    @Test
    void testWriterGeneral(){
        try {
            MatchHistory mh = new MatchHistory();
            mh.addGame(new Game(1, "yuumi"));
            mh.addGame(new Game (5, "jax"));
            mh.addGame(new Game (8, "star guardian"));

            JsonWriter writer = new JsonWriter("./data/testWriterGeneral.json");
            writer.open();
            writer.write(mh);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneral.json");
            mh = reader.read();
            HashMap<Integer, Game> games = mh.getGames();
            assertEquals(3, mh.getNumGames());
            checkGame(1, "yuumi", games.get(1));
            checkGame(5, "jax",  games.get(2));
            checkGame(8, "star guardian", games.get(3));

        } catch (IOException e) {
            fail("Unexpected IOException, can't write");
        }
    }
}
