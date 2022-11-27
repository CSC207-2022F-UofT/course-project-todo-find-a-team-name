package screens.feature_6_frameworks_drivers;

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
    private final String file;
    public SessionGateway(FileImportRequestModel file) {
        this.file = file.getFilePath();
    }
    /**
     * Returns a string representation of the JSON file it reads.
     * @return String
     */
    public String fileToString() throws IOException {
        // Files.readString(Path.of("src/main/java/screens/courses_cleaned.json"));
        return Files.readString(Path.of(this.file));
    }
    /**
     * Given a string representation of a JSON file, return a HashMap of all course info from the JSON file
     * where the key is the course code and value is course information.
     * @param jsonData JSON data
     * @return HashMap<String, CalendarCourse>
     */
    public HashMap<String, CalendarCourse> readFromFile(String jsonData) throws ParseException {
        HashMap<String, CalendarCourse> allCourses = new HashMap<>();
        ArrayList<Section> allSections = new ArrayList<>();
        ArrayList<Block> allBlocks = new ArrayList<>();
        // Parse String
        JSONParser parser = new JSONParser();
        JSONObject jsonObj = (JSONObject) parser.parse(jsonData);
        // Iterate through jsonObj
        for (Object key : jsonObj.keySet()) {
            JSONObject courseInfo = (JSONObject) jsonObj.get(key);
            // Iterate through sections
            JSONObject sections = (JSONObject) courseInfo.get("sections"); // Looks at value of "sections"
            for (Object section : sections.keySet()) {
                JSONObject aSection = (JSONObject) sections.get(section);
                // Iterate through blocks
                JSONObject blocks = (JSONObject) aSection.get("blocks"); // Looks at value of "blocks"
                for (Object block : blocks.keySet()) {
                    JSONObject aBlock = (JSONObject) blocks.get(block);
                    Block resultBlock = readFromFileHelper(aBlock);
                    if (resultBlock != null) {
                        allBlocks.add(resultBlock);
                    }
                }
                // Pass to SectionBuilderInteractor
                allSections.add(new SectionBuilderInteractor((String) aSection.get("section"),
                        (String) aSection.get("prof"), allBlocks).newSection());
            }
            // Pass to CourseBuilderInteractor and add to all courses
            CalendarCourse calCourse = new CalendarCourseBuilderInteractor((String) courseInfo.get("title"),
                    allSections, (String) courseInfo.get("session"),
                    (String) courseInfo.get("code"), (String) courseInfo.get("breadth")).newCourse();
            // Add course code as key and new Calendar Course as a value into allCourses
            allCourses.put((String) courseInfo.get("code"), calCourse);
        }
        return allCourses;
    }
    /**
     * Helper method of readFromFile to check for conditions of block's values and builds block
     */
    public Block readFromFileHelper(JSONObject aBlock) {
        if (!(aBlock.get("day") == null && aBlock.get("startTime") == null && aBlock.get("endTime") == null && aBlock.get("room") == null)) {
            if (aBlock.get("room") == null) {
                return new BlockBuilderInteractor((String) aBlock.get("day"), (String) aBlock.get("startTime"),
                        (String) aBlock.get("endTime"), "").newBlock();
            } else if (aBlock.get("day") == null) {
                return new BlockBuilderInteractor("", (String) aBlock.get("startTime"),
                        (String) aBlock.get("endTime"), (String) aBlock.get("room")).newBlock();
            } else if ((aBlock.get("startTime") == null)) {
                return new BlockBuilderInteractor((String) aBlock.get("day"), "",
                        (String) aBlock.get("endTime"), (String) aBlock.get("room")).newBlock();
            } else if (aBlock.get("endTime") == null) {
                return new BlockBuilderInteractor((String) aBlock.get("day"), (String) aBlock.get("startTime"),
                        "", (String) aBlock.get("room")).newBlock();
            } else {
                return new BlockBuilderInteractor((String) aBlock.get("day"), (String) aBlock.get("startTime"),
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
     * Returns a SessionStorerInteractor class of all sessions (Fall and Winter) based on given HashMap of String
     * to CalendarCourse.
     * Note: Use .getAllSessions() method in SessionStorerInteractor to get
     * all Sessions represented as HashMap<String, Session> where the key is the sessionType.
     *
     * @param allCourses HashMap<String, CalendarCourse>
     * @return HashMap<String, Session>
     */
    public SessionStorerInteractor creatingSessionsFromFile(HashMap<String, CalendarCourse> allCourses) {
        return new SessionBuilderInteractor().allSessionBuilder(allCourses);
    }
}