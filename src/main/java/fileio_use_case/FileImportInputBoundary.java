package fileio_use_case;

import entities.CalendarCourse;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.HashMap;

public interface FileImportInputBoundary {
    /**
     * Given a string of the file path of a JSON file, return a HashMap of all course info from the JSON file
     * where the key is the course code and value is course information.
     * @param jsonData FileImportRequestModel storing JSON data file path.
     * @return HashMap<String, CalendarCourse>
     */
    HashMap<String, CalendarCourse> readFromFile(FileImportRequestModel jsonData) throws IOException, ParseException;
}
