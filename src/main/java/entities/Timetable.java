package entities;

import java.util.ArrayList;

/**
 * An implementation of the timetable which stores all of the timetablecourses
 * each timetable block either is empty or corresponds to a Timetable course.
 */
public class Timetable {
    private final ArrayList<TimetableCourse> courseList;
    private final String sessionType;

    public Timetable(ArrayList<TimetableCourse> timetableCourses, String sessionType) {
        this.courseList = new ArrayList<>();
        this.courseList.addAll(timetableCourses);
        this.sessionType = sessionType;
    }

    // Adds course
    public void AddToCourseList(TimetableCourse course) {
        this.courseList.add(course);
    }

    // returns true if there is a conflict
    public boolean isConflicted(Section section) {
        for (TimetableCourse course : this.courseList) {
            for (Section timetableSection : course.getSections()) {
                if (timetableSection.isConflicted(section))
                    return true;
            }
        }
        return false;
    }

    // Gets course
    public TimetableCourse getCourse(String code) {
        for(TimetableCourse course: this.courseList){
            if(course.courseCode.equals(code)){
                return course;
            }
        }
        return null;
    }

    public String getSessionType() {
        return sessionType;
    }

    public boolean existsByCode(String code){
        for (TimetableCourse course: this.courseList){
            if (course.getCourseCode().equals(code)){
                return true;
            }
        }
        return false;
    }

    public void removeCourse(String code){
        for (TimetableCourse course: this.courseList){
            if (course.getCourseCode().equals(code)){
                courseList.remove(course);
                return;
            }
        }
    }
    public ArrayList<TimetableCourse> getCourseList() {
        return courseList;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Timetable) {
            return this.courseList.equals(((Timetable) obj).courseList) &&
                    this.sessionType.equals(((Timetable) obj).sessionType);
        } else {
            return false;
        }
    }
}


