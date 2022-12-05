package timetables_sort_use_case.interface_adapters;

import retrieve_timetable_use_case.application_business.TimetableModel;
import retrieve_timetable_use_case.interface_adapters.TimetableModelConverter;
import screens.TimetableViewModel;
import timetables_sort_use_case.application_business.TimetablesSortOutputBoundary;
import timetables_sort_use_case.application_business.TimetablesSortResponseModel;

/**
 * A concrete implementation of the TimetablesSortOutputBoundary.
 * view is the view that the presenter updates and controls.
 * note that the view is an implementation of AllTimetablesView and not TimetablesSort
 */
public class TimetablesSortPresenter implements TimetablesSortOutputBoundary {

    private AllTimetablesView view;

    public TimetablesSortPresenter(){

    }

    /**
     *
     * @param responseModel: a response model that contains the timetables that the User wants to sort
     */
    @Override
    public void prepareView(TimetablesSortResponseModel responseModel) {
        TimetableModel[] updatedTimetables = responseModel.getTimetables();
        TimetableViewModel[] timetablesViewModel = new TimetableViewModel[updatedTimetables.length];
        for (int i = 0; i < responseModel.getTimetables().length; i++) {
            TimetableViewModel timetableViewModel = TimetableModelConverter.timetableToView(updatedTimetables[i]);
            timetablesViewModel[i] = timetableViewModel;
        }
        view.updateTimetables(timetablesViewModel);
    }

    /**
     *
     * @param view the AllTimetablesView that the presenter updates
     */
    @Override
    public void setView(AllTimetablesView view) {
        this.view = view;
    }
}
