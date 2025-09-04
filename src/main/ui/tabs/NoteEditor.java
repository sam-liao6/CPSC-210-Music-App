package ui.tabs;

import java.awt.FlowLayout;
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
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;

import model.Note;
import model.NoteGroup;
import model.Tag;
import ui.NoteAppUI;

// Represents a note editor UI for editing a given note
public class NoteEditor extends Tab {

    private Note note;
    private ArrayList<Tag> tags;
    private ArrayList<NoteGroup> groups;
    private ArrayList<Tag> assignedTags;
    private String group;
    
    // EFFECTS: constructs a note editor GUI tab
    public NoteEditor(NoteAppUI controller, Note note) {
        super(controller);

        this.note = note;

        tags = controller.getTags();
        groups = controller.getGroups();
        assignedTags = note.getTags();
        group = note.getGroup();

        JPanel groupsPanel = new JPanel();
        groupsPanel.setLayout(new FlowLayout());
        groupsPanel.add(displayAssignedGroup());
        groupsPanel.add(displayUnassignedNoteGroup());
        JPanel tagsPanel = new JPanel();
        tagsPanel.setLayout(new FlowLayout());
        tagsPanel.add(displayAssignedTags());
        tagsPanel.add(displayUnassignedTags());

        JTextArea text = new JTextArea(textsToString(), 30, 30);

        this.add(groupsPanel);
        this.add(tagsPanel);
        this.add(text);
    }

    // EFFECTS: converts all texts assigned to this note into a single string
    private String textsToString() {
        String text = "";
        for (String t : note.getTexts()) {
            text = text + t;
        }

        return text;
    }

    // EFFECTS: returns assigned group panel 
    private JPanel displayAssignedGroup() {
        JLabel assignedGroupLabel = new JLabel("Assigned to group: ");
        JLabel assignedGroupName = new JLabel(group);
        JButton removeGroup = new JButton("Remove group assignment");
        JPanel topGroupPanel = new JPanel();
        JPanel assignedGroupPanel = new JPanel();
        
        removeGroupAddListener(removeGroup);

        topGroupPanel.setLayout(new FlowLayout());
        topGroupPanel.add(assignedGroupLabel);
        topGroupPanel.add(assignedGroupName);
        assignedGroupPanel.setLayout(new GridLayout(2, 1));
        assignedGroupPanel.add(topGroupPanel);
        assignedGroupPanel.add(removeGroup);

        return assignedGroupPanel;
    }

    // EFFECTS: adds listener for remove group button
    public void removeGroupAddListener(JButton removeGroup) {
        removeGroup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                if (buttonPressed.equals("Remove group assignment")) {
                    DefaultListModel<String> groupNames = groupsToName(groups);
                    note.setGroup("");
                    groups.get(groupNames.indexOf(group)).removeNote(note);

                    JTabbedPane tabbedPane = getController().getTabbedPane();
                    tabbedPane.remove(tabbedPane.getSelectedComponent());
                    getController().loadNoteEditor(note);
                    tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
                }
            }
        });
    }



    // EFFECTS: returns unassigned group(s) panel
    public JPanel displayUnassignedNoteGroup() {
        JLabel unassignedNoteGroupsLabel = new JLabel("Available groups: ");
        ArrayList<NoteGroup> unassignedGroups = new ArrayList<>();
        for (NoteGroup g : groups) {
            if (!group.equals(g.getGroupName())) {
                unassignedGroups.add(g);
            }
        }

        JList<String> unassignedGroupsJList = new JList<>(groupsToName(unassignedGroups));
        JButton addGroup = new JButton("Add Selected Group(s)");
        JPanel unassignedGroupsPanel = new JPanel(); 
        unassignedGroupsPanel.setLayout(new GridLayout(3, 1));
        unassignedGroupsPanel.add(unassignedNoteGroupsLabel);
        unassignedGroupsPanel.add(unassignedGroupsJList);
        unassignedGroupsPanel.add(addGroup);
        unassignedGroupsJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        addGroupButtonListener(addGroup, unassignedGroupsJList);

        return unassignedGroupsPanel;
    }

    // EFFECTS: returns the panel for assigned tags
    private JPanel displayAssignedTags() {
        JLabel assignedTagsLabel = new JLabel("Assigned Tags: ");
        JList<String> assignedTagsJList = new JList<>(tagsToName(assignedTags));
        JButton removeTags = new JButton("Remove Selected Tag(s)");
        JPanel assignedTagsPanel = new JPanel(); 
        assignedTagsPanel.setLayout(new GridLayout(3, 1));
        assignedTagsPanel.add(assignedTagsLabel);
        assignedTagsPanel.add(assignedTagsJList);
        assignedTagsPanel.add(removeTags);
        removeTagButtonListener(removeTags, assignedTagsJList);

        
        return assignedTagsPanel;
    }

    // EFFECTS: returns the panel for unassigned tags
    private JPanel displayUnassignedTags() {
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

        return unassignedTagsPanel;
    }

    // EFFECTS: adds a listener for remove tag button
    private void removeTagButtonListener(JButton removeTag, JList<String> listTag) {
        removeTag.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                if (buttonPressed.equals("Remove Selected Tag(s)")) {
                    
                    DefaultListModel<String> tagNames = tagsToName(assignedTags);

                    for (String t : listTag.getSelectedValuesList()) {
                        note.removeTag(assignedTags.get(tagNames.indexOf(t)));
                    }
                    
                    JTabbedPane tabbedPane = getController().getTabbedPane();
                    tabbedPane.remove(tabbedPane.getSelectedComponent());
                    getController().loadNoteEditor(note);
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
                            note.addTag(getController().getTags().get(tagNames.indexOf(t)));
                        }
                    }
                    
                    JTabbedPane tabbedPane = getController().getTabbedPane();
                    tabbedPane.remove(tabbedPane.getSelectedComponent());
                    getController().loadNoteEditor(note);
                    tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
                }
            }
        });
    }

    // EFFECTS: adds a listener for add group button
    private void addGroupButtonListener(JButton addGroup, JList<String> listGroup) {
        addGroup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                if (buttonPressed.equals("Add Selected Group(s)")) {
                    
                    DefaultListModel<String> groupNames = groupsToName(groups);
                    if (groupNames.indexOf(group) != -1) {
                        groups.get(groupNames.indexOf(group)).removeNote(note);
                    }

                    String groupName = listGroup.getSelectedValue();
                    groups.get(groupNames.indexOf(groupName)).addNote(note);
                    note.setGroup(groupName);

                    JTabbedPane tabbedPane = getController().getTabbedPane();
                    tabbedPane.remove(tabbedPane.getSelectedComponent());
                    getController().loadNoteEditor(note);
                    tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
                }
            }
        });
    }

    // EFFECTS: converts tags to tag names and returns an array of tag names
    private DefaultListModel<String> tagsToName(ArrayList<Tag> tags) {
        DefaultListModel<String> model = new DefaultListModel<>();
        for (Tag t : tags) {
            model.addElement(t.getTagName());
        }

        return model;
    }

    // EFFECTS: converts groups to group names and returns an array of group names
    private DefaultListModel<String> groupsToName(ArrayList<NoteGroup> groupsToName) {
        DefaultListModel<String> model = new DefaultListModel<>();
        for (NoteGroup g : groupsToName) {
            model.addElement(g.getGroupName());
        }

        return model;
    }
}
