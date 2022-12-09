package timetables_sort_use_case.interface_adapters;

import display_timetable_use_case.application_business.DisplayTimetableInputBoundary;
import timetables_sort_use_case.application_business.AllTimetablesInputBoundary;

/**
 * The controller responsible for subscribing to ALlTimetablesScreen publisher and updating TimetableUI
 */
public class AllTimetablesController {
    DisplayTimetableInputBoundary interactor;
    AllTimetablesInputBoundary publisher;

    public AllTimetablesController(DisplayTimetableInputBoundary interactor, AllTimetablesInputBoundary publisher) {
        this.interactor = interactor;
        this.publisher = publisher;
    }

    public void setTTUI(int i) {
        publisher.updateTimetable(i);
    }

}
