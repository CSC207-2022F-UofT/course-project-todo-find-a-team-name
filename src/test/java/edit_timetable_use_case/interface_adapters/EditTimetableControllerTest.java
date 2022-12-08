package edit_timetable_use_case.interface_adapters;

import edit_timetable_use_case.application_business.*;
import entities.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrieve_timetable_use_case.application_business.RetrieveTimetableInteractor;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * A testing class for EditTimetableController that verifies that the controller throws the correct exceptions (or
 * lack of exceptions) when remove, add or edit are called.
 * These tests do not test the correctness of corresponding interactors so much as testing that inputs result in the
 * correct exceptions being thrown. See the tests of the corresponding interactors for the above.
 */
class EditTimetableControllerTest {

    RemoveCourseInteractor RCInteractor;
    EditTimetableController controller;

    AddCourseInteractor ACInteractor;

    EditCourseInteractor ECInteractor;
    TestEditTimetableView view;

    /**
     * Creates an EditTimetableController (controller), corresponding interactors from the edit, remove and add
     * use cases, and the view, and inserts a non-empty timetable.
     */
    @BeforeEach
    void setUp() {
        try{
            TimetableCourse c = new TimetableCourse("", new ArrayList<>(),
                "", "EGX101", "");
            ArrayList<TimetableCourse> courses = new ArrayList<>(List.of(c));
            Timetable t = new Timetable(courses, "F");

            Session session = new Session("F");
            List<Section> sections = new ArrayList<>();
            sections.add(new Section("LEC0101", "instr1", new ArrayList<>()));
            sections.add(new Section("TUT0102", "instr1", new ArrayList<>()));
            sections.add(new Section("TUT0101", "instr1", new ArrayList<>()));
            sections.add(new Section("PRA0101", "instr1", new ArrayList<>()));
            CalendarCourse course = new CalendarCourse("some course", sections, "F",
                    "CSC108", "1");

            view = new TestEditTimetableView();
            RemoveCoursePresenter RCPresenter = new RemoveCoursePresenter();
            RCPresenter.setView(view);
            RCInteractor = new RemoveCourseInteractor(RCPresenter);
            RCInteractor.setTimetable(t);
            RetrieveTimetableInteractor retrieveInteractor = new RetrieveTimetableInteractor();
            retrieveInteractor.setTimetable(t);
            retrieveInteractor.setSession(session);
            RCInteractor.setRetrieveInteractor(retrieveInteractor);
            AddCoursePresenter ACPresenter = new AddCoursePresenter();
            ACPresenter.setView(view);
            ACInteractor = new AddCourseInteractor(ACPresenter);
            ACInteractor.setRetrieveInteractor(retrieveInteractor);
            EditCoursePresenter ECPresenter = new EditCoursePresenter();
            ECPresenter.setView(view);
            ECInteractor = new EditCourseInteractor(ECPresenter);
            ECInteractor.setRetrieveInteractor(retrieveInteractor);
            controller = new EditTimetableController(RCInteractor, ACInteractor, ECInteractor);
            ACPresenter.setView(view);
            ACInteractor.setTimetable(t);

            session.addCourse(course);
            ACInteractor.setSession(session);
        }
        catch (InvalidSectionsException e){
            fail("Should not have thrown an exception here.");
        }

    }

    @AfterEach
    void tearDown() {
    }

    /**
     * Remove should be successfully called in this case.
     */
    @Test
    void removeSucceeds() {
        try {
            controller.remove("EGX101");
        }
        catch (RemoveCourseFailedException e){
            fail("This call should not have resulted in a RemoveCourseFailedException.");
        }
    }

    /**
     * Remove should throw a RemoveCourseFailedException because the course code given doesn't exist in the timetable.
     */
    @Test
    void removeFails(){
        try{
            controller.remove("NAC300");
            fail("Interactor should have thrown RemoveCourseFailed exception.");
        }
        catch(RemoveCourseFailedException e){
            assertTrue(true);
        }
    }

    /**
     * Tests that a correct call (without more than 1 section of any given type) does not raise an exception.
     */
    @Test
    void addSucceeds(){
        List<String> sectionCodes = new ArrayList<>();
        sectionCodes.add("TUT0101");
        sectionCodes.add("LEC0101");
        sectionCodes.add("PRA0101");
        try{
            controller.add("CSC108", sectionCodes);
            assertTrue(true);
        }
        catch(InvalidSectionsException e){
            fail("Valid sections were given. Add should not have failed here.");
        }
    }

    /**
     * Tests that an invalid call (with more than 1 section of a given type) raises an InvalidSectionsException.
     */
    @Test
    void addFails(){
        List<String> sectionCodes = new ArrayList<>();
        sectionCodes.add("TUT0101");
        sectionCodes.add("TUT0102");
        try{
            controller.add("CSC108", sectionCodes);
            fail("This call should have thrown an InvalidSectionsException.");
        }
        catch(InvalidSectionsException e){
            assertTrue(true);
        }
    }

}
