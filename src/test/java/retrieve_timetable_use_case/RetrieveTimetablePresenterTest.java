package retrieve_timetable_use_case;

import entities.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrieve_timetable_use_case.application_business.*;
import retrieve_timetable_use_case.interface_adapters.TimetableModelConverter;
import display_timetable_use_case.frameworks_and_drivers.SessionViewModel;

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

        Block blockActual = new Block("WE", "14:00", "16:00", "AB106");

        ArrayList<Block> actualBlocks = new ArrayList<>();
        actualBlocks.add(blockActual);

        Section sectionActual = new Section("LEC0101", "prof!!", actualBlocks);

        ArrayList<Section> actualSections = new ArrayList<>();
        actualSections.add(sectionActual);

        CalendarCourse courseActual = new CalendarCourse("some course", actualSections, "F", "EGG100", "" +
                "BR1");
            Session sessionActual = new Session("F");
            sessionActual.addCourse(courseActual);

            sessionModel = EntityConverter.generateSessionResponse(sessionActual);
            sessionViewModel = TimetableModelConverter.sessionToView(EntityConverter.generateSessionResponse(sessionActual));
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
