package edit_timetable_use_case;

import java.util.List;

/**
 *
 */
public class EditTimetableResponseModel {
    private String courseCode;
    private List<String> sectionCodes;
    private boolean success;

    private String message;

    /**
     * @param courseCode
     * @param success
     */
    public EditTimetableResponseModel(String courseCode, List<String> sectionCodes, boolean success){
        this.courseCode = courseCode;
        this.sectionCodes = sectionCodes;
        this.success = success;
    }

    /**
     * @return
     */
    public String getCourseCode() {
        return courseCode;
    }

    public List<String> getSectionCodes(){
        return sectionCodes;
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
