package entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlockTest {
    @Test
    void testBlockConstructor() {
        Block b1 = new Block("MO", "12:34", "43:21", "MP 102");

        // assertions
        assertEquals(0, b1.getDay());
        assertEquals(12.34, b1.getStartTime(), 0.001);
        assertEquals(43.21, b1.getEndTime(), 0.001);
        assertEquals("MP 102", b1.getRoom());
    }

}