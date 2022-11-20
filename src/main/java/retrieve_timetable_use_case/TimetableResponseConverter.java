package retrieve_timetable_use_case;

import screens.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TimetableResponseConverter {

    public static SessionViewModel SessionToView(SessionResponseModel session){
        HashMap<String, TimetableViewCourseModel> courses = new HashMap<String, TimetableViewCourseModel>();
        for (String courseCode : session.getCourses().keySet()){
            courses.put(courseCode, courseToView(session.getCourses().get(courseCode)));
        }
        return new SessionViewModel(courses, session.getSessionType());
    }

    public static TimetableViewModel timetableToView(TimetableResponseModel timetable){
        List<TimetableViewCourseModel> courses = new ArrayList<TimetableViewCourseModel>();
        for (CourseResponseModel course : timetable.getCourses() ){
            courses.add(courseToView(course));
        }

        return new TimetableViewModel(courses);
    }

    public static TimetableViewCourseModel courseToView(CourseResponseModel course){
        List<TimetableViewSectionModel> sections = new ArrayList<TimetableViewSectionModel>();
        for (SectionResponseModel section : course.getSections()){
            sections.add(sectionToView(section));
        }

        return new TimetableViewCourseModel(course.getCourseCode(), sections);
    }

    public static TimetableViewSectionModel sectionToView(SectionResponseModel section){
        List<TimetableViewBlockModel> blocks = new ArrayList<TimetableViewBlockModel>();
        for (BlockResponseModel block : section.getBlocks()){
            blocks.add(blockToView(block));
        }

        return new TimetableViewSectionModel(section.getCode(), blocks);
    }

    public static TimetableViewBlockModel blockToView(BlockResponseModel block){
        return new TimetableViewBlockModel(block.getDay(), block.getStartTime(), block.getEndTime());
    }
}
