public class DepthFilter implements Filter {

    private double minDepth, maxDepth;
    private String myName;

    public DepthFilter (double min, double max) { init(null, min, max); }

    public DepthFilter (String name, double min, double max) { init(name, min, max); }

    private void init (String name, double min, double max) {
        if (min > max) {
            double temp = min;
            min = max;
            max = temp;
        }
        minDepth = min;
        maxDepth = max;
        myName = (name == null || name.isEmpty()) ? "Depth" : name;
    }

    public String getName() { return myName; }

    public boolean satisfies(QuakeEntry qe) {
        if (qe == null) return false;
        else return minDepth <= qe.getDepth() && qe.getDepth() <= maxDepth;
    }
}
