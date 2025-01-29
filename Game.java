import java.util.Stack;

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Stack<Room> roomLog;
    private Player player;

    public Game() 
    {
        createRooms();
        parser = new Parser();
        roomLog = new Stack<>();
        player = new Player(currentRoom);
    }

    private void createRooms()
    {
         NPC João;
        
        João = new NPC("João","Agente da Agência Brasileira de Inteligência");
        João.setSpeech("Acredito que ela tenha ido para o norte");
        João.setSpeech("Eu conheço uma tal de Carma, o que deseja saber sobre ela?");
        João.setLogBackUp();

        Room brasilia, salvador, beloHorizonte, saoPaulo, coritiba, rioDeJaneiro;

        brasilia = new Room("na sede da ABIN, em Brasília");
        salvador = new Room("em Salvador, Bahia");
        beloHorizonte = new Room("em Belo Horizonte, Minas Gerais");
        saoPaulo = new Room("em São Paulo, capital");
        coritiba = new Room("em Curitiba, Paraná");
        rioDeJaneiro = new Room("em Rio de Janeiro, capital");
            
        
        brasilia.setExit("norte", salvador);
        brasilia.setExit("leste", beloHorizonte);
        brasilia.addNPC(João);
        
        salvador.setExit("oeste", brasilia);
        salvador.setExit("sul", beloHorizonte);

        beloHorizonte.setExit("norte", salvador);
        beloHorizonte.setExit("leste", rioDeJaneiro);
        beloHorizonte.setExit("sul", saoPaulo);

        saoPaulo.setExit("norte", beloHorizonte);
        saoPaulo.setExit("sul", coritiba);

        coritiba.setExit("norte", saoPaulo);

        rioDeJaneiro.setExit("oeste", beloHorizonte);
        rioDeJaneiro.setExit("sul", saoPaulo);

        // Depois será implementado o método para usar o mapa.
        // Ao usar o mapa as chaves das saídas receberá o nome da cidade, facilitando o jogo.
        // Ex.: ao invés de saídas: norte, sul.. será saídas: belo horizonte, curitiba...
        brasilia.addItem(new Item("um mapa", 0.2));

        saoPaulo.addItem(new Item("a mysterious book", 1.2));
        saoPaulo.addItem(new Item("a broken computer", 3.5));
        saoPaulo.addItem(new Item("a heavy box", 7.5));
        saoPaulo.addItem(new Item("a magic mushroom", 0.3));

        currentRoom = brasilia;
    }

    public void play() 
    {            
        printWelcome();

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
            System.out.println();
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    private void printWelcome()
    {
        System.out.println("\nABIN (Agência Brasileira de Inteligência)");
        /*System.out.println("""
        
        Arquivo: Carmen Sandiego: Operação Brasil
        
        Bem vindo, Agente,
        
        Você foi encarregado de capturar a ladra internacional Carmen Sandiego, que roubou a
        Joia Imperial Brasileira (parte da coroa de Dom Pedro II). Ela está escondida em uma
        das grandes cidades brasileiras.
        
        Você precisa seguir suas pistas, interagir com informantes, e capturá-la antes que ela
        fuja do país para sempre.
        """);
        System.out.println("Digite 'help' se você precisar de ajuda.\n");
        printLocationInfo();
        System.out.println();*/
    }

    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) 
        {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) printHelp();
        else if (commandWord.equals("go")) goRoom(command);
        else if (commandWord.equals("look")) look(command);
        else if (commandWord.equals("eat")) eat(command);
        else if (commandWord.equals("quit")) wantToQuit = quit(command);
        else if (commandWord.equals("back")) back(command);
        else if (commandWord.equals("take")) take(command);
        else if (commandWord.equals("drop")) drop(command);
        else if (commandWord.equals("items")) items(command);
        else if (commandWord.equals("talk")) talk(command);
        return wantToQuit;
    }

    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println(parser.getValidCommands());
    }
    
     private void talk(Command command)
    {
        if(!command.hasSecondWord())
        {
            System.out.println("Talk to who?");
            return;
        }
        else
        {
            String npcToTalk = command.getSecondWord();
            Room currentRoom = player.getCurrentRoom();
    
            for (NPC npc: currentRoom.getNpcs())
            {
                if (npc.getName().equalsIgnoreCase(npcToTalk))
                {
                    System.out.print(npc.getSpeech());
                    break;
                }
                else System.out.println("I dont know this person");
            }    
        }
    }


    private void goRoom(Command command)
    {
        if(!command.hasSecondWord()) {
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();
        Room nextRoom = player.getCurrentRoom().getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        } 
        else {
            roomLog.push(player.getCurrentRoom());
            player.setCurrentRoom(nextRoom);
            printLocationInfo();
        }
    }

    private void look(Command command) 
    {
        if(command.hasSecondWord())
        {
            System.out.println("I don't know what you mean...");
            return;
        }
        else printLocationInfo();
    }

    private void take(Command command)
    {
        if(!command.hasSecondWord())
        {
            System.out.println("Take what?");
            return;
        }

        String itemName = command.getSecondWord();
        Room currentRoom = player.getCurrentRoom();

        Item itemToTake = null;

        for (Item item : currentRoom.getItems())
        {
            if (item.getDescription().equalsIgnoreCase(itemName))
            {
                itemToTake = item;
                break;
            }
        }

        if (itemToTake == null)
        {
            System.out.println("There is no such item here!");
        }
        else
        {
            double currentWeight = player.getCurrentWeight();

            if (currentWeight + itemToTake.getWeight() <= player.getMaxWeight())
            {
                player.addItemToInventory(itemToTake);
                currentRoom.removeItem(itemToTake);
                System.out.println("You picked up: " + itemToTake.getDetails());
            }
            else
            {
                System.out.println("You can't carry " + itemToTake.getDescription() + ". It's too heavy!");
            }

        }
    }

    private void drop(Command command)
    {
        if(!command.hasSecondWord())
        {
            System.out.println("Drop what?");
            return;
        }

        String itemName = command.getSecondWord();

        Item itemToDrop = null;

        for (Item item : player.getInventory())
        {
            if (item.getDescription().equalsIgnoreCase(itemName))
            {
                itemToDrop = item;
                break;
            }
        }

        if (itemToDrop == null)
        {
            System.out.println("You don't have such an item!");
        }
        else
        {
            player.getCurrentRoom().addItem(itemToDrop);
            player.removeItemFromInventory(itemToDrop);
            System.out.println("You dropped: " + itemToDrop.getDetails());
        }
    }

    private void back(Command command)
    {
        if(command.hasSecondWord())
        {
            System.out.println("I don't know what you mean...");
            return;
        }
        else
        {
            if(roomLog.empty()) System.out.println("You can't go back");
            else
            {   
                player.setCurrentRoom(roomLog.pop());
                printLocationInfo();
            }
        }
    }

    private void eat(Command command)
    {
        if (!command.hasSecondWord())
        {
            System.out.println("Eat what?");
            return;
        }

        String itemName = command.getSecondWord();
        Item itemToEat = null;

        for (Item item : player.getInventory())
        {
            if (item.getDescription().equalsIgnoreCase(itemName))
            {
                itemToEat = item;
                break;
            }
        }

        if (itemToEat == null)
        {
            System.out.println("You don't have that item to eat!");
            return;
        }

        if (itemToEat.getDescription().equalsIgnoreCase("a magic mushroom"))
        {
            player.setMaxWeight(player.getMaxWeight() + 5.0);
            player.removeItemFromInventory(itemToEat);
            System.out.println("You ate the magic mushroom. You carrying increased to " + player.getMaxWeight() + "kg");
        }
        else
        {
            System.out.println("You can't eat " + itemToEat.getDescription() + ".");
        }
    }

    private void items(Command command)
    {
        if(command.hasSecondWord()) {
            System.out.println("I don't know what you mean...");
            return;
        }
        else {
            System.out.println(player.getInventoryDescription());
        }
    }

    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;
        }
    }

    private void printLocationInfo()
    {
        System.out.println(player.getCurrentRoom().getLongDescription());
    }

    private Room getCurrentRoom()
    {
        return currentRoom;
    }

}