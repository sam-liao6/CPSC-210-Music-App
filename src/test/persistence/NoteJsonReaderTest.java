package persistence;

import model.Note;
import model.Tag;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class NoteJsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        NoteJsonReader reader = new NoteJsonReader("./data/noSuchFile.json");
        try {
            @SuppressWarnings("unused")
            ArrayList<Note> lon = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyNote() {
        NoteJsonReader reader = new NoteJsonReader("./data/testReaderEmptyArrayOfNotes.json");
        try {
            ArrayList<Note> lon = reader.read();
            assertEquals(0, lon.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralNote() {
        NoteJsonReader reader = new NoteJsonReader("./data/testReaderGeneralArrayOfNotes.json");
        try {
            ArrayList<Note> lon = reader.read();
            Note n1 = lon.get(0);
            assertEquals("CPSC 210 Notes", n1.getTitle());
            assertEquals("", n1.getGroup());
            assertEquals(new ArrayList<>(), n1.getTags());
            assertEquals(new ArrayList<>(), n1.getTexts());

            Note n2 = lon.get(1);
            ArrayList<String> texts = new ArrayList<>();
            texts.add("Hello!");
            texts.add("Today's Lecture will include ... ");
            texts.add("Have a good weekend!");
            ArrayList<Tag> tags = new ArrayList<>();
            Tag t1 = new Tag("Important");
            Tag t2 = new Tag("Class Notes");
            tags.add(t1);
            tags.add(t2);
            assertEquals("Math 101", n2.getTitle());
            assertEquals(2, n2.getTags().size());
            assertEquals("Important", n2.getTags().get(0).getTagName());
            assertEquals("Class Notes", n2.getTags().get(1).getTagName());
            assertEquals(texts, n2.getTexts());
            assertEquals("University Notes", n2.getGroup());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}