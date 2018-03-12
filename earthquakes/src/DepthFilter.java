public class DepthFilter implements Filter {

    private double minDepth, maxDepth;

    public DepthFilter (double min, double max) {
        if (min > max) {
            double temp = min;
            min = max;
            max = temp;
        }
        minDepth = min;
        maxDepth = max;
    }

    public boolean satisfies(QuakeEntry qe) {
        if (qe == null) return false;
        else return minDepth <= qe.getDepth() && qe.getDepth() <= maxDepth;
    }
}
