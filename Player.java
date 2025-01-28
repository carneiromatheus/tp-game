import java.util.ArrayList;

public class Player
{
    private Room currentRoom;
    private ArrayList<Item> inventory;
    private double maxWeight;

    public Player(Room startingRoom)
    {
        this.currentRoom = startingRoom;
        this.inventory = new ArrayList<>();
        this.maxWeight = 10.0;
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
    
    public double getCurrentWeight()
    {
        double totalWeight = 0;
        
        for (Item item : inventory)
        {
            totalWeight += item.getWeight();
        }
        
        return totalWeight;
    }
    
    public void setMaxWeight(double maxWeight)
    {
        this.maxWeight = maxWeight;
    }
    
    public double getMaxWeight()
    {
        return maxWeight;
    }
}