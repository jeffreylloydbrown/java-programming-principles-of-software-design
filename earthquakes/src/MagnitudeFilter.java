import java.util.*;

public class MagnitudeFilter implements Filter {

    private double minMagnitude, maxMagnitude;

    public MagnitudeFilter (double min, double max) {
        if (min > max) {
            double temp = min;
            min = max;
            max = temp;
        }
        minMagnitude = min;
        maxMagnitude = max;
    }

    public boolean satisfies(QuakeEntry qe) {
        if (qe == null) return false;
        else return minMagnitude <= qe.getMagnitude() && qe.getMagnitude() <= maxMagnitude;
    }
}
