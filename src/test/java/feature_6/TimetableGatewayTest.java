package feature_6;

import entities.*;
import entities.InvalidSectionsException;
import fileio_use_case.frameworks_and_drivers.TimetableGateway;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TimetableGatewayTest {
    /**
     *
     */
    @Test
    void testTimetableToFile() throws InvalidSectionsException, IOException, ParseException {
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
    }
}
