package fileio_use_case.application_business.timetable_specific_classes;
import entities.InvalidSectionsException;
import fileio_use_case.application_business.FileImportRequestModel;
import org.json.simple.parser.ParseException;
import retrieve_timetable_use_case.application_business.TimetableModel;

import java.io.IOException;

public interface TimetableFileImportInputBoundary {
    /**
     * Given FileImportRequestModel, which holds a string of the JSON file path and
     * given a session type, return a TimetableModel with specified course type (Timetable or Calendar)
     * from the JSON file.
     * @param jsonData FileImportRequestModel, Course Type
     * @return TimetableModel
     */
     TimetableModel readFromFile(FileImportRequestModel jsonData, String courseType) throws IOException, ParseException, java.text.ParseException, InvalidSectionsException;

    /** Reads timetableRequestModel into a JSON file stored in
     * src/main/saved_timetables/.
     * @param timetableRequestModel - a request model for Timetable
     */
     void timetableToFile(TimetableModel timetableRequestModel, String sessionType) throws InvalidSectionsException;
}
