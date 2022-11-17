package entities;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CalendarCourseTest {

    @Test
    void removeSection() {
        Section s1 = new Section("LEC0101", "inst1", new ArrayList<Block>());
        Section s2 = new Section("LEC0102", "inst2", new ArrayList<Block>());

        CalendarCourse c = new CalendarCourse("Test Course",
                new ArrayList<Section>(List.of(s1, s2)),
                "F", "EGX101", "BR1");

        c.removeSection(s1);

        assertTrue(c.getSections().contains(s2));
        assertFalse(c.getSections().contains(s1));
    }
}