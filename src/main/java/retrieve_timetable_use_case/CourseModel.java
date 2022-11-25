package retrieve_timetable_use_case;

import java.util.ArrayList;
import java.util.List;

public class CourseModel {
    private final String title;
    private List<SectionModel> sections;
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
        return new ArrayList<SectionModel>(this.sections);
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
}
