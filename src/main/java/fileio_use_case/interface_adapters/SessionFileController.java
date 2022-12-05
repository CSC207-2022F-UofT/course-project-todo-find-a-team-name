package fileio_use_case.interface_adapters;

import entities.InvalidSectionsException;
import fileio_use_case.application_business.FileImportRequestModel;
import fileio_use_case.application_business.session_specific_classes.SessionFileImportInputBoundary;
import org.json.simple.parser.ParseException;
import retrieve_timetable_use_case.application_business.SessionModel;

import java.io.IOException;

/**
 * Controller that passes file path of JSON file to interactor to store and turn into sessions
 * for the program.
 */

public class SessionFileController {
    private final SessionFileImportInputBoundary interactor;
    public SessionFileController(SessionFileImportInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Takes in file path of Session, creates request model, and pass request model to the interactor
     * to create fall and winter session files.
     * REPRESENTATION INVARIANT: file contains both fall and winter courses
     * @param filePath - File path to JSON file for session
     * @return SessionModel
     */
    public SessionModel createSessionFile(String filePath, String sessionType) throws IOException, ParseException, java.text.ParseException, InvalidSectionsException {
        FileImportRequestModel requestModel = new FileImportRequestModel(filePath);
        return this.interactor.readFromFile(requestModel, sessionType);
    }

}
