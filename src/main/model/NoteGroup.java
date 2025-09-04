package model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

// This class represents a group of notes. A NoteGroup contains a string representative of the
// group name, a list of notes belonging to that group, and a list of tags assigned to the group

public class NoteGroup implements Writable {

    private String groupName;
    private ArrayList<Note> notes;
    private ArrayList<Tag> tags;
    
    // MODIFIES: this
    // EFFECTS: constructs a note group. A newly formed note group should have
    // a string, a empty list of notes belonging to the group, and a empty list of tags assigned to the group
    public NoteGroup(String groupName) {
        this.groupName = groupName;
        this.notes = new ArrayList<>();
        this.tags = new ArrayList<>();
    }

    // EFFECTS: gets the name of the group
    public String getGroupName() {
        return groupName;
    }

    // EFFECTS: sets this object to new group name
    public void setGroupName(String groupName) {
        EventLog.getInstance().logEvent(new Event(this.groupName + " name changed to " + groupName));
        this.groupName = groupName;
    }

    // EFFECTS: gets the notes member to this group
    public ArrayList<Note> getNotes() {
        return notes;
    }

    // EFFECTS: gets the tags assigned to this group
    public ArrayList<Tag> getTags() {
        return tags;
    }

    // MODIFIES: this
    // EFFECTS: adds a tag to this object and every note in this object and assigns every note in
    // this object to the tag
    public void addTag(Tag tag) {
        this.tags.add(tag);
        for (Note n : this.notes) {
            if (!n.getTags().contains(tag)) {
                n.addTag(tag);
            }
        }

        EventLog.getInstance().logEvent(new Event(this.groupName + " added tag " + tag.getTagName()));
    }

    // REQUIRES: tags is not empty
    // MODIFIES: this
    // EFFECTS: removes a tag from this object and every note in this object, deassigning every note
    // in this object from the tag.
    public void removeTag(Tag tag) {
        this.tags.remove(tag);

        for (Note n : this.notes) {
            if (n.getTags().contains(tag)) {
                n.removeTag(tag);
            }
        }

        EventLog.getInstance().logEvent(new Event(this.groupName + " removed tag " + tag.getTagName()));
    }

    // REQUIRES: note is not already a member
    // MODIFIES: this
    // EFFECTS: adds a note to this object and reassigns note to be in this group
    // and adds tags assigned to this group onto the note added
    public void addNote(Note note) {
        this.notes.add(note);
        if (!tags.isEmpty()) {
            for (Tag t : tags) {
                if (!note.getTags().contains(t)) {
                    note.addTag(t);
                }
            }
        }

        note.setGroup(this.groupName);
        EventLog.getInstance().logEvent(new Event(this.groupName + " added note " + note.getTitle()));
    }

    // REQUIRES: member notes is not empty and note is member of notes in this group
    // MODIFIES: this
    // EFFECTS: removes note from this object and reassigning note to be part of no group
    // and removes all tags assigned to this group from the note removed
    public void removeNote(Note note) {
        this.notes.remove(note);
        for (Tag t : tags) {
            if (note.getTags().contains(t)) {
                note.removeTag(t);
            }    
        }

        note.setGroup("");
        EventLog.getInstance().logEvent(new Event(this.groupName + " removed note " + note.getTitle()));
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", groupName);
        json.put("tags", tagsToJson());
        json.put("notes", notesToJson());
        return json;
    }

    // EFFECTS: returns the tags in this note group as a JSON array
    private JSONArray tagsToJson() {
        JSONArray json = new JSONArray();
        
        for (Tag t : tags) {
            json.put(t.toJson());
        }

        return json;
    }

    // EFFECTS: returns the notes in this note group as a JSON array
    private JSONArray notesToJson() {
        JSONArray json = new JSONArray();

        for (Note n : notes) {
            json.put(n.toJson());
        }

        return json;
    }
}
