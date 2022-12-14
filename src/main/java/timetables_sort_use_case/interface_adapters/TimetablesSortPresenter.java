package timetables_sort_use_case.interface_adapters;

import display_timetable_use_case.frameworks_and_drivers.TimetableViewModel;
import retrieve_timetable_use_case.application_business.TimetableModel;
import retrieve_timetable_use_case.interface_adapters.TimetableModelConverter;
import timetables_sort_use_case.application_business.TimetablesSortOutputBoundary;
import timetables_sort_use_case.application_business.TimetablesSortResponseModel;

/**
 * A concrete implementation of the TimetablesSortOutputBoundary.
 * view is the view that the presenter updates and controls.
 * note that the view is an implementation of AllTimetablesView and not TimetablesSort
 */
public class TimetablesSortPresenter implements TimetablesSortOutputBoundary {

    private AllTimetablesView view;

    /**
     * converts into a TimeTableViewModel array then calls the view's updateTimetables method to present it
     * @param responseModel: a response model that contains the updated TimeTableModels
     */
    @Override
    public void prepareView(TimetablesSortResponseModel responseModel) {
        TimetableModel[] updatedTimetables = responseModel.getTimetables();
        TimetableViewModel[] timetablesViewModel = new TimetableViewModel[updatedTimetables.length];
        for (int i = 0; i < updatedTimetables.length; i++) {
            TimetableViewModel timetableViewModel = TimetableModelConverter.timetableToView(updatedTimetables[i]);
            timetablesViewModel[i] = timetableViewModel;
        }
        view.updateTimetables(timetablesViewModel);
    }

    @Override
    public void setView(AllTimetablesView view) {
        this.view = view;
    }
}
