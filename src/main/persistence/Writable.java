package persistence;

import org.json.JSONObject;
// citation: JsonSerializationDemo from EdX, Phase 2
// JSON interface

public interface Writable {
    // Source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo, toJson()
    // EFFECTS: returns as JSON object
    JSONObject toJson();
}
