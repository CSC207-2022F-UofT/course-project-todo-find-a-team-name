package display_timetable_use_case.application_business;

import entities.*;
import org.junit.jupiter.api.Test;
import retrieve_timetable_use_case.application_business.BlockModel;
import retrieve_timetable_use_case.application_business.CourseModel;
import retrieve_timetable_use_case.application_business.SectionModel;
import retrieve_timetable_use_case.application_business.TimetableModel;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DisplayTimetableInteractorTest {

    @Test
    void testDisplayTimetableSuccess() {
        SectionModel sectionModel1 = new SectionModel("LEC-0101", "inst1",
                List.of(new BlockModel(0, 12, 13, "room1")));
        SectionModel sectionModel2 = new SectionModel("TUT-0101", "inst1",
                List.of(new BlockModel(1, 15, 16, "room1")));
        SectionModel sectionModel3 = new SectionModel("LEC-0101", "inst1",
                List.of(new BlockModel(0, 13, 14, "room1")));

        CourseModel courseModel1 = new CourseModel("course1", List.of(sectionModel1, sectionModel2),
                "S", "CSC111", "1");

        CourseModel courseModel2 = new CourseModel("course2", List.of(sectionModel3),
                "S", "CSC112", "3");


        TimetableModel expectedTimetableModel = new TimetableModel(List.of(courseModel1, courseModel2));

        DisplayTimetableOutputBoundary dummyPresenter = new DisplayTimetableOutputBoundary() {
            @Override
            public void prepareTimetable(TimetableModel timetableModel) {
                assertEquals(expectedTimetableModel, timetableModel);
            }

            @Override
            public void prepareFailView(String message) {
                fail("prepareFailView(" + message + ") should not be called.");
            }
        };
        DisplayTimetableInteractor interactor = new DisplayTimetableInteractor(dummyPresenter);

        try {

            Section section1 = new Section("LEC-0101", "inst1",
                    List.of(new Block("MO", "12:00", "13:00", "room1")));

            Section section2 = new Section("TUT-0101", "inst1",
                    List.of(new Block("TU", "15:00", "16:00", "room1")));


            Section section3 = new Section("LEC-0101", "inst1",
                    List.of(new Block("MO", "13:00", "14:00", "room1")));

            TimetableCourse course1 = new TimetableCourse("course1",
                    List.of(section1, section2), "S", "CSC111", "1");
            TimetableCourse course2 = new TimetableCourse("course2",
                    List.of(section3), "S", "CSC112", "3");

            ArrayList<TimetableCourse> courses = new ArrayList<>();
            courses.add(course1);
            courses.add(course2);
            interactor.onNext(new Timetable(courses, "S"));
        } catch (InvalidSectionsException e) {
            throw new RuntimeException(e);
        }
        interactor.displayTimetable();
    }

    @Test
    void testDisplayTimetableFail() {
        DisplayTimetableOutputBoundary dummyPresenter = new DisplayTimetableOutputBoundary() {
            @Override
            public void prepareTimetable(TimetableModel timetableModel) {
                fail("prepareTimetable should not be called.");

            }

            @Override
            public void prepareFailView(String message) {
                assertEquals("Timetable not loaded yet!", message);
            }
        };
        DisplayTimetableInteractor interactor = new DisplayTimetableInteractor(dummyPresenter);
        interactor.displayTimetable();
    }
}