import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LargestQuakesTest {

    // assignment values are printed rounded to hundreths.
    private double round (double value) {
        return (int)(value*100.0 + ((value >= 0) ? 0.5 : -0.5))/100.0;
    }

    @Test
    void indexOfLargest () {
        LargestQuakes lq = new LargestQuakes();
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "test/data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        assertEquals(25, list.size());

        // check for null safety
        assertEquals(-1, lq.indexOfLargest(new ArrayList<QuakeEntry>()));
        assertEquals(-1, lq.indexOfLargest(null));

        // largest of a 1 element list is that element.
        ArrayList<QuakeEntry> one = new ArrayList<QuakeEntry>();
        one.add(list.get(0));
        assertEquals(0, lq.indexOfLargest(one));

        // From assignment
        assertEquals(3, lq.indexOfLargest(list));
    }

    @Test
    void getLargest () {
        LargestQuakes lq = new LargestQuakes();
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "test/data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        assertEquals(25, list.size());

        // check for null safety
        assertEquals(0, lq.getLargest(new ArrayList<QuakeEntry>(), 1).size());
        assertEquals(0, lq.getLargest(null, 1).size());
        assertEquals(0, lq.getLargest(list, -1).size());
        assertEquals(0, lq.getLargest(list, 0).size());

        // largest of 1 quake is that quake.
        ArrayList<QuakeEntry> one = new ArrayList<QuakeEntry>();
        one.add(list.get(0));
        ArrayList<QuakeEntry> res1 = lq.getLargest(one, 1);
        assertEquals(1, res1.size());
        assertEquals(one.get(0), res1.get(0));

        // result of howMany == list.size()+1 has size `list.size()`
        assertEquals(list.size(), lq.getLargest(list, list.size()).size());
        assertEquals(list.size(), lq.getLargest(list, list.size()+1).size());

        // from assignment
        ArrayList<QuakeEntry> res5 = lq.getLargest(list, 5);
        assertEquals(5, res5.size());
        assertEquals(26.38, round(res5.get(0).getLocation().getLatitude()));
        assertEquals(142.71, round(res5.get(0).getLocation().getLongitude()));
        assertEquals(-11.63, round(res5.get(1).getLocation().getLatitude()));
        assertEquals(165.52, round(res5.get(1).getLocation().getLongitude()));
        assertEquals(-24.67, round(res5.get(2).getLocation().getLatitude()));
        assertEquals(-175.93, round(res5.get(2).getLocation().getLongitude()));
        assertEquals(8.53, round(res5.get(3).getLocation().getLatitude()));
        assertEquals(-71.34, round(res5.get(3).getLocation().getLongitude()));
        assertEquals(40.37, round(res5.get(4).getLocation().getLatitude()));
        assertEquals(73.20, round(res5.get(4).getLocation().getLongitude()));
    }

    @Test
    void findLargestQuakes () {
        LargestQuakes lq = new LargestQuakes();
        lq.findLargestQuakes();
    }

}