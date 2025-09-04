package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import org.json.*;

import model.Note;
import model.Tag;

// Represents a reader that reads an array of notes from JSON data stored in file
public class NoteJsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public NoteJsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads array of notes from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ArrayList<Note> read() throws IOException {
        String jsonData = readFile(source);
        JSONArray jsonArray = new JSONArray(jsonData);
        ArrayList<Note> notes = new ArrayList<>();
        for (Object obj : jsonArray) {
            JSONObject json = (JSONObject) obj;
            notes.add(parseNote(json));
        }

        return notes;
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses note from JSON object and returns it
    private Note parseNote(JSONObject jsonObject) {
        String name = jsonObject.getString("title");
        String groupName = jsonObject.getString("group");
        Note n = new Note(name);
        n.setGroup(groupName);
        addTexts(n, jsonObject);
        addTags(n, jsonObject);
        return n;
    }

    
    // MODIFIES: n
    // EFFECTS: parses texts from JSON object and adds it to note
    private void addTexts(Note n, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("texts");
        for (Object json : jsonArray) {
            JSONObject nextText = (JSONObject) json;
            addText(n, nextText);
        }
    }

    // MODIFIES: n
    // EFFECTS: prases text from JSON object and adds it to note
    private void addText(Note n, JSONObject jsonObject) {
        String text = jsonObject.getString("text");
        n.addText(text);     
    }

    // MODIFIES: n
    // EFFECTS: parses tag from JSON object and adds it to note
    private void addTags(Note n, JSONObject jsonObject) {
        JSONArray jsonArrayTag = jsonObject.getJSONArray("tags");

        for (Object json : jsonArrayTag) {
            JSONObject nextTag = (JSONObject) json;
            addTag(n, nextTag);
        }
    }

    // MODIFIES: n
    // EFFECTS: parses tag from JSON object and adds it to note
    private void addTag(Note n, JSONObject jsonObject) {
        String tagName = jsonObject.getString("name");
        JSONArray notes = jsonObject.getJSONArray("notes");

        Tag t = new Tag(tagName);
    
        for (Object json : notes) {
            t.addNote(json.toString());
        }
    
        n.addTag(t);
    }
}
