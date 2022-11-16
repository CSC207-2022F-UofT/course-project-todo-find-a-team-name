package entities;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SectionTest {

    static Section section;
    static List<Block> blocks;

    @BeforeAll
    static void setUp(){
        blocks = new ArrayList<>();
        blocks.add(new Block("MO", "12:30", "14:00", "room1"));
        blocks.add(new Block("MO", "10:30", "12:00", "room2"));
        blocks.add(new Block("TU", "16:00", "19:30", "room2"));
        blocks.add(new Block("TH", "12:00", "13:30", "room3"));
        section = new Section("LEC0101", "inst1", blocks);
    }

    @Test
    void isConflictedFalse() {
        List<Block> blocks2 = new ArrayList<>();
        blocks2.add(new Block("MO", "14:30", "15:00", "room3"));
        blocks2.add(new Block("TU", "12:30", "14:00", "room1"));
        blocks2.add(new Block("TH", "14:00", "15:30", "room3"));
        Section other = new Section("LEC0201", "inst2", blocks2);

        assertFalse(section.isConflicted(other));
    }

    @Test
    void isConflictedTrue() {
        List<Block> blocks2 = new ArrayList<>();
        blocks2.add(new Block("MO", "14:30", "15:00", "room3"));
        blocks2.add(new Block("TU", "15:30", "16:30", "room1"));
        blocks2.add(new Block("TH", "14:00", "15:30", "room3"));
        Section other = new Section("LEC0201", "inst2", blocks2);

        assertTrue(section.isConflicted(other));
    }

    @Test
    void getCode() {
        assertEquals("LEC0101", section.getCode());
    }

    @Test
    void getInstructorName() {
        assertEquals("inst1", section.getInstructorName());
    }

    @Test
    void getBlocks() {
        assertEquals(blocks, section.getBlocks());
    }

    @Test
    void testToString() {
        assertEquals("Section {code: LEC0101, instructor name: inst1, # of blocks: 4}", section.toString());
    }
}