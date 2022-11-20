package entities;
import java.util.*;

/** An implementation of the timetable which stores all of the timetablecourses
 * each timetable block either is empty or corresponds to a Timetable course.
 */
public class Timetable {
    private ArrayList<TimetableCourse> CourseList;
    public Timetable(ArrayList<TimetableCourse> timetableCourses){
        this.CourseList = new ArrayList<TimetableCourse>();
        this.CourseList.addAll(timetableCourses);
        }
    public TimetableCourse getCourse(String code){
        for(TimetableCourse course: this.CourseList){
            if(course.courseCode.equals(code)){
                return course;
            }
        }
        return null;
    }

    public ArrayList<TimetableCourse> getCourseList() {
        return CourseList;
    }
}


