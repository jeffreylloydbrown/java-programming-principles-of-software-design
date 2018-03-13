public class DistanceFilter implements Filter {
    private Location myLocation = null;
    private double maxDistance;
    private String myName;

    public DistanceFilter (Location location, double distance) { init(null, location, distance); }

    public DistanceFilter (String name, Location location, double distance) {
        init(name, location, distance);
    }

    private void init (String name, Location location, double distance) {
        // `location` being null should throw, but we don't know how to do that yet.
        // And I don't know what happens if a constructor throws.

        // Location's constructor cannot tolerate receiving a null, so protect against that.
        if (location != null) myLocation = new Location(location);
        maxDistance = distance;
        myName = (name == null || name.isEmpty()) ? "Distance" : name;
    }

    public String getName() { return myName; }

    public boolean satisfies(QuakeEntry qe) {
        if (qe == null || myLocation == null) return false;
        else return qe.getLocation().distanceTo(myLocation) < maxDistance;
    }
}
