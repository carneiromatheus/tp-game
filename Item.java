
/**
 * This class is used to recognize items and objects that can be used by the player.
 *
 * @author  Augusto Henrique
 * @version 2025.01.24
 */

public class Item
{
    private String itemName;
    private String itemDescription;
    private double itemWeight;
    /*
     * Set item description
     * 
     */
    
    public Item(String itemName)
    {
        this.itemName = itemName;
    }
    
    public void setItemDescription(String itemDescription)
    {
        this.itemDescription = itemDescription;
    }
    /*
     * Returns item description
     */
    public String getItemDescription()
    {
        return itemDescription;
    }
    /*
     * Set item weight
     */
    public void setItemWeight(double itemWeight)
    {
        this.itemWeight = itemWeight;
    }

    public double getItemWeight()
    {
        return itemWeight;
    }

    public String getItemName()
    {
        return itemName;
    }
}