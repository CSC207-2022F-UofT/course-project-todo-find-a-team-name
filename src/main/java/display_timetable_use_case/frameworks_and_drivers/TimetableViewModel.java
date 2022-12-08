package display_timetable_use_case.frameworks_and_drivers;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

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

    /**
     * Return whether this object is equal to obj
     * @param obj object compared
     * @return whether this object is equal to obj
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof TimetableViewModel)){
            return false;
        }

        TimetableViewModel other = (TimetableViewModel) obj;

        return new HashSet<>(timetableCourseData).equals(new HashSet<>(other.timetableCourseData));
    }

    /**
     * Returns a hash code value for this object.
     * If two objects are equal based on equals method, hashCode also returns same integers.
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(new HashSet<>(timetableCourseData));
    }

    @Override
    public String toString() {
        return "TimetableViewModel{" +
                "timetableCourseData=" + timetableCourseData +
                '}';
    }

    public TimetableViewCourseModel getCourse(String courseCode){
        for (TimetableViewCourseModel course : timetableCourseData){
            if (course.getCode().equals(courseCode)){
                return course;
            }
        }
        return null;
    }
}


