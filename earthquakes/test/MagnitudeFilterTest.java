import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MagnitudeFilterTest {
    @Test
    void getName () {
        assertEquals("Magnitude", new MagnitudeFilter(1,2).getName());
        assertEquals("my filter", new MagnitudeFilter("my filter", 1,2).getName());
        assertEquals("Magnitude", new MagnitudeFilter("", 1,2).getName());
        assertEquals("Magnitude", new MagnitudeFilter(null, 1,2).getName());
    }
    
    @Test
    void satisfies () {
        QuakeEntry mag1 = new QuakeEntry(0.1, 0.2, 1.0, "mag 1", 0.0);

        assertFalse(new MagnitudeFilter(0.0, 0.0).satisfies(mag1));
        assertTrue(new MagnitudeFilter(0.9, 1.1).satisfies(mag1));
        assertTrue(new MagnitudeFilter(1.1, 0.9).satisfies(mag1));
        assertTrue(new MagnitudeFilter(1.0, 1.1).satisfies(mag1));
        assertTrue(new MagnitudeFilter(0.9, 1.0).satisfies(mag1));

        QuakeEntry neg1 = new QuakeEntry(0.1, 0.2, -1.0, "negative 1", 0.0);
        assertFalse(new MagnitudeFilter(-1.1, -1.1).satisfies(neg1));
        assertTrue(new MagnitudeFilter(-1.1, -1.0).satisfies(neg1));
        assertTrue(new MagnitudeFilter(-1.0, -0.9).satisfies(neg1));
        assertTrue(new MagnitudeFilter(-0.9, -1.1).satisfies(neg1));
    }

}