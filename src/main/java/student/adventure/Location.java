package student.adventure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Location {
    private String name;
    private String description;
    private List<Direction> directions;
    private List<Item> items;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Direction> getDirections() {
        return directions;
    }

    public List<Item> getItems() {
        return items;
    }

    public static Map<String, Direction> generateDirectionMap(Location location) {
        Map<String, Direction> directionMap = new HashMap<>();
        for (Direction direction: location.getDirections()) {
            directionMap.put(direction.getDirectionName(), direction);
        }

        return directionMap;
    }
}
