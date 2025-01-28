import java.util.ArrayList;

public class Player
{
    private Room currentRoom;
    private ArrayList<Item> inventory;

    public Player(Room startingRoom)
    {
        this.currentRoom = startingRoom;
        this.inventory = new ArrayList<>();
    }

    public Room getCurrentRoom()
    {
        return currentRoom;
    }

    public void setCurrentRoom(Room newRoom)
    {
        currentRoom = newRoom;
    }

    public void addItemToInventory(Item item)
    {
        inventory.add(item);
    }

    public void removeItemFromInventory(Item item)
    {
        inventory.remove(item);
    }

    public ArrayList<Item> getInventory()
    {
        return inventory;
    }

    public String getInventoryDescription()
    {
        if (inventory.isEmpty())
        {
            return "You inventory is empty.";
        }

        StringBuilder inventoryDescription = new StringBuilder("You are carrying:");
        
        for (Item item : inventory)
        {
            inventoryDescription.append("\n- ").append(item.getDetails());
        }
        
        return inventoryDescription.toString();
    }
}