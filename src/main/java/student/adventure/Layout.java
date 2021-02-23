package student.adventure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Layout {
    private String startingLocation;
    private String endingLocation;
    private List<Location> locations;
    private String videoUrl;

    public String getStartingLocation() {
        return startingLocation;
    }

    public String getEndingLocation() {
        return endingLocation;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public String getVideoUrl() { return videoUrl; }

    /**
     * Creates a map with key being location name and value being location object.
     * @param layout class that contains all locations
     * @return map of string location name to location object
     */
    public static Map<String, Location> generateLocationDictionary(Layout layout) {
        Map<String, Location> locationMap = new HashMap<>();
        for (Location location: layout.getLocations()) {
            locationMap.put(location.getName(), location);
        }

        return locationMap;
    }
}
