package timetables_sort_use_case.interface_adapters;

import timetables_sort_use_case.application_business.TimetablesSortInputBoundary;
import timetables_sort_use_case.application_business.TimetablesSortRequestModel;

/**
 * The controller used to process requests to sort timetables by User preference. This controller will communicate with
 * 1 interactor to sort timetables.
 */
public class TimetablesSortController {
    TimetablesSortInputBoundary interactor;

    public TimetablesSortController(TimetablesSortInputBoundary interactor){
        this.interactor = interactor;
    }

    /**
     * Sorts timetables and displays it
     *
     * @param timeButton the text in the chosen timeButton
     * @param breakButton the text in the chosen breakButton
     */
    public void sort(String timeButton, String breakButton) {
        TimetablesSortRequestModel timetablesSortRequestModel = new TimetablesSortRequestModel(timeButton, breakButton);
        interactor.sort(timetablesSortRequestModel);
    }
}
