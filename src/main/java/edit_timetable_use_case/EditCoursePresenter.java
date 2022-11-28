package edit_timetable_use_case;

import retrieve_timetable_use_case.TimetableModel;
import retrieve_timetable_use_case.TimetableModelConverter;
import screens.EditTimetableView;
import screens.TimetableViewModel;

/**
 * A concrete implementation of the EditCourseOutputBoundary.
 * view is the view that the presenter updates and controls.
 */
public class EditCoursePresenter implements EditCourseOutputBoundary{

    private EditTimetableView view;

    public EditCoursePresenter(){}

    public EditCoursePresenter(EditTimetableView view){
        this.view = view;
    }

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

    /**
     * @param view the EditTimetableView that the presenter updates.
     */
    @Override
    public void setView(EditTimetableView view){
        this.view = view;
    }
}
