package retrieve_timetable_use_case;

import entities.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * A suite of tests for the RetrieveTimetableInteractor.
 */
class RetrieveTimetableInteractorTest {
    private RetrieveTimetableInteractor interactor;
    private CourseModel courseModel;
    private SessionModel sessionModel;
    private TimetableModel timetableModel;

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
            Session sessionActual = new Session("F");
            sessionActual.addCourse(courseActual);

            Timetable timetable = new Timetable(timetableCourses, "F");

            timetableModel = new TimetableModel(timetableModelCourses);

            interactor = new RetrieveTimetableInteractor(timetable, sessionActual);
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
        assertEquals(courseModel, interactor.retrieveTimetableCourse(request));
    }

    /**
     * Asserts that the CourseModel object returned by the interactor is equivalent to the input CalendarCourse.
     */
    @Test
    void retrieveCalendarCourse() {
        RetrieveTimetableRequestModel request = new RetrieveTimetableRequestModel(
                "", "EGG100", "");
        assertEquals(courseModel, interactor.retrieveCalendarCourse(request));
    }

    /**
     * Asserts that the SessionModel object returned by the interactor is equivalent to the input Session.
     */
    @Test
    void retrieveSession() {
        assertEquals(sessionModel, interactor.retrieveSession());
    }

    /**
     * Asserts that the TimetableModel object returned by the interactor is equivalent to the input Timetable.
     */
    @Test
    void retrieveTimetable() {
        assertEquals(timetableModel, interactor.retrieveTimetable());
    }
}