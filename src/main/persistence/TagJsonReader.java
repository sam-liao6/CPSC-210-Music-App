package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import org.json.*;

import model.Tag;

// Represents a reader that reads a list of tags from JSON data stored in file
public class TagJsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public TagJsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads array of tags from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ArrayList<Tag> read() throws IOException {
        String jsonData = readFile(source);
        JSONArray jsonArray = new JSONArray(jsonData);
        ArrayList<Tag> tags = new ArrayList<>();
        for (Object obj : jsonArray) {
            JSONObject json = (JSONObject) obj;
            tags.add(parseTag(json));
        }

        return tags;
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses tag from JSON object and returns it
    private Tag parseTag(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Tag t = new Tag(name);
        addNotes(t, jsonObject);
        return t;
    }

    // MODIFIES: t
    // EFFECTS: parses note titles from JSON object and adds it to tag
    private void addNotes(Tag t, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("notes");
        for (Object json : jsonArray) {
            JSONObject nextNote = (JSONObject) json;
            addNote(t, nextNote);
        }
    }

    // MODIFIES: t
    // EFFECTS: prases note title from JSON object and adds it to tag
    private void addNote(Tag t, JSONObject jsonObject) {
        String noteName = jsonObject.getString("note");
        t.addNote(noteName);     
    }
}
