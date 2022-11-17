package edit_timetable_use_case;

import java.util.ArrayList;

/**
 *
 */
public class EditTimetableController {
    RemoveCourseInputBoundary removeCourseInteractor;

    /**
     * @param removeCourseInteractor
     */
    public EditTimetableController(RemoveCourseInputBoundary removeCourseInteractor){
        this.removeCourseInteractor = removeCourseInteractor;
    }

    /**
     * @param courseCode
     * @return
     */
    public EditTimetableResponseModel remove(String courseCode) throws RemoveCourseFailedException {
        EditTimetableRequestModel requestModel = new EditTimetableRequestModel(courseCode, new ArrayList<>());
        return removeCourseInteractor.remove(requestModel);
    }
}