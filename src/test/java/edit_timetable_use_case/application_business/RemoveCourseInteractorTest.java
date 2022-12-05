package edit_timetable_use_case.application_business;

import edit_timetable_use_case.interface_adapters.TestEditTimetableView;
import entities.InvalidSectionsException;
import entities.Session;
import entities.Timetable;
import entities.TimetableCourse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import edit_timetable_use_case.interface_adapters.RemoveCoursePresenter;
import retrieve_timetable_use_case.application_business.RetrieveTimetableInteractor;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * A class to test the RemoveCourseInteractor.
 */
class RemoveCourseInteractorTest {

    TimetableCourse c;
    Timetable t;
    RemoveCourseInteractor interactor;

    /**
     * Creates a timetable with a course EGX101, and a corresponding view and presenter.
     */
    @BeforeEach
    public void setUp(){
        try{
            c = new TimetableCourse("", new ArrayList<>(),
                    "", "EGX101", "");
            ArrayList<TimetableCourse> courses = new ArrayList<>(List.of(c));
            t = new Timetable(courses, "F");
            RemoveCoursePresenter presenter = new RemoveCoursePresenter();
            interactor = new RemoveCourseInteractor(presenter);
            presenter.setView(new TestEditTimetableView());
            interactor.setTimetable(t);
            RetrieveTimetableInteractor retrieveInteractor = new RetrieveTimetableInteractor();
            retrieveInteractor.setTimetable(t);
            retrieveInteractor.setSession(new Session(""));
            interactor.setRetrieveInteractor(retrieveInteractor);
        }
        catch (InvalidSectionsException e) {
            fail("Should not have thrown an error.");
        }

    }



    /**
     * Tests to see if remove successfully removes a course in a timetable and
     * returns a EditTimetableResponseModel with the correct message.
     */
    @Test
    void removeSucceeds() {
        EditTimetableRequestModel request = new EditTimetableRequestModel("EGX101", new ArrayList<>());
        try {
            interactor.remove(request);
            assertFalse(t.getCourseList().contains(c));
        }
        catch (RemoveCourseFailedException e){
            fail("Remove should have succeeded here.");
        }
    }


    /**
     * Remove should fail here, since no course in the timetable has course code NAC300.
     */
    @Test
    void removeFails(){
        EditTimetableRequestModel request = new EditTimetableRequestModel("NAC300", new ArrayList<>());
        try{
            interactor.remove(request);
            fail("Interactor should have thrown RemoveCourseFailed exception.");
        }
        catch(RemoveCourseFailedException e){
            assertEquals("NAC300 could not be removed.", e.getMessage());
        }
    }


}