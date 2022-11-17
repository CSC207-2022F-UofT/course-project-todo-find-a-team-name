package entities;

import java.util.ArrayList;
import java.util.HashMap;

/** An entity that represents Session.
 * A session contains all available courses.
 * Representation Invariants:
 * 1. Courses in a session that happens in the Fall, Winter or both.
 * 2. For methods that take in a course code, it is ASSUMED that the course HAS BEEN ADDED in this session.
 * 3. All CalenderCourse objects contains course codes
 * Session Types: Fall (F), Winter (S), Both (Y)
*/
public class Session {

    // allSessionCourses contains all courses in this session:
    // key (Course Code) and value (CalendarCourse object)
    private HashMap<String, CalendarCourse> allSessionCourses;
    private final String sessionType;

    // Constructor
    public Session(String sessionType) {
        allSessionCourses = new HashMap<>();
        this.sessionType = sessionType;
    }

    /**
     * Adds course to this session
     * @param course (a CalendarCourse object)
     */
    public boolean addCourse(CalendarCourse course) {
        if (!allSessionCourses.containsValue(course) && course.getCourseSession().equals(this.sessionType)) {
            // Adds key (Course Code) and value (CalendarCourse object)
            allSessionCourses.put(course.getCourseCode(), course);
            return true;
        }
        return false;
    }
    /**
     * Returns the session type of this session. Either Fall (F), Winter (S), or Fall & Winter (Y).
     * @return String
     */
    public String getSessionType() {
        return this.sessionType;
    }

    /**
     * Removes given courseCode in this session
     * @param courseCode String representation of course code
     */
    public void removeCourse(String courseCode) {
        allSessionCourses.remove(courseCode);
    }

    /**
     * Returns CalendarCourse object based given courseCode.
     * @param courseCode String representation of course code.
     * @return CalendarCourse
     */
    public CalendarCourse loadCalendarCourse(String courseCode){
        return allSessionCourses.get(courseCode);
    }

    /**
     * Returns a list of all courses codes in this session with given Breadth Category.
     * @param breadth A breadth category
     * @return ArrayList<String>
     */
    public ArrayList<String> allCourseCodesBRSession(String breadth) {
        ArrayList<String> allCourseCodesBR = new ArrayList<>();
        for (String courseCode : allSessionCourses.keySet()) {
            if (allSessionCourses.get(courseCode).getBreadth().equals(breadth)) {
                allCourseCodesBR.add(courseCode);
            }
        }
        return allCourseCodesBR;
    }

    /**
     * Returns a list of all course codes in this session
     * @return ArrayList<String>
     */
    public ArrayList<String> allCourseCodesSession() {
        return new ArrayList<>(allSessionCourses.keySet());
    }

    @Override
    public String toString() {
        return "Session: " + this.sessionType + " { " + allSessionCourses + " }";
    }

}
