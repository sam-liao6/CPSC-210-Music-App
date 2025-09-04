package ui.tabs;

import javax.swing.JButton;
import javax.swing.JPanel;

import ui.NoteAppUI;

import java.awt.*;

// Represents a Tab with a controller
public abstract class Tab extends JPanel {
    
    private final NoteAppUI controller;

    // REQUIRES: NoteAppUI controller that holds this tab
    public Tab(NoteAppUI controller) {
        this.controller = controller;
    }

    // EFFECTS: creates and returns row with button included
    public JPanel formatButtonRow(JButton b) {
        JPanel p = new JPanel();
        p.setLayout(new FlowLayout());
        p.add(b);

        return p;
    }

    // EFFECTS: returns the SmartHomeUI controller for this tab
    public NoteAppUI getController() {
        return controller;
    }
}
