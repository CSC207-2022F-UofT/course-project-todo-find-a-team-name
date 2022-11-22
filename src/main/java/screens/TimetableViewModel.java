package screens;

import java.util.List;

public class TimetableViewModel {
    private final List<TimetableViewCourseModel> timetableCourseData;

    public TimetableViewModel(List<TimetableViewCourseModel> timetableCourseData) {
        this.timetableCourseData = timetableCourseData;
    }

    public List<TimetableViewCourseModel> getCourseData() {
        return timetableCourseData;
    }
}


