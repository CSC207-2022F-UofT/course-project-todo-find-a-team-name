package edit_timetable_use_case;

public interface RemoveCourseOutputBoundary {
    /**
     * @param responseModel
     * @return
     */
    EditTimetableResponseModel prepareView(EditTimetableResponseModel responseModel) throws RemoveCourseFailedException;
}
