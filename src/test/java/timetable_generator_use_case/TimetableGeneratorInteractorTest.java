package timetable_generator_use_case;
import entities.*;
import generate_timetable_course_use_case.TimetableCourseGenerator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import timetable_generator_use_case.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class TimeTableGeneratorInteractorTest {
    @BeforeAll
    // Create CalendarCourse, list of Timetables, and request models
    static void setup() throws InvalidSectionsException {
        Section section1a = new Section("LEC0101", "Bob", new ArrayList<Block>());
        Section section2a = new Section("LEC0201", "Bobby", new ArrayList<Block>());
        TimetableCourse t1 = new TimetableCourse("Test Course",
                new ArrayList<Section>(List.of(section1a, section2a)),
                "S", "MP101", "BR4");
        Section section1b = new Section("LEC0101", "Bob", new ArrayList<Block>());
        Section section2b = new Section("LEC0201", "Bobby", new ArrayList<Block>());
        TimetableCourse t2 = new TimetableCourse("Test Course",
                new ArrayList<Section>(List.of(section1b, section2b)),
                "S", "CS101", "BR5");
        Section section1c = new Section("LEC0101", "Bob", new ArrayList<Block>());
        Section section2c = new Section("LEC0201", "Bobby", new ArrayList<Block>());
        TimetableCourse t3 = new TimetableCourse("Test Course",
                new ArrayList<Section>(List.of(section1c, section2c)),
                "S", "CSC236", "BR5");
        ArrayList<TimetableCourse> courseListOne = new ArrayList<>();
        courseListOne.add(t1);
        courseListOne.add(t2);
        courseListOne.add(t3);
        Timetable timetableOne = new Timetable(courseListOne, "S");

        Section section1d = new Section("LEC0101", "Bob", new ArrayList<Block>());
        Section section2d = new Section("LEC0201", "Bobby", new ArrayList<Block>());
        TimetableCourse t4 = new TimetableCourse("Test Course",
                new ArrayList<Section>(List.of(section1d, section2d)),
                "F", "CSC110", "BR4");
        Section section1e = new Section("LEC0101", "Bob", new ArrayList<Block>());
        Section section2e = new Section("LEC0201", "Bobby", new ArrayList<Block>());
        TimetableCourse t5 = new TimetableCourse("Test Course",
                new ArrayList<Section>(List.of(section1e, section2e)),
                "F", "CSC111", "BR5");
        Section section1f = new Section("LEC0101", "Bob", new ArrayList<Block>());
        Section section2f = new Section("LEC0201", "Bobby", new ArrayList<Block>());
        TimetableCourse t6 = new TimetableCourse("Test Course",
                new ArrayList<Section>(List.of(section1f, section2f)),
                "F", "CSC207", "BR5");
        ArrayList<TimetableCourse> courseListTwo = new ArrayList<>();
        courseListTwo.add(t4);
        courseListTwo.add(t5);
        courseListTwo.add(t6);
        Timetable timetableTwo = new Timetable(courseListTwo, "F");

        TimetableGeneratorRequestModel requestModelOne = new
                TimetableGeneratorRequestModel(new ArrayList<Timetable>(List.of(timetableOne,timetableTwo)));
    }
//    void noPossibleTimetable(){
//        ArrayList<TimetableCourse> noCourses = new ArrayList<TimetableCourse>();
//        Timetable emptyTimetable = new Timetable(noCourses, "F");
//        ArrayList<Timetable> generatedTimetables = new ArrayList<Timetable>();
//        generatedTimetables.add(emptyTimetable);
//        TimetableGeneratorInteractor interactor = new TimetableGeneratorInteractor();
//        ArrayList<CalendarCourse> overlap = new ArrayList<CalendarCourse>();
//        generatedTimetables = interactor.timetableGenerator(emptyTimetable, )
//    Needs to use the gateway for the test but gateway is WIP
}

