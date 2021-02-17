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

    /**
     * Executes a command.
     * @param arguments Array of Strings with the first element being the command.
     */
    public void executeCommand(String[] arguments) {
        String command = arguments[0].toLowerCase();
        switch(command) {
            case "go":
                if (arguments.length < 2 || arguments[1] == null) {
                    System.out.println("Enter a direction to go");
                } else {
                    goDirection(arguments[1]);
                    GameEngine.printDefaultInfo();
                }
                break;
            case "examine":
                GameEngine.printDefaultInfo();
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
            case "inventory":
                System.out.println(getStringOfItemsInInventory(GameEngine.player));
                break;
            default:
        }
    }

    /**
     * Move the player a certain direction from their current location.
     * @param directionName The direction to move in.
     */
    public String goDirection(String directionName) {
        Map<String, Direction> directionDictionary = Location.generateDirectionDictionary(currentLocation);
        directionName = UserInput.capitalizeFirstLetter(directionName);

        if (!directionDictionary.containsKey(directionName)) {
            System.out.println("You can't go " + "\"" + directionName + "\"!");
            return "You can't go " + "\"" + directionName + "\"!";
        } else {
            String locationName = directionDictionary.get(directionName).getLocation();
            this.setCurrentLocation(GameEngine.locationDictionary.get(locationName));
            return locationName;
        }
    }

    /**
     * Takes an item from the current room and adds it to the player's inventory.
     * @param itemName Name of item to take.
     */
    public void takeItem(String itemName) {
        String unformattedItemName = itemName;
        itemName = itemName.toLowerCase();
        Map<String, Item> itemDictionary = Location.generateItemDictionary(currentLocation);
        if (!itemDictionary.containsKey(itemName)) {
            System.out.println("There is no item " + "\"" + unformattedItemName + "\" in the room.");
        } else {
            inventory.add(itemDictionary.get(itemName));
            currentLocation.removeItem(itemDictionary.get(itemName));
            System.out.println("Item " + unformattedItemName + " taken.");
        }
    }

    /**
     * Drops an item from the player's inventory and adds it to the current room.
     * @param itemName Name of item to drop.
     */
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
                System.out.println("Item " + unformattedItemName + " dropped.");

                isItemInInventory = true;
            }
            inventoryIndex += 1;
        }
        if (!isItemInInventory) {
            System.out.println("You don't have " + "\"" + unformattedItemName + "\"!");
        }
    }

    /**
     * Checks if player has reached the ending location.
     * @param player An instance of Player.
     * @return Whether the player has reached the ending location.
     */
    public static boolean playerHasReachedEndingLocation(Player player) {
        return player.getCurrentLocation().getName().equals("Windhelm");
    }

    public static String getStringOfItemsInInventory(Player player) {
        final int NUMBER_OF_ITEMS = player.getInventory().size();

        String itemsInInventory = "You have";
        switch (NUMBER_OF_ITEMS) {
            case 0:
                return itemsInInventory + " nothing in your inventory.";
            case 1:
                itemsInInventory += ": ";
                return itemsInInventory + player.getInventory().get(0).getItemName();
            default:
                itemsInInventory += ": ";
                StringBuilder inventoryBuilder = new StringBuilder();
                for (int i = 0; i < NUMBER_OF_ITEMS - 1; i++) {
                    inventoryBuilder.append(player.getInventory().get(i).getItemName());
                    inventoryBuilder.append(", ");
                }
                inventoryBuilder.append(player.getInventory().get(player.getInventory().size() - 1).getItemName());

                return itemsInInventory + inventoryBuilder.toString();
        }
    }
}
