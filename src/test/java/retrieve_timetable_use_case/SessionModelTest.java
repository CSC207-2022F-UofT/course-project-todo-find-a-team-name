package retrieve_timetable_use_case;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;

/**
 * A test suite for the SessionModel class, primarily to confirm the correctness of its equal method.
 * The setters and getters are currently too simple to require testing, but must be tested if more complex
 * behaviour is introduced.
 */
class SessionModelTest {
    private SessionModel session;

    @BeforeEach
    void setUp() {
        HashMap<String, CourseModel> courses = new HashMap<>();
        courses.put("SD204", (new CourseModel("Something cool", new ArrayList<>(), "F", "SD204", "BR1")));
        session = new SessionModel(courses, "F");
    }

    @AfterEach
    void tearDown() {
    }

    /**
     * Tests that the Session and its equivalent SessionModel are considered equal.
     */
    @Test
    void testEquals() {
        HashMap<String, CourseModel> courses = new HashMap<>();
        courses.put("SD204", (new CourseModel("Something cool", new ArrayList<>(), "F", "SD204", "BR1")));
        assertEquals(new SessionModel(courses, "F"), session);
    }

    /**
     * Tests that the Session and a non-equivalent SessionModel are non-equal.
     */
    @Test
    void testNotEquals() {
        assertNotEquals(new SessionModel(new HashMap<>(), "F"), session);
    }
}