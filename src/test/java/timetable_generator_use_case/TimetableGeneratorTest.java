package timetable_generator_use_case;
import entities.*;
import org.junit.jupiter.api.Test;
import timetable_generator_use_case.application_business.TimetableGeneratorInteractor;
import timetable_generator_use_case.interface_adapters.TimetableGeneratorPresenter;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TimetableGeneratorTest {
    /** Checks if no timetables can be made due to it overlapping with all timetables in a list of timetables */
    @Test
    void noPossibleTimetable() throws InvalidSectionsException {
        List<Block> blocks1 = new ArrayList<>();
        blocks1.add(new Block("MO", "8:30", "10:00", "room3"));
        // Create Timetable 1
        Section section1a = new Section("LEC-0101", "Bob", blocks1);
        TimetableCourse ta = new TimetableCourse("Test Course",
                new ArrayList<>(List.of(section1a)), "S", "MP101", "BR4");
        ArrayList<TimetableCourse> courseListOne = new ArrayList<>();
        courseListOne.add(ta);
        Timetable timetableOne = new Timetable(courseListOne, "S");

        // Create Timetable 2
        List<Block> blocks2 = new ArrayList<>();
        blocks2.add(new Block("MO", "8:30", "10:00", "room3"));
        Section section2a = new Section("LEC-0101", "Bob", blocks2);
        TimetableCourse timetableCourse2 = new TimetableCourse("Test Course",
                new ArrayList<>(List.of(section2a)), "S", "CSC207", "BR5");
        ArrayList<TimetableCourse> courseListTwo = new ArrayList<>();
        courseListTwo.add(timetableCourse2);
        Timetable timetableTwo = new Timetable(courseListTwo, "S");

        // List of Calendar Course
        List<Block> blocks3 = new ArrayList<>();
        blocks3.add(new Block("MO", "8:30", "10:00", "room3"));
        Section section1b = new Section("LEC-0101", "Bob", blocks3);
        CalendarCourse t2 = new CalendarCourse("Test Course", List.of(section1b), "S", "BA101", "BR4");
        TimetableGeneratorPresenter presenter = new TimetableGeneratorPresenter();
        TimetableGeneratorInteractor ti = new TimetableGeneratorInteractor(presenter);
        List<CalendarCourse> lst = new ArrayList<>();
        lst.add(t2);
        List<Timetable> timetables = new ArrayList<>();
        timetables.add(timetableOne);
        timetables.add(timetableTwo);
        assertTrue((ti.generateTimetable(lst, timetables)).isEmpty());
    }
    /** Checks if any timetables can be made */
    @Test
    void possibleTimetable() throws InvalidSectionsException {
        List<Block> blocks1 = new ArrayList<>();
        blocks1.add(new Block("MO", "10:30", "12:00", "room3"));
        // Create Timetable
        Section section1a = new Section("LEC-0101", "Bob", blocks1);
        TimetableCourse ta = new TimetableCourse("Test Course",
                new ArrayList<>(List.of(section1a)), "S", "MP101", "BR4");
        ArrayList<TimetableCourse> courseListOne = new ArrayList<>();
        courseListOne.add(ta);
        Timetable timetableOne = new Timetable(courseListOne, "S");

        // List of Calendar Course
        List<Block> blocks2 = new ArrayList<>();
        blocks2.add(new Block("MO", "8:30", "10:00", "room3"));
        Section section1b = new Section("LEC-0101", "Bob", blocks2);
        CalendarCourse t2 = new CalendarCourse("Test Course", List.of(section1b), "S", "BA101", "BR4");
        TimetableGeneratorPresenter presenter = new TimetableGeneratorPresenter();
        TimetableGeneratorInteractor ti = new TimetableGeneratorInteractor(presenter);
        List<CalendarCourse> lst = new ArrayList<>();
        lst.add(t2);
        List<Timetable> timetables = new ArrayList<>();
        timetables.add(timetableOne);
        assertFalse((ti.generateTimetable(lst, timetables)).isEmpty());
    }
}
