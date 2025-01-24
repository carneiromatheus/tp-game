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
import java.util.Set;

public class Room
{
    public String description;
    private HashMap<String, Room> exits;

    /**
     * Cria uma sala com sua descrição.
     * A descrição é algo como “uma cozinha” ou “um pátio aberto”.
     * 
     * Cria uma coleção para obter as saídas dessa sala.
     * Inicialmente, ela não tem saída.
     * 
     * @param description A descrição da sala.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<>();
    }

    /**
     * Define as saídas dessa sala.
     * 
     * @param direction Direção da saída.
     * @param neighbor Destino da saída.
     */
    public void setExit(String direction, Room neighbor) 
    {
        if (direction != null && neighbor != null)
        {
            exits.put(direction, neighbor);
        }
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Retorna a sala de uma saída deste Room,
     * Por exemplo, "outside"
     * 
     * @param Uma direção de saída
     * @return A sala referente a direção de saída
     */
    public Room getExit(String direction)
    {
        return exits.get(direction);
    }

    /**
     * Retorna uma descrição das saidas deste Room
     * Por exemplo ," Exits: north west
     * 
     * @return uma descrição dass saídas disponíveis
     */

    public String getExitString()
    {
        String exitString = "Exits:";
        Set<String> keys = exits.keySet();
        
        for (String exit : keys) {
            exitString += " " + exit;
        }
        
        return exitString;
    }

    public String getLongDescription()
    {
        return "You are " + description + "\n" + getExitString(); 
    }
}
