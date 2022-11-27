package feature6;

import entities.Block;
import entities.CalendarCourse;
import entities.Section;
import entities.Session;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import screens.feature_6_frameworks_drivers.SessionGateway;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class SessionGatewayTest {
    @Test
    void checkingFormatAndValuesEquals() throws IOException, ParseException {
        SessionGateway convertingFile1 = new SessionGateway();
        // Course from testing.json
        String jsonToStr1 = convertingFile1.fileToString("src/test/java/feature6/testing.json");
        HashMap<String, CalendarCourse> result1 = convertingFile1.readFromFile(jsonToStr1);
        Session Winter = convertingFile1.extractSession(result1, "S");
        CalendarCourse wantedCourse = Winter.getCalendarCourse("IFP040H1");

        // Building Course Manually
        ArrayList<Block> allBlocks = new ArrayList<>();
        allBlocks.add(new Block("MO", "17:00", "20:00", ""));
        Section section = new Section("LEC-5101", "", allBlocks);
        CalendarCourse c1 = new CalendarCourse("Applied Concepts in Economics", new ArrayList<>(List.of(section)),
                "S", "IFP040H1", "5");

        System.out.println(c1);
        System.out.println(wantedCourse);
        assertTrue(c1.equals(wantedCourse));
    }

}
