package fileio_use_case.interface_adapters;

import fileio_use_case.application_business.FileImportRequestModel;
import fileio_use_case.application_business.session_specific_classes.SessionFileImportInputBoundary;
import org.json.simple.parser.ParseException;

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
     */
    public void createSessionFile(String filePath) throws IOException, ParseException {
        FileImportRequestModel requestModel = new FileImportRequestModel(filePath);
        this.interactor.readFromFile(requestModel, "F");
        this.interactor.readFromFile(requestModel, "S");
    }

}