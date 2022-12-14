package fileio_use_case.application_business.session_specific_classes;

import entities.InvalidSectionsException;
import fileio_use_case.application_business.FileImportRequestModel;
import org.json.simple.parser.ParseException;
import retrieve_timetable_use_case.application_business.SessionModel;

import java.io.IOException;

public interface SessionFileImportInputBoundary {
    /**
     * Given FileImportRequestModel, which holds a string of the JSON file path and
     * given a session type, return a Session with specified session type from the JSON file.
     * @param jsonData FileImportRequestModel, Session Type
     * @return Session
     */
    SessionModel readFromFile(FileImportRequestModel jsonData, String sessionType) throws IOException, ParseException, java.text.ParseException, InvalidSectionsException;
}
