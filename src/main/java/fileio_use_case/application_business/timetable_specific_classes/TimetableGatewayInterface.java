package fileio_use_case.application_business.timetable_specific_classes;

import entities.InvalidSectionsException;
import entities.Timetable;

import java.io.IOException;

/** Interface for TimetableGateway */
public interface TimetableGatewayInterface {
    Timetable readFromFile(String filePath, String courseType) throws IOException, org.json.simple.parser.ParseException, InvalidSectionsException;
}
