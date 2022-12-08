package fileio_use_case.interface_adapters;

import entities.InvalidSectionsException;
import fileio_use_case.application_business.FileImportRequestModel;
import fileio_use_case.application_business.timetable_specific_classes.TimetableFileImportInputBoundary;
import org.json.simple.parser.ParseException;
import retrieve_timetable_use_case.application_business.TimetableModel;

import java.io.IOException;

/**
 * Controller that passes file path of JSON file to interactor to store and turn into Timetables
 * for the program.
 */

public class TimetableFileController {
    private final TimetableFileImportInputBoundary interactor;
    public TimetableFileController(TimetableFileImportInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Takes in file path of a Timetable, creates request model, and pass request model to the interactor
     * to create a Timetable
     * @param filePath - File path to JSON file for session
     */
    public void createTimetableFile(String filePath) throws IOException, ParseException, java.text.ParseException, InvalidSectionsException {
        FileImportRequestModel requestModel = new FileImportRequestModel(filePath);
        this.interactor.readFromFile(requestModel, "Timetable");
    }

    /**
     * Takes in TimetableModel to create Timetable file saved into src/main/saved_timetables/
     * @param model - a TimetableModel
     * @param sessionType - the session type of TimetableModel
     */
    public void saveTimetable(TimetableModel model, String sessionType) throws InvalidSectionsException {
        this.interactor.timetableToFile(model, sessionType);
    }
}
