package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import org.json.*;

import model.Note;
import model.NoteGroup;
import model.Tag;

// Represents a reader that reads a list of note groups from JSON data stored in file
public class NoteGroupJsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public NoteGroupJsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads array of note groups from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ArrayList<NoteGroup> read() throws IOException {
        String jsonData = readFile(source);
        JSONArray jsonArray = new JSONArray(jsonData);
        ArrayList<NoteGroup> ng = new ArrayList<>();
        for (Object obj : jsonArray) {
            JSONObject json = (JSONObject) obj;
            ng.add(parseNoteGroup(json));
        }

        return ng;
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses note group from JSON object and returns it
    private NoteGroup parseNoteGroup(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        NoteGroup ng = new NoteGroup(name);
        addNotes(ng, jsonObject);
        addTagsToGroup(ng, jsonObject);
        return ng;
    }

    // MODIFIES: ng
    // EFFECTS: parses note titles from JSON object and adds it to tag
    private void addNotes(NoteGroup ng, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("notes");
        for (Object json : jsonArray) {
            JSONObject nextNote = (JSONObject) json;
            addNote(ng, nextNote);
        }
    }

    // MODIFIES: ng
    // EFFECTS: parses note from JSON object and adds it to note group
    private void addNote(NoteGroup ng, JSONObject jsonObject) {
        String name = jsonObject.getString("title");
        String groupName = jsonObject.getString("group");
        Note n = new Note(name);
        n.setGroup(groupName);
        addTexts(n, jsonObject);
        addTags(n, jsonObject);
        ng.addNote(n);
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

        for (Object obj : notes) {
            JSONObject json = (JSONObject) obj;
            String noteName = json.getString("note");
            t.addNote(noteName);
        }
    
        n.addTag(t);
    }

    // EFFECTS: parses an array of tags from JSON object and adds it to note group
    private void addTagsToGroup(NoteGroup ng, JSONObject jsonObject) {
        JSONArray jsonArrayTag = jsonObject.getJSONArray("tags");

        for (Object json : jsonArrayTag) {
            JSONObject nextTag = (JSONObject) json;
            addTagToGroup(ng, nextTag);
        }
    }

    // MODIFIES: ng
    // EFFECTS: parses tag from JSON object and adds it to note group
    private void addTagToGroup(NoteGroup ng, JSONObject jsonObject) {
        String tagName = jsonObject.getString("name");
        JSONArray notes = jsonObject.getJSONArray("notes");

        Tag t = new Tag(tagName);
    
        for (Object obj : notes) {
            JSONObject json = (JSONObject) obj;
            String noteName = json.getString("note");
            t.addNote(noteName);
        }
    
        ng.addTag(t);
    }
}
