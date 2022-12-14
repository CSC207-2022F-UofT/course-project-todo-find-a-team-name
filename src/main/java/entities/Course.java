package entities;

import java.util.ArrayList;
import java.util.List;

/** An entity representing a Course.
 * Strings are used for tags because it's convenient with JSON. Don't actually use them as strings!
 * Instance Attributes:
 * - title: Title of the Course
 * - sections: A list of all sections in the Course.
 * - courseSession: the course session (First, Second).
 * - courseCode:  the course code.
 * - breadth: the Course Breadth Category.
 * BR is sometimes an empty string. There should be a way to handle this somewhere.
 * This shouldn't change after initialization.
 */
public abstract class Course {

    protected final String title;
    protected final List<Section> sections;
    protected final String courseSession;
    protected final String courseCode;
    protected final String breadth;

    Course(String title, List<Section> sections, String courseSession, String courseCode, String breadth){
        this.title = title;
        this.sections = sections;
        this.courseSession = courseSession;
        this.courseCode = courseCode;
        this.breadth = breadth;
    }

    public String getTitle() {
        return title;
    }

    public List<Section> getSections() {
        return sections;
    }

    public String getCourseSession() {
        return courseSession;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getBreadth() {
        return breadth;
    }

    @Override
    public String toString() {
        return "Title: " + title + "\n Sections: " + sections + "\n CourseSession: " + courseSession + "\n CourseCode: "
                + courseCode + "\n Breadth: " + breadth;
    }

    public ArrayList<String> getSectionCodes(){
        ArrayList<String> sectionCodes = new ArrayList<>();
        for(Section section: sections){
            sectionCodes.add(section.getCode());
        }
        return sectionCodes;
    }

    /** A Course == another course, if all the attributes are the same. **/
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Course) {
            return this.title.equals(((Course) obj).title) && this.courseSession.equals(((Course) obj).courseSession)
                    && this.courseCode.equals(((Course) obj).courseCode) && this.breadth.equals(((Course) obj).breadth)
                    && this.sections.equals(((Course) obj).sections);
        } else {
            return false;
        }
    }
}