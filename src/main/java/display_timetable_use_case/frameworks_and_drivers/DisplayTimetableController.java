package display_timetable_use_case.frameworks_and_drivers;

import display_timetable_use_case.application_business.DisplayTimetableInputBoundary;
import display_timetable_use_case.application_business.DisplayTimetableInteractor;

/**
 * Controller for display timetable use case that communicate with the interactor
 * to perform the use case.
 */
public class DisplayTimetableController {
    DisplayTimetableInputBoundary interactor;

    /**
     * Constructs the DisplayTimetableController from the given interactor
     *
     * @param interactor interactor for display timetable use case
     */
    public DisplayTimetableController(DisplayTimetableInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Ask interactor to display the timetable
     */
    public void displayTimetable(){
        interactor.displayTimetable();
    }
}
