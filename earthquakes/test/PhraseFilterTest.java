import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhraseFilterTest {
    @Test
    void satisfies () {
        QuakeEntry quake = new QuakeEntry(1.0, 2.0, 3.0, "a test quake", 4.0);
        QuakeEntry emptyInfo = new QuakeEntry(1.0, 2.0, 3.0, "", 4.0);
        QuakeEntry nullInfo = new QuakeEntry(1.0, 2.0, 3.0, null, 4.0);

        // null safety
        assertFalse(new PhraseFilter("", "qu").satisfies(quake));
        assertFalse(new PhraseFilter(null, "qu").satisfies(quake));
        assertFalse(new PhraseFilter("start", "").satisfies(quake));
        assertFalse(new PhraseFilter("start", null).satisfies(quake));
        assertFalse(new PhraseFilter("start", "qu").satisfies(null));

        // bad commands
        assertFalse(new PhraseFilter("somewhere", "qu").satisfies(quake));

        // check the commands
        assertTrue(new PhraseFilter("start", "a test").satisfies(quake));
        assertFalse(new PhraseFilter("Start", "a test").satisfies(quake));
        assertFalse(new PhraseFilter("start", "qu").satisfies(quake));
        assertTrue(new PhraseFilter("end", "quake").satisfies(quake));
        assertFalse(new PhraseFilter("End", "quake").satisfies(quake));
        assertFalse(new PhraseFilter("end", "qu").satisfies(quake));
        assertTrue(new PhraseFilter("any", "qu").satisfies(quake));
        assertFalse(new PhraseFilter("Any", "qu").satisfies(quake));
        assertFalse(new PhraseFilter("any", "#@").satisfies(quake));

        // should all be false for empty or null info strings.
        assertFalse(new PhraseFilter("start", "a test").satisfies(emptyInfo));
        assertFalse(new PhraseFilter("start", "a test").satisfies(nullInfo));
        assertFalse(new PhraseFilter("end", "quake").satisfies(emptyInfo));
        assertFalse(new PhraseFilter("end", "quake").satisfies(nullInfo));
        assertFalse(new PhraseFilter("any", "qu").satisfies(emptyInfo));
        assertFalse(new PhraseFilter("any", "qu").satisfies(nullInfo));
    }

}