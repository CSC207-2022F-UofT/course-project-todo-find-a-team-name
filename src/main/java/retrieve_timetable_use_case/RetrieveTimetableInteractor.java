package retrieve_timetable_use_case;

import entities.Block;
import entities.Course;
import entities.Section;

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
        return generateCourseResponse(session.getCourse(requestModel.getCourseCode()));
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

        return new SectionResponseModel();
    }
}
