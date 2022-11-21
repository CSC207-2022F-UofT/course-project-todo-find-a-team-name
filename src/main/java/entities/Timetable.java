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
    // Adds course
    public void AddToCourseList(TimetableCourse course){
        this.CourseList.add(course);
    }
    // returns true if there is a conflict
    public boolean isConflicted(Section section){
        for (TimetableCourse course : this.CourseList){
            for (Section timetableSection : course.getSections()){
                if (timetableSection.isConflicted(section))
                    return true;
            }
        }
        return false;
    } 
    // Gets course
    public TimetableCourse getCourse(String code){
        for(TimetableCourse course: this.CourseList){
            if(course.courseCode.equals(code)){
                return course;
            }
        }
        return null;
    }

}


