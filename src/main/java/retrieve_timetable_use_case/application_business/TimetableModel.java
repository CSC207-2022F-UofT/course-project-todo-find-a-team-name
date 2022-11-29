package retrieve_timetable_use_case.application_business;

import java.util.List;

/**
 *  A data carrier class that doubles as a request and response model containing all information
 *  that a Session would contain while protecting Controllers and Presenters from changes to
 *  the original entity.
 *  courses is a list of CourseModels that are contained in the timetable.
 */
public class TimetableModel {

    private final List<CourseModel> courses;

    public TimetableModel(List<CourseModel> courses){
        this.courses = courses;
    }

    public List<CourseModel> getCourses() {
        return courses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimetableModel that = (TimetableModel) o;
        return getCourses().equals(that.getCourses());
    }
}
