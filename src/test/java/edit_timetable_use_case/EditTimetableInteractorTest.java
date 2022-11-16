package edit_timetable_use_case;

import entities.TimetableCourse;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import screens.EditTimetablePresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class EditTimetableInteractorTest {

    TimetableCourse c;
    Timetable t;
    EditTimetableInteractor interactor;

    @Before
    public void setUp(){
        c = new TimetableCourse(.....);
        ArrayList<TimetableCourse> courses = new ArrayList<TimetableCourse>(List.of(c));
        Timetable t = new Timetable(.....);
        Session s = new Session(.....);

        EditTimetablePresenter p = new EditTimetablePresenter();

        interactor = new EditTimetableInteractor(t, s, p);
    }



    /**
     * Tests to see if remove successfully removes a course in a timetable and
     * returns a EditTimetableResponseModel with the correct message.
     */
    @Test
    void removeSucceeds() {
        EditTimetableRequestModel request = new EditTimetableRequestModel("EGX101");

        try{
            String message = interactor.remove(request).getMessage();
            assertEquals("EGX101 has been successfully removed.", message);
            assertFalse(t.getCourses.contains(c));
        }
        catch(RemoveCourseFailedException e){
            fail("Remove should have succeeded here.");
        }
    }



    @Test
    void removeFails(){
        EditTimetableRequestModel request = new EditTimetableRequestModel("NAC300");
        try{
            interactor.remove(request);
            fail("Interactor should have thrown RemoveCourseFailed exception.");
        }
        catch(RemoveCourseFailedException e){
            assertEquals("NAC300 could not be removed.", e.getMessage());
        }
    }

    @Test
    void add() {
    }

    @Test
    void edit() {
    }
}