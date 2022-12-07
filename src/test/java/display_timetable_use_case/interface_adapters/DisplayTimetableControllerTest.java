package display_timetable_use_case.interface_adapters;

import display_timetable_use_case.application_business.DisplayTimetableInputBoundary;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class used for testing DisplayTimetableController
 */
class DisplayTimetableControllerTest {

    /**
     * Test whether displayTimetable calls appropriate method in DisplayTimetableInputBoundary
     */
    @Test
    void testDisplayTimetable() {
        final boolean[] methodReached = {false};
        DisplayTimetableInputBoundary dummyInteractor = () -> methodReached[0] = true;
        DisplayTimetableController controller = new DisplayTimetableController(dummyInteractor);
        controller.displayTimetable();
        assertTrue(methodReached[0]);
    }
}