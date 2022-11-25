package retrieve_timetable_use_case;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SectionModelTest {
    private SectionModel section;

    @BeforeEach
    public void SetUp() {
        ArrayList<BlockModel> blocks = new ArrayList<>();
        blocks.add(new BlockModel(0, 1, 2, ""));
        section = new SectionModel("CSC108", "prof!!", blocks);

    }

    @Test
    void testEquals() {
        ArrayList<BlockModel> blocks = new ArrayList<>();
        blocks.add(new BlockModel(0, 1, 2, ""));
        SectionModel other = new SectionModel("CSC108", "prof!!", blocks);
        assertEquals(other, section);
    }

    @Test
    void testNotEquals() {
        ArrayList<BlockModel> otherBlocks = new ArrayList<>();
        otherBlocks.add(new BlockModel(2, 14, 16, "AB105"));
        SectionModel other = new SectionModel("CSC108", "prof!!", otherBlocks);
        assertNotEquals(other, section);
    }
}