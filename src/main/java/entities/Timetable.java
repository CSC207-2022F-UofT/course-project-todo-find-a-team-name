package entities;
import java.util.*;

/** An implementation of the timetable which stores all of the timetablecourses
 * each timetable block either is empty or corresponds to a Timetable course.
 */
public class Timetable implements Comparable<Timetable> {
    private ArrayList<TimetableCourse> courseList;
    private String sessionType;
    private double score;
    
    public Timetable(ArrayList<TimetableCourse> timetableCourses, String sessionType){
        this.courseList = new ArrayList<TimetableCourse>();
        this.courseList.addAll(timetableCourses);
        this.sessionType = sessionType;
        this.score = 0;
    }
    // Adds course
    public void AddToCourseList(TimetableCourse course){
        this.courseList.add(course);
    }
    // returns true if there is a conflict
    public boolean isConflicted(Section section){
        for (TimetableCourse course : this.courseList){
            for (Section timetableSection : course.getSections()){
                if (timetableSection.isConflicted(section))
                    return true;
            }
        }
        return false;
    } 
    // Gets course
    public TimetableCourse getCourse(String code){
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

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public int compareTo(Timetable o) {
        return Double.compare(score, o.score);
    }
}


