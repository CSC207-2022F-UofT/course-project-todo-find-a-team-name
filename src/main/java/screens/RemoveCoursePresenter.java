package screens;

import edit_timetable_use_case.RemoveCourseOutputBoundary;
import edit_timetable_use_case.EditTimetableResponseModel;
import edit_timetable_use_case.RemoveCourseFailedException;

/**
 *
 */
public class RemoveCoursePresenter implements RemoveCourseOutputBoundary {


    /**
     * @param responseModel
     * @return
     */
    @Override
    public EditTimetableResponseModel prepareView(EditTimetableResponseModel responseModel) throws RemoveCourseFailedException {
        if (responseModel.isSuccess()){
            responseModel.setMessage(responseModel.getCourseCode() + " has been successfully removed.");
            return responseModel;
        }
        else{
            throw new RemoveCourseFailedException(responseModel.getCourseCode());
        }
    }
}
