package student.adventure;

import java.util.List;
import java.util.Map;

public class Player {
    private Location currentLocation;
    private List<Item> inventory;

    public Player(Location currentLocation, List<Item> inventory) {
        this.currentLocation = currentLocation;
        this.inventory = inventory;
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

    public void addItem(Item item) {
        this.inventory.add(item);
    }

    public void goDirection(String directionName) {
        Map<String, Direction> directionDictionary = Location.generateDirectionDictionary(currentLocation);
        directionName = UserInput.capitalizeFirstLetter(directionName);

        if (!directionDictionary.containsKey(directionName)) {
            System.out.println("That is an invalid direction!");
        } else {
            String locationName = directionDictionary.get(directionName).getLocation();
            this.setCurrentLocation(GameEngine.locationDictionary.get(locationName));
        }
    }
}
