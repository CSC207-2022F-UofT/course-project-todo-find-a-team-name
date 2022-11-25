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

class EditTimetableControllerTest {

    RemoveCourseInputBoundary RCInteractor;
    EditTimetableController controller;

    AddCourseInteractor ACInteractor;

    TestEditTimetableView view;

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