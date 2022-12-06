package edit_timetable_use_case.application_business;

import entities.InvalidSectionsException;

/**
 * The input boundary used in the add course use case.
 */
public interface AddCourseInputBoundary {

    /**
     * @param request an EditTimetableRequestModel that contains the course code of the course to be added, and the
     *                section codes of the sections to be added to that course.
     * @throws InvalidSectionsException when more than one section of a type (LEC, PRA or TUT) has been given by the
     * user.
     */
    void add(EditTimetableRequestModel request) throws InvalidSectionsException;
}
