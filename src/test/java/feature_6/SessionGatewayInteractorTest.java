package feature_6;

import entities.InvalidSectionsException;
import fileio_use_case.application_business.FileImportRequestModel;
import fileio_use_case.application_business.session_specific_classes.SessionGatewayInteractor;
import fileio_use_case.frameworks_and_drivers.SessionGateway;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import retrieve_timetable_use_case.application_business.SessionModel;

import java.io.IOException;
class SessionGatewayInteractorTest {
    /** Tests if sessions can be created given a JSON file */
    @Test
    void testingCreatingAllSessions() throws ParseException, IOException, java.text.ParseException, InvalidSectionsException {
        FileImportRequestModel filePath = new FileImportRequestModel("src/main/resources/courses_cleaned.json");
        SessionGateway gateway = new SessionGateway();
        SessionGatewayInteractor convertFile = new SessionGatewayInteractor(gateway);
        SessionModel result = convertFile.readFromFile(filePath, "S");
        Assertions.assertEquals(result.getSessionType(), "S");
    }
}
