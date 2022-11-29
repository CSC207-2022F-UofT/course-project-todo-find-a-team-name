package fileio_use_case;

import entities.CalendarCourse;
import entities.Session;

import java.util.HashMap;
/** Builds Session **/
public class SessionBuilderInteractor {
    public SessionBuilderInteractor() {}
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
    /**
     * Returns a SessionStorer class of all sessions (Fall and Winter) based on given HashMap of String
     * to CalendarCourse.
     * Note: Use .getAllSessions() method in SessionStorer to get
     * all Sessions represented as HashMap<String, Session> where the key is the sessionType.
     *
     * @param allCourses HashMap<String, CalendarCourse>
     * @return SessionStorer
     */
    public SessionStorer allSessionBuilder(HashMap<String, CalendarCourse> allCourses) {
        SessionStorer sessionStorer = new SessionStorer();
        Session Fall = new Session("F");
        sessionStorer.addSession("F", Fall);
        Session Winter = new Session("S");
        sessionStorer.addSession("S", Winter);

        for (String courseName : allCourses.keySet()) {
            CalendarCourse calCourse = allCourses.get(courseName);
            if (calCourse.getCourseSession().equals("F")) {
                Fall.addCourse(calCourse);
            }
            else {
                Winter.addCourse(calCourse);
            }
        }
        return sessionStorer;
    }

}
