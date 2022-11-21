package edit_timetable_use_case;

import entities.InvalidSectionsException;

import java.util.ArrayList;
import java.util.List;

/**
 * The controller used to process requests to edit the timetable. This controller will communicate with 1 interactor
 * for each type of request (removing a course, adding a new course, editing an existing course).
 */
public class EditTimetableController {
    private RemoveCourseInputBoundary removeCourseInteractor;
    private AddCourseInputBoundary addCourseInteractor;

    public EditTimetableController(RemoveCourseInputBoundary removeCourseInteractor,
                                   AddCourseInputBoundary addCourseInteractor){
        this.removeCourseInteractor = removeCourseInteractor;
        this.addCourseInteractor = addCourseInteractor;
    }


    public void remove(String courseCode) throws RemoveCourseFailedException {
        EditTimetableRequestModel requestModel = new EditTimetableRequestModel(courseCode, new ArrayList<>());
        removeCourseInteractor.remove(requestModel);
    }

    public void add(String courseCode, List<String> sectionCodes) throws InvalidSectionsException {
        EditTimetableRequestModel requestModel = new EditTimetableRequestModel(courseCode, sectionCodes);
        addCourseInteractor.add(requestModel);
    }
}