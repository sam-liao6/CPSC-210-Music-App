package model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

// This class represents a Note. A note could have a note group, tags, a list of strings representative of text.

public class Note implements Writable {

    private String title;
    private ArrayList<String> texts;
    private ArrayList<Tag> tags;
    private String group;

    // MODIFIES: this
    // EFFECTS: constructs a note, a newly formed note should have a unique title,
    // no texts, tags, or categories
    public Note(String title) {
        this.title = title;
        this.texts = new ArrayList<>();
        this.tags = new ArrayList<>();
        this.group = "";
    }

    // EFFECTS: gets the texts recorded in this note
    public ArrayList<String> getTexts() {
        return texts;
    }
    
    // MODIFIES: this
    // EFFECTS: adds a text to this object
    public void addText(String text) {
        EventLog.getInstance().logEvent(new Event("Text added to " + this.title));
        this.texts.add(text);
    }

    // EFFECTS: gets the title of this note
    public String getTitle() {
        return title;
    }

    // MODIFIES: this
    // EFFECTS: sets this object to string
    public void setTitle(String title) {
        EventLog.getInstance().logEvent(new Event(this.title + " changed to " + title));
        this.title = title;
    }

    // EFFECTS: gets the category this note belongs to
    public String getGroup() {
        return group;
    }

    // EFFECTS: gets the tags belonging to this note
    public ArrayList<Tag> getTags() {
        return tags;
    }

    // MODIFIES: this
    // EFFECTS: assigns this object to group 
    public void setGroup(String group) {
        EventLog.getInstance().logEvent(new Event(this.title + " set group to " + group));
        this.group = group;
    }

    // MODIFIES: this
    // EFFECTS: adds a tag to this object and assigned this object to the tag
    public void addTag(Tag tag) {
        this.tags.add(tag);
        tag.addNote(this.getTitle());
        EventLog.getInstance().logEvent(new Event(this.title + " added tag " + tag.getTagName()));
    }

    // REQUIRES: tags is not empty and that tag is a member of this object
    // MODIFIES: this
    // EFFECTS: removes a tag from this object
    public void removeTag(Tag tag) {
        this.tags.remove(tag);
        tag.removeNote(this.getTitle());
        EventLog.getInstance().logEvent(new Event(this.title + " removed tag " + tag.getTagName()));
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("group", group);
        json.put("texts", textsToJson());
        json.put("tags", tagsToJson());
        return json;
    }

    // EFFECTS: returns texts in this note as a JSON array
    private JSONArray textsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (String t : texts) {
            JSONObject json = new JSONObject();
            json.put("text", t);
            jsonArray.put(json);
        }

        return jsonArray;
    }

    // EFFECTS: returns tags in this note as a JSON array
    private JSONArray tagsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Tag t : tags) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }
}
