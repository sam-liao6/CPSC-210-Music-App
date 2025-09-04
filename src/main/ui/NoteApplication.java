package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import persistence.NoteGroupJsonReader;
import persistence.NoteGroupJsonWriter;
import persistence.NoteJsonReader;
import persistence.NoteJsonWriter;
import persistence.TagJsonReader;
import persistence.TagJsonWriter;
import model.NoteGroup;
import model.Tag;
import model.Note;


// note taking application
public class NoteApplication {
  
    private static final String NOTESJSON_STORE = "./data/notes.json";
    private static final String GROUPSJSON_STORE = "./data/groups.json";
    private static final String TAGSJSON_STORE = "./data/tags.json";
    private Scanner input;
    private ArrayList<Note> notes;
    private ArrayList<NoteGroup> groups;
    private ArrayList<Tag> tags;
    private NoteJsonWriter noteJsonWriter;
    private NoteJsonReader noteJsonReader;
    private NoteGroupJsonWriter noteGroupJsonWriter;
    private NoteGroupJsonReader noteGroupJsonReader;
    private TagJsonWriter tagJsonWriter;
    private TagJsonReader tagJsonReader;

    // EFFECTS: runs the note application
    public NoteApplication() {
        runApp();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runApp() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("n")) {
            displayNoteMenu();
            command = input.next();
            handleNoteCommand(command);
        } else if (command.equals("g")) {
            displayGroupMenu();
            command = input.next();
            handleGroupCommand(command);
        } else if (command.equals("t")) {
            displayTagMenu();
            command = input.next();
            handleTagCommand(command);
        } else if (command.equals("s")) {
            saveCurrentData();
        } else if (command.equals("l")) {
            loadData();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: initalizes and runs application
    private void init() {
        tags = new ArrayList<>();
        notes = new ArrayList<>();
        groups = new ArrayList<>();
        input = new Scanner(System.in);
        noteJsonWriter = new NoteJsonWriter(NOTESJSON_STORE);
        noteJsonReader = new NoteJsonReader(NOTESJSON_STORE);
        noteGroupJsonWriter = new NoteGroupJsonWriter(GROUPSJSON_STORE);
        noteGroupJsonReader = new NoteGroupJsonReader(GROUPSJSON_STORE);
        tagJsonWriter = new TagJsonWriter(TAGSJSON_STORE);
        tagJsonReader = new TagJsonReader(TAGSJSON_STORE);
        input.useDelimiter("\r?\n|\r");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nWhat would you like to change or do?:");
        System.out.println("\tn -> note");
        System.out.println("\tg -> group");
        System.out.println("\tt -> tag");
        System.out.println("\ts -> save");
        System.out.println("\tl -> load");
        System.out.println("\tq -> quit");
    }

    // EFFECTS: displays menu of note to user
    private void displayNoteMenu() {
        System.out.println("\nWhat would you like to do?:");
        System.out.println("\tcn -> create note");
        System.out.println("\tast -> assign tag");
        System.out.println("\trmvt -> remove tag");
        System.out.println("\tat -> add text");
        System.out.println("\tdt -> display text");
        System.out.println("\tq -> quit");
    }

    // EFFECTS: displays menu of group to user
    private void displayGroupMenu() {
        System.out.println("\nWhat would you like to do?:");
        System.out.println("\tcg -> create group");
        System.out.println("\tadt -> add tag");
        System.out.println("\trmvt -> remove tag");
        System.out.println("\tan -> add note");
        System.out.println("\tdng -> display notes in this group");
        System.out.println("\tq -> quit");
    }

    // EFFECTS: displays menu of tag to user
    private void displayTagMenu() {
        System.out.println("\nWhat would you like to do?:");
        System.out.println("\tct -> create tag");
        System.out.println("\tdnt -> display notes assigned to tag");
        System.out.println("\tq -> quit");
    }

    // EFFECTS: handles the given command for a note
    private void handleNoteCommand(String command) {
        if (command.equals("cn")) {
            createNote();
        } else if (command.equals("ast")) {
            addTagToNote();
        } else if (command.equals("at")) {
            addTextToNote();
        } else if (command.equals("rmvt")) {
            removeTagFromNote();
        } else if (command.equals("dt")) {
            displayText();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: handles the given command for a note group
    private void handleGroupCommand(String command) {
        if (command.equals("cg")) {
            createGroup();
        } else if (command.equals("adt")) {
            addTagToGroup();
        } else if (command.equals("rmvt")) {
            removeTagFromGroup();
        } else if (command.equals("an")) {
            addNoteToGroup();
        } else if (command.equals("dng")) {
            displayNotesToGroup();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: handles the given command for a tag
    private void handleTagCommand(String command) {
        if (command.equals("ct")) {
            createTag();
        } else if (command.equals("dnt")) {
            displayNotesToTag();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a note and adds it to this object
    private void createNote() {
        boolean possible = false;
        System.out.println("\nTitle the note: ");
        String name = input.next();
        while (!possible) {
            possible = true;
            for (Note n : notes) {
                if (name.equals(n.getTitle())) {
                    possible = false;
                    System.out.println("\tTitle is already being used");
                    System.out.println("\tTitle the note: ");
                    name = input.next();
                }
            }
        }

        notes.add(new Note(name));
    }

    // MODIFIES: this
    // EFFECTS: assigns a tag to a given note
    private void addTagToNote() {
        System.out.println("\nEnter the title of the note you wish to tag");
        String noteName = input.next();
        for (Note n : notes) {
            if (n.getTitle().equals(noteName)) {
                System.out.println("\tEnter the tag name");
                String tagName = input.next();
                for (Tag t : tags) {
                    if (t.getTagName().equals(tagName)) {
                        n.addTag(t);
                        break;
                    }
                }
                
                System.out.println("Tag does not exist");
            }
        }

    }

    // MODIFIES: this
    // EFFECTS: deassigns a tag from a note
    private void removeTagFromNote() {
        System.out.println("\nEnter the title of the note you wish to modify");
        String noteName = input.next();
        for (Note n : notes) {
            if (n.getTitle().equals(noteName)) {
                System.out.println("\tEnter the tag you wish to remove");
                String tagName = input.next();
                for (Tag t : tags) {
                    if (t.getTagName().equals(tagName)) {
                        if (n.getTags().contains(t)) {
                            n.removeTag(t);
                        }
                    }
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: adds text to a given note
    private void addTextToNote() {
        System.out.println("\nEnter the title of the note you wish to modify");
        String noteName = input.next();
        for (Note n : notes) {
            if (noteName.equals(n.getTitle())) {
                System.out.println("\tEnter your notes");
                n.addText(input.next());
            }
        }
    }
    
    // EFFECTS: displays all the text attached to a note
    private void displayText() {
        System.out.println("\nEnter the title of the note you wish to display");
        String noteName = input.next();
        for (Note n : notes) {
            if (n.getTitle().equals(noteName)) {
                for (String t : n.getTexts()) {
                    System.out.println("\t" + t);
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a new note group and adds it to this object
    private void createGroup() {
        boolean possible = false;
        System.out.println("\nEnter the title of the group you wish to create");
        String groupName = input.next();
        while (!possible) {
            possible = true;
            for (NoteGroup g : groups) {
                if (g.getGroupName().equals(groupName)) {
                    possible = false;
                    System.out.println("\tGroup already exists");
                    System.out.println("\tEnter the title of the group you wish to create");
                    groupName = input.next();
                }
            }  
        }

        groups.add(new NoteGroup(groupName));
    }

    // MODIFIES: this
    // EFFECTS: assigns a tag to a group
    private void addTagToGroup() {
        System.out.println("\nEnter the title of the group you wish to modify");
        String groupName = input.next();
        for (NoteGroup g : groups) {
            if (g.getGroupName().equals(groupName)) {
                System.out.println("\tEnter the tag you wish to add");
                String tagName = input.next();
                for (Tag t : tags) {
                    if (t.getTagName().equals(tagName)) {
                        g.addTag(t);
                    }
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: deassigns a tag from a group
    private void removeTagFromGroup() {
        System.out.println("\nEnter the title of the group you wish to modify");
        String groupName = input.next();
        for (NoteGroup g : groups) {
            if (g.getGroupName().equals(groupName)) {
                System.out.println("\tEnter the tag you wish to remove");
                String tagName = input.next();
                for (Tag t : g.getTags()) {
                    if (t.getTagName().equals(tagName)) {
                        g.removeTag(t);
                        break;
                    }
                }
            }
        }
    }
    
    // MODIFIES: this
    // EFFECTS: adds a note to a group
    private void addNoteToGroup() {
        System.out.println("\nEnter the title of the group you wish to modify");
        String groupName = input.next();
        for (NoteGroup g : groups) {
            if (g.getGroupName().equals(groupName)) {
                System.out.println("\tEnter the title of the note you wish to add");
                String noteName = input.next();
                for (Note n : notes) {
                    if (n.getTitle().equals(noteName)) {
                        g.addNote(n);
                    }
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: displays all notes assigned to given group
    private void displayNotesToGroup() {
        System.out.println("\nEnter the title of the group you wish to display");
        String groupName = input.next();
        for (NoteGroup g : groups) {
            if (g.getGroupName().equals(groupName)) {
                for (Note n : g.getNotes()) {
                    System.out.println("\t" + n.getTitle());
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a tag and adds it to this object
    private void createTag() {
        boolean possible = false;
        System.out.println("\nEnter the name of the tag you wish to create");
        String tagName = input.next();
        while (!possible) {
            possible = true;
            for (Tag t : tags) {
                if (t.getTagName().equals(tagName)) {
                    possible = false;
                    System.out.println("\tTag already exists");
                    System.out.println("\tEnter the title of the tag you wish to create");
                    tagName = input.next();
                }
            }  
        }

        tags.add(new Tag(tagName));
    }

    // EFFECTS: displays all notes assigned to given tag
    private void displayNotesToTag() {
        System.out.println("\nEnter the title of the tag you wish to display");
        String tagName = input.next();
        for (Tag t : tags) {
            if (t.getTagName().equals(tagName)) {
                for (String n : t.getAssignedNotes()) {
                    System.out.println("\t" + n);
                }
            }
        }
    }

    // EFFECTS: saves the current workspace to file
    private void saveCurrentData() {
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
    private void loadData() {
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
}
