package display_timetable_use_case.frameworks_and_drivers;

import java.util.HashMap;

/**
 * View model representing all information needed for displaying session data
 */
public class SessionViewModel {

    private final HashMap<String, TimetableViewCourseModel> courses;
    private final String sessionType;

    /**
     * Constructs SessionViewModel from the given courses and session type
     *
     * @param courses dictionary mapping course code to its corresponding TimetableViewCourseModel
     * @param sessionType type of the session (F or S)
     */
    public SessionViewModel(HashMap<String, TimetableViewCourseModel> courses, String sessionType){
        this.courses = courses;
        this.sessionType = sessionType;
    }

    /**
     * Returns whether obj is equal to this object
     * @param obj object compared
     * @return whether obj is equal to this object
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SessionViewModel)){
            return false;
        }
        SessionViewModel other = (SessionViewModel) obj;
        return this.sessionType.equals(other.sessionType)
                && courses.equals(other.courses);
    }

    /**
     * Returns courses for this view model
     *
     * @return courses for this view model
     */
    public HashMap<String, TimetableViewCourseModel> getCourses() {
        return courses;
    }

    /**
     * Returns the session type of this view model
     *
     * @return the session type of this view model
     */
    public String getSessionType() {
        return sessionType;
    }
}
