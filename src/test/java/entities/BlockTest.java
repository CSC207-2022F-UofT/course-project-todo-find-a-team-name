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

    /**
     * Checks whether equals method returns true when two blocks are equal
     */
    @Test
    void testEqualsTrue(){
        Block b1 = new Block("TU", "10:30", "11:00", "MP 102");
        Block b2 = new Block("TU", "10:30", "11:00", "MP 102");
        assertEquals(b1, b2);
    }

    /**
     * Checks whether equals method returns false when two blocks are not equal
     */
    @Test
    void testEqualsFalse(){
        Block b1 = new Block("TU", "10:30", "11:00", "MP 102");
        Block b2 = new Block("TU", "14:30", "17:00", "MP 111");
        assertNotEquals(b1, b2);
    }

    /**
     * Check whether hashCode returns same value if two blocks are equal
     */
    @Test
    void testHashCode(){
        Block b1 = new Block("TU", "10:30", "11:00", "MP 102");
        Block b2 = new Block("TU", "10:30", "11:00", "MP 102");
        assertEquals(b1.hashCode(), b2.hashCode());
    }
}