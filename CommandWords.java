public class CommandWords
{
    private static final String[] validCommands = {
        "go", "quit", "help", "look", "eat", "back", "take", "drop", "items", "talk"
    };

    public CommandWords()
    {
        // nothing to do at the moment...
    }

    public boolean isCommand(String aString)
    {
        for(int i = 0; i < validCommands.length; i++) {
            if(validCommands[i].equals(aString))
                return true;
        }

        return false;
    }

    public String getValitCommandWords()
    {
        StringBuilder commands = new StringBuilder();
        
        for(String commandWord : validCommands )
        {
            commands.append(commandWord).append(" ");
        }
        
        return commands.toString().trim();
    }
}
