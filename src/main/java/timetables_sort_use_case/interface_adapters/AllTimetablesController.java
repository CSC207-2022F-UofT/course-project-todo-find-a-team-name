package timetables_sort_use_case.interface_adapters;

import timetables_sort_use_case.application_business.AllTimetablesInputBoundary;

/**
 * The controller responsible for subscribing to AllTimetablesScreen publisher and updating TimetableUI
 */
public class AllTimetablesController {
    AllTimetablesInputBoundary allTimetablesInteractor;

    public AllTimetablesController(AllTimetablesInputBoundary allTimetablesInteractor) {
        this.allTimetablesInteractor = allTimetablesInteractor;
    }

    public void updateSubscribers(int i) {
        allTimetablesInteractor.updateSubscribers(i);
    }

}
