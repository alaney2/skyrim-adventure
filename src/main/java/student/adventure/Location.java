package student.adventure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Location {
    private String name;
    private String description;
    private List<Direction> directions;
    private List<Item> items;
    private String imageUrl;

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

    public String getImageUrl() { return imageUrl; }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void setDirections(List<Direction> directions) {
        this.directions = directions;
    }

    /**
     * Creates a map with key being direction name and value being direction object.
     * @param location location of available directions
     * @return map of string direction name to direction object
     */
    public static Map<String, Direction> generateDirectionDictionary(Location location) {
        Map<String, Direction> directionDictionary = new HashMap<>();
        for (Direction direction: location.getDirections()) {
            directionDictionary.put(direction.getDirectionName(), direction);
        }

        return directionDictionary;
    }

    /**
     * Creates a list of direction names the player can go to.
     * @return list of String direction names
     */
    public List<String> getAvailableDirectionNames() {
        List<String> availableDirectionNames = new ArrayList<>();
        for (Direction direction: directions) {
            availableDirectionNames.add(direction.getDirectionName());
        }
        return availableDirectionNames;
    }

    /**
     * Creates a map with key being item name and value being item object.
     * @param location location of items
     * @return map of string item name to item object
     */
    public static Map<String, Item> generateItemDictionary(Location location) {
        Map<String, Item> itemDictionary = new HashMap<>();
        for (Item item: location.getItems()) {
            itemDictionary.put(item.getItemName().toLowerCase(), item);
        }

        return itemDictionary;
    }

    /**
     * Creates a list of item names in the current location.
     * @return list of String item names
     */
    public List<String> getAvailableItemsToTake() {
        List<String> availableItems = new ArrayList<>();
        for (Item item: items) {
            availableItems.add(item.getItemName());
        }
        return availableItems;
    }

    /**
     * Creates a formatted string of available directions from current location.
     * @param location location to get directions from
     * @return a string of available direction names
     */
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

    /**
     * Creates a string of available items in the current location.
     * @param location location to get items from
     * @return String of item names
     */
    public static String getStringOfAvailableItems(Location location) {
        final int NUMBER_OF_ITEMS = location.getItems().size();

        switch (NUMBER_OF_ITEMS) {
            case 0:
                return "";
            case 1:
                return location.getItems().get(0).getItemName();
            default:
                StringBuilder availableItemsBuilder = new StringBuilder();
                for (int i = 0; i < NUMBER_OF_ITEMS - 1; i++) {
                    availableItemsBuilder.append(location.getItems().get(i).getItemName());
                    availableItemsBuilder.append(", ");
                }
                availableItemsBuilder.append(location.getItems().get(location.getItems().size() - 1).getItemName());

                return availableItemsBuilder.toString();
        }
    }
}
