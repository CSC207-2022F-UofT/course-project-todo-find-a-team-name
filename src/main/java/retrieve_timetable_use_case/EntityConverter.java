package retrieve_timetable_use_case;

import entities.Block;
import entities.Course;
import entities.Section;
import entities.Session;

import java.util.ArrayList;
import java.util.HashMap;

public class EntityConverter {

    public static SessionResponseModel generateSessionResponse(Session session){
        HashMap<String, CourseResponseModel> courses = new HashMap<String, CourseResponseModel>();
        for (String courseCode : session.getAllSessionCourses().keySet()){
            courses.put(courseCode, generateCourseResponse(session.getAllSessionCourses().get(courseCode)));
        }
        return new SessionResponseModel(courses, session.getSessionType());
    }

    public static CourseResponseModel generateCourseResponse(Course course){
        ArrayList<SectionResponseModel> sections = new ArrayList<SectionResponseModel>();

        for (Section section : course.getSections()){
            sections.add(generateSectionResponse(section));
        }

        return new CourseResponseModel(course.getTitle(), sections, course.getCourseSession(), course.getCourseCode(),
                course.getBreadth());
    }

    public static SectionResponseModel generateSectionResponse(Section section){
        ArrayList<BlockResponseModel> blocks = new ArrayList<BlockResponseModel>();

        for (Block block : section.getBlocks()){
            blocks.add(generateBlockResponse(block));
        }

        return new SectionResponseModel(section.getCode(), section.getInstructorName(), blocks);
    }

    public static BlockResponseModel generateBlockResponse(Block block){
        return new BlockResponseModel(block.getDay(), block.getStartTime(), block.getEndTime(), block.getRoom());
    }

}
