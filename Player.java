import java.util.ArrayList;

/**
 * Escreva uma descrição da classe Player aqui.
 * 
 * @author (seu nome) 
 * @version (um número da versão ou uma data)
 */
public class Player
{
    // variáveis de instância - substitua o exemplo abaixo pelo seu próprio
    private String playerName;
    private Game game;
    private Room location;
    private ArrayList<Item> items;
    
    /**
     * Construtor para objetos da classe Player
     */
    public Player(String name, Game game)
    {
        playerName = name;
        game = game;
        game.getCurrentRoom();
        items = new ArrayList<>();
    }
    
    public void setPlayerGame(Game game)
    {
        this.game = game;
    }
    
    public String getPlayerName()
    {
        return playerName;
    }
    
    public void setLocation()
    {
        location = game.getCurrentRoom();
    }
    
    public void takeItem(String itemName)
    {
        Item item = location.getItem(itemName);
        items.add(item);
    }
    
    public void dropItem(String itemName)
    {
        Item item = location.getItem(itemName);
        items.remove(item);
    }
    
    public String getItemString()
    {
        String itemString = "";
        if(items.size() == 0) 
        {
            itemString = "The player has no items!";
            return itemString;
        }
        for(Item item: items) 
        {
            itemString = itemString+" "+ item.getItemName();    
        }
        return itemString;
    }
}
    
    
