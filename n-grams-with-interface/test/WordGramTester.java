import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class WordGramTester {
    @Test
    public void testWordGram(){
        String source = "this is a test this is a test this is a test of words";
        String[] words = source.split("\\s+");
        int size = 4;
        for(int index = 0; index <= words.length - size; index += 1) {
            WordGram wg = new WordGram(words,index,size);
            System.out.println(index+"\t"+wg.length()+"\t"+wg);
            assertTrue(source.contains(wg.toString()));
        }
    }

    @Test
    public void testHashCode() {
        String[] words = new String[2];
        words[0] = "word";
        words[1] = "listing";
        WordGram wg = new WordGram(words, 0, words.length);
        assertEquals("word listing".hashCode(), wg.hashCode());
    }

    private void confirmBadSource(String[] s) {
        try {
            WordGram wg = new WordGram(s, 0, 2);  // can't use s.length if testing s is null.
            assertTrue(false, "exception expected, didn't throw any exception");
        } catch (InvalidParameterException ipe) {
            // got the expected exception, this is a pass
        } catch (Exception e) {
            assertTrue(false, "expected InvalidParameterException, got "+e.toString());
        }
    }

    @Test
    public void testBadSource() {
        confirmBadSource(null);
        confirmBadSource(new String[0]);
    }

    @Test
    public void testNullEmptyWords() {
        String[] words = new String[5];
        words[0] = "hello";
        words[1] = "";
        words[2] = "there";
        words[3] = null;
        words[4] = "done";
        WordGram wg = new WordGram(words, 0, words.length);
    }

    private void confirmBadStart(int start) {
        String source = "this is a test this is a test this is a test of words";
        String[] words = source.split("\\s+");
        try {
            WordGram wg = new WordGram(words, start, 2);
            assertTrue(false, "exception expected, didn't throw any exception");
        } catch (InvalidParameterException ipe) {
            // got the expected exception, this is a pass
        } catch (Exception e) {
            assertTrue(false, "expected InvalidParameterException, got "+e.toString());
        }
    }

    @Test
    public void testInvalidStart () {
        confirmBadStart(-1);
        confirmBadStart(47);
    }

    private void confirmBadSize(int size) {
        String source = "this is a test this is a test this is a test of words";
        String[] words = source.split("\\s+");
        try {
            WordGram wg = new WordGram(words, 0, size);
            assertTrue(false, "exception expected, didn't throw any exception");
        } catch (InvalidParameterException ipe) {
            // got the expected exception, this is a pass
        } catch (Exception e) {
            assertTrue(false, "expected InvalidParameterException, got "+e.toString());
        }
    }

    @Test
    public void testInvalidSize() {
        confirmBadSize(-1);
        confirmBadSize(0);
        confirmBadSize(15);
    }

    private void confirmBadIndex(WordGram wg, int index) {
        try {
            wg.wordAt(index);
            assertTrue(false, "exception expected, didn't throw any exception");
        } catch (IndexOutOfBoundsException ipe) {
            // got the expected exception, this is a pass
        } catch (Exception e) {
            assertTrue(false, "expected IndexOutOfBoundsException, got "+e.toString());
        }
    }

    @Test
    public void testInvalidIndex() {
        String source = "this is a test this is a test this is a test of words";
        String[] words = source.split("\\s+");
        WordGram wg = new WordGram(words, 0, 1);
        confirmBadIndex(wg,-1);
        confirmBadIndex(wg, wg.length());
    }

    @Test
    public void testWordAt() {
        String source = "this is a test this is a test this is a test of words";
        String[] words = source.split("\\s+");
        WordGram wg = new WordGram(words, 0, 3);
        assertEquals("this", wg.wordAt(0));
        assertEquals("is", wg.wordAt(1));
        assertEquals("a", wg.wordAt(2));
    }

    @Test
    public void testToString() {
        String source = "this is a test this is a test this is a test of words";
        String[] words = source.split("\\s+");
        WordGram wg = new WordGram(words, 0, 3);
        assertEquals("this is a", wg.toString());
    }

    @Test
    public void testWordGramEquals(){
        String source = "this is a test this is a test this is a test of words";
        String[] words = source.split("\\s+");
        ArrayList<WordGram> list = new ArrayList<WordGram>();
        int size = 4;
        for(int index = 0; index <= words.length - size; index += 1) {
            WordGram wg = new WordGram(words,index,size);
            list.add(wg);
        }
        WordGram first = list.get(0);
        System.out.println("checking "+first);
        for(int k=0; k < list.size(); k++){
            //if (first == list.get(k)) {
            if (first.equals(list.get(k))) {
                System.out.println("matched at "+k+" "+list.get(k));
            }
        }
    }

    @Test
    public void testShiftAdd() {
        String source = "this is a test this is a test this is a test of words";
        String[] words = source.split("\\s+");
        WordGram start = new WordGram(words, 1, 3);
        assertEquals("is a test", start.toString());

        WordGram wg = start.shiftAdd("yes");
        assertEquals("a test yes", wg.toString());
        assertEquals("is a test", start.toString());

        wg = start.shiftAdd("");
        assertEquals("a test", wg.toString());
        assertEquals(start.length(), wg.length());
        assertEquals("is a test", start.toString());

        wg = start.shiftAdd(null);
        assertEquals("a test", wg.toString());
        assertEquals(start.length(), wg.length());
        assertEquals("is a test", start.toString());
    }
}