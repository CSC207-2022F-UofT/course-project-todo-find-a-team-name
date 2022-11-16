package edit_timetable_use_case;

/**
 *
 */
public class EditTimetableResponseModel {
    private String courseCode;
    private boolean success;

    private String message;

    /**
     * @param courseCode
     * @param success
     */
    public EditTimetableResponseModel(String courseCode, boolean success){
        this.courseCode = courseCode;
        this.success = success;
    }

    /**
     * @return
     */
    public String getCourseCode() {
        return courseCode;
    }

    /**
     * @return
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * @return
     */
    public String getMessage(){
        return message;
    }

    /**
     * @param message
     */
    public void setMessage(String message){
        this.message = message;
    }
}
