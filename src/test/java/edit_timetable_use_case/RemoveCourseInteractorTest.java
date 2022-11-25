package edit_timetable_use_case;

import entities.InvalidSectionsException;
import entities.Section;
import entities.Timetable;
import entities.TimetableCourse;
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
            t = new Timetable(courses, "F");
            RemoveCourseOutputBoundary presenter = new RemoveCoursePresenter();
            interactor = new RemoveCourseInteractor(presenter);
            presenter.setView(new TestEditTimetableView());
            interactor.setTimetable(t);
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



    @Test
    void removeFails(){
        EditTimetableRequestModel request = new EditTimetableRequestModel("NAC300", new ArrayList<String>());
        try{
            interactor.remove(request);
            fail("Interactor should have thrown RemoveCourseFailed exception.");
        }
        catch(RemoveCourseFailedException e){
            assertEquals("NAC300 could not be removed.", e.getMessage());
        }
    }


}