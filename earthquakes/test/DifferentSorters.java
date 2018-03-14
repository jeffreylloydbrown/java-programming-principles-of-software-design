
/**
 * Write a description of class DifferentSorters here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DifferentSorters {
    @Test
    public void sortWithCompareTo() {
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "test/data/nov20quakedata.atom";
        //String source = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        Collections.sort(list);
        for(QuakeEntry qe: list) {
            System.out.println(qe);
        }
        int quakeNumber = 10;
        System.out.println("Print quake entry in position " + quakeNumber);
        QuakeEntry q = list.get(quakeNumber);
        System.out.println(q);
        // (36.75, -116.15), mag = -0.20, depth = -4200.00, title = 57km ESE of Beatty, Nevada
        // I cannot do a simple compare to the full entry because I can't put the full contents of q.getLocation() into
        // the object I create.  So must check it by comparing string results (to avoid rounding failures).
        assertEquals(new QuakeEntry(36.75, -116.15, -0.2,
                "57km ESE of Beatty, Nevada", -4200).toString(), q.toString());
    }

    @Test
    public void sortByMagnitude() {
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "test/data/nov20quakedata.atom";
        //String source = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        Collections.sort(list, new MagnitudeComparator());
        for(QuakeEntry qe: list) {
            System.out.println(qe);
        }

    }

    @Test
    public void sortByDistance() {
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "test/data/nov20quakedata.atom";
        //String source = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        // Location is Durham, NC
        Location where = new Location(35.9886, -78.9072);
        Collections.sort(list, new DistanceComparator(where));
        for(QuakeEntry qe: list) {
            System.out.println(qe);
        }

    }
}
