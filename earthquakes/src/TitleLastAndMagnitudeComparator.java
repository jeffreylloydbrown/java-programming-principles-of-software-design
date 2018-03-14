import java.util.*;

/** Compare two `QuakeEntry` objects by the last word in their title, and then by magnitude. */
public class TitleLastAndMagnitudeComparator implements Comparator<QuakeEntry> {
    public int compare(QuakeEntry q1, QuakeEntry q2) {
        // null.compareTo(...) throws an exception, obviously.  But so does "".compareTo(null).
        // must protect against both cases.
        String q1Info = q1.getInfo();
        String q2Info = q2.getInfo();
        if (q1Info == null) q1Info = "";
        if (q2Info == null) q2Info = "";

        int infoTest = lastWord(q1Info).compareTo(lastWord(q2Info));
        return (infoTest != 0) ? infoTest : Double.compare(q1.getMagnitude(), q2.getMagnitude());
    }

    // Split the string into its words, then return the final word.
    private String lastWord (String s) {
        String[] words = s.split("\\W",0);
        // If the title we get is nothing but white space, `words` will have length zero.
        // If we then return the empty string, we end up not comparing correctly empty to not empty.
        // So instead we will return s in that case, which will compare correctly against empty.
        // Any non-empty string would do to return, and `s` is handy.
        return (words.length == 0) ? s : words[words.length-1];
    }
}
