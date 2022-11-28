package fileio_use_case;

import entities.CalendarCourse;
import org.json.simple.parser.ParseException;
import fileio_use_case.frameworks_and_drivers.SessionGateway;

import java.io.IOException;
import java.util.HashMap;

/** Interactor for SessionGateway **/
public class SessionGatewayInteractor implements FileImportInputBoundary {
    private final SessionGateway sessionGateway;
    public SessionGatewayInteractor() {
        this.sessionGateway = new SessionGateway();
    }
    /**
     * Returns a string representation of the JSON file it reads.
     * @return String
     */
    public String fileToString(FileImportRequestModel file) throws IOException {
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
}
