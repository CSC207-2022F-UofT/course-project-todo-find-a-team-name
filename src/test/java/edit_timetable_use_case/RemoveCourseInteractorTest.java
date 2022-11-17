package edit_timetable_use_case;

import entities.Section;
import entities.TimetableCourse;
import org.junit.jupiter.api.Test;
import screens.RemoveCoursePresenter;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RemoveCourseInteractorTest {

    TimetableCourse c;
    Timetable t;
    RemoveCourseInteractor interactor;

    @BeforeEach
    public void setUp(){
        c = new TimetableCourse("", new ArrayList<Section>(),
                "", "EGX101", "");
        ArrayList<TimetableCourse> courses = new ArrayList<TimetableCourse>(List.of(c));
        Timetable t = new Timetable(.....);
        Session s = new Session(.....);

        RemoveCoursePresenter p = new RemoveCoursePresenter();

        interactor = new RemoveCourseInteractor(t, p);
    }



    /**
     * Tests to see if remove successfully removes a course in a timetable and
     * returns a EditTimetableResponseModel with the correct message.
     */
    @Test
    void removeSucceeds() {
        EditTimetableRequestModel request = new EditTimetableRequestModel("EGX101", new ArrayList<>());

        try{
            String message = interactor.remove(request).getCourseCode();
            assertEquals("EGX101", message);
            assertFalse(t.getCourses.contains(c));
        }
        catch(RemoveCourseFailedException e){
            fail("Remove should have succeeded here.");
        }
    }



    @Test
    void removeFails(){
        EditTimetableRequestModel request = new EditTimetableRequestModel("NAC300", new Arraylist<String>());
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