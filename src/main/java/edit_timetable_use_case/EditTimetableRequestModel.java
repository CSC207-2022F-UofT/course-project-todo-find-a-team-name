package edit_timetable_use_case;

/**
 *
 */
public class EditTimetableRequestModel {
    private String courseCode;

    /**
     * @param courseCode
     */
    EditTimetableRequestModel(String courseCode){
        this.courseCode = courseCode;
    }

    /**
     * @return
     */
    String getCourseCode(){
        return this.courseCode;
    }

}
