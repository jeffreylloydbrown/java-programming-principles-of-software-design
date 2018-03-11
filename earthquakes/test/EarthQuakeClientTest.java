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
    }

    @Test
    void dumpCSV () {
    }

    @Test
    void bigQuakes () {
        EarthQuakeClient e = new EarthQuakeClient();
        e.bigQuakes();
    }

    @Test
    void closeToMe () {
    }

    @Test
    void createCSV () {
    }

}