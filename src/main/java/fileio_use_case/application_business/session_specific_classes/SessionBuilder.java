package fileio_use_case.application_business.session_specific_classes;

import entities.CalendarCourse;
import entities.Session;

import java.util.HashMap;
/** Builds Session **/
public class SessionBuilder {
    public SessionBuilder() {}
    /**
     * Returns a session if given the HashMap representation of all courses and sessionType
     * @param allCourses - contains all sessions, String - session type (Fall (F), Winter (S))
     * @return Session
     */
    public Session aSessionBuilder(HashMap<String, CalendarCourse> allCourses, String sessionType) {
        Session aSession = new Session(sessionType);

        for (String courseName : allCourses.keySet()) {
            if (allCourses.get(courseName).getCourseSession().equals(sessionType)) {
                aSession.addCourse(allCourses.get(courseName));
            }
        }
        return aSession;
    }
}
