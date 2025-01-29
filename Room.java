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
        StringBuilder description = new StringBuilder("Você está " + this.description + ".");

        if (!items.isEmpty()) 
        {
            description.append("\nAqui, você pode ver: ");

            for (Item item : items) 
            {
                description.append("\n\t- ").append(item.getDetails()).append(", ");
            }

            description.setLength(description.length() - 2);
            description.append(".");
        }

        description.append("\n").append(getExitString());

        return description.toString();
    }

    public String getExitString()
    {
        StringBuilder exitString = new StringBuilder("Destinos: ");

        for (String direction : exits.keySet())
        {
            exitString.append("\n\t- ").append(direction).append(", ");
        }
        
        exitString.setLength(exitString.length() - 2);
        exitString.append(".");

        return exitString.toString();
    }
}
