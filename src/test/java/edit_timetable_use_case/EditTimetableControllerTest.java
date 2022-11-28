package edit_timetable_use_case;

import entities.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrieve_timetable_use_case.RetrieveTimetableInputBoundary;
import retrieve_timetable_use_case.RetrieveTimetableInteractor;
import screens.AddCoursePresenter;
import screens.EditTimetableController;
import screens.RemoveCoursePresenter;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * A testing class for EditTimetableController that verifies that the controller throws the correct exceptions (or
 * lack of exceptions) when remove, add or edit are called.
 * These tests do not test the correctness of corresponding interactors so much as testing that inputs result in the
 * correct exceptions being thrown. See the tests of the corresponding interactors for the above.
 */
class EditTimetableControllerTest {

    RemoveCourseInputBoundary RCInteractor;
    EditTimetableController controller;

    AddCourseInputBoundary ACInteractor;

    EditCourseInputBoundary ECInteractor;
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
            view = new TestEditTimetableView();
            RemoveCourseOutputBoundary RCPresenter = new RemoveCoursePresenter();
            RCPresenter.setView(view);
            RCInteractor = new RemoveCourseInteractor(RCPresenter);
            RCInteractor.setTimetable(t);
            RetrieveTimetableInputBoundary retrieveInteractor = new RetrieveTimetableInteractor(t, session);
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
}