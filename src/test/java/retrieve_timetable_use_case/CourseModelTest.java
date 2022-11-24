package retrieve_timetable_use_case;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CourseModelTest {
    private CourseModel course;

    @BeforeEach
    public void setUp() {
        List<SectionModel> sections = new ArrayList<>();
        sections.add(new SectionModel("LEC0101", "prof!!", new ArrayList<>()));
        course = new CourseModel("title", sections, "F", "EGG100", "BR1");
    }

    @Test
    void testEquals() {
        List<SectionModel> sections = new ArrayList<>();
        sections.add(new SectionModel("LEC0101", "prof!!", new ArrayList<>()));
        assertEquals(new CourseModel("title", sections, "F", "EGG100", "BR1"), course);
    }

    @Test
    void testNotEquals() {
        List<SectionModel> otherSections = new ArrayList<>();
        assertNotEquals(new CourseModel("title", otherSections, "F", "EGG100", "BR1"), course);
    }
}