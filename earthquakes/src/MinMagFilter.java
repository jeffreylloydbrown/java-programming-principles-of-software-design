
/**
 * Write a description of class MinMaxFilter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MinMagFilter implements Filter
{
    private double magMin;
    private String myName;

    public MinMagFilter (double min) { init(null, min); }

    public MinMagFilter (String name, double min) { init(name, min); }
    
    private void init (String name, double min) {
        magMin = min;
        myName = (name == null || name.isEmpty()) ? "MinMag" : name;
    }

    public String getName() { return myName; }

    public boolean satisfies(QuakeEntry qe) { 
        if (qe == null) return false;
        else return qe.getMagnitude() >= magMin;
    } 

}
