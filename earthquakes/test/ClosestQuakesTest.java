import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ClosestQuakesTest {
    // assignment values are printed rounded to hundreths.
    private double round (double value) {
        return (int)(value*100.0 + 0.5)/100.0;
    }

    @Test
    void getClosest () {
        ClosestQuakes cq = new ClosestQuakes();
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "test/data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        assertEquals(25, list.size());

        Location jakarta  = new Location(-6.211,106.845);

        // check for null safety
        assertEquals(0, cq.getClosest(new ArrayList<QuakeEntry>(), jakarta, 1).size());
        assertEquals(0, cq.getClosest(null, jakarta, 1).size());
        assertEquals(0, cq.getClosest(list, null, 1).size());
        assertEquals(0, cq.getClosest(list, jakarta, 0).size());
        assertEquals(0, cq.getClosest(list, jakarta, -1).size());

        // ask for only the closest quake, should get 1 back.
        assertEquals(1, cq.getClosest(list,jakarta, 1).size());
        // ask for the closest list.size()+1, should get list.size() back
        assertEquals(list.size(), cq.getClosest(list, jakarta, list.size()).size());
        assertEquals(list.size(), cq.getClosest(list, jakarta, list.size()+1).size());
        // list better still be 25.
        assertEquals(25, list.size());

        // from assignment
        ArrayList<QuakeEntry> results = cq.getClosest(list, jakarta, 3);
        assertEquals(3, results.size());
        // cannot do a simple location compare, because I'm not creating a full object to compare against.
        // latitude and longitude rounded to 2 decimal places in homework PDF.
        assertEquals(0.91, round(results.get(0).getLocation().getLatitude()));
        assertEquals(127.31, round(results.get(0).getLocation().getLongitude()));
        assertEquals(5.86, round(results.get(1).getLocation().getLatitude()));
        assertEquals(126.18, round(results.get(1).getLocation().getLongitude()));
        assertEquals(26.38, round(results.get(2).getLocation().getLatitude()));
        assertEquals(142.71, round(results.get(2).getLocation().getLongitude()));
    }

    @Test
    void findClosestQuakes () {
        ClosestQuakes cq = new ClosestQuakes();
        cq.findClosestQuakes();
    }

}