package recommend_br_use_case.application_business;

import entities.Block;
import entities.InvalidSectionsException;
import entities.Section;
import entities.TimetableCourse;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TargetTimeCourseComparatorTest {

    /**
     * Test whether compare is returning negative integer when distance between target time and average
     * start time of the first argument is less than second argument.
     */
    @Test
    void testCompareLess() {
        List<Section> sections1 = new ArrayList<>();

        List<Block> blocks1 = new ArrayList<>();
        blocks1.add(new Block("MO", "12:30", "14:00", "room1"));
        blocks1.add(new Block("FR", "10:30", "12:00", "room2"));
        sections1.add(new Section("LEC-0101", "", blocks1));

        List<Block> blocks2 = new ArrayList<>();
        blocks2.add(new Block("TH", "11:30", "12:00", "room1"));
        sections1.add(new Section("TUT-0201", "", blocks2));

        List<Section> sections2 = new ArrayList<>();

        List<Block> blocks3 = new ArrayList<>();
        blocks3.add(new Block("MO", "18:30", "22:00", "room1"));
        blocks3.add(new Block("TH", "19:30", "12:00", "room2"));
        sections2.add(new Section("LEC-0101", "", blocks3));

        List<Block> blocks4 = new ArrayList<>();
        blocks4.add(new Block("TU", "15:30", "17:00", "room1"));
        sections2.add(new Section("TUT-0201", "", blocks4));

        try {
            TimetableCourse course1 = new TimetableCourse("course1", sections1, "F", "COS1", "1");
            TimetableCourse course2 = new TimetableCourse("course2", sections2, "F", "COS2", "1");
            TargetTimeCourseComparator courseComparator = new TargetTimeCourseComparator(14);
            assertTrue(0 > courseComparator.compare(course1, course2));
        } catch (InvalidSectionsException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Test whether compare is returning positive integer when distance between target time and average
     * start time of the first argument is larger than second argument.
     */
    @Test
    void testCompareLarger() {
        List<Section> sections1 = new ArrayList<>();

        List<Block> blocks1 = new ArrayList<>();
        blocks1.add(new Block("MO", "19:30", "21:00", "room1"));
        blocks1.add(new Block("FR", "15:30", "17:00", "room2"));
        sections1.add(new Section("LEC-0101", "", blocks1));

        List<Block> blocks2 = new ArrayList<>();
        blocks2.add(new Block("TH", "20:30", "22:00", "room1"));
        sections1.add(new Section("TUT-0201", "", blocks2));

        List<Section> sections2 = new ArrayList<>();

        List<Block> blocks3 = new ArrayList<>();
        blocks3.add(new Block("MO", "10:30", "12:00", "room1"));
        blocks3.add(new Block("TH", "9:30", "11:00", "room2"));
        sections2.add(new Section("LEC-0101", "", blocks3));

        List<Block> blocks4 = new ArrayList<>();
        blocks4.add(new Block("TU", "11:30", "15:00", "room1"));
        sections2.add(new Section("TUT-0201", "", blocks4));

        try {
            TimetableCourse course1 = new TimetableCourse("course1", sections1, "F", "COS1", "1");
            TimetableCourse course2 = new TimetableCourse("course2", sections2, "F", "COS2", "1");
            TargetTimeCourseComparator courseComparator = new TargetTimeCourseComparator(10);
            assertTrue(0 < courseComparator.compare(course1, course2));
        } catch (InvalidSectionsException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Test whether compare is returning 0 when distance between target time and average
     * start time of the first argument is equal to the second argument.
     */
    @Test
    void testCompareEqual() {
        List<Section> sections1 = new ArrayList<>();

        List<Block> blocks1 = new ArrayList<>();
        blocks1.add(new Block("MO", "10:00", "12:00", "room1"));
        blocks1.add(new Block("FR", "12:00", "14:00", "room2"));
        sections1.add(new Section("LEC-0101", "", blocks1));

        List<Block> blocks2 = new ArrayList<>();
        blocks2.add(new Block("TH", "14:00", "16:00", "room1"));
        sections1.add(new Section("TUT-0201", "", blocks2));

        List<Section> sections2 = new ArrayList<>();

        List<Block> blocks3 = new ArrayList<>();
        blocks3.add(new Block("MO", "9:00", "12:00", "room1"));
        blocks3.add(new Block("TH", "15:00", "16:00", "room2"));
        sections2.add(new Section("LEC-0101", "", blocks3));

        List<Block> blocks4 = new ArrayList<>();
        blocks4.add(new Block("TU", "12:00", "12:00", "room1"));
        sections2.add(new Section("TUT-0201", "", blocks4));

        try {
            TimetableCourse course1 = new TimetableCourse("course1", sections1, "F", "COS1", "1");
            TimetableCourse course2 = new TimetableCourse("course2", sections2, "F", "COS2", "1");
            TargetTimeCourseComparator courseComparator = new TargetTimeCourseComparator(22);
            assertEquals(0, courseComparator.compare(course1, course2));
        } catch (InvalidSectionsException e) {
            throw new RuntimeException(e);
        }
    }
}