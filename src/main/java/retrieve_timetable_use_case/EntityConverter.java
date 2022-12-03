package retrieve_timetable_use_case;

import entities.Block;
import entities.Course;
import entities.Section;
import entities.Session;

import java.util.ArrayList;
import java.util.HashMap;


public class EntityConverter {

    public static SessionModel generateSessionResponse(Session session){
        HashMap<String, CourseModel> courses = new HashMap<String, CourseModel>();
        for (String courseCode : session.getAllSessionCourses().keySet()){
            courses.put(courseCode, generateCourseResponse(session.getAllSessionCourses().get(courseCode)));
        }
        return new SessionModel(courses, session.getSessionType());
    }

    public static CourseModel generateCourseResponse(Course course){
        ArrayList<SectionModel> sections = new ArrayList<SectionModel>();

        for (Section section : course.getSections()){
            sections.add(generateSectionResponse(section));
        }

        return new CourseModel(course.getTitle(), sections, course.getCourseSession(), course.getCourseCode(),
                course.getBreadth());
    }

    public static SectionModel generateSectionResponse(Section section){
        ArrayList<BlockModel> blocks = new ArrayList<BlockModel>();

        for (Block block : section.getBlocks()){
            blocks.add(generateBlockResponse(block));
        }

        return new SectionModel(section.getCode(), section.getInstructorName(), blocks);
    }

    public static BlockModel generateBlockResponse(Block block){
        return new BlockModel(block.getDay(), block.getStartTime(), block.getEndTime(), block.getRoom());
    }

}
