package edit_timetable_use_case.application_business;

import entities.InvalidSectionsException;

/**
 * The input boundary for the edit course use case.
 */
public interface EditCourseInputBoundary {

    /**
     * @param request A EditTimetableRequestModel with the course code of the existing course to be edited
     *                and the section codes of all sections that the edited course should have.
     * @throws InvalidSectionsException when the given section codes contain more than 1 section of a
     * given type (LEC, TUT or PRA).
     */
    void edit(EditTimetableRequestModel request) throws InvalidSectionsException, NotInTimetableException;
}
