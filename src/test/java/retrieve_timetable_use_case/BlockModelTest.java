package retrieve_timetable_use_case;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlockModelTest {
    private BlockModel block;

    @Before
    public void setUp() {
        block = new BlockModel(2, 14, 16, "AB107");
    }

    @Test
    void testEquals() {
        assertEquals(block, new BlockModel(2, 14, 16, "AB107"));
    }
}