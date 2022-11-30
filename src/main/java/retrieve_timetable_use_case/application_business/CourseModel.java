package retrieve_timetable_use_case.application_business;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * A data carrier class that doubles as a request and response model containing all information
 * that a Course would contain while protecting Controllers and Presenters from changes to
 * the original entity.
 * It can contain the information of either a CalendarCourse or TimetableCourse as needed.
 * Title refers to the course's title (e.g. "Introduction to the Theory of Computation").
 * Sections is a list of SectionModels that correspond to the sections that belong to the course.
 * courseSession determines which session the course belongs to.
 * courseCode is an abbreviated code assigned to the course (e.g. CSC108).
 * breadth refers to the breadth category the course belongs to.
 */
public class CourseModel {
    private final String title;
    private final List<SectionModel> sections;
    private final String courseSession;
    private final String courseCode;
    private final String breadth;

    public CourseModel(String title, List<SectionModel> sections, String courseSession,
                       String courseCode, String breadth){
        this.title = title;
        this.sections = sections;
        this.courseSession = courseSession;
        this.courseCode = courseCode;
        this.breadth = breadth;
    }

    public String getTitle() {
        return title;
    }

    public List<SectionModel> getSections() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseModel that = (CourseModel) o;
        return getTitle().equals(that.getTitle()) &&
                new HashSet<>(getSections()).equals(new HashSet<>(that.getSections()))
                && getCourseSession().equals(that.getCourseSession())
                && getCourseCode().equals(that.getCourseCode())
                && getBreadth().equals(that.getBreadth());
    }
}
