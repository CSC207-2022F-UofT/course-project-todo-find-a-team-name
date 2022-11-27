package fileio_use_case;

import entities.CalendarCourse;
import entities.Session;
import org.json.simple.parser.ParseException;
import screens.feature_6_frameworks_drivers.sessionGateway;

import java.io.IOException;
import java.util.HashMap;

/** Interactor for sessionGateway **/
public class sessionGatewayInteractor implements fileImportInputBoundary {
    private final screens.feature_6_frameworks_drivers.sessionGateway sessionGateway;
    public sessionGatewayInteractor() {
        this.sessionGateway = new sessionGateway();
    }
    /**
     * Returns a string representation of the JSON file it reads.
     * @return String
     */
    public String fileToString(fileImportRequestModel file) throws IOException {
        String filePath = file.getFilePath();
        return this.sessionGateway.fileToString(filePath);
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
     * Returns a sessionStorerInteractor class of all sessions (Fall and Winter) based on given HashMap of String
     * to CalendarCourse.
     * Note: Use .getAllSessions() method in sessionStorerInteractor to get
     * all Sessions represented as HashMap<String, Session> where the key is the sessionType.
     *
     * @param allCourses HashMap<String, CalendarCourse>
     * @return HashMap<String, Session>
     */
    public sessionStorerInteractor creatingSessionsFromFile(HashMap<String, CalendarCourse> allCourses) {
        return this.sessionGateway.creatingSessionsFromFile(allCourses);
    }
}
