package edit_timetable_use_case;

import entities.*;
import org.junit.jupiter.api.BeforeEach;
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
        try{
            c = new TimetableCourse("", new ArrayList<Section>(),
                    "", "EGX101", "");
            ArrayList<TimetableCourse> courses = new ArrayList<TimetableCourse>(List.of(c));
            Timetable t = new Timetable(courses);
            interactor = new RemoveCourseInteractor(t, new RemoveCoursePresenter());
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
        }
        catch (RemoveCourseFailedException e){
            fail("Remove should have succeeded here.");
        }
        assertFalse(t.getCourseList().contains(c));
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