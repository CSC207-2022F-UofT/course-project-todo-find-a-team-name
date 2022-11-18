package edit_timetable_use_case;

/**
 * The input boundary used in the RemoveCourse use case.
 */
public interface RemoveCourseInputBoundary {

    /**
     * @param requestModel: a EditTimetableRequestModel that contains the course code of the course to be removed.
     * @return: a EditTimetableResponseModel that contains the course code of the course that the user tried to
     * remove and the success of the operation.
     * @throws RemoveCourseFailedException when the course could not be removed. This is most likely when the
     * timetable contains no such course that has the input course code.
     */
    EditTimetableResponseModel remove(EditTimetableRequestModel requestModel) throws RemoveCourseFailedException;
}
