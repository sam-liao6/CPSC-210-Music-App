package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import model.Tag;

class TagJsonReaderTest {
    
    @Test
    void testReaderNonExistentFile() {
        TagJsonReader reader = new TagJsonReader("./data/noSuchFile.json");
        try {
            @SuppressWarnings("unused")
            ArrayList<Tag> lot = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyNote() {
        TagJsonReader reader = new TagJsonReader("./data/testReaderEmptyArrayOfTags.json");
        try {
            ArrayList<Tag> lot = reader.read();
            assertEquals(0, lot.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralNote() {
        TagJsonReader reader = new TagJsonReader("./data/testReaderGeneralArrayOfTags.json");
        try {
            ArrayList<Tag> lot = reader.read();
            Tag t1 = lot.get(0);
            assertEquals("Important", t1.getTagName());
            assertEquals(new ArrayList<>(), t1.getAssignedNotes());

            Tag t2 = lot.get(1);
            ArrayList<String> notes = new ArrayList<>();
            notes.add("CPSC 210 Notes");
            notes.add("MATH 101 Notes");
            notes.add("PHIL 220 Notes");
            assertEquals("Class Notes", t2.getTagName());
            assertEquals(notes, t2.getAssignedNotes());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
