package ui.tabs;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import model.NoteGroup;
import ui.ButtonNames;
import ui.NoteAppUI;

// Represents a note group UI for viewing all note groups 
public class NoteGroupTab extends Tab {

    private ArrayList<NoteGroup> groups;
    
    // EFFECTS: constructs a note group GUI tab
    public NoteGroupTab(NoteAppUI controller) {
        super(controller);
        groups = getController().getGroups();

        placeTopBar();
        displayGroups(groups);
    }
    
    // EFFECTS: constructs a note group GUI tab using given array of groups
    public NoteGroupTab(NoteAppUI controller, ArrayList<NoteGroup> tempGroups) {
        super(controller);
        groups = getController().getGroups();

        placeTopBar();
        displayGroups(tempGroups);
    }

    // MODIFIES: this
    // EFFECTS: creates a panel to display all groups information and adds it to display
    public void displayGroups(ArrayList<NoteGroup> groups) {
        for (NoteGroup group : groups) {
            JLabel groupName = new JLabel(group.getGroupName());
            JButton editButton = new JButton("Edit");
            JButton delButton = new JButton("Delete " + group.getGroupName());
            JPanel groupRow = new JPanel(new FlowLayout());

            groupRow.add(groupName);
            groupRow.add(editButton);
            groupRow.add(delButton);
        
            editButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String buttonPressed = e.getActionCommand();
                    NoteAppUI controller = getController();
                    JTabbedPane tab = controller.getTabbedPane();
                    if (buttonPressed.equals("Edit") && tab.indexOfTab(group.getGroupName()) == -1) {
                        controller.loadNoteGroupEditor(group);
                        tab.setSelectedIndex(tab.getTabCount() - 1);
                    }
                }
            });

            deleteButtonListener(delButton, group);

            this.add(groupRow);
        }
    }

    // EFFECTS: adds a listener to delete button
    private void deleteButtonListener(JButton button, NoteGroup group) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                if (buttonPressed.equals("Delete " + group.getGroupName())) {
                    getController().removeGroup(group);
                    getController().updateTabs();
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: creates a top bar panel and adds it to the display
    private void placeTopBar() {
        JButton searchButton = new JButton(ButtonNames.SEARCH.getValue());
        JTextField searchField = new JTextField("");
        JPanel searchRow = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Create Group");

        searchField.setColumns(25);

        searchRow.add(searchField);
        searchRow.add(searchButton);
        searchRow.add(addButton);

        addButtonListener(addButton, searchField);
        searchButtonListener(searchButton, searchField);
        
        this.add(searchRow);
    }

    // EFFECTS: adds a listener for group add button
    private void addButtonListener(JButton addButton, JTextField searchField) {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                if (buttonPressed.equals("Create Group")) {
                    ArrayList<String> groupNames = new ArrayList<>();
                    for (NoteGroup group : groups) {
                        groupNames.add(group.getGroupName());
                    }
                    
                    if (!groupNames.contains(searchField.getText())) {
                        getController().addNoteGroup(new NoteGroup(searchField.getText()));
                        removeAll();
                        placeTopBar();
                        displayGroups(groups);
                    }
                }
            }
        });
    }

    // EFFECTS; adds a listener to search button
    private void searchButtonListener(JButton searchButton, JTextField searchField) {
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                if (buttonPressed.equals(ButtonNames.SEARCH.getValue())) {
                    ArrayList<NoteGroup> tempGroups = new ArrayList<>();
                    for (NoteGroup group : groups) {
                        String searchVal = searchField.getText();
                        String groupName = group.getGroupName().toLowerCase();
                        if (groupName.contains(searchVal)) {
                            tempGroups.add(group);
                        }
                    }

                    JTabbedPane tabbedPane = getController().getTabbedPane();
                    tabbedPane.remove(tabbedPane.getSelectedComponent());
                    getController().loadNoteGroupTab(tempGroups);
                    tabbedPane.setSelectedIndex(2);
                }
            }
        });
    }
}
