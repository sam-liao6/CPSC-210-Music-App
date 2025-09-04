package ui.tabs;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import model.Note;
import model.NoteGroup;
import model.Tag;
import ui.NoteAppUI;

// Represents a note group editor UI for editing a given note group
public class NoteGroupEditor extends Tab {

    private NoteGroup group;
    private ArrayList<Tag> tags;
    private ArrayList<Note> notes;
    private ArrayList<Tag> assignedTags;
    private ArrayList<Note> assignedNotes;
    
    // EFFECTS: constructs a note group editor GUI tab
    public NoteGroupEditor(NoteAppUI controller, NoteGroup group) {
        super(controller);

        this.group = group;

        tags = controller.getTags();
        notes = controller.getNotes();
        assignedTags = group.getTags();
        assignedNotes = group.getNotes();

        displayAssignedNotes();
        displayAssignedTags();
        displayUnassignedNotes();
        displayUnassignedTags();
    }

    // MODIFIES: this
    // EFFECTS: adds assigned tags panel to display
    public void displayAssignedTags() {
        JLabel assignedTagsLabel = new JLabel("Assigned Tags: ");
        JList<String> assignedTagsJList = new JList<>(tagsToName(assignedTags));
        JButton removeTags = new JButton("Remove Selected Tag(s)");
        JPanel assignedTagsPanel = new JPanel(); 
        assignedTagsPanel.setLayout(new GridLayout(3, 1));
        assignedTagsPanel.add(assignedTagsLabel);
        assignedTagsPanel.add(assignedTagsJList);
        assignedTagsPanel.add(removeTags);
        removeTagButtonListener(removeTags, assignedTagsJList);

        
        this.add(assignedTagsPanel);
    }

    // MODIFIES: this
    // EFFECTS: adds assigned notes panel to display
    public void displayAssignedNotes() {
        JLabel assignedNotesLabel = new JLabel("Assigned Notes: ");
        JList<String> assignedNotesJList = new JList<>(notesToName(assignedNotes));
        JButton removeNotes = new JButton("Remove Selected Note(s)");
        JPanel assignedNotesPanel = new JPanel(); 
        assignedNotesPanel.setLayout(new GridLayout(3, 1));
        assignedNotesPanel.add(assignedNotesLabel);
        assignedNotesPanel.add(assignedNotesJList);
        assignedNotesPanel.add(removeNotes);
        removeNoteButtonListener(removeNotes, assignedNotesJList);

        this.add(assignedNotesPanel);
    }

    // MODIFIES: this
    // EFFECTS: adds unassigned tags panel to display
    public void displayUnassignedTags() {
        JLabel unassignedTagsLabel = new JLabel("Unassigned Tags: ");
        DefaultListModel<String> assignedTagNames = tagsToName(assignedTags);
        ArrayList<Tag> unassignedTags = new ArrayList<>();
        for (Tag t : tags) {
            if (!assignedTagNames.contains(t.getTagName())) {
                unassignedTags.add(t);
            }
        }

        JList<String> unassignedTagsJList = new JList<>(tagsToName(unassignedTags));
        JButton addTags = new JButton("Add Selected Tag(s)");
        JPanel unassignedTagsPanel = new JPanel(); 
        unassignedTagsPanel.setLayout(new GridLayout(3, 1));
        unassignedTagsPanel.add(unassignedTagsLabel);
        unassignedTagsPanel.add(unassignedTagsJList);
        unassignedTagsPanel.add(addTags);
        addTagButtonListener(addTags, unassignedTagsJList);

        this.add(unassignedTagsPanel);
    }

    // MODIFIES: this
    // EFFECTS: adds unassigned notes button to display
    public void displayUnassignedNotes() {
        JLabel unassignedNotesLabel = new JLabel("Unassigned Notes: ");
        DefaultListModel<String> assignedNoteTitles = notesToName(assignedNotes);
        ArrayList<Note> unassignedNotes = new ArrayList<>();
        for (Note n : notes) {
            if (!assignedNoteTitles.contains(n.getTitle())) {
                unassignedNotes.add(n);
            }
        }

        JList<String> unassignedNotesJList = new JList<>(notesToName(unassignedNotes));
        JButton addNotes = new JButton("Add Selected Note(s)");
        JPanel unassignedNotesPanel = new JPanel(); 
        unassignedNotesPanel.setLayout(new GridLayout(3, 1));
        unassignedNotesPanel.add(unassignedNotesLabel);
        unassignedNotesPanel.add(unassignedNotesJList);
        unassignedNotesPanel.add(addNotes);
        addNoteButtonListener(addNotes, unassignedNotesJList);

        this.add(unassignedNotesPanel);
    }

