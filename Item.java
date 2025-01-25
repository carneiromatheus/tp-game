
/**
 * This class is used to recognize items and objects that can be used by the player.
 *
 * @author  Augusto Henrique
 * @version 2025.01.24
 */

public class Item
{
    private String itemDescription;

    /**
     * Constructor - initialise the class item.
     */
    public Item(String description)
    {
        itemDescription = description;
    }
    /*
     * Returns item description
     */
    public String getItemDescription() {
        return itemDescription;
    }

}