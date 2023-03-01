package persistence;

import model.Game;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkGame(int rank, String comp, Game g) {
        assertEquals(comp,g.getComp());
        assertEquals(rank, g.getRank());

    }
}
