package feature_6;

import entities.Block;
import entities.CalendarCourse;
import entities.Section;
import entities.Session;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import fileio_use_case.frameworks_and_drivers.SessionGateway;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class SessionGatewayTest {
    /** Checks extract Session Method with empty and non-empty hashmap **/
    @Test
    void checkExtractSession() throws IOException, ParseException {
        // Empty Hashmap
        SessionGateway convertingFile2 = new SessionGateway();
        HashMap<String, CalendarCourse> empty= new HashMap<>();
        Session emptySession = convertingFile2.extractSession(empty, "F");
        assertEquals(emptySession.getSessionType(), "F");
        assertEquals((emptySession.getAllSessionCourses()).size(), 0);

        // Hashmap
        SessionGateway convertingFile3 = new SessionGateway();
        String jsonToStr1 = convertingFile3.fileToString("src/main/resources/test_session_data.json");
        HashMap<String, CalendarCourse> result1 = convertingFile3.readFromFile(jsonToStr1);
        Session fallSession = convertingFile3.extractSession(result1, "F");
        assertEquals(fallSession.getAllSessionCourses().size(), 8);
    }

    /** Checks if SessionGateway can correctly parse the text in JSON file
     * into a Calendar Course with the right format and values.
     */
    @Test
    void checkingFormatAndValuesEquals() throws IOException, ParseException {
        SessionGateway convertingFile1 = new SessionGateway();
        // Course from testing.json
        String jsonToStr1 = convertingFile1.fileToString("src/main/resources/testing.json");
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