package retrieve_timetable_use_case;

import java.util.ArrayList;
import java.util.List;

public class CourseResponseModel {
    private final String title;
    private List<SectionResponseModel> sections;
    private final String courseSession;
    private final String courseCode;
    private final String breadth;

    public CourseResponseModel(String title, List<SectionResponseModel> sections, String courseSession,
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

    public List<SectionResponseModel> getSections() {
        return new ArrayList<SectionResponseModel>(this.sections);
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
