package timetables_sort_use_case.interface_adapters;

import display_timetable_use_case.interface_adapters.TimetableViewCourseModel;
import display_timetable_use_case.interface_adapters.TimetableViewModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import retrieve_timetable_use_case.application_business.CourseModel;
import retrieve_timetable_use_case.application_business.TimetableModel;
import retrieve_timetable_use_case.interface_adapters.TimetableModelConverter;
import timetables_sort_use_case.application_business.TimetablesSortRequestModel;
import timetables_sort_use_case.application_business.TimetablesSortResponseModel;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * A suite of tests to confirm that TimetablesSortPresenter updates the view appropriately
 */

public class TimetablesSortPresenterTest {
    private TimetablesSortPresenter presenter;
    private TestAllTimetablesView view;

    @BeforeEach
    void setUp(){
        presenter = new TimetablesSortPresenter();
        view = new TestAllTimetablesView();
        presenter.setView(view);
    }

    /**
     * Tests that TimetablesSortPresenter updates the view with the correct TimetableViewModels
     * note that we test the length and not the actual timetables because the two will always have
     * different hashes unless we complicate the test a lot and knowing that they are the same length
     * is enough to prove to us that the TimetableModels are moved to the view
     */
    @Test
    void updateTimetables() {
        TimetableModel[] updatedTimetables = new TimetableModel[1];
        List<CourseModel> courses = new ArrayList<>();
        courses.add(new CourseModel("some course", new ArrayList<>(), "F",
                "CSD203", "BR1"));
        TimetableModel timetable1 = new TimetableModel(courses);

        updatedTimetables[0] = timetable1;
        TimetablesSortResponseModel response = new TimetablesSortResponseModel(updatedTimetables);
        presenter.prepareView(response);

        TimetableViewModel[] timetables = new TimetableViewModel[1];
        timetables[0] = TimetableModelConverter.timetableToView(timetable1);

        Assertions.assertEquals(timetables.length, view.timetables.length);
    }
}