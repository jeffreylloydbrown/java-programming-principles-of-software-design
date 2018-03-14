import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuakeEntryTest {
    private double latitude = 1.5;
    private double longitude = 2.5;
    private double magnitude = 3.5;
    private String title = "a test quake";
    private double depth = 4.5;

    private QuakeEntry quake = new QuakeEntry(latitude, longitude, magnitude, title, depth);

    @Test
    void getLocation () {
        assertEquals(latitude, quake.getLocation().getLatitude());
        assertEquals(longitude, quake.getLocation().getLongitude());
    }

    @Test
    void getMagnitude () {
        assertEquals(magnitude, quake.getMagnitude());
    }

    @Test
    void getInfo () {
        assertEquals(title, quake.getInfo());
    }

    @Test
    void getDepth () {
        assertEquals(depth, quake.getDepth());
    }

    @Test
    void compareTo () {
        assertTrue(quake.compareTo(quake) == 0);
        assertTrue(quake.compareTo(new QuakeEntry(latitude, longitude, magnitude, title, depth)) == 0);
        assertTrue(quake.compareTo(new QuakeEntry(latitude, longitude, magnitude-0.1, title, depth)) > 0);
        assertTrue(quake.compareTo(new QuakeEntry(latitude, longitude, magnitude+0.1, title, depth)) < 0);

        // same magnitudes, different depths
        assertTrue(quake.compareTo(new QuakeEntry(latitude, longitude, magnitude, title, depth-0.1)) > 0);
        assertTrue(quake.compareTo(new QuakeEntry(latitude, longitude, magnitude, title, depth+0.1)) < 0);
    }

    @Test
    void stringize () {
        assertEquals("(1.50, 2.50), mag = 3.50, depth = 4.50, title = a test quake", quake.toString());
    }

}