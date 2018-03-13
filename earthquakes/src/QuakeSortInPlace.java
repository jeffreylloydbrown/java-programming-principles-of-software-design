
/**
 * Write a description of class QuakeSortInPlace here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;
import edu.duke.*;

// Implementing sort algorithms is a complete waste of my time.
// I understand why it is in the class, since the class is for
// less experienced developers.  I will be doing as little possible
// to complete this part of the assignment, since I'm just ducky
// using Collections.sort() for all sorting.

public class QuakeSortInPlace {
    public QuakeSortInPlace() {
        // TODO Auto-generated constructor stub
    }
   
    public int getSmallestMagnitude(ArrayList<QuakeEntry> quakes, int from) {
        int minIdx = from;
        for (int i=from+1; i< quakes.size(); i++) {
            if (quakes.get(i).getMagnitude() < quakes.get(minIdx).getMagnitude()) {
                minIdx = i;
            }
        }
        return minIdx;
    }

    public int getLargestDepth(ArrayList<QuakeEntry> quakeData, int from) {
        int largestIdx = from;
        for (int i=from+1; i < quakeData.size(); i++) {
            if (quakeData.get(i).getDepth() > quakeData.get(largestIdx).getDepth())
                largestIdx = i;
        }
        return largestIdx;
    }
    
    public void sortByMagnitude(ArrayList<QuakeEntry> in) {
       
       for (int i=0; i< in.size(); i++) {
            int minIdx = getSmallestMagnitude(in,i);
            QuakeEntry qi = in.get(i);
            QuakeEntry qmin = in.get(minIdx);
            in.set(i,qmin);
            in.set(minIdx,qi);
        }
        
    }

    public void sortByMagnitudeWithCheck(ArrayList<QuakeEntry> in) {
        int i;
        for (i=0; i< in.size(); i++) {
            if (checkInSortedOrder(in))
                break;
            int minIdx = getSmallestMagnitude(in,i);
            QuakeEntry qi = in.get(i);
            QuakeEntry qmin = in.get(minIdx);
            in.set(i,qmin);
            in.set(minIdx,qi);
        }
        System.out.println("Passes required = "+i);

    }

    public void sortByLargestDepth (ArrayList<QuakeEntry> in) {
        for (int i=0; i< in.size(); i++) {
            int largestIdx = getLargestDepth(in,i);
            QuakeEntry qi = in.get(i);
            QuakeEntry qmax = in.get(largestIdx);
            in.set(i,qmax);
            in.set(largestIdx,qi);
        }
    }

    public void onePassBubbleSort(ArrayList<QuakeEntry> quakeData, int numSorted) {
        // sorts element i-1 and i, which is why we start at 1.
        for (int i = 1; i < quakeData.size() - numSorted; i++) {
            if (quakeData.get(i-1).getMagnitude() > quakeData.get(i).getMagnitude()) {
                QuakeEntry temp = quakeData.get(i-1);
                quakeData.set(i-1, quakeData.get(i));
                quakeData.set(i, temp);
            }
        }
    }

    private void showList(ArrayList<QuakeEntry> list) {
        for (QuakeEntry qe : list)
            System.out.println(qe);
    }

    public void sortByMagnitudeWithBubbleSort(ArrayList<QuakeEntry> in) {
        for (int pass = 0; pass < in.size(); pass++) {
            //System.out.println("Pass "+pass+":");
            //showList(in);
            onePassBubbleSort(in, pass);
        }
    }

    public boolean checkInSortedOrder(ArrayList<QuakeEntry> quakes) {
        for (int i = 1; i < quakes.size(); i++)
            if (quakes.get(i-1).getMagnitude() > quakes.get(i).getMagnitude())
                return false;
        return true;
    }

    public void sortByMagnitudeWithBubbleSortWithCheck(ArrayList<QuakeEntry> in) {
        int pass;
        for (pass = 0; pass < in.size(); pass++) {
            if (checkInSortedOrder(in))
                break;
            //System.out.println("Pass "+pass+": ");
            //showList(in);
            onePassBubbleSort(in, pass);
        }
        System.out.println("required "+pass+" passes: ");
    }

    public void testSort() {
        EarthQuakeParser parser = new EarthQuakeParser(); 
        //String source = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "test/data/nov20quakedatasmall.atom";
        //String source = "test/data/nov20quakedata.atom";
        //String source = "test/data/earthquakeDataSampleSix1.atom";
        //String source = "test/data/earthquakeDataSampleSix2.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);  
       
        System.out.println("read data for "+list.size()+" quakes");    
        //sortByMagnitude(list);
        //sortByLargestDepth(list);
        //sortByMagnitudeWithBubbleSort(list);
        //sortByMagnitudeWithBubbleSortWithCheck(list);
        sortByMagnitudeWithCheck(list);
        System.out.println("sorted list:");
        showList(list);

    }
    
    public void createCSV() {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "test/data/nov20quakedata.atom";
        String source = "test/data/nov20quakedatasmall.atom";
        //String source = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        dumpCSV(list);
        System.out.println("# quakes read: " + list.size());
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
