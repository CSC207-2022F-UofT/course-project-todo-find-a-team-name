package feature6;

import entities.Block;
import entities.CalendarCourse;
import entities.Section;
import entities.Session;
import fileio_use_case.FileImportRequestModel;
import fileio_use_case.SessionGatewayInteractor;
import fileio_use_case.SessionStorerInteractor;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SessionGatewayInteractorTest {
    @Test
    void creatingAllSessionsAndGetSessionType() throws ParseException, IOException {
        FileImportRequestModel filePath = new FileImportRequestModel("src/main/java/screens/courses_cleaned.json");
        SessionGatewayInteractor convertFile = new SessionGatewayInteractor();
        String jsonToStr = convertFile.fileToString(filePath);
        HashMap<String, CalendarCourse> result = convertFile.readFromFile(jsonToStr);

        SessionStorerInteractor allSessions = convertFile.creatingSessionsFromFile(result);
        assertTrue(allSessions.getAllSessions().containsKey("S"));
        assertTrue(allSessions.getAllSessions().containsKey("F"));

        // Get Winter (S) session
        Session Winter = allSessions.getSession("S");
        assertEquals("S", Winter.getSessionType());
    }
    @Test
    void checkingFormatAndValuesEquals() throws IOException, ParseException {
        FileImportRequestModel filePath1 = new FileImportRequestModel("src/test/java/feature6/testing.json");
        SessionGatewayInteractor convertingFile1 = new SessionGatewayInteractor();
        // Course from testing.json
        String jsonToStr1 = convertingFile1.fileToString(filePath1);
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
