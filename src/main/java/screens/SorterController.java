package screens;

import timetablesSorter_use_case.SorterInputBoundary;
import timetablesSorter_use_case.SorterRequestModel;

/**
 * The controller used to process requests to sort timetables by User preference. This controller will communicate with
 * 1 interactor to sort timetables.
 */
public class SorterController {
    SorterInputBoundary interactor;

    /**
     * Constructs SorterController given SorterInputBoundary (interactor)
     * @param interactor responsible for sorting timetables
     */
    public SorterController(SorterInputBoundary interactor){
        this.interactor = interactor;
    }

    /**
     * Sorts timetables and displays it
     *
     * @param timeButton the text in the chosen timeButton
     * @param breakButton the text in the chosen breakButton
     */
    public void preferenceSort(String timeButton, String breakButton) {
        SorterRequestModel sorterRequestModel = new SorterRequestModel(timeButton, breakButton);
        interactor.preferenceSort(sorterRequestModel);
    }
    public void errorMessage() {

    }
}
