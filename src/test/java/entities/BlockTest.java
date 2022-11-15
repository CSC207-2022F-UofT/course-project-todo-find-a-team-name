package entities;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BlockTest {
    @Before
    public void setUp() {
    }
    @After
    public void tearDown() {
    }

    @Test(timeout = 500)
    public void testBlockConstructor() {
        Block b1 = new Block("MO", "12:34", "43:21", "MP 102");

        // assertions
        assertEquals(0, b1.getDay());
        assertEquals(12.34, b1.getStartTime(), 0.01);
        assertEquals(43.21, b1.getEndTime(), 0.01);
        assertEquals("MP 102", b1.getRoom());
    }
}