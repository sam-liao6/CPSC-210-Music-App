package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import model.Tag;

class TagJsonWriterTest {
    
    @Test
    void testWriterInvalidFile() {
        try {
            @SuppressWarnings("unused")
            ArrayList<Tag> lot = new ArrayList<>();
            TagJsonWriter writer = new TagJsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            ArrayList<Tag> lot = new ArrayList<>();
            TagJsonWriter writer = new TagJsonWriter("./data/testWriterEmptyArrayOfTags.json");
            writer.open();
            writer.write(lot);
            writer.close();

            TagJsonReader reader = new TagJsonReader("./data/testWriterEmptyArrayOfTags.json");
            lot = reader.read();
            assertEquals(0, lot.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            ArrayList<Tag> lot = new ArrayList<>();
            Tag t1 = new Tag("Important");
            Tag t2 = new Tag("Class Notes");
            ArrayList<String> notes = new ArrayList<>();
            t2.addNote("CPSC 210 Notes");
            t2.addNote("MATH 101 Notes");
            t2.addNote("PHIL 220 Notes");
            notes.add("CPSC 210 Notes");
            notes.add("MATH 101 Notes");
            notes.add("PHIL 220 Notes");

            lot.add(t1);
            lot.add(t2);

            TagJsonWriter writer = new TagJsonWriter("./data/testWriterGeneralArrayOfTags.json");

            writer.open();
            writer.write(lot);
            writer.close();

            TagJsonReader reader = new TagJsonReader("./data/testWriterGeneralArrayOfTags.json");
            lot = reader.read();
            t1 = lot.get(0);
            t2 = lot.get(1);

            assertEquals("Important", t1.getTagName());
            assertEquals(new ArrayList<>(), t1.getAssignedNotes());

            assertEquals("Class Notes", t2.getTagName());            
            assertEquals(notes, t2.getAssignedNotes());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
