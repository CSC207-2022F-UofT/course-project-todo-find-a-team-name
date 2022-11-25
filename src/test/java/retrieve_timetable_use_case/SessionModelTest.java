package retrieve_timetable_use_case;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void testEquals() {
        HashMap<String, CourseModel> courses = new HashMap<>();
        courses.put("SD204", (new CourseModel("Something cool", new ArrayList<>(), "F", "SD204", "BR1")));
        assertEquals(new SessionModel(courses, "F"), session);
    }

    @Test
    void testNotEquals() {
        assertNotEquals(new SessionModel(new HashMap<>(), "F"), session);
    }
}