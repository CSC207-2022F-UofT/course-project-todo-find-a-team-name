package edit_timetable_use_case;


/** #TODO: replace with ______InputBoundary.
 * A input boundary to be replaced by AddCourseInputBoundary and EditCourseInputBoundary
 */

public interface EditTimetableInputBoundary {
    /**
     * @param requestModel
     * @return
     */
    EditTimetableResponseModel add(EditTimetableRequestModel requestModel);
    EditTimetableResponseModel edit(EditTimetableRequestModel requestModel);
}
