package ui.tabs;

import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import model.Note;
import ui.ButtonNames;
import ui.NoteAppUI;

public class NoteTab extends Tab {

    private ArrayList<Note> notes;
    private BufferedImage image;
    
    // EFFECTS: creates a note tab GUI
    public NoteTab(NoteAppUI controller) {
        super(controller);
        notes = getController().getNotes();

        placeTopBar();
        placeSecondBar();
        displayNotes(notes);
    }

    // EFFECTS: creates a note tab GUI using the notes provided
    public NoteTab(NoteAppUI controller, ArrayList<Note> tempNotes) {
        super(controller);
        notes = getController().getNotes();

        placeTopBar();
        placeSecondBar();
        displayNotes(tempNotes);
    }


    // EFFECTS: converts image to a label and returns the label
    public JLabel imagePanel() {
        try {                
            image = ImageIO.read(new File("data/note-taking-app-icon-in-illustration-vector.jpg"));
            JLabel picLabel = new JLabel(new ImageIcon(image.getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
            return picLabel;
        } catch (IOException ex) {
            // handle exception...
            return new JLabel();
        }
    }

    // MODIFIES: this
    // EFFECTS: converts all notes into a informational label with buttons and adds them to the display
    public void displayNotes(ArrayList<Note> notes) {
        for (Note note : notes) {
            JLabel noteTitle = new JLabel(note.getTitle());
            JButton editButton = new JButton("Edit");
            JButton delButton = new JButton("Delete " + note.getTitle());
            JPanel noteRow = new JPanel(new FlowLayout());

            noteRow.add(noteTitle);
            noteRow.add(editButton);
            noteRow.add(delButton);
        
            editButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String buttonPressed = e.getActionCommand();
                    NoteAppUI controller = getController();
                    JTabbedPane tab = controller.getTabbedPane();
                    if (buttonPressed.equals("Edit") && tab.indexOfTab(note.getTitle()) == -1) {
                        controller.loadNoteEditor(note);
                        tab.setSelectedIndex(tab.getTabCount() - 1);
                    }
                }
            });

            deleteButtonListener(delButton, note);

            this.add(noteRow);
        }
    }

    // EFFECTS: adds a listener for delete button
    private void deleteButtonListener(JButton button, Note note) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                if (buttonPressed.equals("Delete " + note.getTitle())) {
                    getController().removeNote(note);
                    getController().updateTabs();
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: creates the control bar with save and refresh button and adds it to display
    private void placeSecondBar() {
        JLabel leftBuffer = new JLabel("----------=======");
        JLabel rightBuffer = new JLabel("=======----------");
        JButton saveButton = new JButton("Save");
        JButton refreshButton = new JButton("Refresh");
        JButton reloadButton = new JButton("Reload");
        JPanel controlPanel = new JPanel(new FlowLayout());

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                if (buttonPressed.equals("Save")) {
                    getController().saveCurrentData();
                }
            }
        });

        refreshButtonAddListener(refreshButton);
        reloadButtonAddListener(reloadButton);

        controlPanel.add(leftBuffer);
        controlPanel.add(saveButton);
        controlPanel.add(refreshButton);
        controlPanel.add(reloadButton);
        controlPanel.add(rightBuffer);

        this.add(controlPanel);
    }

    // EFFECTS: adds a listener for the reload button
    private void reloadButtonAddListener(JButton reloadButton) {
        reloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                if (buttonPressed.equals("Reload")) {
                    getController().reload();
                }
            } 
        });
    }

    // EFFECTS: adds a listener for the refresh button
    private void refreshButtonAddListener(JButton refreshButton) {
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                if (buttonPressed.equals("Refresh")) {
                    getController().refresh();
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: creates the search bar with a search field and button and adds it to display
    private void placeTopBar() {
        JButton searchButton = new JButton(ButtonNames.SEARCH.getValue());
        JTextField searchField = new JTextField("");
        JPanel searchRow = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Add Note");

        searchField.setColumns(25);

        searchRow.add(imagePanel());
        searchRow.add(searchField);
        searchRow.add(searchButton);
        searchRow.add(addButton);

        searchButtonListener(searchButton, searchField);
        addButtonListener(addButton, searchField);
        
        this.add(searchRow);
    }

    // EFFECTS: adds a listener for add note button
    private void addButtonListener(JButton addButton, JTextField searchField) {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                if (buttonPressed.equals("Add Note")) {
                    ArrayList<String> noteTitles = new ArrayList<>();
                    for (Note n : notes) {
                        noteTitles.add(n.getTitle());
                    }
                    
                    if (!noteTitles.contains(searchField.getText())) {
                        getController().addNote(new Note(searchField.getText()));
                        removeAll();
                        placeTopBar();
                        placeSecondBar();
                        displayNotes(notes);
                    }
                }
            }
        });
    }

    // EFFECTS: adds a listener for search button
    private void searchButtonListener(JButton searchButton, JTextField searchField) {
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                if (buttonPressed.equals(ButtonNames.SEARCH.getValue())) {
                    ArrayList<Note> tempNotes = new ArrayList<>();
                    for (Note note : notes) {
                        String searchVal = searchField.getText();
                        String noteName = note.getTitle().toLowerCase();
                        if (noteName.contains(searchVal)) {
                            tempNotes.add(note);
                        }
                    }

                    JTabbedPane tabbedPane = getController().getTabbedPane();
                    tabbedPane.remove(tabbedPane.getSelectedComponent());
                    getController().loadNoteTab(tempNotes);
                    tabbedPane.setSelectedIndex(0);
                }
            }
        });
    }
}
