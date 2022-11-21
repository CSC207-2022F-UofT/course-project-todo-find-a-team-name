package edit_timetable_use_case;

import entities.InvalidSectionsException;

public interface AddCourseInputBoundary {

    public void add(EditTimetableRequestModel request) throws InvalidSectionsException;
}
