package entities;

/** A concrete implementation of Course that stores all sections available in a course.
 *
 */

public class CalendarCourse extends Course {
    public CalendarCourse(List<Section> sections, String courseSession, String courseCode, String breadth){
        super(sections, courseSession, courseCode, breadth);
    }

    public void removeSection(Section toRemove){
        this.sections.remove(toRemove);
    }
}