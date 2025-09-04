package model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestTag {
    
    private Tag t1;
    private Note n1;
    private Note n2;
    private ArrayList<String> assignedNotes;

    @BeforeEach
    void runBefore() {
        t1 = new Tag("CPSC 210");
        assignedNotes = new ArrayList<>();
        n1 = new Note("Title 1");
        n2 = new Note("Title 2");
    }

    @Test
    void testConstructor() {
        assertEquals("CPSC 210", t1.getTagName());
        assertEquals(new ArrayList<>(), t1.getAssignedNotes());
    }

    @Test
    void testGetTagName() {
        assertEquals("CPSC 210", t1.getTagName());
    }

    @Test
    void testAddNote() {
        t1.addNote(n1.getTitle());
        assignedNotes.add(n1.getTitle());
        assertEquals(assignedNotes, t1.getAssignedNotes());

        t1.addNote(n2.getTitle());
        assignedNotes.add(n2.getTitle());
        assertEquals(assignedNotes, t1.getAssignedNotes());

        t1.removeNote(n1.getTitle());
        assignedNotes.remove(n1.getTitle());
        assertEquals(assignedNotes, t1.getAssignedNotes());

        t1.removeNote(n2.getTitle());
        assignedNotes.remove(n2.getTitle());
        assertEquals(assignedNotes, t1.getAssignedNotes());
    }

}
