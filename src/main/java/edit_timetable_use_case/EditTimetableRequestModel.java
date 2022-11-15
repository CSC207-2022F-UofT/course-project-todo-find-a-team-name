package edit_timetable_use_case;

public class EditTimetableRequestModel {
    private String courseCode;

    EditTimetableRequestModel(String courseCode){
        this.courseCode = courseCode;
    }

    String getCourseCode(){
        return this.courseCode;
    }

}
