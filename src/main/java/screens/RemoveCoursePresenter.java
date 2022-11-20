package screens;

import edit_timetable_use_case.RemoveCourseOutputBoundary;
import edit_timetable_use_case.EditTimetableResponseModel;
import edit_timetable_use_case.RemoveCourseFailedException;
import retrieve_timetable_use_case.TimetableResponseModel;

/**
 *
 */
public class RemoveCoursePresenter implements RemoveCourseOutputBoundary {

    private EditTimetableView view;

    /**
     * @param responseModel
     * @return
     */

    public RemoveCoursePresenter(EditTimetableView view){
        this.view = view;
    }
    @Override
    public void prepareView(EditTimetableResponseModel responseModel) throws RemoveCourseFailedException {
        TimetableResponseModel updatedTimetable = responseModel.getUpdatedTimetable();
        TimetableViewModel ttViewModel = TimetableConverter.TimetableResponseToView(updatedTimetable);
        view.updateTimetable(ttViewModel);
        if (responseModel.isSuccess()){
            String successMessage = responseModel.getCourseCode() + " has been successfully removed.";
            view.displayResponse(successMessage);
        }
        else{
            throw new RemoveCourseFailedException(responseModel.getCourseCode());
        }
    }
}
