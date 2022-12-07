package fileio_use_case.frameworks_and_drivers;

import entities.*;
import fileio_use_case.application_business.*;
import fileio_use_case.application_business.session_specific_classes.SessionBuilder;
import fileio_use_case.application_business.session_specific_classes.SessionGatewayInterface;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 *  The SessionGateway gets courses from a local JSON file and
 *  puts them into a session.
 */

public class SessionGateway implements SessionGatewayInterface {
    public SessionGateway() {
    }
    /**
     * Given a string of the JSON file path and session type, return a Session with specified session type
     * from the JSON file where the key is the course code and value is course information.
     * @param filePath file path for JSON file, Session Type
     * @return Session
     */
    public Session readFromFile(String filePath, String sessionType) throws IOException, ParseException, InvalidSectionsException {
        // Files.readString(Path.of("src/main/java/screens/courses_cleaned.json"));
        ProgramFileReader aFileReader = new ProgramFileReader();
        aFileReader.parseString(Files.readString(Path.of(filePath)), "Calendar");
        HashMap<String, CalendarCourse> calendarCourseHashMap = aFileReader.returnCalendarCourseHashMap();
        return extractSession(calendarCourseHashMap, sessionType);
    }
    /**
     * HELPER METHOD
     */
    private Session extractSession(HashMap<String, CalendarCourse> allCourses, String sessionType) {
        return new SessionBuilder().aSessionBuilder(allCourses, sessionType);
    }
//    /**
//     * Returns HashMap<String, Session> of all sessions (Fall and Winter) based on given HashMap of String
//     * to CalendarCourse, where the key is the SessionType.
//     * Note: Use .getAllSessions() method in SessionStorer to get
//     * all Sessions represented as HashMap<String, Session> where the key is the sessionType.
//     *
//     * @param allCourses HashMap<String, CalendarCourse>
//     * @return HashMap<String, Session>
//     */
//    public HashMap<String, Session> creatingSessionsFromFile(HashMap<String, CalendarCourse> allCourses)
//        return new SessionBuilder().allSessionBuilder(allCourses);
}