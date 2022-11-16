package edit_timetable_use_case;


/** The input boundary used to remove a course in the EditTimetable use case.
 *
 */

public interface EditTimetableInputBoundary {
    /**
     * @param requestModel
     * @return
     */
    EditTimetableResponseModel remove(EditTimetableRequestModel requestModel);
    EditTimetableResponseModel add(EditTimetableRequestModel requestModel);
    EditTimetableResponseModel edit(EditTimetableRequestModel requestModel);
}
