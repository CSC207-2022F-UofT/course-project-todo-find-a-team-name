package retrieve_timetable_use_case;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * A test suite for the SectionModel class, primarily to confirm the correctness of its equal method.
 * The setters and getters are currently too simple to require testing, but must be tested if more complex
 * behaviour is introduced.
 */
class SectionModelTest {
    private SectionModel section;

    @BeforeEach
    public void SetUp() {
        ArrayList<BlockModel> blocks = new ArrayList<>();
        blocks.add(new BlockModel(0, 1, 2, ""));
        section = new SectionModel("CSC108", "prof!!", blocks);

    }

    /**
     * Tests that the Block and its equivalent BlockModel are considered equal.
     */
    @Test
    void testEquals() {
        ArrayList<BlockModel> blocks = new ArrayList<>();
        blocks.add(new BlockModel(0, 1, 2, ""));
        SectionModel other = new SectionModel("CSC108", "prof!!", blocks);
        assertEquals(other, section);
    }

    /**
     * Tests that the Block and a non-equivalent BlockModel are non-equal.
     */
    @Test
    void testNotEquals() {
        ArrayList<BlockModel> otherBlocks = new ArrayList<>();
        otherBlocks.add(new BlockModel(2, 14, 16, "AB105"));
        SectionModel other = new SectionModel("CSC108", "prof!!", otherBlocks);
        assertNotEquals(other, section);
    }
}