package edit_timetable_use_case;

/**
 * The output boundary of the RemoveCourse use case.
 */
public interface RemoveCourseOutputBoundary {
    /**
     * @param responseModel: a EditTimetableResponseModel that contains the course that the user attempted to remove,
     *                     as well as the success of the attempt.
     * @return the input responseModel, mutated such that it contains a message to inform the user of the attempt's
     * result.
     */
    EditTimetableResponseModel prepareView(EditTimetableResponseModel responseModel) throws RemoveCourseFailedException;
}
