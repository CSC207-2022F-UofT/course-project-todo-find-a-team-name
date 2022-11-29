package edit_timetable_use_case.interface_adapters;

import edit_timetable_use_case.application_business.EditTimetableResponseModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrieve_timetable_use_case.TimetableModel;

import java.util.ArrayList;

/**
 * A suite of tests to confirm that AddCoursePresenter updates the view appropriately.
 */
class AddCoursePresenterTest {

    private AddCoursePresenter presenter;
    private TestEditTimetableView view;

    @BeforeEach
    void setUp(){
        presenter = new AddCoursePresenter();
        view = new TestEditTimetableView();
        presenter.setView(view);
    }

    /**
     * Tests that AddCoursePresenter updates the view with the correct message.
     * This does not confirm whether the updated timetable view model is correct, which is an area of further development.
     * For now, it suffices that TimetableModelConverter is shown to be correct in its own tests.
     */
    @Test
    void prepareViewOnSuccess() {
        EditTimetableResponseModel response = new EditTimetableResponseModel("CSC209",
                new TimetableModel(new ArrayList<>()));
        presenter.prepareView(response);
        Assertions.assertEquals("CSC209 was successfully added.", view.response);
    }
}