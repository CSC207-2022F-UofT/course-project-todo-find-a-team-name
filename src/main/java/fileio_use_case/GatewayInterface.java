package fileio_use_case;

import entities.CalendarCourse;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

public interface GatewayInterface {
    /**
     * Given a string of the file path of a JSON file, return a HashMap of all course info from the JSON file
     * where the key is the course code and value is course information.
     * @param filePath JSON data file path
     * @return HashMap<String, CalendarCourse>
     */
    HashMap<String, CalendarCourse> readFromFile(String filePath) throws IOException, ParseException, org.json.simple.parser.ParseException;
}
