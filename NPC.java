import java.util.ArrayList;
import java.util.Stack;

public class Npc
{
    private String description;
    private String name; 
    private ArrayList<String> speeches;
    public Stack<String> dialogue;
    public Npc(String name, String description)
    {
        this.description = description;
        this.name = name;
        speeches  = new ArrayList<>(); 
        dialogue = new Stack<>();
    }
    
    public String getDetails()
    {
        return name+"\n"+description;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void addSpeech(String speech)
    {
        speeches.add(speech);
    }
    
    public void setDialogue()
    {
        for(int i = speeches.size()-1; i >= 0; i--) dialogue.push(speeches.get(i));
    }
    
    public String getDialogue()
    {
        if(dialogue.isEmpty()) setDialogue();
        return dialogue.pop();
    }
    
    public boolean isEmpty()
    {
        return dialogue.isEmpty();
    }
    
}
