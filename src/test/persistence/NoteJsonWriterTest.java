package persistence;

import org.junit.jupiter.api.Test;

import model.Note;
import model.Tag;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class NoteJsonWriterTest {

    @Test
    void testWriterInvalidFile() {
        try {
            @SuppressWarnings("unused")
            ArrayList<Note> lon = new ArrayList<>();
            NoteJsonWriter writer = new NoteJsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            ArrayList<Note> lon = new ArrayList<>();
            NoteJsonWriter writer = new NoteJsonWriter("./data/testWriterEmptyArrayOfNotes.json");
            writer.open();
            writer.write(lon);
            writer.close();

            NoteJsonReader reader = new NoteJsonReader("./data/testWriterEmptyArrayOfNotes.json");
            lon = reader.read();
            assertEquals(0, lon.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            ArrayList<Note> lon = new ArrayList<>();
            ArrayList<Tag> tags = new ArrayList<>();
            ArrayList<String> texts = new ArrayList<>();
            Note n1 = new Note("CPSC 210 Notes");
            Note n2 = new Note("Math 101");
            Tag t1 = new Tag("Important");
            Tag t2 = new Tag("Class Notes");

            n2.setGroup("University Notes");

            n2.addText("Hello!");
            n2.addText("Today's Lecture will include ... ");
            n2.addText("Have a good weekend!");

            texts.add("Hello!");
            texts.add("Today's Lecture will include ... ");
            texts.add("Have a good weekend!");

            n2.addTag(t1);
            n2.addTag(t2);

            tags.add(t1);
            tags.add(t2);

            lon.add(n1);
            lon.add(n2);

            NoteJsonWriter writer = new NoteJsonWriter("./data/testWriterGeneralArrayOfNotes.json");

            writer.open();
            writer.write(lon);
            writer.close();

            NoteJsonReader reader = new NoteJsonReader("./data/testWriterGeneralArrayOfNotes.json");
            lon = reader.read();
            n1 = lon.get(0);
            n2 = lon.get(1);
            assertEquals("CPSC 210 Notes", n1.getTitle());
            assertEquals("", n1.getGroup());
            assertEquals(new ArrayList<>(), n1.getTags());
            assertEquals(new ArrayList<>(), n1.getTexts());

            assertEquals("Math 101", n2.getTitle());
            assertEquals(2, n2.getTags().size());
            assertEquals("Important", n2.getTags().get(0).getTagName());
            assertEquals("Class Notes", n2.getTags().get(1).getTagName());
            assertEquals(texts, n2.getTexts());
            assertEquals("University Notes", n2.getGroup());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}