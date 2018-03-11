
/**
 * Find N-closest quakes
 * 
 * @author Duke Software/Learn to Program
 * @version 1.0, November 2015
 */

import java.util.*;

public class ClosestQuakes {
    // Locate the quake closest to `from` in `list` and return that entry.
    // no error checking, because the callers do that.  `list` and `from` cannot be empty.
    private QuakeEntry findTheClosestQuake (ArrayList<QuakeEntry> list, Location from) {
        QuakeEntry closest = list.get(0);
        for (int k = 1; k < list.size(); k++) {
            if (list.get(k).getLocation().distanceTo(from) < closest.getLocation().distanceTo(from))
                closest = list.get(k);
        }
        return closest;
    }

    public ArrayList<QuakeEntry> getClosest(ArrayList<QuakeEntry> quakeData, Location current, int howMany) {
        ArrayList<QuakeEntry> ret = new ArrayList<QuakeEntry>();
        // Verify parameters
        if (quakeData == null || quakeData.isEmpty()) return ret;
        if (current == null) return ret;
        if (howMany <= 0) return ret;

        // Make a copy of the input list so we can safely destroy it.
        // Not checking if howMany > quakeData.size(), because quakeData is
        // probably not sorted by distance, and that is required of this method.
        ArrayList<QuakeEntry> in = new ArrayList<QuakeEntry>(quakeData);

        while (howMany > 0 && ! in.isEmpty()) {
            QuakeEntry closest = findTheClosestQuake(in, current);
            ret.add(closest);
            in.remove(closest);
            howMany--;
        }
        return ret;
    }

    public void findClosestQuakes() {
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "test/data/nov20quakedata.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+list.size());

        Location jakarta  = new Location(-6.211,106.845);

        ArrayList<QuakeEntry> close = getClosest(list,jakarta,10);
        for(int k=0; k < close.size(); k++){
            QuakeEntry entry = close.get(k);
            double distanceInMeters = jakarta.distanceTo(entry.getLocation());
            System.out.printf("%4.2f\t %s\n", distanceInMeters/1000,entry);
        }
        System.out.println("number found: "+close.size());
    }
    
}
