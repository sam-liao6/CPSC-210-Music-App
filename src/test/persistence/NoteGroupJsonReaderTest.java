package persistence;

import model.Note;
import model.NoteGroup;
import model.Tag;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class NoteGroupJsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        NoteGroupJsonReader reader = new NoteGroupJsonReader("./data/noSuchFile.json");
        try {
            @SuppressWarnings("unused")
            ArrayList<NoteGroup> ngs = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyNoteGroups() {
        NoteGroupJsonReader reader = new NoteGroupJsonReader("./data/testReaderEmptyArrayOfNoteGroups.json");
        try {
            ArrayList<NoteGroup> ngs = reader.read();
            assertEquals(0, ngs.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralNoteGroups() {
        NoteGroupJsonReader reader = new NoteGroupJsonReader("./data/testReaderGeneralArrayOfNoteGroups.json");
        try {
            ArrayList<NoteGroup> ngs = reader.read();
            NoteGroup ng1 = ngs.get(0);
            assertEquals("Term 1 Notes", ng1.getGroupName());
            assertEquals(new ArrayList<>(), ng1.getTags());
            assertEquals(new ArrayList<>(), ng1.getNotes());

            ArrayList<Note> notes = new ArrayList<>();
            ArrayList<Tag> tags = new ArrayList<>();
            Tag t1 = new Tag("Important");
            Tag t2 = new Tag("Class Notes");
            Note n1 = new Note("CPSC 210 Notes");
            Note n2 = new Note("MATH 101 Notes");

            t1.addNote(n1.getTitle());
            t1.addNote(n2.getTitle());
            n1.addTag(t1);
            n1.addTag(t2);
            t2.addNote(n1.getTitle());
            t2.addNote(n2.getTitle());
            n2.addTag(t1);
            n2.addTag(t2);

            tags.add(t1);
            tags.add(t2);
            notes.add(n1);
            notes.add(n2);


            NoteGroup ng2 = ngs.get(1);
            assertEquals("Term 1 Notes", ng1.getGroupName());

            assertEquals(2, ng2.getTags().size());
            assertEquals(2, ng2.getNotes().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}