package persistence;

import model.MatchHistory;
import org.json.JSONObject;

import java.io.*;
// citation: JsonSerializationDemo from EdX, Phase 2
// A writer that writes JSON representation of match history to file

public class JsonWriter {
    private static final int INDENT = 4;
    private PrintWriter writer;
    private String destination;

    // Source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo, JsonWriter()
    // EFFECTS: constructs writer that writes to dest. file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // Source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo, open()
    // MODIFIES: this
    // EFFECTS: opens writer and throws FileNotFoundException if destination file not found
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // Source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo, write()
    // MODIFIES: this
    // EFFECTS: writes JSON representation of match history to file
    public void write(MatchHistory mh) {
        JSONObject json = mh.toJson();
        saveToFile(json.toString(INDENT));
    }

    // Source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo, saveToFile()
    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }

    // Source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo, close()
    // MODIFIES: this
    // EFFECTS: closes JSON writer
    public void close() {
        writer.close();
    }


}
