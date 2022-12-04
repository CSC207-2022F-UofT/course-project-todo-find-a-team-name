package fileio_use_case.application_business.timetable_specific_classes;

import entities.InvalidSectionsException;
import entities.Timetable;

import java.io.IOException;
import java.text.ParseException;

/** Interface for TimetableGateway */
public interface TimetableGatewayInterface {
    Timetable readFromFile(String filePath, String sessionType) throws IOException, ParseException, org.json.simple.parser.ParseException, InvalidSectionsException;
}
