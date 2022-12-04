package fileio_use_case.application_business.session_specific_classes;

import entities.*;
import fileio_use_case.application_business.FileImportRequestModel;
import org.json.simple.parser.ParseException;
import retrieve_timetable_use_case.BlockModel;
import retrieve_timetable_use_case.CourseModel;
import retrieve_timetable_use_case.SectionModel;
import retrieve_timetable_use_case.SessionModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/** Interactor for SessionGateway **/
public class SessionGatewayInteractor implements SessionFileImportInputBoundary {
    private final SessionGatewayInterface sessionGateway;
    public SessionGatewayInteractor(SessionGatewayInterface sessionGateway) {
        this.sessionGateway = sessionGateway;
    }
    /**
     * Given FileImportRequestModel, which holds a string of the JSON file path and
     * given a session type, return a SessionModel with specified session type
     * from the JSON file where the key is the course code and value is course information.
     * @param jsonData FileImportRequestModel, Session Type
     * @return SessionModel
     */
    public SessionModel readFromFile(FileImportRequestModel jsonData, String sessionType) throws IOException, ParseException, java.text.ParseException, InvalidSectionsException {
        String filePath = jsonData.getFilePath();
        Session aSession = this.sessionGateway.readFromFile(filePath, sessionType);
        HashMap<String, CalendarCourse> allSessionCourses = aSession.getAllSessionCourses();
        return createSessionModel(allSessionCourses, sessionType);
    }
    /** HELPER METHOD to create SessionModel **/
    private SessionModel createSessionModel(HashMap<String, CalendarCourse> allSessionCourses, String sessionType) {
        HashMap<String, CourseModel> allSessionCoursesModel = new HashMap<>();
        for (CalendarCourse course : allSessionCourses.values()) {
            List<SectionModel> sectionList = new ArrayList<>();
            for (Section section : course.getSections()) {
                List<BlockModel> blockList = new ArrayList<>();
                for (Block block : section.getBlocks()) {
                    BlockModel aNewBlock = new BlockModel(block.getDay(), block.getStartTime(),
                            block.getEndTime(), block.getRoom());
                    blockList.add(aNewBlock);
                }
                SectionModel aNewSection = new SectionModel(section.getCode(), section.getInstructorName(), blockList);
                sectionList.add(aNewSection);
            }
            CourseModel aNewCourse = new CourseModel(course.getTitle(), sectionList,
                    course.getCourseSession(), course.getCourseCode(), course.getBreadth());
            allSessionCoursesModel.put(course.getCourseCode(), aNewCourse);
        }
        return new SessionModel(allSessionCoursesModel, sessionType);
    }
}
