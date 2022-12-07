package display_timetable_use_case.frameworks_and_drivers;

import java.util.List;

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
}
