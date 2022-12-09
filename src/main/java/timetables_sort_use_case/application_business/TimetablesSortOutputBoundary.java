package timetables_sort_use_case.application_business;

import timetables_sort_use_case.interface_adapters.AllTimetablesView;

/**
 * The output boundary of the Timetables Sort use case
 */
public interface TimetablesSortOutputBoundary {
    /**
     * converts into a TimeTableViewModel array then calls the view's updateTimetables method to present it
     * @param responseModel: a response model that contains the updated TimeTableModels
     */
    void prepareView(TimetablesSortResponseModel responseModel);

    void setView(AllTimetablesView view);
}
