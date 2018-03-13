import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DepthFilterTest {
    @Test
    void getName () {
        assertEquals("Depth", new DepthFilter(10,20).getName());
        assertEquals("my filter", new DepthFilter("my filter", 10,20).getName());
        assertEquals("Depth", new DepthFilter("", 10, 20).getName());
        assertEquals("Depth", new DepthFilter(null, 10, 20).getName());
    }

    @Test
    void satisfies () {
        QuakeEntry pos1 = new QuakeEntry(0.1, 0.2, 0.0, "mag 1", 1.0);

        assertFalse(new DepthFilter(0.0, 0.0).satisfies(pos1));
        assertTrue(new DepthFilter(0.9, 1.1).satisfies(pos1));
        assertTrue(new DepthFilter(1.1, 0.9).satisfies(pos1));
        assertTrue(new DepthFilter(1.0, 1.1).satisfies(pos1));
        assertTrue(new DepthFilter(0.9, 1.0).satisfies(pos1));

        QuakeEntry neg1 = new QuakeEntry(0.1, 0.2, 0.0, "negative 1", -1.0);
        assertFalse(new DepthFilter(-1.1, -1.1).satisfies(neg1));
        assertTrue(new DepthFilter(-1.1, -1.0).satisfies(neg1));
        assertTrue(new DepthFilter(-1.0, -0.9).satisfies(neg1));
        assertTrue(new DepthFilter(-0.9, -1.1).satisfies(neg1));
    }

}