package fileio_use_case.application_business.session_specific_classes;

import entities.InvalidSectionsException;
import entities.Session;

import java.io.IOException;
import java.text.ParseException;

public interface SessionGatewayInterface {
    /**
     * Given a string of the JSON file path and session type, return a session with specified session type
     * from the JSON file where the key is the course code and value is course information.
     * @param filePath file path for JSON file, Session Type
     * @return Session
     */
    Session readFromFile(String filePath, String sessionType) throws IOException, ParseException, org.json.simple.parser.ParseException, InvalidSectionsException;
}
