package retrieve_timetable_use_case;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * A test suite for the TimetableModel class, primarily to confirm the correctness of its equal method.
 * The setters and getters are currently too simple to require testing, but must be tested if more complex
 * behaviour is introduced.
 */
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

    /**
     * Tests that the Timetable and its equivalent TimetableModel are considered equal.
     */
    @Test
    void testEquals() {
        List<CourseModel> courses = new ArrayList<>();
        courses.add(new CourseModel("some course", new ArrayList<>(), "F",
                "CSD203", "BR1"));
        assertEquals(new TimetableModel(courses), timetable);
    }

    /**
     * Tests that the Timetable and a non-equivalent TimetableModel are non-equal.
     */
    @Test
    void testNotEquals() {
        assertNotEquals(new TimetableModel(new ArrayList<>()), timetable);
    }
}