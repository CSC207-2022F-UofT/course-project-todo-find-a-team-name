package retrieve_timetable_use_case;

import java.util.HashMap;

/**
 *  A data carrier class that doubles as a request and response model containing all information
 *  that a Session would contain while protecting Controllers and Presenters from changes to
 *  the original entity.
 *  courses is a HashMap from course codes to corresponding course models that belong to the session.
 *  sessionType is a string (e.g. "F") that details the semester the session belongs to.
 */
public class SessionModel {

    private final HashMap<String, CourseModel> courses;
    private final String sessionType;

    public SessionModel(HashMap<String, CourseModel> courses, String sessionType){
        this.courses = courses;
        this.sessionType = sessionType;
    }

    public HashMap<String, CourseModel> getCourses() {
        return courses;
    }

    public String getSessionType() {
        return sessionType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionModel that = (SessionModel) o;
        return getCourses().equals(that.getCourses()) && getSessionType().equals(that.getSessionType());
    }
}
