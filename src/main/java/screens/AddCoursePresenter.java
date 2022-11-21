package screens;

import edit_timetable_use_case.AddCourseOutputBoundary;
import edit_timetable_use_case.EditTimetableResponseModel;
import edit_timetable_use_case.RemoveCourseFailedException;
import retrieve_timetable_use_case.TimetableResponseConverter;
import retrieve_timetable_use_case.TimetableResponseModel;

public class AddCoursePresenter implements AddCourseOutputBoundary {
    private EditTimetableView view;

    /**
     * @param
     * @return
     */

    public AddCoursePresenter(){}
    public AddCoursePresenter(EditTimetableView view){
        this.view = view;
    }
    @Override
    public void prepareView(EditTimetableResponseModel responseModel) throws RemoveCourseFailedException {
        TimetableResponseModel updatedTimetable = responseModel.getUpdatedTimetable();
        TimetableViewModel ttViewModel = TimetableResponseConverter.timetableToView(updatedTimetable);
        view.updateTimetable(ttViewModel);
        if (responseModel.isSuccess()){
            String successMessage = responseModel.getCourseCode() + " has been successfully removed.";
            view.displayResponse(successMessage);
        }
        else{
            throw new RemoveCourseFailedException(responseModel.getCourseCode());
        }
    }

    public void setView(EditTimetableView view){
        this.view = view;
    }
}
