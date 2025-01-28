import java.util.ArrayList;
import java.util.HashMap;

public class Room
{
    public String description;
    private HashMap<String, Room> exits;
    private ArrayList<Item> items;

    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<>();
        items = new ArrayList<>();
    }

    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    public Room getExit(String direction)
    {
        return exits.get(direction);
    }

    public void addItem(Item item)
    {
        items.add(item);
    }
    
    public void removeItem(Item item)
    {
        items.remove(item);
    }

    public ArrayList<Item> getItems()
    {
        return items;
    }

    public String getLongDescription() 
    {
        StringBuilder description = new StringBuilder("You are " + this.description + ".\n");
        description.append(getExitString());

        if (!items.isEmpty()) 
        {
            description.append("\nYou see here: ");

            for (Item item : items) 
            {
                description.append(item.getDetails()).append(", ");
            }

            description.setLength(description.length() - 2);
        }

        return description.toString();
    }

    public String getExitString()
    {
        StringBuilder exitString = new StringBuilder("Exits:");

        for (String direction : exits.keySet())
        {
            exitString.append(" ").append(direction);
        }

        return exitString.toString();
    }
}
