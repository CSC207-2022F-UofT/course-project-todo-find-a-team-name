package generate_overlapping_timetable_use_case.interface_adapters;

import java.util.ArrayList;
import java.util.List;

/**
 * A helper ViewModel representing a Course for the OverlapInput Dialog.
 * Data is identical to the Entity version.
 * Can be quickly initialized from a CourseModel
 */
public class OverlapTimetableCourseViewModel {
    private final String title;
    private final List<OverlapTimetableSectionViewModel> sections;
    private final String courseSession;
    private final String courseCode;
    private final String breadth;

    public OverlapTimetableCourseViewModel(String title, List<OverlapTimetableSectionViewModel> sections, String courseSession,
                                           String courseCode, String breadth) {
        this.title = title;
        this.sections = sections;
        this.courseSession = courseSession;
        this.courseCode = courseCode;
        this.breadth = breadth;
    }

    public String getTitle() {
        return title;
    }

    public List<OverlapTimetableSectionViewModel> getSections() {
        return new ArrayList<>(this.sections);
    }

    public String getCourseSession() {
        return courseSession;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getBreadth() {
        return this.breadth;
    }

    /**
     * Equality for these viewModels <==> data class equality
     **/
    @Override
    public boolean equals(Object o) {
        if (o instanceof OverlapTimetableCourseViewModel) {
            OverlapTimetableCourseViewModel other = (OverlapTimetableCourseViewModel) o;
            return this.title.equals(other.title) && this.sections.equals(other.sections)
                    && this.courseSession.equals(other.courseSession) && this.courseCode.equals(other.courseCode)
                    && this.breadth.equals(other.breadth);
        } else {
            return false;
        }
    }
}
