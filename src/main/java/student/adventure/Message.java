package student.adventure;

public class Message {
    public static String getDefaultInfo(Player player) {
        String getDescription = player.getCurrentLocation().getDescription();
        String availableDirections = Location.getFormattedStringOfAvailableDirections(player.getCurrentLocation());
        String itemsVisible = "Items visible: " + Location.getStringOfAvailableItems(player.getCurrentLocation());
        return getDescription + "\n" + availableDirections + "\n" + itemsVisible;
    }
}
