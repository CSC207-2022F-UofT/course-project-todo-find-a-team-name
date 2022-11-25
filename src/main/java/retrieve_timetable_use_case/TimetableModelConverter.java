package retrieve_timetable_use_case;

import screens.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TimetableModelConverter {

    public static SessionViewModel sessionToView(SessionModel session){
        HashMap<String, TimetableViewCourseModel> courses = new HashMap<String, TimetableViewCourseModel>();
        for (String courseCode : session.getCourses().keySet()){
            courses.put(courseCode, courseToView(session.getCourses().get(courseCode)));
        }
        return new SessionViewModel(courses, session.getSessionType());
    }

    public static TimetableViewModel timetableToView(TimetableModel timetable){
        List<TimetableViewCourseModel> courses = new ArrayList<TimetableViewCourseModel>();
        for (CourseModel course : timetable.getCourses() ){
            courses.add(courseToView(course));
        }

        return new TimetableViewModel(courses);
    }

    public static TimetableViewCourseModel courseToView(CourseModel course){
        List<TimetableViewSectionModel> sections = new ArrayList<TimetableViewSectionModel>();
        for (SectionModel section : course.getSections()){
            sections.add(sectionToView(section));
        }

        return new TimetableViewCourseModel(course.getCourseCode(), sections);
    }

    public static TimetableViewSectionModel sectionToView(SectionModel section){
        List<TimetableViewBlockModel> blocks = new ArrayList<TimetableViewBlockModel>();
        for (BlockModel block : section.getBlocks()){
            blocks.add(blockToView(block));
        }

        return new TimetableViewSectionModel(section.getCode(), blocks);
    }

    public static TimetableViewBlockModel blockToView(BlockModel block){
        return new TimetableViewBlockModel(block.getDay(), block.getStartTime(), block.getEndTime(), block.getRoom());
    }
}
