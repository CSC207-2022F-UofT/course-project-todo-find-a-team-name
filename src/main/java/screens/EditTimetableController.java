package screens;

import edit_timetable_use_case.AddCourseInputBoundary;
import edit_timetable_use_case.EditTimetableRequestModel;
import edit_timetable_use_case.RemoveCourseFailedException;
import edit_timetable_use_case.RemoveCourseInputBoundary;
import entities.InvalidSectionsException;

import java.util.ArrayList;
import java.util.List;

/**
 * The controller used to process requests to edit the timetable. This controller will communicate with 1 interactor
 * for each type of request (removing a course, adding a new course, editing an existing course).
 */
public class EditTimetableController {
    private final RemoveCourseInputBoundary removeCourseInteractor;
    private final AddCourseInputBoundary addCourseInteractor;

    public EditTimetableController(RemoveCourseInputBoundary removeCourseInteractor,
                                   AddCourseInputBoundary addCourseInteractor){
        this.removeCourseInteractor = removeCourseInteractor;
        this.addCourseInteractor = addCourseInteractor;
    }


    /**
     * @param courseCode The course code of the course to be removed.
     * @throws RemoveCourseFailedException if the course code is of a course that is not in the timetable.
     */
    public void remove(String courseCode) throws RemoveCourseFailedException {
        EditTimetableRequestModel requestModel = new EditTimetableRequestModel(courseCode, new ArrayList<>());
        removeCourseInteractor.remove(requestModel);
    }

    /**
     * @param courseCode The course code of the course to be added.
     * @param sectionCodes The codes of the sections to be added.
     * @throws InvalidSectionsException if the sections given create an invalid TimetableCourse (more than 1 TUT, PRA
     *  or LEC section is selected).
     */
    public void add(String courseCode, List<String> sectionCodes) throws InvalidSectionsException {
        EditTimetableRequestModel requestModel = new EditTimetableRequestModel(courseCode, sectionCodes);
        addCourseInteractor.add(requestModel);
    }
}