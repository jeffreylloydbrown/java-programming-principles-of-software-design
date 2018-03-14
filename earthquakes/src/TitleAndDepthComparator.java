import java.util.*;

public class TitleAndDepthComparator implements Comparator<QuakeEntry> {
    public int compare(QuakeEntry q1, QuakeEntry q2) {
        // null.compareTo(...) is an exception, obviously.  But so is "".compareTo(null).
        // must protect against both cases.
        String q1Info = q1.getInfo();
        String q2Info = q2.getInfo();
        if (q1Info == null) q1Info = "";
        if (q2Info == null) q2Info = "";

        int infoTest = q1Info.compareTo(q2Info);
        return (infoTest != 0) ? infoTest : Double.compare(q1.getDepth(), q2.getDepth());
    }
}
