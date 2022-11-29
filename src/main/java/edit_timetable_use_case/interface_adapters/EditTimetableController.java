package edit_timetable_use_case.interface_adapters;

import edit_timetable_use_case.application_business.EditTimetableRequestModel;
import edit_timetable_use_case.application_business.NotInTimetableException;
import edit_timetable_use_case.application_business.RemoveCourseFailedException;
import edit_timetable_use_case.application_business.RemoveCourseInputBoundary;
import edit_timetable_use_case.application_business.AddCourseInputBoundary;
import edit_timetable_use_case.application_business.EditCourseInputBoundary;
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
    private final EditCourseInputBoundary editInteractor;

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
     * @param courseCode the course code of the course to be added. The course itself must be in the timetable already.
     * @param sectionCodes the codes of the sections that need to be in the table. The sections must be in the session.
     * @throws NotInTimetableException when the course code given is not in the timetable.
     * @throws InvalidSectionsException when more than one section of any given type (LEC, PRA or TUT) is given.
     */
    public void edit(String courseCode, List<String> sectionCodes) throws NotInTimetableException, InvalidSectionsException {
        EditTimetableRequestModel request = new EditTimetableRequestModel(courseCode, sectionCodes);
        editInteractor.edit(request);
    }
}