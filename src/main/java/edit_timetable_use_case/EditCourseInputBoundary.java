package edit_timetable_use_case;

import entities.InvalidSectionsException;
import entities.Session;
import entities.Timetable;
import retrieve_timetable_use_case.RetrieveTimetableInputBoundary;

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

    /**
     * @param session The session corresponding to the timetable being edited. Any sections and courses added
     *                should be present in the session.
     */
    void setSession(Session session);

    /**
     * @param timetable The timetable being edited. Any course being edited should already be present in the session.
     */
    void setTimetable(Timetable timetable);

    void setRetrieveInteractor(RetrieveTimetableInputBoundary retrieveInteractor);
}
