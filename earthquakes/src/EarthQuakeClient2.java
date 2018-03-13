import java.util.*;
import edu.duke.*;

public class EarthQuakeClient2 {
    public EarthQuakeClient2() {
        // TODO Auto-generated constructor stub
    }

    public ArrayList<QuakeEntry> filter(ArrayList<QuakeEntry> quakeData, Filter f) { 
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for(QuakeEntry qe : quakeData) { 
            if (f.satisfies(qe)) { 
                answer.add(qe); 
            } 
        } 
        
        return answer;
    }

    private void showQuakes (ArrayList<QuakeEntry> list) {
        for (QuakeEntry qe : list)
            System.out.println(qe);
    }

    public void quakesWithFilter() { 
        EarthQuakeParser parser = new EarthQuakeParser(); 
        //String source = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "test/data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);         
        System.out.println("read data for "+list.size()+" quakes");

        /*
        Filter f = new MinMagFilter(4.0);
        ArrayList<QuakeEntry> m7  = filter(list, f);
        showQuakes(m7);
        */
        /*
        Filter mag = new MagnitudeFilter(4.0, 5.0);
        Filter depth = new DepthFilter(-35000.0, -12000.0);
        System.out.println("should see Lagunillas, Venezuela and Ishinomaki, Japan, 2 total");
        showQuakes(filter(filter(list, mag), depth));
        */
        Location tokyo = new Location(35.42, 139.43);
        Filter within = new DistanceFilter(tokyo, 10000000);
        Filter phrase = new PhraseFilter("end", "Japan");
        System.out.println("should see Chichi-shima, Japan and Ishinomaki, Japan, 2 total");
        showQuakes(filter(filter(list, within), phrase));
    }

    public void createCSV() {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "test/data/nov20quakedata.atom";
        String source = "test/data/nov20quakedatasmall.atom";
        //String source = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        dumpCSV(list);
        System.out.println("# quakes read: "+list.size());
    }

    public void dumpCSV(ArrayList<QuakeEntry> list) {
        System.out.println("Latitude,Longitude,Magnitude,Info");
        for(QuakeEntry qe : list){
            System.out.printf("%4.2f,%4.2f,%4.2f,%s\n",
                qe.getLocation().getLatitude(),
                qe.getLocation().getLongitude(),
                qe.getMagnitude(),
                qe.getInfo());
        }
    }

}
