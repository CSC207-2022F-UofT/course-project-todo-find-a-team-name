package edit_timetable_use_case;

import retrieve_timetable_use_case.TimetableModel;

import java.util.List;

/**
 * A response model that contains the relevant information needed by the presenter's view.
 * If this response model was used in the RemoveCourse use case, courseCode is the course code of the
 * that the user attempted to remove. SectionCodes is never used in this case.
 * UpdatedTimetable is a TimetableModel used to update the view after the operation is done.
 *
 */
public class EditTimetableResponseModel {
    private final String courseCode;
    private final List<String> sectionCodes;

    private final TimetableModel updatedTimetable;

    public EditTimetableResponseModel(String courseCode, List<String> sectionCodes, TimetableModel
                                      updatedTimetable){
        this.courseCode = courseCode;
        this.sectionCodes = sectionCodes;
        this.updatedTimetable = updatedTimetable;
    }

    public String getCourseCode() {
        return courseCode;
    }

    /*This method is unused, creating an IntelliJ warning. It will be used in the AddCourse use case.*/
    public List<String> getSectionCodes(){
        return sectionCodes;
    }


    public TimetableModel getUpdatedTimetable() {
        return updatedTimetable;
    }
}
