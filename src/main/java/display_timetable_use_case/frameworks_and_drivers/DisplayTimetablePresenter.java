package display_timetable_use_case.frameworks_and_drivers;

import display_timetable_use_case.interface_adapters.TimetableViewModel;
import display_timetable_use_case.application_business.DisplayTimetableOutputBoundary;
import retrieve_timetable_use_case.application_business.TimetableModel;
import retrieve_timetable_use_case.interface_adapters.TimetableModelConverter;

public class DisplayTimetablePresenter implements DisplayTimetableOutputBoundary {
    private ITimetableUI view;

    public DisplayTimetablePresenter(ITimetableUI view) {
        this.view = view;
    }

    @Override
    public void prepareTimetable(TimetableModel timetableModel) {
        TimetableViewModel viewModel = TimetableModelConverter.timetableToView(timetableModel);
        view.updateTimetable(viewModel);
    }

    @Override
    public void prepareFailView(String message) {
        view.showTimetableFailView(message);
    }

    public void setView(ITimetableUI view){
        this.view = view;
    }
}
