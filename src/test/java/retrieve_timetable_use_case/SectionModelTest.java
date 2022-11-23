package retrieve_timetable_use_case;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SectionModelTest {
    private SectionModel section;

    @Before
    public void SetUp() {
        ArrayList<BlockModel> blocks = new ArrayList<BlockModel>();
        blocks.add(new BlockModel(0, 1, 2, ""));
        section = new SectionModel("CSC108", "prof!!", blocks);

    }

    @Test
    void testEquals() {
        ArrayList<BlockModel> blocks = new ArrayList<BlockModel>();
        blocks.add(new BlockModel(0, 1, 2, ""));
        assertEquals(section, new SectionModel("CSC108", "prof!!", blocks));
    }
}