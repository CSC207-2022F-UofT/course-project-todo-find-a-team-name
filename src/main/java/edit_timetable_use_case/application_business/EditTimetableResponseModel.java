package edit_timetable_use_case.application_business;

import retrieve_timetable_use_case.TimetableModel;

/**
 * A response model that contains the relevant information needed by the presenter's view.
 * If this response model was used in the RemoveCourse use case, courseCode is the course code of the
 * that the user attempted to remove. SectionCodes is never used in this case.
 * UpdatedTimetable is a TimetableModel used to update the view after the operation is done.
 *
 */
public class EditTimetableResponseModel {
    private final String courseCode;

    private final TimetableModel updatedTimetable;

    public EditTimetableResponseModel(String courseCode, TimetableModel
                                      updatedTimetable){
        this.courseCode = courseCode;
        this.updatedTimetable = updatedTimetable;
    }

    public String getCourseCode() {
        return courseCode;
    }


    public TimetableModel getUpdatedTimetable() {
        return updatedTimetable;
    }
}
