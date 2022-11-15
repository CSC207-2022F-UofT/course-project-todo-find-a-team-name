package edit_timetable_use_case;

public class EditTimetableResponseModel {
    private String courseCode;
    private boolean success;

    private String message;

    public EditTimetableResponseModel(String courseCode, boolean success){
        this.courseCode = courseCode;
        this.success = success;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage(){
        return message;
    }

    public void setMessage(String message){
        this.message = message;
    }
}
