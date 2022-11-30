package fileio_use_case;

import entities.CalendarCourse;
import entities.Section;

import java.util.ArrayList;
import java.util.List;
/** Builds CalendarCourse **/
public class CalendarCourseBuilder {
    protected String title;
    protected List<Section> sections;
    protected String courseSession;
    protected String courseCode;
    protected String breadth;
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
