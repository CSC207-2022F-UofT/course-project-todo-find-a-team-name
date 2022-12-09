package edit_timetable_use_case.application_business;

import edit_timetable_use_case.interface_adapters.EditCoursePresenter;
import edit_timetable_use_case.interface_adapters.TestEditTimetableView;
import entities.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrieve_timetable_use_case.application_business.RetrieveTimetableInteractor;

import java.util.ArrayList;
import java.util.List;

/**
 * A suite of tests to confirm that EditCourseInteractor correctly mutates Timetable objects and throws exceptions
 * on invalid inputs.
 */
class EditCourseInteractorTest {
    private Timetable t;
    private EditCourseInteractor interactor;
    private Section s1;
    private Section s2;
    private Section s3;

    @BeforeEach
    void setUp() {
        s1 = new Section("LEC0101", "", new ArrayList<>());
        s2 = new Section("LEC0102", "", new ArrayList<>());
        s3 = new Section("TUT0101", "", new ArrayList<>());

        List<Section> calendarSections = new ArrayList<>();
        calendarSections.add(s1);
        calendarSections.add(s2);
        calendarSections.add(s3);

        List<Section> timetableSections = new ArrayList<>();
        timetableSections.add(s1);
        timetableSections.add(s3);

        List<TimetableCourse> ttCourses = new ArrayList<>();
        try {
            ttCourses.add(new TimetableCourse("", timetableSections, "F", "CSC207", "1"));
            t = new Timetable(ttCourses, "F");
            Session s = new Session("F");
            s.addCourse(new CalendarCourse("", calendarSections, "F", "CSC207", "1"));

            EditCoursePresenter presenter = new EditCoursePresenter();
            presenter.setView(new TestEditTimetableView());
            interactor = new EditCourseInteractor(presenter);
            interactor.setTimetable(t);
            interactor.setSession(s);
            RetrieveTimetableInteractor retrieveInteractor = new RetrieveTimetableInteractor();
            retrieveInteractor.setSession(s);
            retrieveInteractor.setTimetable(t);
            interactor.setRetrieveInteractor(retrieveInteractor);
        }
        catch (InvalidSectionsException ignored){}
    }

    /**
     * In this test, we edit CSC207 so that it goes from having sections LEC0101, TUT0101, to having LEC0102, TUT0101
     * and confirm that it does so.
     */
    @Test
    void editSucceeds() {
        List<String> sectionCodes = new ArrayList<>();
        sectionCodes.add("LEC0102");
        sectionCodes.add("TUT0101");
        EditTimetableRequestModel request = new EditTimetableRequestModel("CSC207", sectionCodes);
        try{
            interactor.edit(request);
        }
        catch(InvalidSectionsException  | NotInTimetableException e){
            Assertions.fail("No exception should have been thrown here.");
        }
        Assertions.assertTrue(t.getCourse("CSC207").getSections().contains(s2));
        Assertions.assertTrue(t.getCourse("CSC207").getSections().contains(s3));
        Assertions.assertFalse(t.getCourse("CSC207").getSections().contains(s1));
    }


    /**
     * We request to edit the course NOT100, which is not in the timetable, and so we expect a NotInTimetableException
     * to be raised.
     */
    @Test
    void editNotInTimetable() {
        EditTimetableRequestModel request = new EditTimetableRequestModel("NOT100", new ArrayList<>());

        try{
            interactor.edit(request);
            Assertions.fail("Edit should not have succeeded here.");
        }
        catch(NotInTimetableException e){
            Assertions.assertTrue(true);
        }
        catch(InvalidSectionsException e){
            Assertions.fail("Edit should have raised a NotInTimetableException.");
        }
    }

    /**
     *
     */
    @Test
    void editInvalidSections() {
        List<String> codes = new ArrayList<>();
        codes.add("LEC0101");
        codes.add("LEC0102");
        EditTimetableRequestModel request = new EditTimetableRequestModel("CSC207", codes);

        try{
            interactor.edit(request);
            Assertions.fail("Edit should not have succeeded here.");
        }
        catch(NotInTimetableException e){
            Assertions.fail("Edit should have raised a InvalidSectionsException.");
        }
        catch(InvalidSectionsException e){
            Assertions.assertTrue(true);
        }

    }
}