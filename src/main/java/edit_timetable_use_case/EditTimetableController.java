package edit_timetable_use_case;

import edit_timetable_use_case.*;
import entities.InvalidSectionsException;

import java.util.ArrayList;
import java.util.List;

/**
 * The controller used to process requests to edit the timetable. This controller will communicate with 1 interactor
 * for each type of request (removing a course, adding a new course, editing an existing course).
 */
public class EditTimetableController {
    private final RemoveCourseInputBoundary removeInteractor;
    private final AddCourseInputBoundary addInteractor;
    private EditCourseInputBoundary editInteractor;

    public EditTimetableController(RemoveCourseInputBoundary removeCourseInteractor,
                                   AddCourseInputBoundary addCourseInteractor,
                                   EditCourseInputBoundary editCourseInteractor){
        this.removeInteractor = removeCourseInteractor;
        this.addInteractor = addCourseInteractor;
        this.editInteractor = editCourseInteractor;
    }


    /**
     * @param courseCode The course code of the course to be removed.
     * @throws RemoveCourseFailedException if the course code is of a course that is not in the timetable.
     */
    public void remove(String courseCode) throws RemoveCourseFailedException {
        EditTimetableRequestModel requestModel = new EditTimetableRequestModel(courseCode, new ArrayList<>());
        removeInteractor.remove(requestModel);
    }

    /**
     * @param courseCode The course code of the course to be added.
     * @param sectionCodes The codes of the sections to be added.
     * @throws InvalidSectionsException if the sections given create an invalid TimetableCourse (more than 1 TUT, PRA
     *  or LEC section is selected).
     */
    public void add(String courseCode, List<String> sectionCodes) throws InvalidSectionsException {
        EditTimetableRequestModel requestModel = new EditTimetableRequestModel(courseCode, sectionCodes);
        addInteractor.add(requestModel);
    }

    /**
     * @param courseCode The code of the course to be edited. This course must be in the timetable.
     * @param sectionCodes The section codes of the course to be edited. These section codes must be in
     *                     the session.
     */
    public void edit(String courseCode, List<String> sectionCodes) throws NotInTimetableException, InvalidSectionsException {
        EditTimetableRequestModel request = new EditTimetableRequestModel(courseCode, sectionCodes);
        editInteractor.edit(request);
    }
}