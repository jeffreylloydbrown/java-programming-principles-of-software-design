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
    void filterByDepth () {
        EarthQuakeClient e = new EarthQuakeClient();
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "test/data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        assertEquals(25, list.size());

        // check for null safety
        assertEquals(0, e.filterByDepth(new ArrayList<QuakeEntry>(), 0.0, 0.0).size());
        assertEquals(0, e.filterByDepth(null, 0.0, 0.0).size());

        // min and max order don't matter.
        assertEquals(25, e.filterByDepth(list, +100000000, -1000000000).size());
        assertEquals(25, e.filterByDepth(list, -100000000, +1000000000).size());

        // minDepth == maxDepth always finds nothing.
        assertEquals(0, e.filterByDepth(list, -10000, -10000).size());

        // from assignment
        assertEquals(5, e.filterByDepth(list, -10000, -5000).size());
    }

    @Test
    void filterByPhrase () {
        EarthQuakeClient e = new EarthQuakeClient();
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "test/data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        assertEquals(25, list.size());

        // check for null safety
        assertEquals(0, e.filterByPhrase(new ArrayList<QuakeEntry>(), "start", "Can").size());
        assertEquals(0, e.filterByPhrase(null, "start", "Can").size());
        assertEquals(0, e.filterByPhrase(list, "", "Can").size());
        assertEquals(0, e.filterByPhrase(list, null, "Can").size());
        assertEquals(0, e.filterByPhrase(list, "start", "").size());
        assertEquals(0, e.filterByPhrase(list, "start", null).size());

        // incorrect `where` means zero results
        assertEquals(0, e.filterByPhrase(list, "someplace", "Can").size());

        // from assignment
        assertEquals(4, e.filterByPhrase(list, "end", "California").size());
        assertEquals(3, e.filterByPhrase(list, "any", "Can").size());
        assertEquals(2, e.filterByPhrase(list, "start", "Explosion").size());
    }

    @Test
    void dumpCSV () {
    }

    @Test
    void bigQuakes () {
        EarthQuakeClient e = new EarthQuakeClient();
        System.out.println("bigQuakes()");
        e.bigQuakes();
        System.out.println("bigQuakes() complete\n");
    }

    @Test
    void closeToMe () {
        EarthQuakeClient e = new EarthQuakeClient();
        System.out.println("closeToMe()");
        e.closeToMe();
        System.out.println("closeToMe() complete\n");
    }

    @Test
    void createCSV () {
        EarthQuakeClient e = new EarthQuakeClient();
        System.out.println("createCSV()");
        e.createCSV();
        System.out.println("createCSV() complete\n");
    }

    @Test
    void quakesOfDepth () {
        EarthQuakeClient e = new EarthQuakeClient();
        System.out.println("quakesOfDepth()");
        e.quakesOfDepth();
        System.out.println("quakesOfDepth() complete\n");
    }

    @Test
    void quakesByPhrase () {
        EarthQuakeClient e = new EarthQuakeClient();
        System.out.println("quakesByPhrase()");
        e.quakesByPhrase();
        System.out.println("quakesByPhrase() complete\n");
    }

    @Test
    void practiceQuiz () {
        EarthQuakeClient e = new EarthQuakeClient();
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "test/data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);

        System.out.println("Q1.  "+e.filterByDepth(list, -8000, -5000).size());
        System.out.println("Q2.  "+e.filterByPhrase(list, "start", "Explosion").size());
        System.out.println("Q3.  "+e.filterByPhrase(list, "end", "California").size());
        System.out.println("Q4.  "+e.filterByPhrase(list, "any", "Creek").size());
        LargestQuakes lq = new LargestQuakes();
        ArrayList<QuakeEntry> top = lq.getLargest(list, 5);
        System.out.printf("Q5.  %4.2f\n", top.get(2).getMagnitude());
        System.out.println("Q6.  use country from '"+top.get(4).getInfo()+"'");
    }

}