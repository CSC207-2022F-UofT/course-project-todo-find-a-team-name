package retrieve_timetable_use_case.application_business;

import entities.Block;
import entities.Course;
import entities.Section;
import entities.Session;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A class of static methods to be freely used by different interactors to convert from timetable
 * and session entities to their corresponding model.
 */
public class EntityConverter {

    public static SessionModel generateSessionResponse(Session session){
        HashMap<String, CourseModel> courses = new HashMap<>();
        for (String courseCode : session.getAllSessionCourses().keySet()){
            courses.put(courseCode, generateCourseResponse(session.getAllSessionCourses().get(courseCode)));
        }
        return new SessionModel(courses, session.getSessionType());
    }

    public static CourseModel generateCourseResponse(Course course){
        ArrayList<SectionModel> sections = new ArrayList<>();

        for (Section section : course.getSections()){
            sections.add(generateSectionResponse(section));
        }

        return new CourseModel(course.getTitle(), sections, course.getCourseSession(), course.getCourseCode(),
                course.getBreadth());
    }

    public static SectionModel generateSectionResponse(Section section){
        ArrayList<BlockModel> blocks = new ArrayList<>();

        for (Block block : section.getBlocks()){
            blocks.add(generateBlockResponse(block));
        }

        return new SectionModel(section.getCode(), section.getInstructorName(), blocks);
    }

    public static BlockModel generateBlockResponse(Block block){
        return new BlockModel(block.getDay(), block.getStartTime(), block.getEndTime(), block.getRoom());
    }
}
