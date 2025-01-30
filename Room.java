import java.util.ArrayList;
import java.util.HashMap;

public class Room
{
    public String description;
    private HashMap<String, Room> exits;
    private ArrayList<Item> items;
    private ArrayList<Npc> npcs;
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<>();
        items = new ArrayList<>();
        npcs = new ArrayList<>();
    }

    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }
    
    public boolean npcExist(Npc npcToCheck)
    {
        for(Npc npc: npcs)
        {
            if(npcToCheck == npc) return true;
        }
        return false;
    }
    
    public void addNPC(Npc npc)
    {
        if(npcExist(npc)) return;
        else{
            npcs.add(npc);
        }
        
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
    
    public ArrayList<Npc> getNpcs()
    {
        return npcs;
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
        
         if(!npcs.isEmpty())
        {
             description.append("\nAqui você pode ver:");
             for (Npc npc: npcs) 
             {
                 description.append("\n\t- ").append(npc.getDetails()).append(", ");
             }
            
             description.setLength(description.length() - 2);
             description.append(".");
        }
        else{
            description.append("\nAinda não há ninguem por aqui....\n:"); 
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
