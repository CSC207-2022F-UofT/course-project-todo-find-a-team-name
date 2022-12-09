package screens;

import display_timetable_use_case.frameworks_and_drivers.TimetableViewCourseModel;

import java.util.HashMap;

public class SessionViewModel {

    private final HashMap<String, TimetableViewCourseModel> courses;
    private final String sessionType;

    public SessionViewModel(HashMap<String, TimetableViewCourseModel> courses, String sessionType){
        this.courses = courses;
        this.sessionType = sessionType;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SessionViewModel)){
            return false;
        }
        SessionViewModel other = (SessionViewModel) obj;
        return this.sessionType.equals(other.sessionType)
                && courses.equals(other.courses);
    }

    public HashMap<String, TimetableViewCourseModel> getCourses() {
        return courses;
    }

    public String getSessionType() {
        return sessionType;
    }
}
