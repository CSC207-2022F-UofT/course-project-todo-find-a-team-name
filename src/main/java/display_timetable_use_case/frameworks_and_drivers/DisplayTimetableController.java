package display_timetable_use_case.frameworks_and_drivers;

import display_timetable_use_case.application_business.DisplayTimetableInteractor;

public class DisplayTimetableController {
    DisplayTimetableInteractor interactor;

    public DisplayTimetableController(DisplayTimetableInteractor interactor) {
        this.interactor = interactor;
    }

    public void displayTimetable(){
        interactor.displayTimetable();
    }
}
