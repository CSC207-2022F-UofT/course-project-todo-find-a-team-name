package feature_6;

import entities.Block;
import entities.CalendarCourse;
import entities.Section;
import entities.Session;
import fileio_use_case.application_business.FileImportRequestModel;
import fileio_use_case.application_business.session_specific_classes.SessionGatewayInteractor;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SessionGatewayInteractorTest {
    /** Tests if sessions can be created given a JSON file */
    @Test
    void testingCreatingAllSessions() throws ParseException, IOException {
        FileImportRequestModel filePath = new FileImportRequestModel("src/main/resources/courses_cleaned.json");
        SessionGatewayInteractor convertFile = new SessionGatewayInteractor();
        Session result = convertFile.readFromFile(filePath, "S");

        Assertions.assertEquals(result.getSessionType(), "S");
    }
    /** Checks if SessionGatewayInteractor when calling on SessionGateway can correctly parse the text in JSON file
     * into a Calendar Course with the right format and values.
     */
    @Test
    void checkingFormatAndValuesEquals() throws IOException, ParseException {
        FileImportRequestModel filePath1 = new FileImportRequestModel("src/main/resources/testing.json");
        SessionGatewayInteractor convertingFile1 = new SessionGatewayInteractor();
        // Course from testing.json
        Session Winter = convertingFile1.readFromFile(filePath1, "S");
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
