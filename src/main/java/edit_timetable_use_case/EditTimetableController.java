package edit_timetable_use_case;

import java.util.ArrayList;

/**
 * The controller used to process requests to edit the timetable. This controller will communicate with 1 interactor
 * for each type of request (removing a course, adding a new course, editing an existing course).
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
    public void remove(String courseCode) throws RemoveCourseFailedException {
        EditTimetableRequestModel requestModel = new EditTimetableRequestModel(courseCode, new ArrayList<>());
        removeCourseInteractor.remove(requestModel);
    }
}