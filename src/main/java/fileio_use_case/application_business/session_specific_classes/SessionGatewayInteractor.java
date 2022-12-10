package fileio_use_case.application_business.session_specific_classes;

import entities.*;
import fileio_use_case.application_business.FileImportRequestModel;
import org.json.simple.parser.ParseException;
import retrieve_timetable_use_case.application_business.BlockModel;
import retrieve_timetable_use_case.application_business.CourseModel;
import retrieve_timetable_use_case.application_business.SectionModel;
import retrieve_timetable_use_case.application_business.SessionModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Flow;

/** Interactor for SessionGateway **/
public class SessionGatewayInteractor implements SessionFileImportInputBoundary, Flow.Publisher<Object> {
    final ArrayList<Flow.Subscriber<Object>> receivers;
    private final SessionGatewayInterface sessionGateway;
    public SessionGatewayInteractor(SessionGatewayInterface sessionGateway) {
        this.sessionGateway = sessionGateway;
        this.receivers = new ArrayList<>();
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
        for (Flow.Subscriber<Object> subscriber : receivers){
            subscriber.onNext(aSession); // Things you want to pass. Here it's a session object.
        }
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
    /**
     * Add subscribers/observers to this class
     * @param subscriber - a subscriber
     * */
    @Override
    public void subscribe(Flow.Subscriber<? super Object> subscriber) {
        receivers.add(subscriber); // Adds subscribe to list of subscribers
    }
}
