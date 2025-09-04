package persistence;

import org.json.JSONArray;

import model.NoteGroup;

import java.io.*;
import java.util.ArrayList;

// Represents a writer that writes JSON representation of a list of tags to file
public class NoteGroupJsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public NoteGroupJsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of array of note group to file
    public void write(ArrayList<NoteGroup> groups) {
        JSONArray jsonArray = new JSONArray();
        
        for (NoteGroup ng : groups) {
            jsonArray.put(ng.toJson());
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
