//package feature_6;
//
//import entities.InvalidSectionsException;
//import fileio_use_case.application_business.session_specific_classes.SessionGatewayInteractor;
//import fileio_use_case.frameworks_and_drivers.SessionGateway;
//import fileio_use_case.interface_adapters.SessionFileController;
//import org.json.simple.parser.ParseException;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import retrieve_timetable_use_case.application_business.SessionModel;
//
//import java.io.IOException;
//class SessionFileControllerTest {
//    /** Tests if sessions can be created given a JSON file */
//    @Test
//    void testingCreateSessionFile() throws ParseException, IOException, java.text.ParseException, InvalidSectionsException {
//        SessionGateway gateway = new SessionGateway();
//        SessionGatewayInteractor convertFile = new SessionGatewayInteractor(gateway);
//        SessionFileController controller = new SessionFileController(convertFile);
//        SessionModel sessionModel = controller.createSessionFile("src/main/resources/courses_cleaned.json", "S");
//        Assertions.assertEquals(sessionModel.getSessionType(), "S");
//    }
//}