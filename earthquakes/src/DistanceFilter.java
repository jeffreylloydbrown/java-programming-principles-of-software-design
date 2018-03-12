public class DistanceFilter implements Filter {
    private Location myLocation = null;
    private double maxDistance;

    public DistanceFilter (Location location, double distance) {
        // `location` being null should throw, but we don't know how to do that yet.
        // And I don't know what happens if a constructor throws.

        // Location's constructor cannot tolerate receiving a null, so protect against that.
        if (location != null) myLocation = new Location(location);
        maxDistance = distance;
    }

    public boolean satisfies(QuakeEntry qe) {
        if (qe == null || myLocation == null) return false;
        else return qe.getLocation().distanceTo(myLocation) < maxDistance;
    }
}
