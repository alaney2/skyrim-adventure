package student.adventure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Layout {
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

    public static Map<String, Location> generateLocationDictionary(Layout layout) {
        Map<String, Location> locationMap = new HashMap<>();
        for (Location location: layout.getLocations()) {
            locationMap.put(location.getName(), location);
        }

        return locationMap;
    }
}
