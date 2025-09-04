package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import model.Note;
import model.NoteGroup;
import model.Tag;

class NoteGroupJsonWriterTest {
    
    @Test
    void testWriterInvalidFile() {
        try {
            @SuppressWarnings("unused")
            ArrayList<NoteGroup> ngs = new ArrayList<>();
            NoteGroupJsonWriter writer = new NoteGroupJsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            ArrayList<NoteGroup> ngs = new ArrayList<>();
            NoteGroupJsonWriter writer = new NoteGroupJsonWriter("./data/testWriterEmptyArrayOfNoteGroups.json");
            writer.open();
            writer.write(ngs);
            writer.close();

            NoteGroupJsonReader reader = new NoteGroupJsonReader("./data/testWriterEmptyArrayOfNoteGroups.json");
            ngs = reader.read();
            assertEquals(0, ngs.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            NoteGroup ng1 = new NoteGroup("Term 1 Notes");
            NoteGroup ng2 = new NoteGroup("Term 2 Notes");
            ArrayList<NoteGroup> ngs = new ArrayList<>();
            ArrayList<Tag> tags = new ArrayList<>();
            ArrayList<String> texts = new ArrayList<>();
            ArrayList<Note> notes = new ArrayList<>();
            Tag t1 = new Tag("Important");
            Tag t2 = new Tag("Class Notes");
            Note n1 = new Note("CPSC 210 Notes");
            Note n2 = new Note("MATH 101 Notes");

            ArrayList<String> noteTitles = new ArrayList<>();
            noteTitles.add(n1.getTitle());
            noteTitles.add(n2.getTitle());

            ng2.addNote(n1);
            ng2.addNote(n2);

            ng2.addTag(t1);
            ng2.addTag(t2);

            n2.addText("Hello!");
            n2.addText("Today's Lecture will include ... ");
            n2.addText("Have a good weekend!");

            texts.add("Hello!");
            texts.add("Today's Lecture will include ... ");
            texts.add("Have a good weekend!");

            tags.add(t1);
            tags.add(t2);
            notes.add(n1);
            notes.add(n2);
            ngs.add(ng1);
            ngs.add(ng2);

            NoteGroupJsonWriter writer = new NoteGroupJsonWriter("./data/testWriterGeneralArrayOfNoteGroups.json");

            writer.open();
            writer.write(ngs);
            writer.close();

            NoteGroupJsonReader reader = new NoteGroupJsonReader("./data/testWriterGeneralArrayOfNoteGroups.json");
            ngs = reader.read();
            ng1 = ngs.get(0);
            ng2 = ngs.get(1);

            assertEquals("Term 1 Notes", ng1.getGroupName());
            assertEquals(new ArrayList<>(), ng1.getTags());
            assertEquals(new ArrayList<>(), ng1.getNotes());

            assertEquals("Term 2 Notes", ng2.getGroupName());      
            assertEquals("Important", ng2.getTags().get(0).getTagName());
            assertEquals(noteTitles, ng2.getTags().get(0).getAssignedNotes());
            assertEquals("Class Notes", ng2.getTags().get(1).getTagName());
            assertEquals(noteTitles, ng2.getTags().get(1).getAssignedNotes());  
            assertEquals("CPSC 210 Notes", ng2.getNotes().get(0).getTitle());
            assertEquals("MATH 101 Notes", ng2.getNotes().get(1).getTitle());
            assertEquals(texts, ng2.getNotes().get(1).getTexts());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
