package retrieve_timetable_use_case;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TimetableModelTest {
    private TimetableModel timetable;

    @BeforeEach
    void setUp() {
        List<CourseModel> courses = new ArrayList<>();
        courses.add(new CourseModel("some course", new ArrayList<>(), "F",
                "CSD203", "BR1"));
        timetable = new TimetableModel(courses);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testEquals() {
        List<CourseModel> courses = new ArrayList<>();
        courses.add(new CourseModel("some course", new ArrayList<>(), "F",
                "CSD203", "BR1"));
        assertEquals(new TimetableModel(courses), timetable);
    }

    @Test
    void testNotEquals() {
        assertNotEquals(new TimetableModel(new ArrayList<>()), timetable);
    }
}