package fileio_use_case.application_business.session_specific_classes;

import entities.Block;
import entities.CalendarCourse;
import entities.Section;
import entities.Session;
import fileio_use_case.application_business.FileImportRequestModel;
import org.json.simple.parser.ParseException;
import fileio_use_case.frameworks_and_drivers.SessionGateway;
import retrieve_timetable_use_case.application_business.BlockModel;
import retrieve_timetable_use_case.application_business.CourseModel;
import retrieve_timetable_use_case.application_business.SectionModel;
import retrieve_timetable_use_case.application_business.SessionModel;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/** Interactor for SessionGateway **/
public class SessionGatewayInteractor implements SessionFileImportInputBoundary {
    private final SessionGateway sessionGateway;
    public SessionGatewayInteractor() {
        this.sessionGateway = new SessionGateway();
    }
    /**
     * Given FileImportRequestModel, which holds a string of the JSON file path and
     * given a session type, return a SessionModel with specified session type
     * from the JSON file where the key is the course code and value is course information.
     * @param jsonData FileImportRequestModel, Session Type
     * @return SessionModel
     */
    public SessionModel readFromFile(FileImportRequestModel jsonData, String sessionType) throws IOException, ParseException {
        String filePath = jsonData.getFilePath();
        Session aSession = this.sessionGateway.readFromFile(filePath, sessionType);
        HashMap<String, CalendarCourse> allSessionCourses = aSession.getAllSessionCourses();
        HashMap<String, CourseModel> returnAllSessionCoursesModel = new HashMap<>();
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
            returnAllSessionCoursesModel.put(course.getCourseCode(), aNewCourse);
        }
        return new SessionModel(returnAllSessionCoursesModel, sessionType);
    }
}
