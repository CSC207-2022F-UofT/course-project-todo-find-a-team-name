package fileio_use_case;

import entities.Session;
import org.json.simple.parser.ParseException;
import fileio_use_case.frameworks_and_drivers.SessionGateway;

import java.io.IOException;

/** Interactor for SessionGateway **/
public class SessionGatewayInteractor implements FileImportInputBoundary {
    private final SessionGateway sessionGateway;
    public SessionGatewayInteractor() {
        this.sessionGateway = new SessionGateway();
    }
    /**
     * Given FileImportRequestModel, which holds a string of the JSON file path and
     * given a session type, return a Session with specified session type
     * from the JSON file where the key is the course code and value is course information.
     * @param jsonData FileImportRequestModel, Session Type
     * @return Session
     */
    public Session readFromFile(FileImportRequestModel jsonData, String sessionType) throws IOException, ParseException {
        String filePath = jsonData.getFilePath();
        return this.sessionGateway.readFromFile(filePath, sessionType);
    }
//    /**
//     * Returns a HashMap<String, Session> class of all sessions (Fall and Winter) based on given HashMap of String
//     * to CalendarCourse.
//     * Note: Use .getAllSessions() method in SessionStorer to get
//     * all Sessions represented as HashMap<String, Session> where the key is the sessionType.
//     *
//     * @param allCourses HashMap<String, CalendarCourse>
//     * @return HashMap<String, Session>
//     */
//    public HashMap<String, Session> creatingSessionsFromFile(HashMap<String, CalendarCourse> allCourses)
//        return this.sessionGateway.creatingSessionsFromFile(allCourses);
}
