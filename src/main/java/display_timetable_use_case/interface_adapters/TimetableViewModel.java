package display_timetable_use_case.interface_adapters;

import java.util.List;

/**
 * Class storing all information needed for displaying timetable
 * Instance Attributes:
 *      - timetableCourseData: list of TimetableViewCourseModel, each containing information corresponding to some
 *      course needed for displaying timetable
 */
public class TimetableViewModel {
    private final List<TimetableViewCourseModel> timetableCourseData;

    /**
     * Constructs TimetableViewModel from the given list of TimetableViewCourseModel, containing
     * information needed for displaying timetable for each course
     *
     * @param timetableCourseData data for course contained in the timetable
     */
    public TimetableViewModel(List<TimetableViewCourseModel> timetableCourseData) {
        this.timetableCourseData = timetableCourseData;
    }

    /**
     * Returns list of TimetableViewCourseModel for this model, containing information needed for displaying
     * timetable for each course
     *
     * @return list of TimetableViewCourseModel for this model
     */
    public List<TimetableViewCourseModel> getCourseData() {
        return timetableCourseData;
    }
}


