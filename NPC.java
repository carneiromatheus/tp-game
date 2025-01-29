
import java.util.Stack;
import java.util.ArrayList;

public class NPC
{
    private String description;
    private String name;
    private Stack<String> speechLog;
    private Stack<String> logBackUp; 
    private int index;
    
    public NPC(String name, String description)
    {
         this.description = description;
         this.name = name;
         speechLog = new Stack<>();
         index = 0;
    }
    
    public String getDetails()
    {
        return name+"\n"+description;
    }
    
    public String getName()
    {
        return name;
    }
    
    public String getSpeech()
    {
        if(speechLog.isEmpty()) speechLog = logBackUp;
        String speech = speechLog.pop();
        return speech;
    }
    
    public void setSpeech(String speech) 
    {
        speechLog.push(speech);
    }
    
    public void setLogBackUp()
    {
        logBackUp = speechLog;
    }
    
}
