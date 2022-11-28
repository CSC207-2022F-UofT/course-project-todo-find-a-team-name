package screens;

import edit_timetable_use_case.AddCourseOutputBoundary;
import edit_timetable_use_case.EditTimetableResponseModel;
import retrieve_timetable_use_case.TimetableModel;
import retrieve_timetable_use_case.TimetableModelConverter;

/**
 * The presenter used in the add course use case.
 * view is the EditTimetableView that the presenter updates.
 */
public class AddCoursePresenter implements AddCourseOutputBoundary {
    private EditTimetableView view;
    public AddCoursePresenter(){}
    public AddCoursePresenter(EditTimetableView view){
        this.view = view;
    }
    @Override
    public void prepareView(EditTimetableResponseModel responseModel)  {
        TimetableModel updatedTimetable = responseModel.getUpdatedTimetable();
        TimetableViewModel ttViewModel = TimetableModelConverter.timetableToView(updatedTimetable);
        view.updateTimetable(ttViewModel);
        view.displayResponse(responseModel.getCourseCode() + " was successfully added.");
    }

    /**
     * @param view the EditTimetableView that the presenter updates.
     */
    @Override
    public void setView(EditTimetableView view){
        this.view = view;
    }
}
