package entities;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InstructorConstraintTest {
    static InstructorConstraint instructorConstraint;
    static CalendarCourse course;
    static Section section;


    @BeforeAll
    static void setUp(){
        List<Block> blocks = new ArrayList<>();
        List<Section> sections = new ArrayList<>();
        List<String> instructorNames = new ArrayList<>();
        instructorNames.add("inst1");
        instructorNames.add("inst2");
        sections.add(new Section("LEC-0101", "inst1", blocks));
        sections.add(new Section("TUT-0401", "inst2", blocks));
        sections.add(new Section("PRA-0301", "inst3", blocks));
        course = new CalendarCourse("courseA", sections, "", "", "");
        instructorConstraint = new InstructorConstraint(instructorNames, true);


    }

    @Test
    void filter() {
        ArrayList<Section> original_sections = new ArrayList<>(course.getSections());
        instructorConstraint.filter(course);
        ArrayList<Section> new_sections = new ArrayList<>(course.getSections());
        assertEquals(original_sections.get(2), new_sections.get(0));
        assertEquals(new_sections.size(), original_sections.size() - 2);

    }

    @Test
    void getInstructorName() {
        ArrayList<String> expectedNames = new ArrayList<>();
        expectedNames.add("inst1");
        expectedNames.add("inst2");
        assertEquals(expectedNames,instructorConstraint.getInstructorName());
    }

    @Test
    void testToString() {
        String expected = "Instructor BlackList Constraint: [inst1, inst2]";
        assertEquals(expected, instructorConstraint.toString());

    }
}
