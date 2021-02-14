package student.adventure;

import java.util.List;

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
}
