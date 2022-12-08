package entities;

import java.util.HashSet;
import java.util.List;

/** A concrete implementation of Course that stores all sections available in a course.
 *
 */

public class CalendarCourse extends Course {
    /**
     * All parameters of the constructor are identical to Course's.
     *
     * @param title         Title of the Course
     * @param sections      A list of all sections in the Course.
     * @param courseSession the course session (First, Second).
     * @param courseCode    the course code.
     * @param breadth       the Course Breadth Category.
     */
    public CalendarCourse(String title, List<Section> sections, String courseSession, String courseCode, String breadth) {
        super(title, sections, courseSession, courseCode, breadth);
    }

    /**
     * removeSection removes a single section from the course's sections.
     *
     * @param toRemove The section to be removed from Sections.
     */
    public void removeSection(Section toRemove) {
        this.sections.remove(toRemove);
    }


    /**
     * @param obj another object, which is compared to the current CalendarCourse
     * @return true iff the entries of each instance attribute is identical.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof CalendarCourse))
            return false;

        CalendarCourse other = (CalendarCourse) obj;

        return (this.title.equals(other.title) && new HashSet<>(this.sections).equals(new HashSet<>(other.sections)) &&
                this.courseSession.equals(other.courseSession) && this.courseCode.equals(other.courseCode)
                && this.breadth.equals(other.breadth));
    }

    /**
     * @return true iff any section in Sections is a lecture section.
     */
    public boolean hasLecture() {
        for (Section section : sections) {
            if (section.isLecture()) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return true iff any section in Sections is a tutorial section.
     */
    public boolean hasTutorial() {
        for (Section section : sections) {
            if (section.isTutorial()) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return true iff any section in Sections is a practical section.
     */
    public boolean hasPractical() {
        for (Section section : sections) {
            if (section.isPractical()) {
                return true;
            }
        }
        return false;
    }
}