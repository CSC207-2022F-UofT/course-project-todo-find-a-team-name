package edit_timetable_use_case.application_business;

import java.util.List;

/**
 * The request model used to request an edit to a timetable (remove a course, add a course and sections, edit a
 * course already in the timetable).
 * Instance Attributes:
 * - courseCode: the course code of the course to be removed, added or edited.
 * - sectionCodes (optional): the section codes of the sections desired in a course to be added or edited.
 */
public class EditTimetableRequestModel {
    private final String courseCode;
    private final List<String> sectionCodes;


    public EditTimetableRequestModel(String courseCode, List<String> sectionCodes){
        this.courseCode = courseCode;
        this.sectionCodes = sectionCodes;
    }

    /**
     * @return the course code stored in the request.
     */
    String getCourseCode(){
        return this.courseCode;
    }

    /**
     * @return the section codes stored in the request.
     */
    List<String> getSectionCodes(){
        return this.sectionCodes;
    }

}
