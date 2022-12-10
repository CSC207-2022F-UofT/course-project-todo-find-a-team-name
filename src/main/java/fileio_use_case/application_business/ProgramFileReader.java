package fileio_use_case.application_business;

import entities.*;
import fileio_use_case.application_business.session_specific_classes.CalendarCourseBuilder;
import fileio_use_case.application_business.timetable_specific_classes.TimetableCourseBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.HashMap;

/** Class that reads files and converts it to either TimetableCourse or CalendarCourse and returns it*/
public class ProgramFileReader {
    final HashMap<String, CalendarCourse> allCalendarCourses;
    final HashMap<String, TimetableCourse> allTimetableCourses;
    String sessionType;
    public ProgramFileReader() {
        this.allCalendarCourses = new HashMap<>();
        this.allTimetableCourses = new HashMap<>();
        this.sessionType = "";
    }
    public void parseString(String jsonData, String type) throws ParseException, InvalidSectionsException {
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
            // Add course code as key and new Course as a value into allCourses
            if (type.equals("Calendar")) {
                CalendarCourse calCourse = new CalendarCourseBuilder((String) courseInfo.get("title"), allSections,
                        (String) courseInfo.get("session"), (String) courseInfo.get("code"),
                        (String) courseInfo.get("breadth")).newCourse();
                allCalendarCourses.put((String) courseInfo.get("code"), calCourse);
            }
            else {
                TimetableCourse timetableCourse = new TimetableCourseBuilder((String) courseInfo.get("title"), allSections,
                        (String) courseInfo.get("session"), (String) courseInfo.get("code"),
                        (String) courseInfo.get("breadth")).newCourse();
                allTimetableCourses.put((String) courseInfo.get("code"), timetableCourse);
            }
        }
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
    /** Returns HashMap of String to CalendarCourse from file **/
    public HashMap<String, CalendarCourse> returnCalendarCourseHashMap() {
        return this.allCalendarCourses;
    }
    /** Returns HashMap of String to TimetableCourse from file **/
    public HashMap<String, TimetableCourse> returnTimetableCourseHashMap() {
        return this.allTimetableCourses;
    }
    /** Returns session type of imported timetable **/
    public String returnSessionTypeImport() {
         TimetableCourse aCourse = allTimetableCourses.values().iterator().next();
         this.sessionType = aCourse.getCourseSession();
         return this.sessionType;
    }
}
