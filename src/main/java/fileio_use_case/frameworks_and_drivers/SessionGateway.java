package fileio_use_case.frameworks_and_drivers;

import entities.*;
import fileio_use_case.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 *  The SessionGateway gets courses from a local JSON file and
 *  puts them into a session.
 */

public class SessionGateway implements GatewayInterface {
    public SessionGateway() {}
    /**
     * Given a string of the JSON file path, return a HashMap of all course info from the JSON file
     * where the key is the course code and value is course information.
     * @param filePath file path for JSON file
     * @return HashMap<String, CalendarCourse>
     */
    public HashMap<String, CalendarCourse> readFromFile(String filePath) throws IOException, ParseException {
        // Files.readString(Path.of("src/main/java/screens/courses_cleaned.json"));
        return parseString(Files.readString(Path.of(filePath)));
    }
    /**
     * HELPER METHOD
     */
    private HashMap<String, CalendarCourse> parseString(String jsonData) throws ParseException {
        HashMap<String, CalendarCourse> allCourses = new HashMap<>();
        // Parse String
        JSONParser parser = new JSONParser();
        JSONObject jsonObj = (JSONObject) parser.parse(jsonData);
        // Iterate through jsonObj
        for (Object key : jsonObj.keySet()) {
            JSONObject courseInfo = (JSONObject) jsonObj.get(key);
            // Iterate through sections
            JSONObject sections = (JSONObject) courseInfo.get("sections"); // Looks at value of "sections"
            ArrayList<Section> allSections = new ArrayList<>();
            for (Object section : sections.keySet()) {
                JSONObject aSection = (JSONObject) sections.get(section);
                // Iterate through blocks
                JSONObject blocks = (JSONObject) aSection.get("blocks"); // Looks at value of "blocks"
                ArrayList<Block> allBlocks = new ArrayList<>();
                for (Object block : blocks.keySet()) {
                    JSONObject aBlock = (JSONObject) blocks.get(block);
                    Block resultBlock = readFromFileHelper(aBlock);
                    if (resultBlock != null) {
                        allBlocks.add(resultBlock);
                    }
                }
                // Pass to SectionBuilder
                allSections.add(new SectionBuilder((String) aSection.get("section"),
                        (String) aSection.get("prof"), allBlocks).newSection());
            }
            // Pass to CourseBuilderInteractor and add to all courses
            CalendarCourse calCourse = new CalendarCourseBuilder((String) courseInfo.get("title"), allSections,
                    (String) courseInfo.get("session"), (String) courseInfo.get("code"),
                    (String) courseInfo.get("breadth")).newCourse();
            // Add course code as key and new Calendar Course as a value into allCourses
            allCourses.put((String) courseInfo.get("code"), calCourse);
        }
        return allCourses;
    }
    /**
     * Helper method of the helper method parseString to check for conditions of block's values and builds block
     */
    private Block readFromFileHelper(JSONObject aBlock) {
        if (!(aBlock.get("day") == null && aBlock.get("startTime") == null && aBlock.get("endTime") == null && aBlock.get("room") == null)) {
            if (aBlock.get("room") == null) {
                return new BlockBuilder((String) aBlock.get("day"), (String) aBlock.get("startTime"),
                        (String) aBlock.get("endTime"), "").newBlock();
            } else if (aBlock.get("day") == null) {
                return new BlockBuilder("", (String) aBlock.get("startTime"),
                        (String) aBlock.get("endTime"), (String) aBlock.get("room")).newBlock();
            } else if ((aBlock.get("startTime") == null)) {
                return new BlockBuilder((String) aBlock.get("day"), "",
                        (String) aBlock.get("endTime"), (String) aBlock.get("room")).newBlock();
            } else if (aBlock.get("endTime") == null) {
                return new BlockBuilder((String) aBlock.get("day"), (String) aBlock.get("startTime"),
                        "", (String) aBlock.get("room")).newBlock();
            } else {
                return new BlockBuilder((String) aBlock.get("day"), (String) aBlock.get("startTime"),
                        (String) aBlock.get("endTime"), (String) aBlock.get("room")).newBlock();
            }
        }
        return null;
    }
    /**
     * Returns a session if given the HashMap representation of all courses and sessionType
     * @param allCourses - contains all sessions, String - session type (Fall (F), Winter (S))
     * @return Session
     */
    public Session extractSession(HashMap<String, CalendarCourse> allCourses, String sessionType) {
        return new SessionBuilderInteractor().aSessionBuilder(allCourses, sessionType);
    }

    /**
     * Returns HashMap<String, Session> of all sessions (Fall and Winter) based on given HashMap of String
     * to CalendarCourse, where the key is the SessionType.
     * Note: Use .getAllSessions() method in SessionStorer to get
     * all Sessions represented as HashMap<String, Session> where the key is the sessionType.
     *
     * @param allCourses HashMap<String, CalendarCourse>
     * @return HashMap<String, Session>
     */
    public HashMap<String, Session> creatingSessionsFromFile(HashMap<String, CalendarCourse> allCourses) {
        return new SessionBuilderInteractor().allSessionBuilder(allCourses);
    }
}