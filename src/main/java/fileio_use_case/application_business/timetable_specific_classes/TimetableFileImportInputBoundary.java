package fileio_use_case.application_business.timetable_specific_classes;
import entities.InvalidSectionsException;
import fileio_use_case.application_business.FileImportRequestModel;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public interface TimetableFileImportInputBoundary {
    /**
     * Given FileImportRequestModel, which holds a string of the JSON file path and
     * given a session type, return a TimetableModel with specified course type (Timetable or Calendar)
     * from the JSON file.
     *
     * @param jsonData FileImportRequestModel, Course Type
     */
     void readFromFile(FileImportRequestModel jsonData, String courseType) throws IOException, ParseException, InvalidSectionsException;
}
