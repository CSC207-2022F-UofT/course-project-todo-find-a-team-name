package edit_timetable_use_case.application_business;

/**
 * An exception that is raised when the user attempts to remove a course from a timetable, but fails.
 */
public class RemoveCourseFailedException extends Exception {
    /**
     * @param courseCode: the course code of the course that the user attempted to remove.
     */
    public RemoveCourseFailedException(String courseCode){
        super(courseCode + " could not be removed.");
    }
}
