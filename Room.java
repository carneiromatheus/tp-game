/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */
import java.util.HashMap;

public class Room
{
    public String description;
    private Room northExit;
    private Room southExit;
    private Room eastExit;
    private Room westExit;
    private String ExitString = " ";

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
    }

    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     * @param north The north exit.
     * @param east The east east.
     * @param south The south exit.
     * @param west The west exit.
     */
    public void setExit(String direction, Room neighbour)
    {
        if(direction == "north") {
             northExit = neighbour;
        }
        if(direction == "east") {
            eastExit = neighbour;
        }
        if(direction == "south") {
            southExit = neighbour;
        }
        if(direction == "west") {
            westExit = neighbour;
        }
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }

    public Room getExits(String direction)
    {
        if(direction == "north") return northExit;
        if(direction == "east") return eastExit;
        if(direction == "south") return southExit;
        else return westExit;
    }

    /**
    *Retorna uma descrição das saidas deste Room, por exemplo ," Exits: north west @return uma descrição dass saídas disponíveis
     */

    public String getExitString()
    {
        ExitString = "";
        if(getExits("north") != null) ExitString = ExitString + "north ";
        if(getExits("east") != null) ExitString = ExitString + "east ";
        if(getExits("south") != null) ExitString = ExitString + "south ";
        if(getExits("west") != null) ExitString = ExitString + "west ";
        return ExitString;
    }

}
