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

    public static Map<String, Direction> generateDirectionDictionary(Location location) {
        Map<String, Direction> directionMap = new HashMap<>();
        for (Direction direction: location.getDirections()) {
            directionMap.put(direction.getDirectionName(), direction);
        }

        return directionMap;
    }

    public static String getFormattedStringOfAvailableDirections(Location location) {
        final int NUMBER_OF_DIRECTIONS = location.getDirections().size();
        switch (NUMBER_OF_DIRECTIONS) {
            case 0:
                return "";
            case 1:
                return location.getDirections().get(0).getDirectionName();
            case 2:
                return location.getDirections().get(0).getDirectionName()
                        + " and " + location.getDirections().get(1).getDirectionName();
            default:
                String availableDirections = "";
                for (int i = 0; i < NUMBER_OF_DIRECTIONS - 1; i++) {
                    availableDirections = availableDirections
                            + location.getDirections().get(i).getDirectionName() + ", ";
                }
                availableDirections += "and " + location.getDirections()
                        .get(NUMBER_OF_DIRECTIONS - 1).getDirectionName();

                return availableDirections;
        }
    }
}
