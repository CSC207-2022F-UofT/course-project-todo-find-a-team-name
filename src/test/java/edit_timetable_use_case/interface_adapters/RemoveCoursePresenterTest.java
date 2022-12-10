package edit_timetable_use_case.interface_adapters;

import edit_timetable_use_case.application_business.EditTimetableResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrieve_timetable_use_case.application_business.TimetableModel;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RemoveCoursePresenterTest {
    private RemoveCoursePresenter presenter;
    private TestEditTimetableView testView;

    @BeforeEach
    void setUp() {
        presenter = new RemoveCoursePresenter();
        testView = new TestEditTimetableView();
        presenter.setView(testView);
    }

    @Test
    void prepareViewOnSuccess() {
        EditTimetableResponseModel response = new EditTimetableResponseModel("CSC207",
                new TimetableModel(new ArrayList<>()));
        presenter.prepareView(response);
        assertEquals("CSC207 was successfully removed.", testView.response);
    }
}