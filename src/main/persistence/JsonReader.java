package persistence;

import model.Game;
import model.MatchHistory;
import org.json.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// citation: JsonSerializationDemo from EdX, Phase 2
// reader that reads tft app from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader for source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads and returns match history from file
    // IOException thrown if there's an error reading the file's data
    public MatchHistory read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObj = new JSONObject(jsonData);
        return parseMH(jsonObj);
    }


    // EFFECTS: reads and returns source as a string
    private String readFile(String source) throws IOException {
        StringBuilder builder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(builder::append);
        }

        return builder.toString();
    }

    // EFFECTS: parses and returns match history from JSON object
    private MatchHistory parseMH(JSONObject jsonObj) {
        MatchHistory mh = new MatchHistory();
        addGames(mh, jsonObj);
        return mh;
    }

    // MODIFIES: mh
    // EFFECTS: parses games from JSON object and adds them to match history
    private void addGames(MatchHistory mh, JSONObject jsonObj) {
        JSONArray jsonArray = jsonObj.getJSONArray("games");
        for (Object json : jsonArray) {
            JSONObject nextGame = (JSONObject) json;
            addGame(mh, nextGame);
        }
    }

    // MODIFIES: mh
    // EFFECTS: parses game from JSON object and adds it to match history
    private void addGame(MatchHistory mh, JSONObject jsonObj) {
        int rank = jsonObj.getInt("rank");
        String comp = jsonObj.getString("comp");
        Game game = new Game(rank, comp);
        mh.addGame(game);

    }
}
