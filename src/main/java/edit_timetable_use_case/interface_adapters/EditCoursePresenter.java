package edit_timetable_use_case.interface_adapters;

import display_timetable_use_case.frameworks_and_drivers.TimetableViewModel;
import edit_timetable_use_case.application_business.EditCourseOutputBoundary;
import edit_timetable_use_case.application_business.EditTimetableResponseModel;
import retrieve_timetable_use_case.application_business.TimetableModel;
import retrieve_timetable_use_case.interface_adapters.TimetableModelConverter;

/**
 * A concrete implementation of the EditCourseOutputBoundary.
 * view is the view that the presenter updates and controls.
 */
public class EditCoursePresenter implements EditCourseOutputBoundary {

    private EditTimetableView view;

    public EditCoursePresenter(){}

    /**
     * @param responseModel a response model with the codes of the courses and sections that the user tried to edit.
     *                      Updates the view with an appropriate success (or failure) message.
     *                      This method updates the timetable displayed on the view, and displays a message
     *                      (e.g. "CSC207 was successfully updated").
     */
    @Override
    public void prepareView(EditTimetableResponseModel responseModel) {
        TimetableModel updatedTimetable = responseModel.getUpdatedTimetable();
        TimetableViewModel ttViewModel = TimetableModelConverter.timetableToView(updatedTimetable);
        view.updateTimetable(ttViewModel);
        view.displayResponse(responseModel.getCourseCode() + " was successfully edited.");

    }

    public void setView(EditTimetableView view){
        this.view = view;
    }
}
