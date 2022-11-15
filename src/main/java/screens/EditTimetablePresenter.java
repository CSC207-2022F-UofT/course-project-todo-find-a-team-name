package screens;

import edit_timetable_use_case.EditTimetableOutputBoundary;
import edit_timetable_use_case.EditTimetableResponseModel;
import edit_timetable_use_case.RemoveCourseFailedException;

public class EditTimetablePresenter implements EditTimetableOutputBoundary {


    /**
     * @param responseModel
     * @return
     */
    @Override
    public EditTimetableResponseModel prepareView(EditTimetableResponseModel responseModel) {
        if (responseModel.isSuccess()){
            responseModel.setMessage(responseModel.getCourseCode() + " has been successfully removed.");
            return responseModel;
        }
        else{
            throw new RemoveCourseFailedException(responseModel.getCourseCode());
        }
    }
}
