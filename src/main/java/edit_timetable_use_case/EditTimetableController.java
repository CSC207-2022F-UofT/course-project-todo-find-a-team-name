package edit_timetable_use_case;

/**
 *
 */
public class EditTimetableController {
    EditTimetableInputBoundary userInput;

    /**
     * @param userInput
     */
    public EditTimetableController(EditTimetableInputBoundary userInput){
        this.userInput = userInput;
    }

    /**
     * @param courseCode
     * @return
     */
    public EditTimetableResponseModel remove(String courseCode) throws RemoveCourseFailedException {
        EditTimetableRequestModel requestModel = new EditTimetableRequestModel(courseCode);
        return userInput.remove(requestModel);
    }
}