import java.util.ArrayList;

public class MatchAllFilter implements Filter {

    private ArrayList<Filter> filters;

    public MatchAllFilter () {
        filters = new ArrayList<Filter>();
    }

    public void addFilter (Filter f) { if (f != null) filters.add(f); }

    public boolean satisfies (QuakeEntry qe) {
        for (Filter f : filters)
            if (! f.satisfies(qe))
                return false;
        return true;
    }
}
