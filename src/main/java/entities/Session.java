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
        this.allSessionCourses = new HashMap<>();
        this.sessionType = sessionType;
    }

    /**
     * Adds course to this session
     * @param course (a CalendarCourse object)
     */
    public boolean addCourse(CalendarCourse course) {
        if (!(allSessionCourses.containsValue(course)) && course.getCourseSession().equals(this.sessionType)) {
            // Adds key (Course Code) and value (CalendarCourse object)
            allSessionCourses.put(course.getCourseCode(), course);
            return true;
        }
        return false;
    }
    /**
     * Returns True if course is in this session using courseCode.
     * @param courseCode a String of course code
     */
    public boolean checkCourseCode(String courseCode) {
        return allSessionCourses.containsKey(courseCode);
    }

    /**
     * Returns True if course is in this session using CalendarCourse object
     * @param course (a CalendarCourse object)
     */
    public boolean checkCalendarCourse(CalendarCourse course) {
        return allSessionCourses.containsValue(course);
    }

    /**
     * Returns the session type of this session. Either Fall (F), Winter (S), or Fall & Winter (Y).
     * @return String of session type
     */
    public String getSessionType() {
        return this.sessionType;
    }

    /**
     * Returns given a CalendarCourse object's course code in this session
     * @param course CalendarCourse object
     */
    public String itsCourseCode(CalendarCourse course) {
        for (String courseCode : allSessionCourses.keySet()) {
            if (allSessionCourses.get(courseCode).equals(course)) {
                return courseCode;
            }
        }
        return " ";
    }

    /**
     * Removes given courseCode in this session
     * @param courseCode String representation of course code
     */
    public void removeCourse(String courseCode) {
        allSessionCourses.remove(courseCode);
    }

    /**
     * Removes number of courses in this session
     * @return Integer
     */
    public Integer numberOfCourses() {
        return allSessionCourses.size();
    }

    /**
     * Returns CalendarCourse object based given courseCode.
     * @param courseCode String representation of course code.
     * @return CalendarCourse
     */
    public CalendarCourse getCalendarCourse(String courseCode){
        return allSessionCourses.get(courseCode);
    }

    /**
     * Returns a unsorted list of all courses codes in this session with given Breadth Category.
     * @param breadth A breadth category
     * @return ArrayList<String> containing strings of course codes
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
     * Returns a unsorted list of all course codes in this session.
     * @return ArrayList<String> containing strings of course codes
     */
    public ArrayList<String> allCourseCodesSession() {
        return new ArrayList<>(allSessionCourses.keySet());
    }

    /**
     * Returns a unsorted list of all CalendarCourse objects in this session
     * @return ArrayList<CalendarCourse> containing strings of course codes
     */
    public ArrayList<CalendarCourse> allCoursesSession() {
        return new ArrayList<>(allSessionCourses.values());
    }

    @Override
    public String toString() {
        return "Session: " + this.sessionType + " { " + allSessionCourses + " }";
    }

    public HashMap<String, CalendarCourse> getAllSessionCourses() {
        return allSessionCourses;
    }
}
