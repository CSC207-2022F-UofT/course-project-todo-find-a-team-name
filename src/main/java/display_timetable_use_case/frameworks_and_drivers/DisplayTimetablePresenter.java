package display_timetable_use_case.frameworks_and_drivers;

import display_timetable_use_case.interface_adapters.TimetableViewModel;
import display_timetable_use_case.application_business.DisplayTimetableOutputBoundary;
import retrieve_timetable_use_case.application_business.TimetableModel;
import retrieve_timetable_use_case.interface_adapters.TimetableModelConverter;

/**
 * Presenter for displayTimetable use case that communicates with view to
 * display the timetable (or error message) to the user
 */
public class DisplayTimetablePresenter implements DisplayTimetableOutputBoundary {
    private ITimetableUI view;

    /**
     * Constructs the DisplayTimetablePresenter with view set to null
     */
    public DisplayTimetablePresenter() {
        this.view = null;
    }

    /**
     * Convert the timetableModel to view model that is suitable for displaying the timetable, and
     * pass that into the view to update the timetable.
     *
     * @param timetableModel model representing all information for the timetable
     */
    @Override
    public void prepareTimetable(TimetableModel timetableModel) {
        TimetableViewModel viewModel = TimetableModelConverter.timetableToView(timetableModel);
        view.updateTimetable(viewModel);
    }

    /**
     * Tells the view to show given message to the user
     * @param message message shown to the user
     */
    @Override
    public void prepareFailView(String message) {
        view.showTimetableFailView(message);
    }

    /**
     * Set view to the given view
     *
     * @param view new view
     */
    public void setView(ITimetableUI view){
        this.view = view;
    }
}
