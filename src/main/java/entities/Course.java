/** An entity representing a Course.
 *
 * Strings are used for tags because it's convenient with JSON. Don't actually use them as strings!
 *
 * Instance Attributes:
 * - title: Title of the Course
 * - sections: A list of all sections in the Course.
 * - courseSession: the course session (First, Second).
 * - courseCode:  the course code.
 * - breadth: the Course Breadth Category.
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