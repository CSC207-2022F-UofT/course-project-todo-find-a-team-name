package timetables_sort_use_case.interface_adapters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import timetables_sort_use_case.application_business.TestTimetablesSortInputBoundary;

/**
 * A suite of tests to confirm that TimetablesSortController passes to the interactor correctly
 */
public class TimetablesSortControllerTest {
    private TimetablesSortController controller;
    private TestTimetablesSortInputBoundary interactor;
    private String timeButton;
    private String breakButton;

    @BeforeEach
    void setUp() {
        interactor = new TestTimetablesSortInputBoundary();
        controller = new TimetablesSortController(interactor);
        timeButton = "1";
        breakButton = "2";
    }

    /**
     * Tests that the controller correctly converts time and break buttons into request model and is sent to interactor
     */
    @Test
    public void timetablesSort() {
        controller.timetablesSort(timeButton, breakButton);
        Assertions.assertEquals("1", interactor.getTimeButton());
        Assertions.assertEquals("2", interactor.getBreakButton());
    }
}
