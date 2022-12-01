package fileio_use_case.application_business.session_specific_classes;

import entities.Session;
import fileio_use_case.application_business.FileImportRequestModel;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public interface SessionFileImportInputBoundary {
    /**
     * Given FileImportRequestModel, which holds a string of the JSON file path and
     * given a session type, return a Session with specified session type
     * from the JSON file where the key is the course code and value is course information.
     * @param jsonData FileImportRequestModel, Session Type
     * @return Session
     */
    Session readFromFile(FileImportRequestModel jsonData, String sessionType) throws IOException, ParseException;
}
