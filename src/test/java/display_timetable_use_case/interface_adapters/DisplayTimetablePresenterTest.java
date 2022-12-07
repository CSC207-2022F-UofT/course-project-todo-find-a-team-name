package display_timetable_use_case.interface_adapters;

import display_timetable_use_case.frameworks_and_drivers.TimetableViewBlockModel;
import display_timetable_use_case.frameworks_and_drivers.TimetableViewCourseModel;
import display_timetable_use_case.frameworks_and_drivers.TimetableViewModel;
import display_timetable_use_case.frameworks_and_drivers.TimetableViewSectionModel;
import org.junit.jupiter.api.Test;
import retrieve_timetable_use_case.application_business.BlockModel;
import retrieve_timetable_use_case.application_business.CourseModel;
import retrieve_timetable_use_case.application_business.SectionModel;
import retrieve_timetable_use_case.application_business.TimetableModel;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DisplayTimetablePresenterTest {

    @Test
    void testPrepareTimetable() {

        SectionModel sectionModel1 = new SectionModel("LEC-0101", "inst1",
                List.of(new BlockModel(0, 12, 13, "room1")));
        SectionModel sectionModel2 = new SectionModel("TUT-0101", "inst1",
                List.of(new BlockModel(1, 15, 16, "room1")));
        SectionModel sectionModel3 = new SectionModel("LEC-0101", "inst1",
                List.of(new BlockModel(0, 13, 14, "room1")));

        CourseModel courseModel1 = new CourseModel("course1", List.of(sectionModel1, sectionModel2),
                "S", "CSC111", "1");

        CourseModel courseModel2 = new CourseModel("course2", List.of(sectionModel3),
                "S", "CSC112", "3");


        TimetableModel timetableModel = new TimetableModel(List.of(courseModel1, courseModel2));

        TimetableViewSectionModel timetableViewSectionModel1 = new TimetableViewSectionModel("LEC-0101",
                List.of(new TimetableViewBlockModel(0, 12, 13)));
        TimetableViewSectionModel timetableViewSectionModel2 = new TimetableViewSectionModel("TUT-0101",
                List.of(new TimetableViewBlockModel(1, 15, 16)));
        TimetableViewSectionModel timetableViewSectionModel3 = new TimetableViewSectionModel("LEC-0101",
                List.of(new TimetableViewBlockModel(0, 13, 14)));

        TimetableViewCourseModel courseViewModel1 = new TimetableViewCourseModel("CSC111",
                List.of(timetableViewSectionModel1, timetableViewSectionModel2));

        TimetableViewCourseModel courseViewModel2 = new TimetableViewCourseModel("CSC111",
                List.of(timetableViewSectionModel3));

        TimetableViewModel expectedViewModel = new TimetableViewModel(List.of(courseViewModel1, courseViewModel2));

        DisplayTimetablePresenter presenter = new DisplayTimetablePresenter();
        ITimetableUI dummyView = new ITimetableUI() {
            @Override
            public void updateTimetable(TimetableViewModel viewModel) {
                assertEquals(expectedViewModel, viewModel);
            }

            @Override
            public void showTimetableFailView(String message) {
                fail("showTimetableFailView(" + message + ") should not be called");
            }
        };
        presenter.setView(dummyView);
        presenter.prepareTimetable(timetableModel);
    }

    @Test
    void testPrepareFailView() {
        DisplayTimetablePresenter presenter = new DisplayTimetablePresenter();
        ITimetableUI dummyView = new ITimetableUI() {
            @Override
            public void updateTimetable(TimetableViewModel viewModel) {
                fail("updateTimetable should not be called");
            }

            @Override
            public void showTimetableFailView(String message) {
                assertEquals("test", message);
            }
        };
        presenter.setView(dummyView);
        presenter.prepareFailView("test");
    }
}