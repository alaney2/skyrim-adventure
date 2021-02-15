package student.adventure;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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

        // Two directions: North and West
        // Three directions: North, South, and West
        switch (NUMBER_OF_DIRECTIONS) {
            case 0:
                return "";
            case 1:
                return location.getDirections().get(0).getDirectionName();
            case 2:
                return location.getDirections().get(0).getDirectionName()
                        + " or " + location.getDirections().get(1).getDirectionName();
            default:
                StringBuilder availableDirectionsBuilder = new StringBuilder();
                for (int i = 0; i < NUMBER_OF_DIRECTIONS - 1; i++) {
                    availableDirectionsBuilder.append(location.getDirections().get(i).getDirectionName());
                    availableDirectionsBuilder.append(", ");
                }
                availableDirectionsBuilder.append("or ");
                availableDirectionsBuilder.append(location.getDirections().get(NUMBER_OF_DIRECTIONS - 1).getDirectionName());

                return availableDirectionsBuilder.toString();
        }
    }

    public static String getStringOfAvailableItems(Location location) {
        final int NUMBER_OF_ITEMS = location.getItems().size();

        switch (NUMBER_OF_ITEMS) {
            case 0:
                return "";
            case 1:
                return location.getItems().get(0).getItemName();
            default:
                StringBuilder availableItemsBuilder = new StringBuilder();
                for (int i = 0; i < NUMBER_OF_ITEMS; i++) {
                    availableItemsBuilder.append(location.getItems().get(i).getItemName());
                    availableItemsBuilder.append(", ");
                }
                availableItemsBuilder.append(location.getItems().get(location.getItems().size() - 1).getItemName());

                return availableItemsBuilder.toString();
        }
    }
}
