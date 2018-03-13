import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MatchAllFilterTest {
    @Test
    void addFilter () {
        MatchAllFilter maf = new MatchAllFilter();

        // null filters don't kill things and don't get added to filter list.
        maf.addFilter(null);
        maf.addFilter(new DepthFilter(10, 20));
        assertEquals("Depth", maf.getName());
    }

    @Test
    void getName () {
        MatchAllFilter maf = new MatchAllFilter();
        assertEquals("", maf.getName());
        maf.addFilter(new PhraseFilter("start", "q"));
        assertEquals("Phrase", maf.getName());
        maf.addFilter(new DepthFilter("my depth filter", 10000, 20000));
        maf.addFilter(new MagnitudeFilter(2,4));
        assertEquals("Phrase my depth filter Magnitude", maf.getName());
    }

    @Test
    void satisfies () {
        MatchAllFilter maf = new MatchAllFilter();
        QuakeEntry qe = new QuakeEntry(1.0, 2.0, 3.0, "a test quake", 4.0);
        // not adding filters should let qe pass thru it.
        assertTrue(maf.satisfies(qe));
        // this single magnitude filter should block it.
        maf.addFilter(new MagnitudeFilter(4.0, 5.0));
        assertFalse(maf.satisfies(qe));

        // this quadruple filter should let q1 thru and block q2
        maf = new MatchAllFilter();
        maf.addFilter(new DepthFilter(-1000, 0));
        maf.addFilter(null);
        maf.addFilter(new PhraseFilter("end", "Texas"));
        maf.addFilter(new PhraseFilter("start", "Explosion"));
        QuakeEntry q1 = new QuakeEntry(0,0,2.4, "Explosion - fertilizer plant in West, Texas", 0);
        QuakeEntry q2 = new QuakeEntry(0, 0, 0.0, "Explosion - texas", -200); // not Texas
        assertTrue(maf.satisfies(q1));
        assertFalse(maf.satisfies(q2));
    }

    private void showQuakes (ArrayList<QuakeEntry> list) {
        for (QuakeEntry qe : list)
            System.out.println(qe);
    }

    @Test
    void testMatchAllFilter () {
        // from assignment
        MatchAllFilter maf = new MatchAllFilter();
        EarthQuakeClient2 ec = new EarthQuakeClient2();
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "test/data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for " + list.size() + " quakes");
        maf.addFilter(new MagnitudeFilter(0.0, 2.0));
        maf.addFilter(new DepthFilter(-100000, -10000));
        maf.addFilter(new PhraseFilter("any", "a"));
        ArrayList<QuakeEntry> res = ec.filter(list, maf);
        assertEquals(2, res.size());
        showQuakes(res);
        System.out.println("Filters used are: "+maf.getName());
    }

    @Test
    void testMatchAllFilter2 () {
        // from assignment
        MatchAllFilter maf = new MatchAllFilter();
        EarthQuakeClient2 ec = new EarthQuakeClient2();
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "test/data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for " + list.size() + " quakes");
        maf.addFilter(new MagnitudeFilter(0.0, 3.0));
        Location tulsa = new Location(36.1314, -95.9372);
        maf.addFilter(new DistanceFilter(tulsa, 10000*1000));
        maf.addFilter(new PhraseFilter("any", "Ca"));
        ArrayList<QuakeEntry> res = ec.filter(list, maf);
        assertEquals(7, res.size());
        showQuakes(res);
    }

}