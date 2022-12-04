package timetable_generator_use_case.application_business;

import entities.*;
import retrieve_timetable_use_case.BlockModel;
import retrieve_timetable_use_case.CourseModel;
import retrieve_timetable_use_case.SectionModel;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a Helper class for the TimetableGeneratorInteractor.
 * Used to convert a courseModel CalendarCourse to TimetableCourse and vice versa.
 */
public class CourseConverter {
    public CalendarCourse courseModelToCalendarCourse(CourseModel course) {
        List<Section> allSections= new ArrayList<>();
        for (SectionModel section : course.getSections()) {
            List<Block> allBlocks= new ArrayList<>();
            for (BlockModel block : section.getBlocks()){
                Block newBlock = new Block(String.valueOf(block.getDay()), String.valueOf((block.getStartTime())),
                        String.valueOf(block.getEndTime()), String.valueOf(block.getRoom()));
                allBlocks.add(newBlock);
            }
            Section sections = new Section(section.getCode(), section.getInstructor(), allBlocks);
            allSections.add(sections);
        }
        return new CalendarCourse(course.getTitle(),
                allSections, course.getCourseSession(), course.getCourseCode(),
                course.getBreadth());
    }
    public TimetableCourse courseModelToTimetableCourse(CourseModel course) throws InvalidSectionsException {
        List<Section> allSections= new ArrayList<>();
        for (SectionModel section : course.getSections()) {
            List<Block> allBlocks= new ArrayList<>();
            for (BlockModel block : section.getBlocks()){
                Block newBlock = new Block(String.valueOf(block.getDay()), String.valueOf((block.getStartTime())),
                        String.valueOf(block.getEndTime()), String.valueOf(block.getRoom()));
                allBlocks.add(newBlock);
            }
            Section sections = new Section(section.getCode(), section.getInstructor(), allBlocks);
            allSections.add(sections);
        }
        return new TimetableCourse(course.getTitle(),
                allSections, course.getCourseSession(), course.getCourseCode(),
                course.getBreadth());
    }

    public CourseModel timetableCourseToCourseModel(TimetableCourse course) {
        List<SectionModel> allSections = new ArrayList<>();
        for (Section section : course.getSections()) {
            List<BlockModel> allBlocks= new ArrayList<>();
            for (Block block : section.getBlocks()){
                BlockModel newBlock = new BlockModel(block.getDay(), block.getStartTime(),
                        block.getEndTime(), block.getRoom());
                allBlocks.add(newBlock);
            }
            SectionModel aSection = new SectionModel(section.getCode(), section.getInstructorName(), allBlocks);
            allSections.add(aSection);
        }
        return new CourseModel(course.getTitle(),
                allSections, course.getCourseSession(), course.getCourseCode(),
                course.getBreadth());
    }
}
