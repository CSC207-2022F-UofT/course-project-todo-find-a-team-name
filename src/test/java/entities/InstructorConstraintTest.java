package entities;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InstructorConstraintTest {
    static List<Block> blocks;
    static Section section1;
    static Section section2;
    static Section section3;

    @BeforeAll
    static void setup(){
        blocks = new ArrayList<>();
        section1 = new Section("LEC-0101", "inst1", blocks);
        section2 = new Section("TUT-0401", "inst2", blocks);
        section3 = new Section("PRA-0301", "inst3", blocks);
    }

    @Test
    void filterRemoveAllSectionsBlackList(){
        List<Section> sections = new ArrayList<>();
        sections.add(section1);
        sections.add(section2);
        sections.add(section3);
        CalendarCourse course = new CalendarCourse("courseA", sections, "", "", "");
        List<String> instructorNames = new ArrayList<>();
        instructorNames.add("inst1");
        instructorNames.add("inst2");
        instructorNames.add("inst3");
        InstructorConstraint instructorConstraint = new InstructorConstraint(instructorNames,true);
        // testing to check original and modified course section type consistency.
        assertFalse(instructorConstraint.filter(course));
    }
    @Test
    void filterRemove2SectionsBlackList() {
        List<Section> sections = new ArrayList<>();
        sections.add(section1);
        sections.add(section2);
        sections.add(section3);
        CalendarCourse course = new CalendarCourse("courseA", sections, "", "", "");
        List<String> instructorNames = new ArrayList<>();
        instructorNames.add("inst1");
        instructorNames.add("inst2");
        InstructorConstraint instructorConstraint = new InstructorConstraint(instructorNames, true);
        // testing to check original and modified course section type consistency.
        assertFalse(instructorConstraint.filter(course));
    }


    @Test
    void filterAllSectionsWhitelist(){
        List<Section> sections = new ArrayList<>();
        sections.add(section1);
        sections.add(section2);
        sections.add(section3);
        CalendarCourse courseBeforeFilter = new CalendarCourse("courseA", sections, "", "", "");
        CalendarCourse course = new CalendarCourse("courseA", sections, "", "", "");
        List<String> instructorNames = new ArrayList<>();
        instructorNames.add("inst1");
        instructorNames.add("inst2");
        instructorNames.add("inst3");
        InstructorConstraint instructorConstraint = new InstructorConstraint(instructorNames,false);
        // testing to check original and modified course section type consistency.
        assertTrue(instructorConstraint.filter(course));
        assertEquals(courseBeforeFilter, course);
        assertEquals(3, course.getSections().size());

    }


    @Test
    void getInstructorName() {
        List<String> instructorNames = new ArrayList<>();
        instructorNames.add("inst1");
        instructorNames.add("inst2");
        InstructorConstraint instructorConstraint = new InstructorConstraint(instructorNames, true);
        List<String> expectedNames = new ArrayList<>();
        expectedNames.add("inst1");
        expectedNames.add("inst2");
        assertEquals(expectedNames,instructorConstraint.getInstructorName());
    }

    @Test
    void testToString() {
        List<String> instructorNames = new ArrayList<>();
        instructorNames.add("inst1");
        instructorNames.add("inst2");
        InstructorConstraint instructorConstraint1 = new InstructorConstraint(instructorNames, true);
        InstructorConstraint instructorConstraint2 = new InstructorConstraint(instructorNames, false);
        String expected1 = "Instructor BlackList Constraint: [inst1, inst2]";
        assertEquals(expected1, instructorConstraint1.toString());
        String expected2 = "Instructor WhiteList Constraint: [inst1, inst2]";
        assertEquals(expected2, instructorConstraint2.toString());
    }
}

