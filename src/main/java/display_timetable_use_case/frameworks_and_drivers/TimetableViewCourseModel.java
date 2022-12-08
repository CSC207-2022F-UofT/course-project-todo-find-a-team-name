package display_timetable_use_case.frameworks_and_drivers;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/**
 * Class representing all information needed for the course in displaying the timetable
 * Instance Attributes:
 *      - code: course code of this course
 *      - sectionModels: models representing section contained in this course, each storing all information needed for
 *      section to display the timetable
 */
public class TimetableViewCourseModel {
    private final String code;
    private final List<TimetableViewSectionModel> sectionModels;

    /**
     * Constructs TimetableViewBlockModel with the course code and section models
     *
     * @param code course code of this course
     * @param sectionModels models representing section contained in this course
     */
    public TimetableViewCourseModel(String code, List<TimetableViewSectionModel> sectionModels) {
        this.code = code;
        this.sectionModels = sectionModels;
    }

    /**
     * Returns code of this course
     *
     * @return course code
     */
    public String getCode() {
        return code;
    }

    /**
     * Returns models representing section contained in this course, each storing all information for
     * section needed to display the timetable
     *
     * @return models representing section contained in this course
     */
    public List<TimetableViewSectionModel> getSectionModels() {
        return sectionModels;
    }

    /**
     * Return whether this object is equal to obj
     * @param obj object compared
     * @return whether this object is equal to obj
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof TimetableViewCourseModel)){
            return false;
        }

        TimetableViewCourseModel other = (TimetableViewCourseModel) obj;

        return code.equals(other.code) && (new HashSet<>(sectionModels)).equals(new HashSet<>(other.sectionModels));
    }

    @Override
    public String toString() {
        return "TimetableViewCourseModel{" +
                "code='" + code + '\'' +
                ", sectionModels=" + sectionModels +
                '}';
    }

    /**
     * Returns a hash code value for this object.
     * If two objects are equal based on equals method, hashCode also returns same integers.
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(code, new HashSet<>(sectionModels));
    }
}
