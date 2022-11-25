package retrieve_timetable_use_case;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class BlockModelTest {
    private BlockModel block;

    @BeforeEach
    public void setUp() {
        block = new BlockModel(2, 14, 16, "AB106");
    }
    @Test
    void testEquals() {
        assertEquals(new BlockModel(2, 14, 16, "AB106"), block);
    }

    @Test
    void testNotEquals() {
        assertNotEquals(new BlockModel(2, 14, 16, "OT104"), block);
    }
}