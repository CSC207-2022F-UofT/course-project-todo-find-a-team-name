package timetables_sort_use_case.interface_adapters;

import display_timetable_use_case.application_business.DisplayTimetableInputBoundary;
import timetables_sort_use_case.application_business.AllTimetablesInputBoundary;

/**
 * The controller responsible for subscribing to AllTimetablesScreen publisher and updating TimetableUI
 */
public class AllTimetablesController {
    DisplayTimetableInputBoundary displayTimetableInputBoundary;
    AllTimetablesInputBoundary allTimetablesInputBoundary;

    public AllTimetablesController(DisplayTimetableInputBoundary displayTimetableInputBoundary, AllTimetablesInputBoundary allTimetablesInputBoundary) {
        this.displayTimetableInputBoundary = displayTimetableInputBoundary;
        this.allTimetablesInputBoundary = allTimetablesInputBoundary;
    }

    public void updateSubscribers(int i) {
        allTimetablesInputBoundary.updateSubscribers(i);
    }

}
