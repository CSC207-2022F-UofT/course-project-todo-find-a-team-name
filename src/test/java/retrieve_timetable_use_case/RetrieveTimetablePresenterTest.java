package retrieve_timetable_use_case;

import entities.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrieve_timetable_use_case.application_business.*;
import retrieve_timetable_use_case.interface_adapters.TimetableModelConverter;
import screens.SessionViewModel;

import java.util.ArrayList;

/**
 * A suite of test for RetrieveTimetablePresenter.
 */
public class RetrieveTimetablePresenterTest {
    private RetrieveTimetablePresenter presenter;
    private TestRetrieveTimetableView view;
    private SessionViewModel sessionViewModel;
    private SessionModel sessionModel;
    @BeforeEach
    void setUp() {
        presenter = new RetrieveTimetablePresenter();
        view = new TestRetrieveTimetableView();
        presenter.setView(view);

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

        CourseModel courseModel = new CourseModel("some course", modelSections, "F", "EGG100", "" +
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

            TimetableModel timetableModel = new TimetableModel(timetableModelCourses);
            sessionModel = EntityConverter.generateSessionResponse(sessionActual);
            sessionViewModel = TimetableModelConverter.sessionToView(EntityConverter.generateSessionResponse(sessionActual));
        }
        catch(InvalidSectionsException ignored){
        }
    }

    /**
     * Tests that updateSession correctly updates the view's session.
     */
    @Test
    void updateSession(){
        presenter.updateSession(sessionModel);
        Assertions.assertEquals(sessionViewModel, view.session);
    }
}
