package ui;

import model.Event;
import model.EventLog;
import model.Note;
import model.NoteGroup;
import model.Tag;
import persistence.NoteGroupJsonReader;
import persistence.NoteGroupJsonWriter;
import persistence.NoteJsonReader;
import persistence.NoteJsonWriter;
import persistence.TagJsonReader;
import persistence.TagJsonWriter;
import ui.tabs.NoteEditor;
import ui.tabs.NoteGroupEditor;
import ui.tabs.NoteGroupTab;
import ui.tabs.NoteTab;
import ui.tabs.TagTab;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.*;

// EFFECTS: represents the note application UI capable of displaying all the tabs and interacting with the user 
public class NoteAppUI extends JFrame implements WindowListener {
    public static final int NOTE_TAB_INDEX = 0;
    public static final int TAG_TAB_INDEX = 1;
    public static final int GROUP_TAB_INDEX = 2;

    private static final String NOTESJSON_STORE = "./data/notes.json";
    private static final String GROUPSJSON_STORE = "./data/groups.json";
    private static final String TAGSJSON_STORE = "./data/tags.json";

    public static final int WIDTH = 600;
    public static final int HEIGHT = 600;
    
    private NoteJsonWriter noteJsonWriter;
    private NoteJsonReader noteJsonReader;
    private NoteGroupJsonWriter noteGroupJsonWriter;
    private NoteGroupJsonReader noteGroupJsonReader;
    private TagJsonWriter tagJsonWriter;
    private TagJsonReader tagJsonReader;
    private ArrayList<Note> notes;
    private ArrayList<NoteGroup> groups;
    private ArrayList<Tag> tags;
    private JTabbedPane topbar;

    public static void main(String[] args) {
        new NoteAppUI();
    }

    // MODIFIES: this
    // EFFECTS: creates SmartHomeUI, loads SmartHome appliances, displays sidebar and tabs
    private NoteAppUI() {
        super("Noter");
        addWindowListener(this);
        setup();
    }

    // MODIFIES: this
    // EFFECTS: clears this and reloads tabs
    public void refresh() {
        topbar.removeAll();  
        loadTabs();
    }

    // MODIFIES: this
    // EFFECTS: clears this and reloads tabs
    public void reload() {
        topbar.removeAll();  
        loadData();
        loadTabs();
    }

