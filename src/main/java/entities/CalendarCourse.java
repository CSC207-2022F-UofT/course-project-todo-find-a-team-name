package entities;

import java.util.List;

/** A concrete implementation of Course that stores all sections available in a course.
 *
 */

public class CalendarCourse extends Course {
    /** All parameters of the constructor are identical to Course's.
     * @param title Title of the Course
     * @param sections A list of all sections in the Course.
     * @param courseSession the course session (First, Second).
     * @param courseCode the course code.
     * @param breadth the Course Breadth Category.
     */
    public CalendarCourse(String title, List<Section> sections, String courseSession, String courseCode, String breadth){
        super(title, sections, courseSession, courseCode, breadth);
    }

    /** removeSection removes a single section from the course's sections.
     * @param toRemove The section to be removed from Sections.
     */
    public void removeSection(Section toRemove){
        this.sections.remove(toRemove);
    }

    public boolean equals(CalendarCourse other){
        return (this.title.equals(other.title) && this.sections.equals(other.sections) &&
                this.courseSession.equals(other.courseSession) && this.courseCode.equals(other.courseCode)
        && this.breadth.equals(other.breadth));
    }
    }
