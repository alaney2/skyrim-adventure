package student.adventure;

import java.util.List;

public class SkyrimMap {
    private String startingLocation;
    private String endingLocation;
    private List<Location> locations;

    public String getStartingLocation() {
        return startingLocation;
    }

    public String getEndingLocation() {
        return endingLocation;
    }

    public List<Location> getLocations() {
        return locations;
    }
}
