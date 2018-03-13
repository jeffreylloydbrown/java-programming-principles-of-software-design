import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatchAllFilterTest {
    @Test
    void addFilter () {
        MatchAllFilter maf = new MatchAllFilter();

        // null filters don't kill things.
        maf.addFilter(null);
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

}