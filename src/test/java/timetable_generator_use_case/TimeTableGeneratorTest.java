package timetable_generator_use_case;
import entities.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TimeTableGeneratorTest {
    @Test
    void noPossibleTimetable() throws InvalidSectionsException {
        List<Block> blocks1 = new ArrayList<>();
        blocks1.add(new Block("MO", "8:30", "10:00", "room3"));
        // Create Timetable
        Section section1a = new Section("LEC-0101", "Bob", blocks1);
        TimetableCourse ta = new TimetableCourse("Test Course",
                new ArrayList<>(List.of(section1a)), "S", "MP101", "BR4");
        ArrayList<TimetableCourse> courseListOne = new ArrayList<>();
        courseListOne.add(ta);
        Timetable timetableOne = new Timetable(courseListOne, "S");
        // Create request model storing timetable
        TimetableGeneratorRequestModel requestModelOne = new
                TimetableGeneratorRequestModel(new ArrayList<>(List.of(timetableOne)));
        // Calendar Course
        List<Block> blocks2 = new ArrayList<>();
        blocks2.add(new Block("MO", "8:30", "10:00", "room3"));
        Section section1b = new Section("LEC-0101", "Bob", blocks2);
        CalendarCourse t2 = new CalendarCourse("Test Course", List.of(section1b), "S", "BA101", "BR4");
        TimetableGeneratorInteractor ti = new TimetableGeneratorInteractor();
        assertTrue(ti.timetableCourseAdder(t2, requestModelOne).isEmpty());
    }
}

