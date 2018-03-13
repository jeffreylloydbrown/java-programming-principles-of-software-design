import java.util.ArrayList;

public class MatchAllFilter implements Filter {

    private ArrayList<Filter> filters;

    public MatchAllFilter () {
        filters = new ArrayList<Filter>();
    }

    public String getName() {
        StringBuilder sb = new StringBuilder();
        for (Filter f : filters)
            sb.append(f.getName()+" ");
        return sb.toString().trim();    // there is an extra space on the end that trim removes.
    }

    public void addFilter (Filter f) { if (f != null) filters.add(f); }

    public boolean satisfies (QuakeEntry qe) {
        for (Filter f : filters)
            if (! f.satisfies(qe))
                return false;
        return true;
    }
}
