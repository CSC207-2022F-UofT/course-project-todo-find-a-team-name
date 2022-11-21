package feature6;

import entities.CalendarCourse;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import screens.feature_6_frameworks_drivers.SessionGateway;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class SessionGatewayTest {
    @Test
    void testingReadFromFile() throws IOException, ParseException {
        SessionGateway convertingFile = new SessionGateway();
        String jsonToStr = convertingFile.fileToString("src/main/java/screens/courses_cleaned.json");
        HashMap<String, CalendarCourse> result = convertingFile.readFromFile(jsonToStr);
        System.out.println(result);
    }
}
