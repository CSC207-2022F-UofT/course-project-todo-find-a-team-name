package entities;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SectionTest {

    static Section lecture;
    static Section tutorial;
    static Section practical;
    static List<Block> blocks;

    @BeforeAll
    static void setUp(){
        blocks = new ArrayList<>();
        blocks.add(new Block("MO", "12:30", "14:00", "room1"));
        blocks.add(new Block("MO", "10:30", "12:00", "room2"));
        blocks.add(new Block("TU", "16:00", "19:30", "room2"));
        blocks.add(new Block("TH", "12:00", "13:30", "room3"));
        lecture = new Section("LEC-0101", "inst1", blocks);
        tutorial = new Section("TUT-0401", "inst2", blocks);
        practical = new Section("PRA-0301", "inst3", blocks);
    }

    @Test
    void isConflictedFalse() {
        List<Block> blocks2 = new ArrayList<>();
        blocks2.add(new Block("MO", "14:30", "15:00", "room3"));
        blocks2.add(new Block("TU", "12:30", "14:00", "room1"));
        blocks2.add(new Block("TH", "14:00", "15:30", "room3"));
        Section other = new Section("LEC-0201", "inst2", blocks2);

        assertFalse(lecture.isConflicted(other));
    }

    @Test
    void isConflictedTrue() {
        List<Block> blocks2 = new ArrayList<>();
        blocks2.add(new Block("MO", "14:30", "15:00", "room3"));
        blocks2.add(new Block("TU", "15:30", "16:30", "room1"));
        blocks2.add(new Block("TH", "14:00", "15:30", "room3"));
        Section other = new Section("LEC-0201", "inst2", blocks2);

        assertTrue(lecture.isConflicted(other));
    }

    @Test
    void getCode() {
        assertEquals("LEC-0101", lecture.getCode());
    }

    @Test
    void getInstructorName() {
        assertEquals("inst1", lecture.getInstructorName());
    }

    @Test
    void getBlocks() {
        for (Block block : lecture.getBlocks()) {
            assertTrue(blocks.contains(block));
        }
    }

    @Test
    void testToString() {
        assertEquals("Section {code: LEC-0101, instructor name: inst1, # of blocks: 4}", lecture.toString());
    }

    @Test
    void isLectureTrue() {
        assertTrue(lecture.isLecture());
    }

    @Test
    void isLectureFalse() {
        assertFalse(tutorial.isLecture());
        assertFalse(practical.isLecture());
    }

    @Test
    void isTutorialTrue() {
        assertTrue(tutorial.isTutorial());
    }

    @Test
    void isTutorialFalse() {
        assertFalse(lecture.isTutorial());
        assertFalse(practical.isTutorial());

    }
    @Test
    void isPracticalTrue(){
        assertTrue(practical.isPractical());
    }

    @Test
    void isPracticalFalse(){
        assertFalse(lecture.isPractical());
        assertFalse(tutorial.isPractical());
    }
}