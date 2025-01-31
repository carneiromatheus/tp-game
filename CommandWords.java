public class CommandWords
{
    private static final String[] validCommands = {
        "viajar", "sair", "ajuda", "observar", "usar", "voltar", "pegar", "soltar", "itens", "conversar"
    };

    public CommandWords()
    {
        // nothing to do at the moment...
    }

    public boolean isCommand(String aString)
    {
        for(int i = 0; i < validCommands.length; i++) {
            if(validCommands[i].equalsIgnoreCase(aString))
                return true;
        }

        return false;
    }

    public String getValitCommandWords()
    {
        StringBuilder commands = new StringBuilder();
        
        for(String commandWord : validCommands )
        {
            commands.append(commandWord).append(", ");
        }
        
        commands.setLength(commands.length() - 2);
        commands.append(".");
        
        return commands.toString().trim();
    }
}
