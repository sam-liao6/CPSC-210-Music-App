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

import model.Tag;
import ui.ButtonNames;
import ui.NoteAppUI;

// Represents a Tag UI that displays all tags
public class TagTab extends Tab {

    private ArrayList<Tag> tags;
    
    // EFFECTS: constructs a tag tab GUI 
    public TagTab(NoteAppUI controller) {
        super(controller);

        tags = getController().getTags();
        placeSearchBar();
        displayTags(tags);
    }

    // EFFECTS: constructs a tag tab GUI 
    public TagTab(NoteAppUI controller, ArrayList<Tag> tempTags) {
        super(controller);

        tags = getController().getTags();
        placeSearchBar();
        displayTags(tempTags);
    }

    // MODIFIES: this
    // EFFECTS: creates a tag information label for all tags and adds it to display
    public void displayTags(ArrayList<Tag> tags) {
        for (Tag tag : tags) {
            JLabel tagName = new JLabel(tag.getTagName());
            JButton tagButton = new JButton("Show Notes Assigned");
            JButton delButton = new JButton("Delete " + tag.getTagName());
            JPanel tagRow = new JPanel(new FlowLayout());

            tagRow.add(tagName);
            tagRow.add(tagButton);
            tagRow.add(delButton);
        
            tagButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String buttonPressed = e.getActionCommand();
                    if (buttonPressed.equals("Show Notes Assigned")) {
                        System.out.println("Hi");
                    }
                }
            });

            deleteButtonListener(delButton, tag);

            this.add(tagRow);
        }
    }

    // EFFECTS: creates a listener for delete tag button
    private void deleteButtonListener(JButton button, Tag tag) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                if (buttonPressed.equals("Delete " + tag.getTagName())) {
                    getController().removeTag(tag);
                    getController().updateTabs();
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: creates a search bar label with a search field, search button, and add tag button and adds it to display
    private void placeSearchBar() {
        JButton searchButton = new JButton(ButtonNames.SEARCH.getValue());
        JButton addButton = new JButton("Add Tag");
        JTextField searchField = new JTextField("");
        JPanel searchRow = new JPanel(new FlowLayout());
        searchField.setColumns(30);
        searchRow.add(searchField);
        searchRow.add(searchButton);
        searchRow.add(addButton);
        
        searchButtonListener(searchButton, searchField);
        addButtonListener(addButton, searchField);

        this.add(searchRow);
    }

    // EFFECTS: adds a listener for search button
    private void searchButtonListener(JButton searchButton, JTextField searchField) {
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                if (buttonPressed.equals(ButtonNames.SEARCH.getValue())) {
                    ArrayList<Tag> tempTags = new ArrayList<>();
                    for (Tag tag : tags) {
                        String searchVal = searchField.getText();
                        String tagName = tag.getTagName().toLowerCase();
                        if (tagName.contains(searchVal)) {
                            tempTags.add(tag);
                        }
                    }

                    JTabbedPane tabbedPane = getController().getTabbedPane();
                    tabbedPane.remove(tabbedPane.getSelectedComponent());
                    getController().loadTagTab(tempTags);
                    tabbedPane.setSelectedIndex(1);
                }
            }
        });
    }

    // EFFECTS: adds a listener for group add button
    private void addButtonListener(JButton addButton, JTextField searchField) {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                if (buttonPressed.equals("Add Tag")) {
                    ArrayList<String> tagNames = new ArrayList<>();
                    for (Tag tag : tags) {
                        tagNames.add(tag.getTagName());
                    }
                    
                    if (!tagNames.contains(searchField.getText())) {
                        getController().addTag(new Tag(searchField.getText()));
                        removeAll();
                        placeSearchBar();
                        displayTags(tags);
                    }
                }
            }
        });
    }
}
