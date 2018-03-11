import java.util.*;

public class LargestQuakes {

    public int indexOfLargest (ArrayList<QuakeEntry> list) {
        if (list == null || list.isEmpty()) return -1;

        int largestIndex = 0;
        for (int k = 1; k < list.size(); k++)
            if (list.get(k).getMagnitude() > list.get(largestIndex).getMagnitude())
                largestIndex = k;
        return largestIndex;
    }

    public ArrayList<QuakeEntry> getLargest(ArrayList<QuakeEntry> quakeData, int howMany) {
        ArrayList<QuakeEntry> ret = new ArrayList<QuakeEntry>();
        // Verify parameters
        if (quakeData == null || quakeData.isEmpty()) return ret;
        if (howMany <= 0) return ret;

        // Make a copy of the input list so we can safely destroy it.
        // Not checking if howMany > quakeData.size(), because quakeData is
        // probably not sorted by distance, and that is required of this method.
        ArrayList<QuakeEntry> in = new ArrayList<QuakeEntry>(quakeData);

        while (howMany > 0 && ! in.isEmpty()) {
            int largestIndex = indexOfLargest(in);
            QuakeEntry qe = in.get(largestIndex);
            ret.add(qe);
            in.remove(qe);
            howMany--;
        }
        return ret;
    }

    public void findLargestQuakes() {
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "test/data/nov20quakedatasmall.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+list.size()+" quakes");
        ArrayList<QuakeEntry> largest5 = getLargest(list, 5);
        for (QuakeEntry qe : largest5)
            System.out.println(qe);
        System.out.println("number found: "+largest5.size());
    }  // findLargestQuakes

}  // LargestQuakes
