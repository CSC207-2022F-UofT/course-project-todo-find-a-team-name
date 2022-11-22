package edit_timetable_use_case;

import entities.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import screens.AddCoursePresenter;
import screens.RemoveCoursePresenter;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EditTimetableControllerTest {

    RemoveCourseInputBoundary RCInteractor;
    EditTimetableController controller;

    AddCourseInteractor ACInteractor;

    @BeforeEach
    void setUp() {
        try{
            TimetableCourse c = new TimetableCourse("", new ArrayList<Section>(),
                "", "EGX101", "");
            ArrayList<TimetableCourse> courses = new ArrayList<TimetableCourse>(List.of(c));
            Timetable t = new Timetable(courses, "F");
            Session s = new Session("F");
            RemoveCourseOutputBoundary p = new RemoveCoursePresenter();
            RCInteractor = new RemoveCourseInteractor(p);
            RCInteractor.setTimetable(t);
            ACInteractor = new AddCourseInteractor(new AddCoursePresenter());
            controller = new EditTimetableController(RCInteractor, ACInteractor);
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
            controller.remove("EGX101");
            assertTrue(true);
        }
        catch (RemoveCourseFailedException e){
            fail("This call should not have resulted in a RemoveCourseFailedException.");
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