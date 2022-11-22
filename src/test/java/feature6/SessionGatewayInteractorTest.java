package feature6;

import entities.Block;
import entities.CalendarCourse;
import entities.Section;
import entities.Session;
import fileio_use_case.SessionGatewayInteractor;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SessionGatewayInteractorTest {
    @Test
    void testingReadFromFile() throws IOException, ParseException {
        SessionGatewayInteractor convertingFile = new SessionGatewayInteractor("src/main/java/screens/courses_cleaned.json");
        String jsonToStr = convertingFile.fileToString();
        HashMap<String, CalendarCourse> result = convertingFile.readFromFile(jsonToStr);
        // System.out.println(result);
    }
    @Test
    void checkingFormatAndValuesEquals() throws IOException, ParseException {
        SessionGatewayInteractor convertingFile1 = new SessionGatewayInteractor("src/test/java/feature6/testing.json");
        // Course from testing.json
        String jsonToStr1 = convertingFile1.fileToString();
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
