package retrieve_timetable_use_case;

import screens.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TimetableResponseConverter {

    public static SessionViewModel SessionToView(SessionResponseModel session){
        HashMap<String, TimetableViewCourseModel> courses = new HashMap<String, TimetableViewCourseModel>();
        for (String courseCode : session.getCourses().keySet()){
            courses.put(courseCode, CourseToView(session.getCourses().get(courseCode)));
        }
        return new SessionViewModel(courses, session.getSessionType());
    }

    public static TimetableViewModel TimetableToView(TimetableResponseModel timetable){
        List<TimetableViewCourseModel> courses = new ArrayList<TimetableViewCourseModel>();
        for (CourseResponseModel course : timetable.getCourses() ){
            courses.add(CourseToView(course));
        }

        return new TimetableViewModel(courses);
    }

    public static TimetableViewCourseModel CourseToView(CourseResponseModel course){
        List<TimetableViewSectionModel> sections = new ArrayList<TimetableViewSectionModel>();
        for (SectionResponseModel section : course.getSections()){
            sections.add(SectionToView(section));
        }

        return new TimetableViewCourseModel(course.getCourseCode(), sections);
    }

    public static TimetableViewSectionModel SectionToView(SectionResponseModel section){
        List<TimetableViewBlockModel> blocks = new ArrayList<TimetableViewBlockModel>();
        for (BlockResponseModel block : section.getBlocks()){
            blocks.add(BlockToView(block));
        }

        return new TimetableViewSectionModel(section.getCode(), blocks);
    }

    public static TimetableViewBlockModel BlockToView(BlockResponseModel block){
        return new TimetableViewBlockModel(block.getDay(), block.getStartTime(), block.getEndTime());
    }
}
