package edit_timetable_use_case.interface_adapters;

import display_timetable_use_case.interface_adapters.TimetableViewModel;
import edit_timetable_use_case.application_business.EditTimetableResponseModel;
import edit_timetable_use_case.application_business.RemoveCourseOutputBoundary;
import retrieve_timetable_use_case.application_business.TimetableModel;
import retrieve_timetable_use_case.interface_adapters.TimetableModelConverter;

/**
 * The presenter used in the RemoveCourse use case.
 * This presenter must have a EditTimetableView set to it before being used (see SetView).
 * View is the view that the presenter updates.
 */
public class RemoveCoursePresenter implements RemoveCourseOutputBoundary {

    private EditTimetableView view;

    public RemoveCoursePresenter(){}
    /**
     * @param responseModel The response model passed by the interactor.
     *                      This method updates the view with a timetable view model representing the new
     *                      state of the Timetable object altered, and displays an appropriate success message.
     */
    @Override
    public void prepareView(EditTimetableResponseModel responseModel) {
        TimetableModel updatedTimetable = responseModel.getUpdatedTimetable();
        TimetableViewModel ttViewModel = TimetableModelConverter.timetableToView(updatedTimetable);
        view.updateTimetable(ttViewModel);
        view.displayResponse(responseModel.getCourseCode() + " was successfully removed.");
    }

    public void setView(EditTimetableView view){
        this.view = view;
    }
}
