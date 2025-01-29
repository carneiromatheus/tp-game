import java.util.Stack;
import java.util.ArrayList;

public class Dialogue
{
    public ArrayList<String> speeches;
    public Stack<String> dialogue;
    public Dialogue ()
    {
        speeches  = new ArrayList<>(); 
        dialogue = new Stack<>();
    }
    
    public void add(String speech)
    {
        speeches.add(speech);
    }
    
    public void setDialogue()
    {
        for(int i = speeches.size()-1; i >= 0; i--)
        {
            dialogue.push(speeches.get(i));
        }
    }
    
    /*public void setDialogue()
    {
        for(String speech: speeches) dialogue.push(speech);   
    }*/
    
    public String getDialogue()
    {
        return dialogue.pop();
    }
    
    public boolean isEmpty()
    {
        return dialogue.isEmpty();
    }
}
