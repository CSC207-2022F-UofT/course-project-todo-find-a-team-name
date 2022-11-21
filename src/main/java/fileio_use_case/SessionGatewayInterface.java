package fileio_use_case;

import entities.CalendarCourse;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public interface SessionGatewayInterface {
    /**
     * @param rawJsonData raw JSON values
     */
    Map<String, CalendarCourse> readFromFile(String rawJsonData) throws IOException, ParseException;
}
