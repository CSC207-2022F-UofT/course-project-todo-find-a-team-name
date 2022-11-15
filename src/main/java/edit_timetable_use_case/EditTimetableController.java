package edit_timetable_use_case;

public class EditTimetableController {
    EditTimetableInputBoundary userInput;

    public EditTimetableController(EditTimetableInputBoundary userInput){
        this.userInput = userInput;
    }

    EditTimetableResponseModel remove(String courseCode){
        EditTimetableRequestModel requestModel = new EditTimetableRequestModel(courseCode);
        return userInput.remove(requestModel);
    }
}