import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EarthQuakeClientTest {
    @Test
    void filterByMagnitude () {
        EarthQuakeClient e = new EarthQuakeClient();
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "test/data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        assertEquals(25, list.size());

        // check for null safety
        assertEquals(0, e.filterByMagnitude(new ArrayList<QuakeEntry>(), 0.0).size());
        assertEquals(0, e.filterByMagnitude(null, 0.0).size());

        // should be the whole list.
        assertEquals(25, e.filterByMagnitude(list, -2000).size());
        // should be no entries, because a magnitude 2000 quake would destroy the planet.
        assertEquals(0, e.filterByMagnitude(list, 2000).size());
    }

    @Test
    void filterByDistanceFrom () {
        EarthQuakeClient e = new EarthQuakeClient();
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "test/data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        assertEquals(25, list.size());

        Location durham = new Location(35.988, -78.907);
        Location bridgeport = new Location(38.17, -118.82);

        // check for null safety
        assertEquals(0, e.filterByDistanceFrom(new ArrayList<QuakeEntry>(), 0.0, durham).size());
        assertEquals(0, e.filterByDistanceFrom(null, 0.0, bridgeport).size());
        assertEquals(0, e.filterByDistanceFrom(list, 0.0, null).size());

        // nothing should be a negative distance
        assertEquals(0, e.filterByDistanceFrom(list, -0.1, durham).size());
        // everything should be within 1,000,000,000 m
        assertEquals(25, e.filterByDistanceFrom(list, 1000000000.0, durham).size());

        // from assignment
        assertEquals(0, e.filterByDistanceFrom(list, 1000000, durham).size());
        assertEquals(7, e.filterByDistanceFrom(list, 1000000, bridgeport).size());
    }

    @Test
    void dumpCSV () {
    }

    @Test
    void bigQuakes () {
        EarthQuakeClient e = new EarthQuakeClient();
        System.out.println("bigQuakes()");
        e.bigQuakes();
        System.out.println("complete\n");
    }

    @Test
    void closeToMe () {
        EarthQuakeClient e = new EarthQuakeClient();
        System.out.println("closeToMe()");
        e.closeToMe();
        System.out.println("complete\n");
    }

    @Test
    void createCSV () {
        EarthQuakeClient e = new EarthQuakeClient();
        System.out.println("createCSV()");
        e.createCSV();
        System.out.println("complete\n");
    }

}