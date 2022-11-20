package retrieve_timetable_use_case;

import entities.Block;
import entities.Course;
import entities.Section;
import entities.Session;
import entities.Timetable;

import java.util.ArrayList;

public class RetrieveTimetableInteractor implements RetrieveTimetableInputBoundary {

    private Timetable timetable;
    private Session session;

    public RetrieveTimetableInteractor(Timetable timetable, Session session){
        this.timetable = timetable;
        this.session = session;
    }

    /**
     * @param requestModel
     * @return
     */

    /**
     * @param requestModel
     * @return
     */
    @Override
    public CourseResponseModel retrieveTimetableCourse(RetrieveTimetableRequestModel requestModel) {
        return generateCourseResponse(timetable.getCourse(requestModel.getCourseCode()));
    }

    /**
     * @param requestModel
     * @return
     */
    @Override
    public CourseResponseModel retrieveCalendarCourse(RetrieveTimetableRequestModel requestModel) {
        return generateCourseResponse(session.getCalendarCourse(requestModel.getCourseCode()));
    }

    @Override
    public SessionResponseModel retrieveSession(){
        return new SessionResponseModel(session.getAllSessionCourses(), session.getSessionType());
    }

    @Override
    public TimetableResponseModel retrieveTimetable(){
        return new TimetableResponseModel(timetable.getCourse);
    }

    private CourseResponseModel generateCourseResponse(Course course){
        ArrayList<SectionResponseModel> sections = new ArrayList<SectionResponseModel>();

        for (Section section : course.getSections()){
            sections.add(generateSectionResponse(section));
        }

        return new CourseResponseModel(course.getTitle(), sections, course.getCourseSession(), course.getCourseCode(),
                course.getBreadth());
    }

    private SectionResponseModel generateSectionResponse(Section section){
        ArrayList<BlockResponseModel> blocks = new ArrayList<BlockResponseModel>();

        for (Block block : section.getBlocks()){
            blocks.add(generateBlockResponse(block));
        }

        return new SectionResponseModel(section.getCode(), section.getInstructorName(), blocks);
    }

    private BlockResponseModel generateBlockResponse(Block block){
        return new BlockResponseModel(block.getDay(), block.getStartTime(), block.getEndTime(), block.getRoom());
    }
}
