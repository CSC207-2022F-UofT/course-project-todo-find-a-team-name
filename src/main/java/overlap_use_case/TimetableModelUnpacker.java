package overlap_use_case;

import entities.*;
import retrieve_timetable_use_case.*;

import java.util.ArrayList;
import java.util.List;

/** A class responsible for converting between Timetable Model classes (that is, including its internal
 * model classes) back into their entity forms. "Unpacks the model class" into the entity.
 */
public class TimetableModelUnpacker {

    /** Unpack a SessionModel into a Session object. **/
    public static Session unpackSessionModel(SessionModel sessionModel){

        Session session = new Session(sessionModel.getSessionType());

        for (CourseModel courseModel : sessionModel.getCourses().values()){
            session.addCourse(unpackCourseModelAsCalendarCourse(courseModel));
        }

        return session;
    }

    /** Unpack a TimetableModel into a Timetable object. **/
    public static Timetable unpackTimetable(TimetableModel timetable){
        ArrayList<TimetableCourse> courses = new ArrayList<>();
        for (CourseModel course : timetable.getCourses() ){
            courses.add(unpackCourseModelasTimetableCourse(course));
        }

        return new Timetable(courses, timetable.getSessionType());
    }

    /** Unpack a list of SectionModels into an ArrayList of Sections. **/
    private static ArrayList<Section> unpackSections(List<SectionModel> sectionModels){
        ArrayList<Section> sections = new ArrayList<>();
        for (SectionModel sectionModel : sectionModels){
            sections.add(unpackSectionModel(sectionModel));
        }
        return sections;
    }

    /** Unpack a CourseModel into a TimetableCourse.
    Since this class is specifically for unpacking Timetables, we make a TimetableCourse.
     */
    public static TimetableCourse unpackCourseModelasTimetableCourse(CourseModel course){

        ArrayList<Section> sections = unpackSections(course.getSections());

        try {
            return new TimetableCourse(course.getTitle(), sections, course.getCourseSession(),
                    course.getCourseCode(), course.getBreadth());
        } catch (InvalidSectionsException e){
            throw new IllegalArgumentException("You tried to unpack a CourseModel that has invalid sections!");
        }
    }

    /** Unpack a CourseModel into a CalendarCourse.
     Since this class is specifically for unpacking Timetables, we make a TimetableCourse.
     */
    public static CalendarCourse unpackCourseModelAsCalendarCourse(CourseModel course){

        ArrayList<Section> sections = unpackSections(course.getSections());

        return new CalendarCourse(course.getTitle(), sections, course.getCourseSession(),
                    course.getCourseCode(), course.getBreadth());

    }

    /** Unpack a SectionModel into a Section **/
    public static Section unpackSectionModel(SectionModel section){
        ArrayList<Block> blocks = new ArrayList<>();
        for(BlockModel blockModel : section.getBlocks()){
            blocks.add(unpackBlockModel(blockModel));
        }
        return new Section(section.getCode(), section.getInstructor(), blocks);
    }

    /** Unpack a BlockModel into a Section **/
    public static Block unpackBlockModel(BlockModel block){
        String blockDay;

        switch (block.getDay()) {
            case 0 -> blockDay = "MO";
            case 1 -> blockDay = "TU";
            case 2 -> blockDay = "WE";
            case 3 -> blockDay = "TH";
            case 4 -> blockDay = "FR";
            default ->
                    throw new IllegalArgumentException("We received a Section that seems to take place on a weekend or"
                            + " otherwise invalid day.");
        }

        return new Block(blockDay, reformatModelTime(block.getStartTime())
                , reformatModelTime(block.getEndTime()), block.getRoom());
    }

    /**  Helper to reformat a BlockModel time value to a String that fits into a Block's time field.
    E.g. 18.00 -> 18:00. 18.50 -> 18:30
     */
    private static String reformatModelTime(Double blockModelTime) {


        // Get the blockModelTime decimal part
        double blockModelTimeMinutes = blockModelTime - blockModelTime.intValue();

        // Convert the minutes to base 60
        blockModelTimeMinutes = blockModelTimeMinutes * 0.6;

        // Update the minute value
        blockModelTime = blockModelTime.intValue() + blockModelTimeMinutes;

        // Now format the string.
        String blockTime;
        blockTime = String.valueOf(blockModelTime);
        blockTime = blockTime.replace('.', ':');

        return blockTime;
    }
}

