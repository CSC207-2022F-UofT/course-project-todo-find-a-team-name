package entities;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for testing Section entity
 */
class SectionTest {

    static Section lecture;
    static Section tutorial;
    static Section practical;
    static List<Block> blocks;

    /**
     * Initialize lecture, tutorial, practical, and blocks that is used throughout the tests
     */
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

    /**
     * Check whether isConflicted returns false when there is no time conflict between two sessions
     */
    @Test
    void isConflictedFalse() {
        List<Block> blocks2 = new ArrayList<>();
        blocks2.add(new Block("MO", "14:30", "15:00", "room3"));
        blocks2.add(new Block("TU", "12:30", "14:00", "room1"));
        blocks2.add(new Block("TH", "14:00", "15:30", "room3"));
        Section other = new Section("LEC-0201", "inst2", blocks2);

        assertFalse(lecture.isConflicted(other));
    }

    /**
     * Check whether isConflicted returns false when there is time conflict between two sessions
     */
    @Test
    void isConflictedTrue() {
        List<Block> blocks2 = new ArrayList<>();
        blocks2.add(new Block("MO", "14:30", "15:00", "room3"));
        blocks2.add(new Block("TU", "15:30", "16:30", "room1"));
        blocks2.add(new Block("TH", "14:00", "15:30", "room3"));
        Section other = new Section("LEC-0201", "inst2", blocks2);

        assertTrue(lecture.isConflicted(other));
    }

    /**
     * Check whether isConflicted returns true when there is time conflict between two sessions where
     * some block of one section is containing block of another
     * (e.g. the time interval 11:00-14:00 is containing 12:00-13:00)
     */
    @Test
    void isConflictedSectionContainsAnotherSection(){
        List<Block> blocks2 = new ArrayList<>();
        blocks2.add(new Block("TH", "11:00", "14:00", "room3"));
        Section other = new Section("LEC-0201", "inst2", blocks2);

        assertTrue(lecture.isConflicted(other));
    }

    /**
     * Check whether getCode is returning the correct section code
     */
    @Test
    void getCode() {
        assertEquals("LEC-0101", lecture.getCode());
    }

    /**
     * Check whether getInstructorName is retuning correct instructor name
     */
    @Test
    void getInstructorName() {
        assertEquals("inst1", lecture.getInstructorName());
    }

    /**
     * Check whether getBlocks is returning correct list of blocks
     */
    @Test
    void getBlocks() {
        for (Block block : lecture.getBlocks()) {
            assertTrue(blocks.contains(block));
        }
    }

    /**
     * Check whether toString is returning String representation of the section in correct format
     */
    @Test
    void testToString() {
        assertEquals("Section {code: LEC-0101, instructor name: inst1, # of blocks: 4}", lecture.toString());
    }

    /**
     * Check whether isLecture is returning true for lecture section
     */
    @Test
    void isLectureTrue() {
        assertTrue(lecture.isLecture());
    }

    /**
     * Check whether isLecture is returning false for tutorial and practical sections
     */
    @Test
    void isLectureFalse() {
        assertFalse(tutorial.isLecture());
        assertFalse(practical.isLecture());
    }

    /**
     * Check whether isTutorial is returning true for tutorial section
     */
    @Test
    void isTutorialTrue() {
        assertTrue(tutorial.isTutorial());
    }

    /**
     * Check whether isTutorial is returning false for lecture and practical sections
     */
    @Test
    void isTutorialFalse() {
        assertFalse(lecture.isTutorial());
        assertFalse(practical.isTutorial());
    }

    /**
     * Check whether isPractical is returning true for practical section
     */
    @Test
    void isPracticalTrue(){
        assertTrue(practical.isPractical());
    }

    /**
     * Check whether isPractical is returning false for lecture and tutorial section
     */
    @Test
    void isPracticalFalse(){
        assertFalse(lecture.isPractical());
        assertFalse(tutorial.isPractical());
    }
}