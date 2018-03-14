
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

    @Test
    public void sortByTitleAndDepth() {
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "test/data/nov20quakedata.atom";
        //String source = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        Collections.sort(list, new TitleAndDepthComparator());
        for(QuakeEntry qe: list) {
            System.out.println(qe);
        }

        // entry 10 in nov20quakedata.atom
        // (49.76, 155.83), mag = 4.70, depth = -58380.00, title = 104km SSW of Severo-Kuril'sk, Russia
        QuakeEntry actual = list.get(10);
        System.out.println("Quake at position 10 is "+actual);

        // additional null safety checks
        QuakeEntry nullInfo = new QuakeEntry(1, 2, 3, null, 4);
        QuakeEntry q2 = new QuakeEntry(1, 2, 3, "title", 4);
        TitleAndDepthComparator td = new TitleAndDepthComparator();
        assertTrue(td.compare(nullInfo, q2) < 0);
        assertTrue(td.compare(q2, nullInfo) > 0);
        assertTrue(td.compare(nullInfo, nullInfo) == 0);
        assertTrue(td.compare(q2, q2) == 0);

        // make sure depth sort is correct when titles match
        assertTrue(td.compare(new QuakeEntry(1, 2, 3, q2.getInfo(), q2.getDepth()-0.1), q2) < 0);
        assertTrue(td.compare(new QuakeEntry(1, 2, 3, q2.getInfo(), q2.getDepth()+0.1), q2) > 0);
    }

    @Test
    public void sortByLastWordInTitleThenByMagnitude() {

        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "test/data/nov20quakedata.atom";
        //String source = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        Collections.sort(list, new TitleLastAndMagnitudeComparator());
        for(QuakeEntry qe: list) {
            System.out.println(qe);
        }

        // entry 10 in nov20quakedata.atom
        // (64.47, -149.48), mag = 0.40, depth = -16300.00, title = 21km WSW of North Nenana, Alaska
        QuakeEntry actual = list.get(10);
        System.out.println("Quake at position 10 is "+actual);

        // additional null safety checks
        QuakeEntry nullInfo = new QuakeEntry(1, 2, 3, null, 4);
        QuakeEntry q2 = new QuakeEntry(1, 2, 3, "  \t\n \t\n", 4);
        TitleLastAndMagnitudeComparator td = new TitleLastAndMagnitudeComparator();
        assertTrue(td.compare(nullInfo, q2) < 0);
        assertTrue(td.compare(q2, nullInfo) > 0);
        assertTrue(td.compare(nullInfo, nullInfo) == 0);
        assertTrue(td.compare(q2, q2) == 0);

        // make sure magnitude sort is correct when titles match
        assertTrue(td.compare(new QuakeEntry(1, 2, q2.getMagnitude()-0.1, q2.getInfo(), 4), q2) < 0);
        assertTrue(td.compare(new QuakeEntry(1, 2, q2.getMagnitude()+0.1, q2.getInfo(), 4), q2) > 0);
    }
}
