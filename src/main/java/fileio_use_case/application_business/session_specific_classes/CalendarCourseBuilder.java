package fileio_use_case.application_business.session_specific_classes;

import entities.CalendarCourse;
import entities.Section;

import java.util.ArrayList;
import java.util.List;
/** Builds CalendarCourse **/
public class CalendarCourseBuilder {
    protected final String title;
    protected final List<Section> sections;
    protected final String courseSession;
    protected final String courseCode;
    protected final String breadth;
    public CalendarCourseBuilder(String title, ArrayList<Section> sections, String courseSession, String courseCode, String breadth) {
        this.title = title;
        this.sections = sections;
        this.courseSession = courseSession;
        this.courseCode = courseCode;
        this.breadth = breadth;
    }
    public CalendarCourse newCourse(){
        return new CalendarCourse(this.title, this.sections, this.courseSession, this.courseCode, this.breadth);
    }
}