    // MODIFIES: this
    // EFFECTS: setups the base GUI
    private void setup() {
        init();
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        loadData();
        topbar = new JTabbedPane();
        topbar.setTabPlacement(JTabbedPane.TOP);
        loadTabs();
        add(topbar);
        setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: adds note tab, tag tab and group tab to this UI
    public void loadTabs() {
        JPanel noteTab = new NoteTab(this);
        JPanel tagTab = new TagTab(this);
        JPanel noteGroupTab = new NoteGroupTab(this);

        topbar.add(noteTab, NOTE_TAB_INDEX);
        topbar.add(tagTab, TAG_TAB_INDEX);
        topbar.add(noteGroupTab, GROUP_TAB_INDEX);
        topbar.setTitleAt(NOTE_TAB_INDEX, "Notes");
        topbar.setTitleAt(TAG_TAB_INDEX, "Tags");
        topbar.setTitleAt(GROUP_TAB_INDEX, "Groups");
    }

    // MODIFIES: this
    // EFFECTS: adds note tab to this displaying notes given
    public void loadNoteTab(ArrayList<Note> tempNotes) {
        topbar.add(new NoteTab(this, tempNotes), NOTE_TAB_INDEX);
        topbar.setTitleAt(NOTE_TAB_INDEX, "Notes");
    }

    // MODIFIES: this
    // EFFECTS: adds note group tab to this displaying groups given
    public void loadNoteGroupTab(ArrayList<NoteGroup> tempGroups) {
        topbar.add(new NoteGroupTab(this, tempGroups), GROUP_TAB_INDEX);
        topbar.setTitleAt(GROUP_TAB_INDEX, "Groups");
    }

    // MODIFIES: this
    // EFFECTS: adds tags tab to this displaying tags given
    public void loadTagTab(ArrayList<Tag> tempTags) {
        topbar.add(new TagTab(this, tempTags), TAG_TAB_INDEX);
        topbar.setTitleAt(TAG_TAB_INDEX, "Tags");
    }

    // MODIFIES: this
    // EFFECTS: adds note group editor tab to this UI
    public void loadNoteGroupEditor(NoteGroup group) {
        JPanel noteTab = new NoteGroupEditor(this, group);

        topbar.add(noteTab);
        topbar.setTitleAt(topbar.indexOfComponent(noteTab), group.getGroupName());
    }

    // MODIFIES: this
    // EFFECTS: adds note editor tab to this UI
    public void loadNoteEditor(Note note) {
        JPanel noteTab = new NoteEditor(this, note);

        topbar.add(noteTab);
        topbar.setTitleAt(topbar.indexOfComponent(noteTab), note.getTitle());
    }

    // MODIFIES: this
    // EFFECTS: redisplays note tab, tag tab, and group tab to this UI
    public void updateTabs() {
        topbar.setComponentAt(NOTE_TAB_INDEX, new NoteTab(this));
        topbar.setComponentAt(TAG_TAB_INDEX, new TagTab(this));
        topbar.setComponentAt(GROUP_TAB_INDEX, new NoteGroupTab(this));
    }

    //EFFECTS: returns topbar of this UI
    public JTabbedPane getTabbedPane() {
        return topbar;
    }

    // EFFECTS: saves the current workspace to file
    public void saveCurrentData() {
        try {
            noteJsonWriter.open();
            noteJsonWriter.write(notes);
            noteJsonWriter.close();
            tagJsonWriter.open();
            tagJsonWriter.write(tags);
            tagJsonWriter.close();
            noteGroupJsonWriter.open();
            noteGroupJsonWriter.write(groups);
            noteGroupJsonWriter.close();
            System.out.println("Saved all data to file");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to files");
        }
    }

    // MODIFIES: this
    // EFFECTS: loads data from file
    public void loadData() {
        try {
            notes = noteJsonReader.read();
            System.out.println("Loaded notes from " + NOTESJSON_STORE);
            tags = tagJsonReader.read();
            System.out.println("Loaded tags from " + TAGSJSON_STORE);
            groups = noteGroupJsonReader.read();
            System.out.println("Loaded groups from " + GROUPSJSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file");
        }
    }

    // REQUIRES: this contains tag
    // EFFECTS: removes tag from this
    public void removeTag(Tag tag) {
        tags.remove(tag);
    }

    // REQUIRES: this contains tag
    // EFFECTS: removes tag from this
    public void removeNote(Note note) {
        notes.remove(note);
    }

    // REQUIRES: this contains tag
    // EFFECTS: removes tag from this
    public void removeGroup(NoteGroup group) {
        groups.remove(group);
    }

    // EFFECTS: returns tags
    public ArrayList<Tag> getTags() {
        return tags;
    }

    // EFFECTS: returns notes
    public ArrayList<Note> getNotes() {
        return notes;
    }

    // EFFECTS: returns noteGroups
    public ArrayList<NoteGroup> getGroups() {
        return groups;
    }

    // MODIFIES: this
    // EFFECTS: initalizes variables
    private void init() {
        tags = new ArrayList<>();
        notes = new ArrayList<>();
        groups = new ArrayList<>();
        noteJsonWriter = new NoteJsonWriter(NOTESJSON_STORE);
        noteJsonReader = new NoteJsonReader(NOTESJSON_STORE);
        noteGroupJsonWriter = new NoteGroupJsonWriter(GROUPSJSON_STORE);
        noteGroupJsonReader = new NoteGroupJsonReader(GROUPSJSON_STORE);
        tagJsonWriter = new TagJsonWriter(TAGSJSON_STORE);
        tagJsonReader = new TagJsonReader(TAGSJSON_STORE);
    }

    // MODIFIES: this
    // EFFECTS: adds a note to this object
    public void addNote(Note note) {
        notes.add(note);
    }

    // MODIFIES: this
    // EFFECTS: adds a note to this object
    public void addNoteGroup(NoteGroup group) {
        groups.add(group);
    }

    // MODIFIES: this
    // EFFECTS: adds a tag to this object
    public void addTag(Tag tag) {
        tags.add(tag);
    }

    @Override
    public void windowClosed(WindowEvent e) {
        return;
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        return;
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        return;
    }

    @Override
    public void windowOpened(WindowEvent e) {
        return;
    }

    @Override
    public void windowClosing(WindowEvent e) {
        Iterator<Event> events = EventLog.getInstance().iterator();
        while (events.hasNext()) {
            System.out.println(events.next());
        }
    }

    @Override
    public void windowIconified(WindowEvent e) {
        return;
    }

    @Override
    public void windowActivated(WindowEvent e) {
        return;
    }

    
}