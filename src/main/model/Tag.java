package model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

// This class represents a Tag. A tag is assigned to notes and contains a string and 
// all the notes it is assigned to.

public class Tag implements Writable {
    
    private String tagName;
    private ArrayList<String> assignedNotes;

    // REQUIRES: given name is unique
    // MODIFIES: this
    // EFFECTS: constructs a tag, a newly formed
    // tag should have the given name and 0 notes assigned to it.
    public Tag(String tagName) { 
        this.tagName = tagName;
        this.assignedNotes = new ArrayList<>();
    }

    // EFFECTS: returns the tag name
    public String getTagName() {
        return tagName;
    }

    // EFFECTS: returns the list of titles of notes assigned to this tag
    public ArrayList<String> getAssignedNotes() {
        return assignedNotes;
    }

    // REQUIRES: note is not already a member of this object
    // MODIFIES: this
    // EFFECTS: adds a note to this object
    public void addNote(String note) {
        if (!assignedNotes.contains(note)) {
            this.assignedNotes.add(note);
            EventLog.getInstance().logEvent(new Event(this.getTagName() + " added note " + note));
        }
    }

    // REQUIRES: note is a member of this object
    // MODIFIES: this
    // EFFECTS: removes note from this object
    public void removeNote(String note) {
        this.assignedNotes.remove(note);
        EventLog.getInstance().logEvent(new Event(this.getTagName() + " removed note " + note));
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", tagName);
        json.put("notes", notesToJson());
        return json;
    }

    // EFFECTS: returns notes assigned to this tag as a JSON array
    private JSONArray notesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (String n : assignedNotes) {
            JSONObject json = new JSONObject();
            json.put("note", n);
            jsonArray.put(json);
        }

        return jsonArray;
    }
}
