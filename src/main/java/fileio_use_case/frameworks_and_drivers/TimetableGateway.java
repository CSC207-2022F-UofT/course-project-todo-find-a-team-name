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
import java.time.LocalDateTime;
import java.util.*;

public class TimetableGateway implements TimetableGatewayInterface {
    public TimetableGateway() {
    }
    /**
     * Takes in filepath, reads it, and converts it into a Timetable.
     *
     * @param filePath    - a Timetable class
     * @param sessionType - a session type (Fall (F), Winter (S))
     * @return Timetable
     */
    public Timetable readFromFile(String filePath, String sessionType) throws IOException, org.json.simple.parser.ParseException, InvalidSectionsException {
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
    /**
     * Saves Timetable to JSON file into src/main/saved_timetables/.
     * @param timetable - a Timetable class
     */
    public void timetableToFile(Timetable timetable) {
        try {
            // create a map
            Map<String, Object> mainMap = new HashMap<>();
            // For Course Info
            for (Course courseModel : timetable.getCourseList()) {
                Map<String, Object> course = new HashMap<>();
                String courseSummaryName = courseModel.getCourseCode() + "-" + courseModel.getCourseSession() + "-" + "20229";
                // For sections
                // Section to another map
                Map<String, Object> courseSectionInfo = new HashMap<>();
                for (Section theSectionModel : courseModel.getSections()) {
                    Map<String, Object> sections = new HashMap<>();
                    // For Blocks
                    // Block to another map
                    Map<String, Object> blocks = new HashMap<>();
                    Map<String, Object> blockInfo = new HashMap<>();
                    for (Block theBlockModel : theSectionModel.getBlocks()) {
                        blocks.put("day", String.valueOf(theBlockModel.getDay()));
                        blocks.put("startTime", String.valueOf(theBlockModel.getStartTime()));
                        blocks.put("endTime", String.valueOf(theBlockModel.getEndTime()));
                        blocks.put("room", theBlockModel.getRoom());

                        blockInfo.put(theBlockModel.getDay() + "Block", blocks);
                    }
                    sections.put("section", theSectionModel.getCode());
                    sections.put("blocks", blockInfo);
                    sections.put("prof", theSectionModel.getInstructorName());

                    courseSectionInfo.put(theSectionModel.getCode(), sections);
                }
                course.put("code", courseModel.getCourseCode());
                course.put("sections", courseSectionInfo);
                course.put("session", courseModel.getCourseSession());
                course.put("breadth", courseModel.getBreadth());
                course.put("title", courseModel.getTitle());

                mainMap.put(courseSummaryName, course);
            }
            // create object mapper instance
            ObjectMapper mapper = new ObjectMapper();
            ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
            // convert mainMap to JSON file
            LocalDateTime dateTime = LocalDateTime.now(); // Current date and time
            writer.writeValue(Paths.get("src/main/saved_timetables/" + "timetable " + dateTime + ".json").toFile(), mainMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}