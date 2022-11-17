package edit_timetable_use_case;

public interface RemoveCourseInputBoundary {

    EditTimetableResponseModel remove(EditTimetableRequestModel requestModel) throws RemoveCourseFailedException;
}
