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
        Room brasilia, salvador, beloHorizonte, saoPaulo, coritiba, rioDeJaneiro;

        brasilia = new Room("na sede da ABIN, em Brasília");
        salvador = new Room("em Salvador, Bahia");
        beloHorizonte = new Room("em Belo Horizonte, Minas Gerais");
        saoPaulo = new Room("em São Paulo, capital");
        coritiba = new Room("em Curitiba, Paraná");
        rioDeJaneiro = new Room("em Rio de Janeiro, capital");

        brasilia.setExit("norte", salvador);
        brasilia.setExit("leste", beloHorizonte);

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

        brasilia.addItem(new Item("arquivo da missão", 0.5));

        // Depois será implementado o método para usar o mapa.
        // Ao usar o mapa as chaves das saídas receberá o nome da
        // cidade, facilitando o jogo.
        // Ex.: ao invés de saídas: norte, sul.. será saídas: belo
        // horizonte, curitiba...

        brasilia.addItem(new Item("mapa do Brasil", 0.2));

        // saoPaulo.addItem(new Item("a mysterious book", 1.2));
        // saoPaulo.addItem(new Item("a computer", 3.5));
        // saoPaulo.addItem(new Item("a heavy box", 7.5));
        // saoPaulo.addItem(new Item("a magic mushroom", 0.3));

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
        System.out.println("Obrigado, Agente. Até a próxima missão.");
    }

    private void printWelcome()
    {
        System.out.println("\nABIN (Agência Brasileira de Inteligência)");
        System.out.println("\nBem vindo, Agente! Você foi designado para uma missão...\n");
        System.out.println("Digite 'ajuda' se você precisar de ajuda.\n");
        printLocationInfo();
        System.out.println();
    }

    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) 
        {
            System.out.println("Não sei o que você quer dizer...");
            return false;
        }

        String commandWord = command.getCommandWord().toLowerCase();
        if (commandWord.equals("ajuda")) printHelp();
        else if (commandWord.equals("viajar")) goRoom(command);
        else if (commandWord.equals("look")) look(command);
        else if (commandWord.equals("usar")) use(command);
        else if (commandWord.equals("sair")) wantToQuit = quit(command);
        else if (commandWord.equals("back")) back(command);
        else if (commandWord.equals("take")) take(command);
        else if (commandWord.equals("drop")) drop(command);
        else if (commandWord.equals("items")) items(command);

        return wantToQuit;
    }

    private void printHelp() 
    {
        System.out.println("Você é um agente da ABIN e uma missão foi designada para você.");
        System.out.println("""
        
        Objetivo: Prender o Suspeito antes que ela fuja do Brasil. Para isso, você deve:
        
        1. Viajar entre cidades brasileiras seguindo pistas que indicam seu próximo destino.
        
        2. Coletar informações com informantes e itens importantes.
        
        3. Resolver enigmas ou usar itens em locais estratégicos.
        
        4. Prender suspeito onde ele se esconde.
        """);
        System.out.println("Suas palavras de comando são:");
        System.out.println(parser.getValidCommands());
    }

    private void goRoom(Command command)
    {
        if(!command.hasSecondWord()) {
            System.out.println("Viajar para onde?");
            return;
        }

        String direction = command.getSecondWord();
        Room nextRoom = player.getCurrentRoom().getExit(direction);

        if (nextRoom == null) {
            System.out.println("Não é um destino.");
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
            System.out.println("Não sei o que você quer dizer...");
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
            System.out.println("Não sei o que você quer dizer...");
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

    private void use(Command command)
    {
        if (!command.hasSecondWord())
        {
            System.out.println("Usar o que?");
            return;
        }

        String itemName = command.getSecondWord();
        Item itemToUse = null;

        for (Item item : player.getInventory())
        {
            if (item.getDescription().equalsIgnoreCase(itemName))
            {
                itemToUse = item;
                break;
            }
        }

        if (itemToUse == null)
        {
            System.out.println("Item não encontrado no inventário.");
            return;
        }

        // if (itemToUse.getDescription().equalsIgnoreCase("a magic mushroom"))
        // {
        // player.setMaxWeight(player.getMaxWeight() + 5.0);
        // player.removeItemFromInventory(itemToUse);
        // System.out.println("You ate the magic mushroom. You carrying increased to " + player.getMaxWeight() + "kg");
        // }
        if (itemToUse.getDescription().equalsIgnoreCase("arquivo da missão"))
        {
            System.out.println("""
            Missão: Carmen Sandiego: Operação Brasil
            
            Você foi encarregado de capturar a ladra internacional
            Carmen Sandiego, que roubou a Joia Imperial Brasileira
            (parte da coroa de Dom Pedro II). Ela está escondida em uma
            das grandes cidades brasileiras.
        
            Você precisa seguir suas pistas, interagir com informantes,
            e capturá-la antes que ela fuja do país para sempre.""");
        }
        else
        {
            System.out.println("Você não pode usar o item " + itemToUse.getDescription() + ".");
        }
    }

    private void items(Command command)
    {
        if(command.hasSecondWord()) {
            System.out.println("Não sei o que você quer dizer...");
            return;
        }
        else {
            System.out.println(player.getInventoryDescription());
        }
    }

    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Sair o que?");
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