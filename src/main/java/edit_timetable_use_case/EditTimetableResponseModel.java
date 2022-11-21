package edit_timetable_use_case;

import retrieve_timetable_use_case.TimetableResponseModel;

import java.util.List;

/**
 * A response model that contains the relevant information needed by the presenter's view.
 *
 * If this response model was used in the RemoveCourse use case, courseCode is the course code of the
 * that the user attempted to remove. SectionCodes is never used in this case.
 *
 *
 *
 * Success is true iff the operation was successful.
 */
public class EditTimetableResponseModel {
    private String courseCode;
    private List<String> sectionCodes;

    private TimetableResponseModel updatedTimetable;

    private String message;

    /**
     * @param courseCode
     */
    public EditTimetableResponseModel(String courseCode, List<String> sectionCodes, TimetableResponseModel
                                      updatedTimetable){
        this.courseCode = courseCode;
        this.sectionCodes = sectionCodes;
        this.updatedTimetable = updatedTimetable;
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
    public String getMessage(){
        return message;
    }

    /**
     * @param message
     */
    public void setMessage(String message){
        this.message = message;
    }

    public TimetableResponseModel getUpdatedTimetable() {
        return updatedTimetable;
    }
}
