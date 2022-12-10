package timetables_sort_use_case.interface_adapters;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import timetables_sort_use_case.application_business.TestAllTimetablesInputBoundary;

/**
 * A suite of tests to confirm that the AllTimetablesController passes to the interactor correctly
 */
public class AllTimetablesControllerTest {
    private TestAllTimetablesInputBoundary interactor;
    private AllTimetablesController controller;
    @BeforeEach
    void setUp() {
        this.interactor = new TestAllTimetablesInputBoundary();
        this.controller = new AllTimetablesController(interactor);
    }

    /**
     * tests that the controller correctly sends the interactor the correct integer
     */
    @Test
    public void updateSubscribers() {
        controller.updateSubscribers(2);
        Assertions.assertEquals(2, interactor.getJ());
    }

}
