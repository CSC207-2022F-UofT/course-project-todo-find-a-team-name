package entities;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SessionTest {
    /** Tests if course has been added to a Session **/
    @Test
    void addingCoursesToSession() {
        // Course 1
        Section section1 = new Section("LEC0101", "A", new ArrayList<>());
        Section section2 = new Section("LEC0201", "B", new ArrayList<>());
        CalendarCourse c1 = new CalendarCourse("Test Course", new ArrayList<>(List.of(section1, section2)),
                "S", "GA101", "BR3");
        // Course 2
        Section section1a = new Section("LEC0101", "C", new ArrayList<>());
        Section section2a = new Section("LEC0201", "D", new ArrayList<>());
        CalendarCourse c2 = new CalendarCourse("Test Course",
                new ArrayList<>(List.of(section1a, section2a)),
                "S", "MP100", "BR4");
        // Course 3
        Section section1b = new Section("LEC0101", "E", new ArrayList<>());
        Section section2b = new Section("LEC0201", "F", new ArrayList<>());
        CalendarCourse c3 = new CalendarCourse("Test Course",
                new ArrayList<>(List.of(section1b, section2b)),
                "S", "MP101", "BR4");
        // Creating new sessions
        Session Winter = new Session("S");
        Session FallWinter = new Session("Y");

        assertTrue(Winter.addCourse(c1));
        assertTrue(Winter.addCourse(c2));
        assertTrue(Winter.addCourse(c3));
        assertFalse(FallWinter.addCourse(c2));
    }
    /** Checks if a course is in a session by its course code and Calendar Course object */
    @Test
    void testingCheckingCourseCodeAndCalendarCourse() {
        // Course 1
        Section section1 = new Section("LEC0101", "A", new ArrayList<>());
        Section section2 = new Section("LEC0201", "B", new ArrayList<>());
        CalendarCourse c1 = new CalendarCourse("Test Course", new ArrayList<>(List.of(section1, section2)),
                "Y", "GA101", "BR3");
        // Course 2
        Section section1a = new Section("LEC0101", "C", new ArrayList<>());
        Section section2a = new Section("LEC0201", "D", new ArrayList<>());
        CalendarCourse c2 = new CalendarCourse("Test Course",
                new ArrayList<>(List.of(section1a, section2a)),
                "Y", "MP100", "BR4");
        // Course 3
        Section section1b = new Section("LEC0101", "E", new ArrayList<>());
        Section section2b = new Section("LEC0201", "F", new ArrayList<>());
        CalendarCourse c3 = new CalendarCourse("Test Course",
                new ArrayList<>(List.of(section1b, section2b)),
                "Y", "MP101", "BR4");
        // Creating new sessions
        Session FallWinter = new Session("Y");

        FallWinter.addCourse(c1);
        FallWinter.addCourse(c2);

        assertFalse(FallWinter.checkCalendarCourse(c3));
        assertFalse(FallWinter.checkCourseCode("MP101"));

        assertTrue(FallWinter.checkCalendarCourse(c1));
        assertTrue(FallWinter.checkCourseCode("GA101"));

        assertTrue(FallWinter.checkCalendarCourse(c2));
        assertTrue(FallWinter.checkCourseCode("MP100"));
    }
    /** Checking if Session is of the right session type */
    @Test
    void testingSessionType() {
        Section section1a = new Section("LEC0101", "C", new ArrayList<>());
        Section section2a = new Section("LEC0201", "D", new ArrayList<>());
        CalendarCourse c2 = new CalendarCourse("Test Course",
                new ArrayList<>(List.of(section1a, section2a)),
                "S", "MP100", "BR4");

        Session FallWinter = new Session("Y");
        FallWinter.addCourse(c2); // Added to see if Session Type mutates even though C2 is "S"
        assertEquals("Y", FallWinter.getSessionType());
    }
    /** Checks is course code of a calendar object in a Session can have its course code correctly retrieved **/
    @Test
    void testingFindingCourseCode() {
        // Course 1
        Section section1 = new Section("LEC0101", "A", new ArrayList<>());
        Section section2 = new Section("LEC0201", "B", new ArrayList<>());
        CalendarCourse c1 = new CalendarCourse("Test Course", new ArrayList<>(List.of(section1, section2)),
                "S", "GA101", "BR3");
        // Course 2
        Section section1a = new Section("LEC0101", "C", new ArrayList<>());
        Section section2a = new Section("LEC0201", "D", new ArrayList<>());
        CalendarCourse c2 = new CalendarCourse("Test Course",
                new ArrayList<>(List.of(section1a, section2a)),
                "S", "MP100", "BR4");
        // Course 3
        Section section1b = new Section("LEC0101", "E", new ArrayList<>());
        Section section2b = new Section("LEC0201", "F", new ArrayList<>());
        CalendarCourse c3 = new CalendarCourse("Test Course",
                new ArrayList<>(List.of(section1b, section2b)),
                "S", "MP101", "BR4");
        // Creating new session
        Session Winter1 = new Session("S");
        Winter1.addCourse(c2);
        Winter1.addCourse(c1);
        Winter1.addCourse(c3);

        assertSame("GA101", Winter1.itsCourseCode(c1));
        assertSame("MP101", Winter1.itsCourseCode(c3));
        assertSame("MP100", Winter1.itsCourseCode(c2));
    }
    /** Tests if a Calendar Course object can be correctly removed from a Session **/
    @Test
    void testingRemovingCourses() {
        // Course 1
        Section section1 = new Section("LEC0101", "A", new ArrayList<>());
        Section section2 = new Section("LEC0201", "B", new ArrayList<>());
        CalendarCourse c1 = new CalendarCourse("Test Course", new ArrayList<>(List.of(section1, section2)),
                "S", "GA101", "BR3");
        // Course 2
        Section section1a = new Section("LEC0101", "C", new ArrayList<>());
        Section section2a = new Section("LEC0201", "D", new ArrayList<>());
        CalendarCourse c2 = new CalendarCourse("Test Course",
                new ArrayList<>(List.of(section1a, section2a)),
                "S", "MP100", "BR4");
        // Course 3
        Section section1b = new Section("LEC0101", "E", new ArrayList<>());
        Section section2b = new Section("LEC0201", "F", new ArrayList<>());
        CalendarCourse c3 = new CalendarCourse("Test Course",
                new ArrayList<>(List.of(section1b, section2b)),
                "S", "MP101", "BR4");
        // Creating new session
        Session Winter1 = new Session("S");
        Winter1.addCourse(c2);
        Winter1.addCourse(c1);
        Winter1.addCourse(c3);

        Winter1.removeCourse(Winter1.itsCourseCode(c3));
        Winter1.removeCourse(Winter1.itsCourseCode(c2));

        assertEquals(1, Winter1.numberOfCourses());
        assertTrue(Winter1.allCoursesSession().contains(c1));
        assertFalse(Winter1.allCoursesSession().contains(c2));
        assertFalse(Winter1.allCoursesSession().contains(c3));
    }
    /** Tests if Calendar Course object can be retrieved correctly from a Session using its course code*/
    @Test
    void testingGetCalendarCode() {
        // Course 1
        Section section1 = new Section("LEC0101", "A", new ArrayList<>());
        Section section2 = new Section("LEC0201", "B", new ArrayList<>());
        CalendarCourse c1 = new CalendarCourse("Test Course", new ArrayList<>(List.of(section1, section2)),
                "S", "GA101", "BR3");
        // Course 2
        Section section1a = new Section("LEC0101", "C", new ArrayList<>());
        Section section2a = new Section("LEC0201", "D", new ArrayList<>());
        CalendarCourse c2 = new CalendarCourse("Test Course",
                new ArrayList<>(List.of(section1a, section2a)),
                "S", "MP100", "BR4");
        // Course 3
        Section section1b = new Section("LEC0101", "E", new ArrayList<>());
        Section section2b = new Section("LEC0201", "F", new ArrayList<>());
        CalendarCourse c3 = new CalendarCourse("Test Course",
                new ArrayList<>(List.of(section1b, section2b)),
                "S", "MP101", "BR4");
        // Creating new session
        Session Winter1 = new Session("S");
        Winter1.addCourse(c2);
        Winter1.addCourse(c1);
        Winter1.addCourse(c3);

        assertEquals(c2, Winter1.getCalendarCourse("MP100"));
        assertEquals(c3, Winter1.getCalendarCourse("MP101"));
        assertEquals(c1, Winter1.getCalendarCourse("GA101"));
    }
    /** Testing if all courses with the given BR is returned from a Session */
    @Test
    void testingAllCourseCodesBRSession() {
        // Course 1
        Section section1 = new Section("LEC0101", "A", new ArrayList<>());
        Section section2 = new Section("LEC0201", "B", new ArrayList<>());
        CalendarCourse c1 = new CalendarCourse("Test Course", new ArrayList<>(List.of(section1, section2)),
                "S", "GA101", "BR3");
        // Course 2
        Section section1a = new Section("LEC0101", "C", new ArrayList<>());
        Section section2a = new Section("LEC0201", "D", new ArrayList<>());
        CalendarCourse c2 = new CalendarCourse("Test Course",
                new ArrayList<>(List.of(section1a, section2a)),
                "S", "MP100", "BR4");
        // Course 3
        Section section1b = new Section("LEC0101", "E", new ArrayList<>());
        Section section2b = new Section("LEC0201", "F", new ArrayList<>());
        CalendarCourse c3 = new CalendarCourse("Test Course",
                new ArrayList<>(List.of(section1b, section2b)),
                "S", "MP101", "BR4");
        // Creating new sessions
        Session Winter1 = new Session("S");
        Winter1.addCourse(c2);
        Winter1.addCourse(c1);
        Winter1.addCourse(c3);

        assertEquals(Winter1.allCourseCodesBRSession("BR4"), new ArrayList<>(List.of("MP100", "MP101")));
        assertEquals(Winter1.allCourseCodesBRSession("BR3"), new ArrayList<>(List.of("GA101")));
        assertEquals(Winter1.allCourseCodesBRSession("BR1"), new ArrayList<>());
    }
    /** Tests if all course codes can be retrieved from a session */
    @Test
    void testingAllCourseCodesSession() {
        // Course 2
        Section section1a = new Section("LEC0101", "C", new ArrayList<>());
        Section section2a = new Section("LEC0201", "D", new ArrayList<>());
        CalendarCourse c2 = new CalendarCourse("Test Course",
                new ArrayList<>(List.of(section1a, section2a)),
                "S", "MP100", "BR4");
        // Course 3
        Section section1b = new Section("LEC0101", "E", new ArrayList<>());
        Section section2b = new Section("LEC0201", "F", new ArrayList<>());
        CalendarCourse c3 = new CalendarCourse("Test Course",
                new ArrayList<>(List.of(section1b, section2b)),
                "S", "MP101", "BR4");
        // Creating new sessions
        Session Winter1 = new Session("S");
        Session Fall1 = new Session("F");
        Winter1.addCourse(c2);
        Winter1.addCourse(c3);

        assertTrue(Winter1.allCourseCodesSession().contains("MP100"));
        assertTrue(Winter1.allCourseCodesSession().contains("MP101"));
        assertFalse(Winter1.allCourseCodesSession().contains("GA101"));
        assertEquals(new ArrayList<>(), Fall1.allCourseCodesSession());
    }
    /** Tests if all Calendar courses can be retrieved from a session */
    @Test
    void testingAllCourseSession() {
        Section section1a = new Section("LEC0101", "C", new ArrayList<>());
        Section section2a = new Section("LEC0201", "D", new ArrayList<>());
        CalendarCourse c2 = new CalendarCourse("Test Course",
                new ArrayList<>(List.of(section1a, section2a)),
                "S", "MP100", "BR4");
        Section section1b = new Section("LEC0101", "E", new ArrayList<>());
        Section section2b = new Section("LEC0201", "F", new ArrayList<>());
        CalendarCourse c3 = new CalendarCourse("Test Course",
                new ArrayList<>(List.of(section1b, section2b)),
                "S", "MP101", "BR4");
        Session Winter = new Session("S");
        Winter.addCourse(c2);
        Winter.addCourse(c3);

        assertTrue(Winter.allCoursesSession().contains(c2));
        assertTrue(Winter.allCoursesSession().contains(c3));
    }
}
