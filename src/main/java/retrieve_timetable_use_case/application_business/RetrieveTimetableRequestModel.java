package retrieve_timetable_use_case.application_business;

/**
 * A request model containing all requirements of a given retrieve timetable request.
 * timetable is used in case one of many timetables needs to be accessed.
 * courseCode refers to the course that needs to be copied, and is required for retrieveTimetableCourse
 * and retrieveCalendarCourse calls.
 * sectionCode is used to retrieve a specific section.
 */
public class RetrieveTimetableRequestModel {
    private final String timetable;
    private final String courseCode;
    private final String sectionCode;

    public RetrieveTimetableRequestModel(String timetable, String courseCode, String sectionCode){
        this.timetable = timetable;
        this.courseCode = courseCode;
        this.sectionCode = sectionCode;
    }

    public java.lang.String getCourseCode() {
        return courseCode;
    }

    public String getTimetable() {
        return timetable;
    }
    
}
