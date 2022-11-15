package edit_timetable_use_case;

public class RemoveCourseFailedException extends Exception {
    public RemoveCourseFailedException(String courseName){
        super(courseName + " could not be removed.");
    }
}
