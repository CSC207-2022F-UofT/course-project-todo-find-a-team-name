package timetables_sort_use_case.interface_adapters;

import timetables_sort_use_case.application_business.TimetablesSortInputBoundary;
import timetables_sort_use_case.application_business.TimetablesSortRequestModel;

/**
 * The controller used to process requests to sort timetables by User preference.
 */
public class TimetablesSortController {
    final TimetablesSortInputBoundary timetablesSortInteractor;

    public TimetablesSortController(TimetablesSortInputBoundary timetablesSortInteractor){
        this.timetablesSortInteractor = timetablesSortInteractor;
    }

    /**
     * Sorts timetables and displays it
     * @param timeButton the text in the chosen timeButton
     * @param breakButton the text in the chosen breakButton
     */
    public void timetablesSort(String timeButton, String breakButton) {
        TimetablesSortRequestModel timetablesSortRequestModel = new TimetablesSortRequestModel(timeButton, breakButton);
        timetablesSortInteractor.timetablesSort(timetablesSortRequestModel);
    }

}
