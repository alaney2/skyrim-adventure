package student.adventure;

import java.util.*;

public class Player {
    private Location currentLocation;
    private Location endingLocation;
    private List<Item> inventory;
    private Map<String, List<String>> commandOptions;

    public Player(Location currentLocation, List<Item> inventory) {
        this.currentLocation = currentLocation;
        this.inventory = inventory;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public Map<String, List<String>> getCommandOptions() {
        return commandOptions;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public void setEndingLocation(Location endingLocation) {
        this.endingLocation = endingLocation;
    }

    public void setInventory(List<Item> inventory) {
        this.inventory = inventory;
    }

    /**
     * Executes a command.
     * @param arguments array of Strings with the first element being the command
     */
    public void executeCommand(String[] arguments) {
        String command = arguments[0].toLowerCase();
        switch(command) {
            case "go":
                if (arguments.length < 2 || arguments[1] == null) {
                    System.out.println("Enter a direction to go");
                } else {
                    goDirection(arguments[1]);
                }
                break;

            case "examine":
                System.out.println(getCurrentLocationInfo());
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
                System.out.println(getStringOfItemsInInventory());
                break;

            case "stormcloak":
                currentLocation = GameEngine.getLocationDictionary().get("Helgen");
                break;

            case "imperial":
                currentLocation = GameEngine.getLocationDictionary().get("BloodyHelgen");
                endingLocation = GameEngine.getLocationDictionary().get("Solitude");
                break;
            default:
                System.out.println("Game should not reach this point!");
        }
    }

    /**
     * Move the player a certain direction from their current location.
     * @param directionName direction to move in
     */
    public void goDirection(String directionName) {
        Map<String, Location> locationDictionary = Layout.generateLocationDictionary(GameEngine.getLayout());
        Map<String, Direction> directionDictionary = Location.generateDirectionDictionary(currentLocation);
        directionName = UserInput.capitalizeFirstLetter(directionName);

        if (!directionDictionary.containsKey(directionName)) {
            System.out.println("You can't go " + "\"" + directionName + "\"!");
        } else {
            String locationName = directionDictionary.get(directionName).getLocation();
            setCurrentLocation(locationDictionary.get(locationName));

            if (currentLocation.getName().equals(endingLocation.getName())) {
                inventory = new ArrayList<>();
                currentLocation.setItems(new ArrayList<>());
                currentLocation.setDirections(new ArrayList<>());
            }

            System.out.println(getCurrentLocationInfo());
        }
    }

    /**
     * Takes an item from the current room and adds it to the player's inventory.
     * @param itemName name of item to take
     */
    public void takeItem(String itemName) {
        String unformattedItemName = itemName;
        itemName = itemName.toLowerCase();
        Map<String, Item> itemDictionary = Location.generateItemDictionary(currentLocation);

        if (!itemDictionary.containsKey(itemName)) {
            System.out.println("There is no item " + "\"" + unformattedItemName + "\" at "
                    + getCurrentLocation().getName() + ".");
        } else {
            inventory.add(itemDictionary.get(itemName));
            currentLocation.removeItem(itemDictionary.get(itemName));
            System.out.println("Item \"" + unformattedItemName + "\" taken.");
        }
    }

    /**
     * Drops an item from the player's inventory and adds it to the current room.
     * @param itemName name of item to drop
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
                System.out.println("Item \"" + unformattedItemName + "\" dropped.");

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
     * @return whether the player has reached the ending location
     */
    public boolean hasReachedEndingLocation() {

        return currentLocation.getName().equals(endingLocation.getName());
    }

    /**
     * Creates a formatted string of items in the player's inventory.
     * @return a formatted string of items in inventory
     */
    public String getStringOfItemsInInventory() {
        final int NUMBER_OF_ITEMS = inventory.size();

        String itemsInInventory = "";
        switch (NUMBER_OF_ITEMS) {
            case 0:

                return itemsInInventory;

            case 1:

                return itemsInInventory + inventory.get(0).getItemName();

            default:
                StringBuilder inventoryBuilder = new StringBuilder();
                for (int i = 0; i < NUMBER_OF_ITEMS - 1; i++) {
                    inventoryBuilder.append(inventory.get(i).getItemName());
                    inventoryBuilder.append(", ");
                }
                inventoryBuilder.append(inventory.get(inventory.size() - 1).getItemName());

                return itemsInInventory + inventoryBuilder.toString();
        }
    }

    /**
     * Creates a list of item names in inventory.
     * @return list of type String containing item names
     */
    public List<String> getAvailableItemsToDrop() {
        List<String> availableItems = new ArrayList<>();
        for (Item item: inventory) {
            availableItems.add(item.getItemName());
        }
        return availableItems;
    }

    /**
     * Gets current location's description, available direction names, and items visible.
     * @return String of location information
     */
    public String getCurrentLocationInfo() {
        String getDescription = currentLocation.getDescription();
        String availableDirections = "You can go: " + Location.getFormattedStringOfAvailableDirections(currentLocation);
        String itemsVisible = "Items visible: " + Location.getStringOfAvailableItems(currentLocation);
        return getDescription + "\n" + availableDirections + "\n" + itemsVisible;
    }

    /**
     * Generates a map of command options the player can execute.
     */
    public void generateCommandOptions() {
        commandOptions = new HashMap<>();
        List<String> availableDirectionNames = currentLocation.getAvailableDirectionNames();
        List<String> availableItemsToTake = currentLocation.getAvailableItemsToTake();
        List<String> availableItemsToDrop = getAvailableItemsToDrop();

        if (currentLocation.getName().equals("ChooseSides")) {
            List<String> blankList = new ArrayList<>();
            blankList.add("");
            commandOptions.put("Imperial", blankList);
            commandOptions.put("Stormcloak", blankList);
        }

        if (availableDirectionNames.size() > 0 && !currentLocation.getName().equals("ChooseSides")) {
            commandOptions.put("go", availableDirectionNames);
        }
        if (availableItemsToTake.size() > 0) {
            commandOptions.put("take", availableItemsToTake);
        }
        if (availableItemsToDrop.size() > 0) {
            commandOptions.put("drop", availableItemsToDrop);
        }
    }
}
