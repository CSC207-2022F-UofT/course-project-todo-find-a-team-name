package edit_timetable_use_case.interface_adapters;

import edit_timetable_use_case.application_business.EditTimetableResponseModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrieve_timetable_use_case.application_business.TimetableModel;

import java.util.ArrayList;

/**
 * A suite of tests for EditCoursePresenter.
 * presenter is an instance of EditCoursePresenter.
 * view is a mock-up view (see TestEditTimetableView).
 */
class EditCoursePresenterTest {

    private EditCoursePresenter presenter;
    private TestEditTimetableView view;

    @BeforeEach
    void setUp(){
        presenter = new EditCoursePresenter();
        view = new TestEditTimetableView();
        presenter.setView(view);
    }

    /**
     * Tests that EditCoursePresenter updates the view with the correct message.
     * This does not confirm whether the updated timetable view model is correct, which is an area of further development.
     * For now, it suffices that TimetableModelConverter is shown to be correct in its own tests.
     */
    @Test
    void prepareViewOnSuccess() {
        EditTimetableResponseModel response = new EditTimetableResponseModel("CSC209",
                new TimetableModel(new ArrayList<>()));
        presenter.prepareView(response);
        Assertions.assertEquals("CSC209 was successfully edited.", view.response);
    }
}