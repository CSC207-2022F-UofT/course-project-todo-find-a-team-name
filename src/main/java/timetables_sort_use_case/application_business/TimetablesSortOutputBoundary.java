package timetables_sort_use_case.application_business;

import timetables_sort_use_case.interface_adapters.AllTimetablesView;

/**
 * The output boundary of the Timetables Sort use case
 */
public interface TimetablesSortOutputBoundary {
    /**
     * @param responseModel: a response model that contains the timetables that the User wants to sort
     */
    void prepareView(TimetablesSortResponseModel responseModel);

    /**
     *
     * @param view the presenter's new view.
     *             Changes the view modified by prepareView
     */
    void setView(AllTimetablesView view);
}
