package edit_timetable_use_case;

public interface EditTimetableOutputBoundary {
    /**
     * @param responseModel
     * @return
     */
    EditTimetableResponseModel prepareRemoveView(EditTimetableResponseModel responseModel) throws RemoveCourseFailedException;
}
