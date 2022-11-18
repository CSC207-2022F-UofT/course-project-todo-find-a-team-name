package edit_timetable_use_case;

public class RetrieveTimetableRequestModel {
    private String timetable;
    private String courseCode;
    private String sectionCode;

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

    public String getSectionCode() {
        return sectionCode;
    }
}
