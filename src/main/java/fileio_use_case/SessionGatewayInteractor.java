package fileio_use_case;

import entities.CalendarCourse;
import entities.Session;
import org.json.simple.parser.ParseException;
import screens.feature_6_frameworks_drivers.SessionGateway;

import java.io.IOException;
import java.util.HashMap;

/** Interactor for SessionGateway **/
public class SessionGatewayInteractor implements FileImportInputBoundary{
    private final SessionGateway sessionGateway;
    public SessionGatewayInteractor(FileImportRequestModel file) {
        this.sessionGateway = new SessionGateway(file);
    }
    /**
     * Returns a string representation of the JSON file it reads.
     * @return String
     */
    public String fileToString() throws IOException {
        return this.sessionGateway.fileToString();
    }
    /**
     * Given a string representation of a JSON file, return a HashMap of all course info from the JSON file
     * where the key is the course code and value is course information.
     * @param jsonData JSON data
     * @return HashMap<String, CalendarCourse>
     */
    public HashMap<String, CalendarCourse> readFromFile(String jsonData) throws ParseException {
        return this.sessionGateway.readFromFile(jsonData);
    }
    /**
     * Returns a session if given the HashMap representation of all courses and sessionType
     * @param allCourses - contains all sessions, String - session type (Fall (F), Winter (S))
     * @return Session
     */
    public Session extractSession(HashMap<String, CalendarCourse> allCourses, String sessionType) {
        return this.sessionGateway.extractSession(allCourses, sessionType);
    }

    /**
     * Returns a SessionStorerInteractor class of all sessions (Fall and Winter) based on given HashMap of String
     * to CalendarCourse.
     * Note: Use .getAllSessions() method in SessionStorerInteractor to get
     * all Sessions represented as HashMap<String, Session> where the key is the sessionType.
     *
     * @param allCourses HashMap<String, CalendarCourse>
     * @return HashMap<String, Session>
     */
    public SessionStorerInteractor creatingSessionsFromFile(HashMap<String, CalendarCourse> allCourses) {
        return this.sessionGateway.creatingSessionsFromFile(allCourses);
    }
}
