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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimetableGateway implements TimetableGatewayInterface {
    public TimetableGateway() {
    }

    /**
     * Saves Timetable to JSON file
     *
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
    
    public static void main(String[] args) {
        try {
            ArrayList<TimetableCourse> ashTimetableCourses = new ArrayList<>();
            // Timetable Course 1
            Block basicBlock = new Block("TU", "18:00", "20:00", "Castle Badr");
            Section basicSection = new Section("LEC-0101", "Mario-chan", List.of(basicBlock));
            TimetableCourse basicTimetableCourse = new TimetableCourse("Intro to How To Rule The World", List.of(basicSection), "S",
                    "TES101", "1");
            // Timetable Course 2
            Block basicBlock2 = new Block("MO", "9:00", "10:00", "Golden Deer");
            Section basicSection2 = new Section("LEC-0201", "Claude", List.of(basicBlock2));
            TimetableCourse basicTimetableCourse2 = new TimetableCourse("How to not be right side up", List.of(basicSection2), "S",
                    "FEH101", "2");
            // Timetable Course 3
            Block basicBlock3 = new Block("TU", "11:00", "12:00", "Ketchup");
            Section basicSection3 = new Section("LEC-0301", "Ash Ketchup", List.of(basicBlock3));
            TimetableCourse basicTimetableCourse3 = new TimetableCourse("How to Catch Them All", List.of(basicSection3), "S",
                    "POK500", "3");
            // Timetable Course 4
            Block basicBlock4 = new Block("TU", "1:00", "2:00", "Pokemon");
            Section basicSection4 = new Section("LEC-0401", "Pikachu", List.of(basicBlock4));
            TimetableCourse basicTimetableCourse4 = new TimetableCourse("Pika Pika", List.of(basicSection4), "S",
                    "PIK100", "4");
            ashTimetableCourses.add(basicTimetableCourse);
            ashTimetableCourses.add(basicTimetableCourse2);
            ashTimetableCourses.add(basicTimetableCourse3);
            ashTimetableCourses.add(basicTimetableCourse4);

            // Timetable
            Timetable ashTimetable = new Timetable(ashTimetableCourses, "S");
            // Timetable Gateway
            TimetableGateway gateway = new TimetableGateway();
            gateway.timetableToFile(ashTimetable);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}