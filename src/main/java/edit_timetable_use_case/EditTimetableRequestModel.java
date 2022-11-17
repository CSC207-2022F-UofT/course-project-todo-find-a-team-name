package edit_timetable_use_case;

import java.util.List;

/**
 *
 */
public class EditTimetableRequestModel {
    private String courseCode;
    private List<String> sectionCodes;

    /**
     * @param courseCode
     */
    EditTimetableRequestModel(String courseCode,List<String> sectionCodes){
        this.courseCode = courseCode;
        this.sectionCodes = sectionCodes;
    }

    /**
     * @return
     */
    String getCourseCode(){
        return this.courseCode;
    }

    List<String> getSectionCodes(){
        return this.sectionCodes;
    }

}
