package retrieve_timetable_use_case;

import entities.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrieve_timetable_use_case.application_business.*;
import retrieve_timetable_use_case.interface_adapters.RetrieveTimetableController;
import retrieve_timetable_use_case.interface_adapters.TimetableModelConverter;
import display_timetable_use_case.frameworks_and_drivers.SessionViewModel;

import java.util.ArrayList;

/**
 * A suite of tests for RetrieveTimetableController.
 */
class RetrieveTimetableControllerTest {

    private CourseModel courseModel;
    private TimetableModel timetableModel;

    private RetrieveTimetableController controller;
    private TestRetrieveTimetableView view;
    SessionViewModel sessionViewModel;

    /**
     * Sets up the controller, as well as a set of timetable data structures and their corresponding models for later
     * comparison.
     */
    @BeforeEach
    void setUp() {

        BlockModel blockModel = new BlockModel(2, 14, 16, "AB106");
        Block blockActual = new Block("WE", "14:00", "16:00", "AB106");

        ArrayList<BlockModel> modelBlocks = new ArrayList<>();
        modelBlocks.add(blockModel);

        ArrayList<Block> actualBlocks = new ArrayList<>();
        actualBlocks.add(blockActual);

        Section sectionActual = new Section("LEC0101", "prof!!", actualBlocks);
        SectionModel sectionModel = new SectionModel("LEC0101", "prof!!", modelBlocks);

        ArrayList<SectionModel> modelSections = new ArrayList<>();
        modelSections.add(sectionModel);

        ArrayList<Section> actualSections = new ArrayList<>();
        actualSections.add(sectionActual);

        courseModel = new CourseModel("some course", modelSections, "F", "EGG100", "" +
                "BR1");
        CalendarCourse courseActual = new CalendarCourse("some course", actualSections, "F", "EGG100", "" +
                "BR1");
        try {
            ArrayList<TimetableCourse> timetableCourses = new ArrayList<>();
            timetableCourses.add(new TimetableCourse("some course", actualSections, "F",
                    "EGG100", "BR1"));

            ArrayList<CourseModel> timetableModelCourses = new ArrayList<>();
            timetableModelCourses.add(courseModel);

            Session sessionActual = new Session("F");
            sessionActual.addCourse(courseActual);

            Timetable timetable = new Timetable(timetableCourses, "F");

            timetableModel = new TimetableModel(timetableModelCourses);

            sessionViewModel = TimetableModelConverter.sessionToView(EntityConverter.generateSessionResponse(sessionActual));

            RetrieveTimetablePresenter presenter = new RetrieveTimetablePresenter();
            view = new TestRetrieveTimetableView();
            presenter.setView(view);

            RetrieveTimetableInteractor interactor = new RetrieveTimetableInteractor(presenter);
            interactor.setTimetable(timetable);
            interactor.setSession(sessionActual);

            controller = new RetrieveTimetableController(interactor);
        }
        catch (InvalidSectionsException ignored){
        }
    }

    @AfterEach
    void tearDown() {
    }

    /**
     * Tests that the object returned by a call of retrieveCalendarCourse has data equivalent to the corresponding
     * CalendarCourse. (See TimetableCourse.equals() for further details).
     */
    @Test
    void retrieveCalendarCourse() {
        Assertions.assertEquals(controller.retrieveCalendarCourse("EGG100"), courseModel);
    }

    /**
     * Tests that the object returned by a call of retrieveTimetableCourse has data equivalent to the corresponding
     * TimetableCourse. (See TimetableCourse.equals() for further details).
     */
    @Test
    void retrieveTimetableCourse() {
        Assertions.assertEquals(controller.retrieveTimetableCourse("EGG100"), courseModel);
    }

    /**
     * Tests that the object returned by a call of retrieveTimetable has data equivalent to the corresponding
     * Timetable. (See Timetable.equals() for further details).
     */
    @Test
    void retrieveTimetable() {
        Assertions.assertEquals(controller.retrieveTimetable(), timetableModel);
    }

    /**
     * Tests that the controller eventually causes the correct view model to be set in the view.
     */
    @Test
    void updateSession(){
        controller.updateSession();
        Assertions.assertEquals(sessionViewModel, view.session);
    }
}