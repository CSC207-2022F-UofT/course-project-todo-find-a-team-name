package edit_timetable_use_case;

import entities.InvalidSectionsException;
import entities.Session;
import entities.Timetable;

public interface AddCourseInputBoundary {

    void add(EditTimetableRequestModel request) throws InvalidSectionsException;

    void setTimetable(Timetable timetable);

    void setSession(Session session);
}