    // EFFECTS: adds a listener for remove tag button
    private void removeTagButtonListener(JButton addTag, JList<String> listTag) {
        addTag.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                if (buttonPressed.equals("Remove Selected Tag(s)")) {
                    
                    // adding functionality 
                    DefaultListModel<String> tagNames = tagsToName(assignedTags);

                    for (String t : listTag.getSelectedValuesList()) {
                        group.removeTag(assignedTags.get(tagNames.indexOf(t)));
                    }
                    
                    JTabbedPane tabbedPane = getController().getTabbedPane();
                    tabbedPane.remove(tabbedPane.getSelectedComponent());
                    getController().loadNoteGroupEditor(group);
                    tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
                }
            }
        });
    }
    
    // EFFECTS: adds a listener for remove note button
    private void removeNoteButtonListener(JButton addNote, JList<String> listNote) {
        addNote.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                if (buttonPressed.equals("Remove Selected Note(s)")) {
                    
                    // adding functionality 
                    DefaultListModel<String> noteNames = notesToName(assignedNotes);

                    for (String n : listNote.getSelectedValuesList()) {
                        group.removeNote(assignedNotes.get(noteNames.indexOf(n)));
                    }
                    
                    JTabbedPane tabbedPane = getController().getTabbedPane();
                    tabbedPane.remove(tabbedPane.getSelectedComponent());
                    getController().loadNoteGroupEditor(group);
                    tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
                }
            }
        });
    }

    // EFFECTS: adds a listener for add tag button
    private void addTagButtonListener(JButton addTag, JList<String> listTag) {
        addTag.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                if (buttonPressed.equals("Add Selected Tag(s)")) {
                    
                    DefaultListModel<String> tagNames = tagsToName(tags);
                    DefaultListModel<String> assignedTagNames = tagsToName(assignedTags);

                    for (String t : listTag.getSelectedValuesList()) {
                        if (!assignedTagNames.contains(t)) {
                            group.addTag(getController().getTags().get(tagNames.indexOf(t)));
                        }
                    }
                    
                    JTabbedPane tabbedPane = getController().getTabbedPane();
                    tabbedPane.remove(tabbedPane.getSelectedComponent());
                    getController().loadNoteGroupEditor(group);
                    tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
                }
            }
        });
    }
    
    // EFFECTS: adds a listener for add note button
    private void addNoteButtonListener(JButton addNote, JList<String> listNote) {
        addNote.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                if (buttonPressed.equals("Add Selected Note(s)")) {
                    
                    DefaultListModel<String> noteNames = notesToName(notes);
                    DefaultListModel<String> assignedNoteNames = notesToName(assignedNotes);

                    for (String n : listNote.getSelectedValuesList()) {
                        if (!assignedNoteNames.contains(n)) {
                            group.addNote(getController().getNotes().get(noteNames.indexOf(n)));
                        }
                    }
                    
                    JTabbedPane tabbedPane = getController().getTabbedPane();
                    tabbedPane.remove(tabbedPane.getSelectedComponent());
                    getController().loadNoteGroupEditor(group);
                    tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
                }
            }
        });
    }

    // EFFECTS: converts tags to tag names and returns a new array of tag names
    private DefaultListModel<String> tagsToName(ArrayList<Tag> tags) {
        DefaultListModel<String> model = new DefaultListModel<>();
        for (Tag t : tags) {
            model.addElement(t.getTagName());
        }

        return model;
    }

    // EFFECTS: converts notes to note titles and returns a new array of note titles
    private DefaultListModel<String> notesToName(ArrayList<Note> notes) {
        DefaultListModel<String> model = new DefaultListModel<>();
        for (Note n : notes) {
            model.addElement(n.getTitle());
        }

        return model;
    }
}
