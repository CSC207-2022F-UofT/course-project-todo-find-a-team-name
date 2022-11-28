package edit_timetable_use_case;

import entities.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrieve_timetable_use_case.RetrieveTimetableInputBoundary;
import retrieve_timetable_use_case.RetrieveTimetableInteractor;
import screens.AddCoursePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * A suite of tests for the AddCourseInteractor, testing that a given timetable entity is correctly mutated on a
 * call of add, or throwing an exception when an invalid set of sections is given.
 */
class AddCourseInteractorTest {
    AddCourseInteractor interactor;
    Timetable timetable;

    @BeforeEach
    void setUp() {
        AddCoursePresenter presenter = new AddCoursePresenter();
        presenter.setView(new TestEditTimetableView());
        interactor = new AddCourseInteractor(presenter);


        timetable = new Timetable(new ArrayList<>(), "F");
        Session session = new Session("F");
        List<Section> sections = new ArrayList<>();
        sections.add(new Section("LEC0101", "instr1", new ArrayList<>()));
        sections.add(new Section("TUT0102", "instr1", new ArrayList<>()));
        sections.add(new Section("TUT0101", "instr1", new ArrayList<>()));
        sections.add(new Section("PRA0101", "instr1", new ArrayList<>()));
        CalendarCourse course = new CalendarCourse("some course", sections, "F",
                "CSC108", "1");
        session.addCourse(course);
        RetrieveTimetableInputBoundary retrieveInteractor = new RetrieveTimetableInteractor(timetable, session);

        interactor.setTimetable(timetable);
        interactor.setRetrieveInteractor(retrieveInteractor);
        interactor.setSession(session);
    }

    @AfterEach
    void tearDown() {
    }

    /**
     * Tests to see that at least 1 section of each type can be selected in an added course.
     */
    @Test
    void addSucceeds() {
        List<String> sectionCodes = new ArrayList<>();
        sectionCodes.add("TUT0101");
        sectionCodes.add("LEC0101");
        sectionCodes.add("PRA0101");
        EditTimetableRequestModel request = new EditTimetableRequestModel("CSC108", sectionCodes);
        try{
            interactor.add(request);
            assert(timetable.getCourse("CSC108").getTutorial().getCode().equals("TUT0101"));
            assert(timetable.getCourse("CSC108").getLecture().getCode().equals("LEC0101"));
            assert(timetable.getCourse("CSC108").getPractical().getCode().equals("PRA0101"));
        }
        catch (InvalidSectionsException e){
            Assertions.fail("Add's call should have succeeded here.");
        }

    }

    /**
     * Tests to see that add fails when more than one of a given section type is selected when adding courses.
     */
    @Test
    void addFails() {
        List<String> sectionCodes = new ArrayList<>();
        sectionCodes.add("TUT0101");
        sectionCodes.add("TUT0102");
        EditTimetableRequestModel request = new EditTimetableRequestModel("CSC108", sectionCodes);
        try{
            interactor.add(request);
            Assertions.fail("Add's call should fail since it was given two TUT sections.");
        }
        catch (InvalidSectionsException e){
            Assertions.assertTrue(true);
        }
    }
}