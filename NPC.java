
import java.util.ArrayList;

public class NPC
{
    private String description;
    private String name; 
    private Dialogue speeches;
    public NPC(String name, String description)
    {
         this.description = description;
         this.name = name;
         speeches = new Dialogue();
    }
    
    public String getDetails()
    {
        return name+"\n"+description;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setSpeech(String speech) 
    {
        speeches.add(speech);
    }
    
    public String getSpeech()
    {
        String speech;
        if(speeches.isEmpty()) speeches.setDialogue();
        speech = speeches.getDialogue();
        return speech;
    }
    public void setDialogue()
    {
        speeches.setDialogue();
    }
    
}
