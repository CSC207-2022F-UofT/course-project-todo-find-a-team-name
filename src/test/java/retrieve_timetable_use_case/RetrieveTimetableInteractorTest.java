package retrieve_timetable_use_case;

import entities.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrieve_timetable_use_case.application_business.*;
import retrieve_timetable_use_case.interface_adapters.TimetableModelConverter;
import screens.SessionViewModel;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A suite of tests for the RetrieveTimetableInteractor.
 */
class RetrieveTimetableInteractorTest {
    private RetrieveTimetableInteractor interactor;
    private CourseModel courseModel;
    private SessionModel sessionModel;
    private SessionViewModel sessionViewModel;
    private TimetableModel timetableModel;
    private TestRetrieveTimetableView view;

    /**
     * Creates a mock-up session and timetable with non-empty courses, sections and blocks, as well as
     * equivalent Models.
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

        HashMap<String, CourseModel> modelCourses = new HashMap<>();
        try {
            ArrayList<TimetableCourse> timetableCourses = new ArrayList<>();
            timetableCourses.add(new TimetableCourse("some course", actualSections, "F",
                    "EGG100", "BR1"));

            ArrayList<CourseModel> timetableModelCourses = new ArrayList<>();
            timetableModelCourses.add(courseModel);

            modelCourses.put(courseModel.getCourseCode(), courseModel);

            sessionModel = new SessionModel(modelCourses, "F");
            sessionViewModel = TimetableModelConverter.sessionToView(sessionModel);
            Session sessionActual = new Session("F");
            sessionActual.addCourse(courseActual);

            Timetable timetable = new Timetable(timetableCourses, "F");

            timetableModel = new TimetableModel(timetableModelCourses);

            RetrieveTimetablePresenter presenter = new RetrieveTimetablePresenter();
            view = new TestRetrieveTimetableView();
            presenter.setView(view);

            interactor = new RetrieveTimetableInteractor(presenter);
            interactor.setTimetable(timetable);
            interactor.setSession(sessionActual);
        }
        catch (InvalidSectionsException ignored){
        }
    }

    @AfterEach
    void tearDown() {
    }

    /**
     * Asserts that the CourseModel object returned by the interactor is equivalent to the input TimetableCourse.
     */
    @Test
    void retrieveTimetableCourse() {
        RetrieveTimetableRequestModel request = new RetrieveTimetableRequestModel(
                "", "EGG100", "");
        Assertions.assertEquals(courseModel, interactor.retrieveTimetableCourse(request));
    }

    /**
     * Asserts that the CourseModel object returned by the interactor is equivalent to the input CalendarCourse.
     */
    @Test
    void retrieveCalendarCourse() {
        RetrieveTimetableRequestModel request = new RetrieveTimetableRequestModel(
                "", "EGG100", "");
        Assertions.assertEquals(courseModel, interactor.retrieveCalendarCourse(request));
    }

    /**
     * Asserts that the SessionModel object returned by the interactor is equivalent to the input Session.
     */
    @Test
    void retrieveSession() {
        Assertions.assertEquals(sessionModel, interactor.retrieveSession());
    }

    /**
     * Asserts that the TimetableModel object returned by the interactor is equivalent to the input Timetable.
     */
    @Test
    void retrieveTimetable() {
        Assertions.assertEquals(timetableModel, interactor.retrieveTimetable());
    }

    /**
     * Asserts that the interactor updates the view with the correct session model as expected.
     * This test's functionality is limited because of the specificity of equality checking on the view models.
     */
    @Test
    void updateSession(){
        interactor.updateSession();
        Assertions.assertTrue(sessionViewModel.getCourses().containsKey("EGG100") && view.session.getCourses().containsKey("EGG100"));
    }
}