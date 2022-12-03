package overlap_use_case;

import entities.*;
import retrieve_timetable_use_case.*;

import java.util.ArrayList;

/** A class responsible for converting between Timetable Model classes (that is, including its internal
 * model classes) back into their entity forms. "Unpacks the model class" into the entity.
 */
public class TimetableModelUnpacker {

    public static Session unpackSessionModel(SessionModel sessionModel){

        Session session = new Session(sessionModel.getSessionType());

        for (CourseModel courseModel : sessionModel.getCourses().values()){
            session.addCourse(unpackCourseModelAsCalendarCourse(courseModel));
        }

        return session;
    }

    public static Timetable unpackTimeTable(TimetableModel timetable){
        ArrayList<TimetableCourse> courses = new ArrayList<>();
        for (CourseModel course : timetable.getCourses() ){
            courses.add(unpackCourseModelasTimetableCourse(course));
        }

        return new Timetable(courses, timetable.getSessionType());
    }

    // Since this class is specifically for unpacking Timetables, we make a TimetableCourse.
    public static TimetableCourse unpackCourseModelasTimetableCourse(CourseModel course){

        ArrayList<Section> sections = new ArrayList<>();
        for (SectionModel sectionModel : course.getSections()){
            sections.add(unpackSectionModel(sectionModel));
        }

        try {
            return new TimetableCourse(course.getTitle(), sections, course.getCourseSession(),
                    course.getCourseCode(), course.getBreadth());
        } catch (InvalidSectionsException e){
            throw new IllegalArgumentException("You tried to unpack a CourseModel that has invalid sections!");
        }
    }

    // FIXME: I think this might be a code smell. Two nearly identical methods with one slightly changed constructor call.
    public static CalendarCourse unpackCourseModelAsCalendarCourse(CourseModel course){

        ArrayList<Section> sections = new ArrayList<>();
        for (SectionModel sectionModel : course.getSections()){
            sections.add(unpackSectionModel(sectionModel));
        }
            return new CalendarCourse(course.getTitle(), sections, course.getCourseSession(),
                    course.getCourseCode(), course.getBreadth());

    }


    public static Section unpackSectionModel(SectionModel section){
        ArrayList<Block> blocks = new ArrayList<>();
        for(BlockModel blockModel : section.getBlocks()){
            blocks.add(unpackBlockModel(blockModel));
        }
        return new Section(section.getCode(), section.getInstructor(), blocks);
    }

    public static Block unpackBlockModel(BlockModel block){
        String blockDay = switch (block.getDay()) {
            case 0 -> "MO";
            case 1 -> "TU";
            case 2 -> "WE";
            case 3 -> "TH";
            case 4 -> "FR";
            default ->
                    throw new IllegalArgumentException("We received a Section that seems to take place on a weekend or"
                            + " otherwise invalid day.");
        };

        return new Block(blockDay, reformatModelTime(block.getStartTime())
                , reformatModelTime(block.getEndTime()), block.getRoom());
    }

    /*  Helper to reformat a BlockModel time value to a String that fits into a Block's time field.
    E.g. 18.00 -> 18:00
     */
    private static String reformatModelTime(Double blockModelTime) {
        String blockTime;
        blockTime = String.valueOf(blockModelTime);
        blockTime = blockTime.replace('.', ':');
        return blockTime;
    }
}

