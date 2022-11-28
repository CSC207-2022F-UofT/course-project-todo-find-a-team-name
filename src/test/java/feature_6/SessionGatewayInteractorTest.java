package feature_6;

import entities.Block;
import entities.CalendarCourse;
import entities.Section;
import entities.Session;
import fileio_use_case.fileImportRequestModel;
import fileio_use_case.sessionGatewayInteractor;
import fileio_use_case.sessionStorerInteractor;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SessionGatewayInteractorTest {
    /** Tests if sessions can be created given a JSON file */
    @Test
    void creatingAllSessionsAndGetSessionType() throws ParseException, IOException {
        fileImportRequestModel filePath = new fileImportRequestModel("src/main/java/screens/courses_cleaned.json");
        sessionGatewayInteractor convertFile = new sessionGatewayInteractor();
        String jsonToStr = convertFile.fileToString(filePath);
        HashMap<String, CalendarCourse> result = convertFile.readFromFile(jsonToStr);

        sessionStorerInteractor allSessions = convertFile.creatingSessionsFromFile(result);
        assertTrue(allSessions.getAllSessions().containsKey("S"));
        assertTrue(allSessions.getAllSessions().containsKey("F"));

        // Get Winter (S) session
        Session Winter = allSessions.getSession("S");
        assertEquals("S", Winter.getSessionType());
    }
    /** Checks if SessionGatewayInteractor when calling on SessionGateway can correctly parse the text in JSON file
     * into a Calendar Course with the right format and values.
     */
    @Test
    void checkingFormatAndValuesEquals() throws IOException, ParseException {
        fileImportRequestModel filePath1 = new fileImportRequestModel("src/test/java/feature_6/testing.json");
        sessionGatewayInteractor convertingFile1 = new sessionGatewayInteractor();
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