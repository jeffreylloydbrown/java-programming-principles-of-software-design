import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MinMagFilterTest {
    @Test
    void getName () {
        assertEquals("MinMag", new MinMagFilter(2).getName());
        assertEquals("my filter", new MinMagFilter("my filter", 2).getName());
        assertEquals("MinMag", new MinMagFilter("", 2).getName());
        assertEquals("MinMag", new MinMagFilter(null, 2).getName());
    }

    @Test
    void satisfies () {
        QuakeEntry mag1 = new QuakeEntry(0.1, 0.2, 1.0, "mag 1", 0.0);

        assertFalse(new MinMagFilter(1.00001).satisfies(mag1));
        assertTrue(new MinMagFilter(0.99999).satisfies(mag1));
        assertTrue(new MinMagFilter(1.0).satisfies(mag1));

        QuakeEntry neg1 = new QuakeEntry(0.1, 0.2, -1.0, "negative 1", 0.0);
        assertFalse(new MinMagFilter(-0.99999).satisfies(neg1));
        assertTrue(new MinMagFilter(-1.0).satisfies(neg1));
        assertTrue(new MinMagFilter(-1.00001).satisfies(neg1));
    }

}