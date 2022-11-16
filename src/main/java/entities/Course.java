package entities;

/** An entity representing a entities.Course.
 *
 * Strings are used for tags because it's convenient with JSON. Don't actually use them as strings!
 *
 * Instance Attributes:
 * - sections: A list of all sections in the entities.Course.
 * - Contains the course session (First, Second).
 * - Contains the course code, and BR category.
 * - title: Title of the entities.Course
 *
 * BR is sometimes an empty string. There should be a way to handle this somewhere.
 * This shouldn't change after initialization.
 */
public abstract class Course() {

    private final String title;
    private final List<Section> sections;
    private final String courseSession;
    private final String courseCode;
    private final String breadth;

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
}