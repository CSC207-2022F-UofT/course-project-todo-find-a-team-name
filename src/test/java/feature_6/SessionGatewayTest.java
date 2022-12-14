package feature_6;

import entities.*;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import fileio_use_case.frameworks_and_drivers.SessionGateway;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class SessionGatewayTest {
    /** Checks extract Session Method with empty and non-empty hashmap **/
    @Test
    void checkExtractSession() throws IOException, ParseException, InvalidSectionsException {
        SessionGateway convertingFile3 = new SessionGateway();
        Session fallSession = convertingFile3.readFromFile("src/main/resources/test_session_data.json", "F");
        assertEquals(fallSession.getAllSessionCourses().size(), 9);
    }

    /** Checks if SessionGateway can correctly parse the text in JSON file
     * into a Calendar Course with the right format and values.
     */
    @Test
    void checkingFormatAndValuesEquals() throws IOException, ParseException, InvalidSectionsException {
        SessionGateway convertingFile1 = new SessionGateway();
        // Course from testing.json
        Session winter = convertingFile1.readFromFile("src/main/resources/testing.json", "S");
        CalendarCourse wantedCourse = winter.getCalendarCourse("IFP040H1");

        // Building Course Manually
        ArrayList<Block> allBlocks = new ArrayList<>();
        allBlocks.add(new Block("MO", "17:00", "20:00", ""));
        Section section = new Section("LEC-5101", "", allBlocks);
        CalendarCourse c1 = new CalendarCourse("Applied Concepts in Economics", new ArrayList<>(List.of(section)),
                "S", "IFP040H1", "5");

        System.out.println(c1);
        System.out.println(wantedCourse);
        assertEquals(c1, wantedCourse);
    }

}
