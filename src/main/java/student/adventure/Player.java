package student.adventure;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Player {
    private Location currentLocation;
    private Location endingLocation;
    private List<Item> inventory;

    public Player(Location currentLocation, List<Item> inventory) {
        this.currentLocation = currentLocation;
        this.inventory = inventory;
    }

    public Location getEndingLocation() {
        return endingLocation;
    }

    public void setEndingLocation(Location endingLocation) {
        this.endingLocation = endingLocation;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public List<Item> getInventory() {
        return inventory;
    }
    public void executeCommand(String command, String[] arguments) {
        switch(command) {
            case "go":
                if (arguments.length < 2 || arguments[1] == null) {
                    System.out.println("Enter a direction to go");
                } else {
                    goDirection(arguments[1]);
                }
                break;
            case "take":
                if (arguments.length < 2 || arguments[1] == null) {
                    System.out.println("Enter an item to take");
                } else {
                    takeItem(arguments[1]);
                }
                break;
            case "drop":
                if (arguments.length < 2 || arguments[1] == null) {
                    System.out.println("Enter an item to drop");
                } else {
                    dropItem(arguments[1]);
                }
                break;
            default:
        }
    }

    public void goDirection(String directionName) {
        Map<String, Direction> directionDictionary = Location.generateDirectionDictionary(currentLocation);
        directionName = UserInput.capitalizeFirstLetter(directionName);

        if (!directionDictionary.containsKey(directionName)) {
            System.out.println("You can't go " + "\"" + directionName + "\"!");
        } else {
            String locationName = directionDictionary.get(directionName).getLocation();
            this.setCurrentLocation(GameEngine.locationDictionary.get(locationName));
        }
    }

    public void takeItem(String itemName) {
        String unformattedItemName = itemName;
        itemName = itemName.toLowerCase();
        Map<String, Item> itemDictionary = Location.generateItemDictionary(currentLocation);
        if (!itemDictionary.containsKey(itemName)) {
            System.out.println("There is no item " + "\"" + unformattedItemName + "\" in the room.");
        } else {
            inventory.add(itemDictionary.get(itemName));
            currentLocation.removeItem(itemDictionary.get(itemName));
        }
    }

    public void dropItem(String itemName) {
        boolean isItemInInventory = false;
        String unformattedItemName = itemName;
        itemName = itemName.toLowerCase();
        int inventoryIndex = 0;
        while (inventoryIndex < inventory.size() && !isItemInInventory) {
            if (inventory.get(inventoryIndex).getItemName().equalsIgnoreCase(itemName)) {
                Item validItem = inventory.get(inventoryIndex);
                inventory.remove(validItem);
                currentLocation.addItem(validItem);

                isItemInInventory = true;
            }
            inventoryIndex += 1;
        }
        if (!isItemInInventory) {
            System.out.println("You don't have " + "\"" + unformattedItemName + "\"!");
        }
    }

    public static boolean playerHasReachedEndingLocation(Player player) {
        return player.getCurrentLocation().getName().equals("Windhelm");
    }
}
