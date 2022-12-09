package entities;

import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of the timetable which stores all of the timetablecourses
 * related to a timetable
 */
public class Timetable implements Comparable<Timetable> {
    private final ArrayList<TimetableCourse> courseList;
    private final String sessionType;
    private double score;

    public Timetable(List<TimetableCourse> timetableCourses, String sessionType) {
        this.courseList = new ArrayList<>();
        this.courseList.addAll(timetableCourses);
        this.sessionType = sessionType;
        this.score = 0;
    }

    // Adds course
    public void addToCourseList(TimetableCourse course) {
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
    public boolean hasCourseOverlap(TimetableCourse course) {
        for (Section section : course.getSections()) {
            for(TimetableCourse currentCourse : this.courseList){
                for(Section courseListSection: currentCourse.getSections()){
                    if(section.isConflicted(courseListSection))
                        return true;
                }
            }
        }
        return false;
    }


    public ArrayList<TimetableCourse> getCourseList() {
        return courseList;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public int compareTo(Timetable o) {
        return Double.compare(score, o.score);
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


