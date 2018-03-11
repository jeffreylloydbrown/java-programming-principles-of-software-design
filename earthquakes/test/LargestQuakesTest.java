import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LargestQuakesTest {
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
    }

    @Test
    void findLargestQuakes () {
        LargestQuakes lq = new LargestQuakes();
        lq.findLargestQuakes();
    }

}