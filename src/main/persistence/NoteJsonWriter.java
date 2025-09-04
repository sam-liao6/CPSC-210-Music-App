package persistence;

import org.json.JSONArray;

import model.Note;

import java.io.*;
import java.util.ArrayList;

// Represents a writer that writes JSON representation of a list of notes to file
public class NoteJsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public NoteJsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of array of notes to file
    public void write(ArrayList<Note> notes) {
        JSONArray jsonArray = new JSONArray();
        
        for (Note n : notes) {
            jsonArray.put(n.toJson());
        }

        saveToFile(jsonArray.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
