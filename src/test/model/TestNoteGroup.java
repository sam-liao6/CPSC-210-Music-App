package model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestNoteGroup {
    
    private NoteGroup ng;
    private Note n1;
    private Note n2;
    private Tag t1;
    private Tag t2;
    private ArrayList<Tag> tempTagList;
    private ArrayList<Note> tempNoteList;

    @BeforeEach
    void runBefore() {
        ng = new NoteGroup("CPSC 210");
        n1 = new Note("Project Summary");
        n2 = new Note("Lecture 4 Notes");
        t1 = new Tag("Important");
        t2 = new Tag("Throwaway");
        tempTagList = new ArrayList<>();
        tempNoteList = new ArrayList<>();
    }

    @Test
    void testConstructor() {
        assertEquals("CPSC 210", ng.getGroupName());
        assertEquals(tempNoteList, ng.getNotes());
        assertEquals(tempTagList, ng.getTags());
    }

    @Test
    void testGetAndSetGroupName() {
        assertEquals("CPSC 210", ng.getGroupName());
        ng.setGroupName("MATH 101");
        assertEquals("MATH 101", ng.getGroupName());
    }

    @Test
    void testAddAndGetNotes() {
        ng.addNote(n1);
        tempNoteList.add(n1);
        assertEquals(tempNoteList, ng.getNotes());
        assertEquals(n1.getGroup(), ng.getGroupName());

        ng.addTag(t1);
        ng.addTag(t2);
        tempTagList.add(t1);

        n2.addTag(t1);
        assertEquals(tempTagList, n2.getTags());
        tempTagList.add(t2);
        assertEquals(tempTagList, ng.getTags());

        ng.addNote(n2);
        tempNoteList.add(n2);
        assertEquals(tempNoteList, ng.getNotes());
        assertEquals(tempTagList, n2.getTags());
        assertEquals(n2.getGroup(), ng.getGroupName());
    }

    @Test
    void testAddAndGetTags() {
        ng.addTag(t1);
        tempTagList.add(t1);
        assertEquals(tempTagList, ng.getTags());

        ng.addNote(n1);
        assertEquals(tempTagList, n1.getTags());

        n1.addTag(t2);
        tempTagList.add(t2);
        assertEquals(tempTagList, n1.getTags());
        ng.addTag(t2);
        assertEquals(tempTagList, ng.getTags());
        assertEquals(tempTagList, n1.getTags());
    }

    @Test
    void testRemoveTag() {
        ng.addTag(t1);
        ng.addTag(t2);
        ng.addNote(n1);
        tempTagList.add(t1);
        tempTagList.add(t2);
        assertEquals(tempTagList, ng.getTags());

        tempTagList.remove(t1);
        n1.removeTag(t1);
        assertEquals(tempTagList, n1.getTags());
        ng.removeTag(t1);
        assertEquals(tempTagList, ng.getTags());
        assertEquals(tempTagList, n1.getTags());

        ng.removeTag(t2);
        tempTagList.remove(t2);
        assertEquals(tempTagList, ng.getTags());
        assertEquals(tempTagList, n1.getTags());
    }

    @Test
    void testRemoveNote() {
        ng.addNote(n1);
        ng.addNote(n2);
        ng.addTag(t1);
        ng.addTag(t2);
        tempTagList.add(t1);
        tempTagList.add(t2);
        tempNoteList.add(n1);
        tempNoteList.add(n2);
        assertEquals(tempNoteList, ng.getNotes());
        assertEquals(tempTagList, n1.getTags());
        assertEquals(tempTagList, n2.getTags());
        assertEquals(ng.getGroupName(), n1.getGroup());
        assertEquals(ng.getGroupName(), n2.getGroup());

        tempTagList.remove(t1);
        n1.removeTag(t1);
        assertEquals(tempTagList, n1.getTags());
        ng.removeNote(n1);
        tempNoteList.remove(n1);
        assertEquals(tempNoteList, ng.getNotes());
        assertEquals(new ArrayList<>(), n1.getTags());
        assertEquals("", n1.getGroup());

        ng.removeNote(n2);
        tempNoteList.remove(n2);
        assertEquals(new ArrayList<>(), ng.getNotes());
        assertEquals(new ArrayList<>(), n2.getTags());
        assertEquals("", n2.getGroup());
    }
}
