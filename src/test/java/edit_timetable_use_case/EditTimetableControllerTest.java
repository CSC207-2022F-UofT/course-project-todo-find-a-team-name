package edit_timetable_use_case;

import entities.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import screens.AddCoursePresenter;
import screens.EditTimetableController;
import screens.RemoveCoursePresenter;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * A testing class for EditTimetableController that verifies that the controller throws the correct exceptions (or
 * lack of exceptions) when remove, add or edit are called.
 */
class EditTimetableControllerTest {

    RemoveCourseInputBoundary RCInteractor;
    EditTimetableController controller;

    AddCourseInteractor ACInteractor;

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
            AddCoursePresenter ACPresenter = new AddCoursePresenter();
            ACPresenter.setView(view);
            ACInteractor = new AddCourseInteractor(ACPresenter);
            controller = new EditTimetableController(RCInteractor, ACInteractor);
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
            assertTrue(true);
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