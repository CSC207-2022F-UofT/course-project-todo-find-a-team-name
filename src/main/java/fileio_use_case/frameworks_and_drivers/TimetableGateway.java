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
import java.time.LocalDate;
import java.util.*;

public class TimetableGateway implements TimetableGatewayInterface {
    public TimetableGateway() {
    }
    /**
     * Takes in filepath, reads it, and converts it into a Timetable.
     *
     * @param filePath - a Timetable class
     * @param courseType - course type (Timetable or Calendar)
     * @return Timetable
     */
    public Timetable readFromFile(String filePath, String courseType) throws IOException, org.json.simple.parser.ParseException, InvalidSectionsException {
        ProgramFileReader aFileReader = new ProgramFileReader();
        aFileReader.parseString(Files.readString(Path.of(filePath)), courseType);
        HashMap<String, TimetableCourse> timetableCourseHashMap = aFileReader.returnTimetableCourseHashMap();
        if (!timetableCourseHashMap.isEmpty()) {
            return extractTimetable(timetableCourseHashMap, (aFileReader.returnSessionTypeImport()));
        }
        else {
            return extractTimetable(timetableCourseHashMap, "F");
        }
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
                    Map<String, Object> blockInfo = new HashMap<>();
                    for (int i = 0; i < theSectionModel.getBlocks().size(); i++ ) {
                        Block theBlockModel = theSectionModel.getBlocks().get(i);
                        Map<String, Object> blocks = new HashMap<>();
                        blocks.put("day", String.valueOf(theBlockModel.getDay()));
                        blocks.put("startTime", (String.valueOf(theBlockModel.getStartTime())).replace(".", ":"));
                        blocks.put("endTime", (String.valueOf(theBlockModel.getEndTime())).replace(".", ":"));
                        blocks.put("room", theBlockModel.getRoom());

                        blockInfo.put(i + "Block", blocks);
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
            LocalDate date = LocalDate.now(); // Current date and time
            writer.writeValue(Paths.get("src/main/saved_timetables/" + "timetable " + date + ".json").toFile(), mainMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}