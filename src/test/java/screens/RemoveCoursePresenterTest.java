package screens;

import edit_timetable_use_case.EditTimetableResponseModel;
import edit_timetable_use_case.TestEditTimetableView;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrieve_timetable_use_case.application_business.TimetableModel;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RemoveCoursePresenterTest {
    private RemoveCoursePresenter presenter;
    private TestEditTimetableView testView;
    private

    @BeforeEach
    void setUp() {
        presenter = new RemoveCoursePresenter();
        testView = new TestEditTimetableView();
        presenter.setView(testView);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void prepareViewOnSuccess() {
        EditTimetableResponseModel response = new EditTimetableResponseModel("CSC207", new ArrayList<>(),
                new TimetableModel(new ArrayList<>()));
        presenter.prepareView(response);
        assertEquals("CSC207 was successfully removed.", testView.response);
    }
}