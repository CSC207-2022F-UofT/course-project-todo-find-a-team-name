package retrieve_timetable_use_case;

import entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EntityConverterTest {
    private BlockModel blockModel;
    private Block blockActual;
    private SectionModel sectionModel;
    private Section sectionActual;
    private CourseModel courseModel;
    private CalendarCourse courseActual;
    private SessionModel sessionModel;
    private Session sessionActual;

    @BeforeEach
    public void setUp() {
        blockModel = new BlockModel(2, 14, 16, "AB106");
        blockActual = new Block("WE", "14:00", "16:00", "AB106");

        ArrayList<BlockModel> modelBlocks = new ArrayList<>();
        modelBlocks.add(blockModel);

        ArrayList<Block> actualBlocks = new ArrayList<>();
        actualBlocks.add(blockActual);

        sectionActual = new Section("LEC0101", "prof!!", actualBlocks);
        sectionModel = new SectionModel("LEC0101", "prof!!", modelBlocks);

        ArrayList<SectionModel> modelSections = new ArrayList<>();
        modelSections.add(sectionModel);

        ArrayList<Section> actualSections = new ArrayList<>();
        actualSections.add(sectionActual);

        courseModel = new CourseModel("some course", modelSections, "F", "EGG100", "" +
                "BR1");
        courseActual = new CalendarCourse("some course", actualSections, "F", "EGG100", "" +
                "BR1");

        HashMap<String, CourseModel> modelCourses = new HashMap<>();
        modelCourses.put(courseModel.getCourseCode(), courseModel);

        sessionModel = new SessionModel(modelCourses, "F");
        sessionActual = new Session("F");
        sessionActual.addCourse(courseActual);
    }

    @Test
    void generateSessionResponse() {
        assertEquals(sessionModel, EntityConverter.generateSessionResponse(sessionActual));
    }

    @Test
    void generateCourseResponse() {
        assertEquals(courseModel, EntityConverter.generateCourseResponse(courseActual));
    }

    @Test
    void generateSectionResponse() {
        assertEquals(sectionModel, EntityConverter.generateSectionResponse(sectionActual));
    }

    @Test
    void generateBlockResponse() {
        assertEquals(blockModel, EntityConverter.generateBlockResponse(blockActual));
    }

}