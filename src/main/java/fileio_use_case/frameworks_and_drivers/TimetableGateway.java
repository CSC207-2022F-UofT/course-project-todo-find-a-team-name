package fileio_use_case.frameworks_and_drivers;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import entities.*;
import fileio_use_case.application_business.ProgramFileReader;
import fileio_use_case.application_business.timetable_specific_classes.TimetableBuilder;
import fileio_use_case.application_business.timetable_specific_classes.TimetableGatewayInterface;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.*;

public class TimetableGateway implements TimetableGatewayInterface {
    public TimetableGateway() {}
    /**
     * Saves Timetable to JSON file
     * @param timetable - a Timetable class
     */
    public void timetableToFile(Timetable timetable) {
        try {
            // create a map
            Map<String, Object> mainMap = new HashMap<>();
            Map<String, Object> course = new HashMap<>();
            Map<String, Object> courseSectionInfo = new HashMap<>();
            Map<String, Object> sections = new HashMap<>();
            Map<String, Object> blocks = new HashMap<>();
            // For Course Info
            for (Course courseModel : timetable.getCourseList()) {
                String courseSummaryName = courseModel.getCourseCode() + "-" + courseModel.getCourseSession() + "-" + "20229";
                // For sections
                // Section to another map
                for (Section theSectionModel : courseModel.getSections()) {
                    // For Blocks
                    // Block to another map
                    for (Block theBlockModel : theSectionModel.getBlocks()) {
                        blocks.put("day", theBlockModel.getDay());
                        blocks.put("startTime", String.valueOf(theBlockModel.getStartTime()));
                        blocks.put("endTime", String.valueOf(theBlockModel.getEndTime()));
                        blocks.put("room", theBlockModel.getRoom());
                    }
                    sections.put("section", theSectionModel.getCode());
                    sections.put("blocks", blocks);
                    sections.put("prof", theSectionModel.getInstructorName());

                    courseSectionInfo.put(theSectionModel.getCode(), sections);
                }
                course.put("code", courseModel.getCourseCode());
                course.put("title", courseModel.getTitle());
                course.put("session", courseModel.getCourseSession());
                course.put("sections", courseSectionInfo);
                course.put("breadth", courseModel.getBreadth());

                mainMap.put(courseSummaryName, course);
            }

            // create object mapper instance
            ObjectMapper mapper = new ObjectMapper();

            ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
            // convert mainMap to JSON file
            LocalDateTime dateTime = LocalDateTime.now(); // Current date and time
            writer.writeValue(Paths.get("timetable" + dateTime + ".json").toFile(), mainMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Takes in filepath, reads it, and converts it into a Timetable.
     * @param filePath - a Timetable class
     * @param sessionType - a session type (Fall (F), Winter (S))
     * @return Timetable
     */
    public Timetable readFromFile(String filePath, String sessionType) throws IOException, ParseException, org.json.simple.parser.ParseException, InvalidSectionsException {
        ProgramFileReader aFileReader = new ProgramFileReader();
        aFileReader.parseString(Files.readString(Path.of(filePath)), "Timetable");
        HashMap<String, TimetableCourse> timetableCourseHashMap = aFileReader.returnTimetableCourseHashMap();
        return extractTimetable(timetableCourseHashMap, sessionType);
    }
    /**
     * HELPER METHOD
     */
    private Timetable extractTimetable(HashMap<String, TimetableCourse> allCourses, String sessionType) {
        return new TimetableBuilder().aTimetableBuilder(allCourses, sessionType);
    }

//    // TODO: Remove later
//    public static void main(String[] args) {
//        try {
//            TimetableModel timetableModel = new TimetableModel();
////            // create a map
////            Map<String, Object> map = new HashMap<>();
////            map.put("name", "John Deo");
////            map.put("email", "john.doe@example.com");
////            map.put("roles", new String[]{"Member", "Admin"});
////            map.put("admin", true);
////
////            // create object mapper instance
////            ObjectMapper mapper = new ObjectMapper();
////
////            ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
////
////            // convert book object to JSON file
////            writer.writeValue(Paths.get("book.json").toFile(), map);
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
}