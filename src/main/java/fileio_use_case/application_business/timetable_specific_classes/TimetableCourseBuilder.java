package fileio_use_case.application_business.timetable_specific_classes;

import entities.InvalidSectionsException;
import entities.Section;
import entities.TimetableCourse;

import java.util.ArrayList;
import java.util.List;

public class TimetableCourseBuilder {
    /** Builds TimetableCourse **/
    protected final String title;
    protected final List<Section> sections;
    protected final String courseSession;
    protected final String courseCode;
    protected final String breadth;
    public TimetableCourseBuilder(String title, ArrayList<Section> sections, String courseSession, String courseCode, String breadth) {
        this.title = title;
        this.sections = sections;
        this.courseSession = courseSession;
        this.courseCode = courseCode;
        this.breadth = breadth;
    }
    public TimetableCourse newCourse() throws InvalidSectionsException {
        return new TimetableCourse(this.title, this.sections, this.courseSession, this.courseCode, this.breadth);
    }
}

