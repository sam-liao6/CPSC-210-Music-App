package model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestNote {
    
    private Note n1;
    private ArrayList<String> tempTextList;
    private ArrayList<Tag> tempTagList;
    private ArrayList<String> tempNoteList;
    private NoteGroup g1;
    private Tag t1;
    private Tag t2;

    @BeforeEach
    void runBefore() {
        n1 = new Note("Lecture Notes");
        g1 = new NoteGroup("CPSC 210");
        t1 = new Tag("Important");
        t2 = new Tag("Throwaway");
        tempTextList = new ArrayList<>();
        tempTagList = new ArrayList<>();
        tempNoteList = new ArrayList<>();
    }

    // constructor test

    @Test
    void testConstructor() {
        assertEquals(tempTextList, n1.getTexts());
        assertEquals("", n1.getGroup());
        assertEquals(tempTagList, n1.getTags());
    }

    // setter tests

    @Test
    void testAddText() {
        n1.addText("Meeting Notes");
        tempTextList.add("Meeting Notes");
        assertEquals(tempTextList, n1.getTexts());
        n1.addText("- Finish CPSC 210 Project");
        tempTextList.add("- Finish CPSC 210 Project");
        assertEquals(tempTextList, n1.getTexts());
    }

    @Test 
    void testAddTag() {
        n1.addTag(t1);
        tempTagList.add(t1);
        assertEquals(tempTagList, n1.getTags());

        n1.addTag(t2);
        tempTagList.add(t2);
        assertEquals(tempTagList, n1.getTags());
    }

    @Test
    void testRemoveTag() {
        n1.addTag(t1);
        n1.addTag(t2);
        tempTagList.add(t1);
        tempTagList.add(t2);
        tempNoteList.add(n1.getTitle());
        assertEquals(tempTagList, n1.getTags());
        assertEquals(tempNoteList, t1.getAssignedNotes());
        assertEquals(tempNoteList, t2.getAssignedNotes());

        n1.removeTag(t2);
        tempTagList.remove(t2);
        assertEquals(tempTagList, n1.getTags());
        assertEquals(new ArrayList<>(), t2.getAssignedNotes());

        n1.removeTag(new Tag("Whatever"));
        assertEquals(tempTagList, n1.getTags());

        n1.removeTag(t1);
        tempTagList.remove(t1);
        assertEquals(new ArrayList<>(), n1.getTags());
        assertEquals(new ArrayList<>(), t1.getAssignedNotes());
    }

    // getter and setter tests

    @Test
    void testGetAndSetTitle() {
        assertEquals("Lecture Notes", n1.getTitle());
        n1.setTitle("MATH 101 Notes");
        assertEquals("MATH 101 Notes", n1.getTitle());
    }

    @Test
    void testGetTexts() {
        n1.addText("Hello");
        tempTextList.add("Hello");
        assertEquals(tempTextList, n1.getTexts());
    }

    @Test
    void testGetAndSetGroup() {
        n1.setGroup(g1.getGroupName());
        assertEquals(g1.getGroupName(), n1.getGroup());
    }

    @Test
    void testGetTags() {
        n1.addTag(t1);
        tempTagList.add(t1);
        assertEquals(tempTagList, n1.getTags());
    }
}
