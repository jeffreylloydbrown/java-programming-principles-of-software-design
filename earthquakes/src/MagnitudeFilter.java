import java.util.*;

public class MagnitudeFilter implements Filter {

    private double minMagnitude, maxMagnitude;
    private String myName;

    public MagnitudeFilter (double min, double max) { init(null, min, max); }

    public MagnitudeFilter (String name, double min, double max) { init(name, min, max); }

    private void init (String name, double min, double max) {
        if (min > max) {
            double temp = min;
            min = max;
            max = temp;
        }
        minMagnitude = min;
        maxMagnitude = max;
        myName = (name == null || name.isEmpty()) ? "Magnitude" : name;
    }

    public String getName() { return myName; }

    public boolean satisfies(QuakeEntry qe) {
        if (qe == null) return false;
        else return minMagnitude <= qe.getMagnitude() && qe.getMagnitude() <= maxMagnitude;
    }
}
