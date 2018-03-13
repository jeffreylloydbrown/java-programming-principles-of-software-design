import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DistanceFilterTest {
    @Test
    void getName () {
        Location loc = new Location(1,2);
        assertEquals("Distance", new DistanceFilter(loc, 100).getName());
        assertEquals("my filter", new DistanceFilter("my filter", loc, 100).getName());
        assertEquals("Distance", new DistanceFilter("", loc, 100).getName());
        assertEquals("Distance", new DistanceFilter(null, loc, 100).getName());
    }
    
    @Test
    void satisfies () {
        Location me = new Location(0.0, 0.0);
        QuakeEntry quake = new QuakeEntry(1.0, 0.0, 0.0, "a quake", 100.0);
        double distance = me.distanceTo(quake.getLocation());

        // no empty Location constructor, so only need to check with null.
        // also, QuakeEntry has no parameterless constructor so only check it with null.
        assertFalse(new DistanceFilter(null, distance).satisfies(quake));
        assertFalse(new DistanceFilter(me, distance).satisfies(null));

        // filter is for less than, not less or equals.  So filtering for the
        // exact distance should be false, and just a little more distance is true.
        // a little less is false.
        assertFalse(new DistanceFilter(me, distance).satisfies(quake));
        assertTrue(new DistanceFilter(me, distance+0.0001).satisfies(quake));
        assertFalse(new DistanceFilter(me, distance-0.0001).satisfies(quake));
    }

}