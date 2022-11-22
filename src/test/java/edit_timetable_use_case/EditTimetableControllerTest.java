package edit_timetable_use_case;

import entities.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import screens.RemoveCoursePresenter;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EditTimetableControllerTest {

    RemoveCourseInputBoundary removeCourseInteractor;
    EditTimetableController controller;

    @BeforeEach
    void setUp() {
        try{
            c = new TimetableCourse("", new ArrayList<Section>(),
                "", "EGX101", "");
            ArrayList<TimetableCourse> courses = new ArrayList<TimetableCourse>(List.of(c));
            Timetable t = new Timetable(courses);
            Session s = new Session("F");
            RemoveCourseOutputBoundary p = new RemoveCoursePresenter();
            removeCourseInteractor = new RemoveCourseInteractor(t, p);
            addCourseInteractor = new AddCourseInteractor(t, s);
            controller = new EditTimetableController(removeCourseInteractor, new add);
        }
        catch (InvalidSectionsException e){
            fail("Should not have thrown an exception here.");
        }

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void removeSucceeds() {
        try {
            assertEquals("EGX101", controller.remove("EGX101").getCourseCode());
        }
        catch (RemoveCourseFailedException e){
            fail("This call should not have resulted in a RemoveCourseFailedException.")
        }
    }

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