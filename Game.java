import java.util.ArrayList;
import java.util.Stack;

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Stack<Room> roomLog;
    private Player player;
    private ArrayList<Room> Rooms;
    private Npc donaBeth, zeca, seuGenaro, beto, carmenSandiego, carlos;
    private boolean finished;
    
    public Game() 
    {
        Rooms = new ArrayList<>();
        createRooms();
        parser = new Parser();
        roomLog = new Stack<>();
        player = new Player(currentRoom);
    }

    private void createRooms()
    {
        
        donaBeth = new Npc("Dona Beth","Uma simpática vendedora de queijos e doces caseiros");
        donaBeth.addSpeech("Uai, meu filho, vi uma moça apressada comprando um colar artesanal");
        donaBeth.addSpeech("Ela disse que ia para um lugar onde se come muito acarajé.");
        donaBeth.addSpeech("Agora cê vê se descobre onde é, né?");
        donaBeth.setDialogue();
        
        zeca = new Npc("Seu Zé", "Capoerista e Artista de Rua no Pelourinho");
        zeca.addSpeech("Oxente! Vi essa moça sim, ela parecia com pressa.");
        zeca.addSpeech("Ela ficou encantada com as flores e plantas daqui, disse que adora natureza.");
        zeca.addSpeech("Antes de sair, comentou que queria visitar um lugar cheio de verde e bem famoso por suas estufas de vidro.");
        zeca.setDialogue();
        
        seuGenaro = new Npc("Seu Genaro","Garçom de um Café em Copacabana");
        seuGenaro.addSpeech("Atendi essa tal de Carmen");
        seuGenaro.addSpeech("Ela estava com pressa e deixou cair este pedaço de papel.");
        seuGenaro.addSpeech("""
        
        _______________
        
        AVENIDA SÃO P**
        ________________
        
        """);
        seuGenaro.addSpeech("Deve ser alguma pista sobre pra onde ela foi.");
        seuGenaro.setDialogue();
        
        beto = new Npc("Beto","Motoboy de Encomendas Rápidas");
        beto.addSpeech("Fiz uma entrega pra uma mulher com um chapéu vermelho.");
        beto.addSpeech("O endereço ficava na rodovia BR-116.");
        beto.addSpeech("Se eu fosse você, dava uma olhada lá.");
        beto.setDialogue();
        
        carmenSandiego = new Npc("Carmen Sandiego", "Ladra internacional");
        
        carlos = new Npc("Agente Carlos", "Comandante ABIN");
        carlos.addSpeech("Parabéns por capturar a Ladra Carmen Sandiego");
        carlos.addSpeech("Obrigado, Agente. Até a próxima missão.");
        
        
        Room brasilia, salvador, beloHorizonte, saoPaulo, coritiba, rioDeJaneiro;

        brasilia = new Room("na sede da ABIN, em Brasília");
        Rooms.add(brasilia);
        beloHorizonte = new Room("em Belo Horizonte, Minas Gerais");
        Rooms.add(beloHorizonte);
        salvador = new Room("em Salvador, Bahia");
        Rooms.add(salvador);
        rioDeJaneiro = new Room("em Rio de Janeiro, capital");
        Rooms.add(rioDeJaneiro);  
        saoPaulo = new Room("em São Paulo, capital");
        Rooms.add(saoPaulo);
        coritiba = new Room("em Curitiba, Paraná");
        Rooms.add(coritiba);
          
        
        brasilia.setExit("norte", salvador);
        brasilia.setExit("leste", beloHorizonte);
        
        beloHorizonte.setExit("norte", salvador);
        beloHorizonte.setExit("leste", rioDeJaneiro);
        beloHorizonte.setExit("sul", saoPaulo);
        
        salvador.setExit("oeste", brasilia);
        salvador.setExit("sul", beloHorizonte);
        
        rioDeJaneiro.setExit("oeste", beloHorizonte);
        rioDeJaneiro.setExit("sul", saoPaulo);
        
        saoPaulo.setExit("norte", beloHorizonte);
        saoPaulo.setExit("sul", coritiba);

        coritiba.setExit("norte", saoPaulo);

        
        brasilia.addItem(new Item("arquivo da missão", 0.7));
        brasilia.addItem(new Item("mapa do Brasil", 0.35));
        brasilia.addItem(new Item("maleta", 1.0));
        brasilia.addItem(new Item("algemas", 2.0));
        
        currentRoom = brasilia;
    }
    private void setFinished()
    {
        finished = true;
    }
    
    public void play() 
    {            
        printWelcome();

        finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
            System.out.println();
        }
    }

    private void printWelcome()
    {
        System.out.println("\nABIN (Agência Brasileira de Inteligência)");
        System.out.println("\nSeja bem vindo, Agente!\nVocê foi designado para uma missão...\n");
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
        else if (commandWord.equals("observar")) look(command);
        else if (commandWord.equals("usar")) use(command);
        else if (commandWord.equals("sair")) wantToQuit = quit(command);
        else if (commandWord.equals("voltar")) back(command);
        else if (commandWord.equals("pegar")) take(command);
        else if (commandWord.equals("soltar")) drop(command);
        else if (commandWord.equals("itens")) items(command);
        else if (commandWord.equals("talk")) talk(command);

        return wantToQuit;
    }

    private void printHelp() 
    {
        System.out.println("""
        Você é um agente da ABIN. Seu objetivo é encontrar e
        prender suspeitos. Para isso, você deve:
        
        1. Viajar entre cidades brasileiras seguindo pistas que
        indicam seu próximo destino.
        
        2. Coletar informações com informantes e itens importantes.
        
        3. Resolver enigmas ou usar itens em locais estratégicos.
        
        4. Prender suspeito onde ele se esconde.
        """);
        System.out.println("Suas palavras de comando são:");
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
    
            for (Npc npc: currentRoom.getNpcs())
            {
                if (npc.getName().equalsIgnoreCase(npcToTalk))
                {
                    System.out.print(npc.getDialogue());
                    break;
                }
                else System.out.println("I dont know this person");
            }    
        }
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
            if(roomLog.peek() == Rooms.get(0)) Rooms.get(1).addNPC(donaBeth);   
            if(roomLog.peek() == Rooms.get(1)) Rooms.get(2).addNPC(zeca);
            if(roomLog.peek() == Rooms.get(2)) Rooms.get(3).addNPC(seuGenaro);
            if(roomLog.peek() == Rooms.get(3)) Rooms.get(4).addNPC(beto);
            if(roomLog.peek() == Rooms.get(4)) Rooms.get(5).addNPC(carmenSandiego);
            if(roomLog.peek() == Rooms.get(5)) Rooms.get(0).addNPC(carlos);
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
            System.out.println("Pegar o que?");
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
            System.out.println("Esse item não existe aqui!");
        }
        else
        {
            double currentWeight = player.getCurrentWeight();

            if (currentWeight + itemToTake.getWeight() <= player.getMaxWeight()
            || itemToTake.getDescription().equalsIgnoreCase("maleta"))
            {
                player.addItemToInventory(itemToTake);

                if (itemToTake.getDescription().equalsIgnoreCase("maleta"))
                {
                    player.setMaxWeight(player.getMaxWeight() + 6.0);
                    currentRoom.removeItem(itemToTake);
                    System.out.println("Você pegou um item especial: maleta");
                    System.out.println("Capacidade máxima de itens aumentada para " + player.getMaxWeight() + "kg");
                }
                else
                {
                    currentRoom.removeItem(itemToTake);
                    System.out.println("Você pegou o item: " + itemToTake.getDetails());
                }
            }
            else
            {
                System.out.println("Você não pode pegar o item " + itemToTake.getDescription() + ".");
                System.out.println("Capacidade máxima de itens alcançada.");
            }

        }
    }

    private void drop(Command command)
    {
        if(!command.hasSecondWord())
        {
            System.out.println("Soltar o que?");
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
            System.out.println("Você não tem esse item!");
        }
        else if (itemToDrop.getDescription().equalsIgnoreCase("maleta") && player.getInventory().size() > 1)
        {
            System.out.println("Não é possível soltar a maleta com itens dentro.");
        }
        else
        {
            player.getCurrentRoom().addItem(itemToDrop);
            player.removeItemFromInventory(itemToDrop);
            System.out.println("Você soltou o item: " + itemToDrop.getDetails());
            if (itemToDrop.getDescription().equalsIgnoreCase("maleta"))
            {
                player.setMaxWeight(player.getMaxWeight() - 6.0);
                System.out.println("Capacidade máxima de itens reduzida para " + player.getMaxWeight() + "kg");
            }
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
            if(roomLog.empty()) System.out.println("Não há destino para retornar.");
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

        if (itemToUse.getDescription().equalsIgnoreCase("arquivo da missão"))
        {
            System.out.println("""
            Missão: Carmen Sandiego: Operação Brasil
            
            Você foi encarregado de capturar a ladra internacional
            Carmen Sandiego, que roubou a Joia Imperial Brasileira
            (parte da coroa de Dom Pedro II). Ela está escondida em uma
            das grandes cidades brasileiras.
        
            Você precisa seguir suas pistas, interagir com informantes,
            e capturá-la antes que ela fuja do país para sempre.
            
            Uma denúncia anônima foi deixada em sua mesa...""");

            if (player.getCurrentRoom().description.equalsIgnoreCase("na sede da ABIN, em Brasília"))
            {
                player.getCurrentRoom().addItem(new Item("denúncia anônima", 0.1));
            }
        }
        else if (itemToUse.getDescription().equalsIgnoreCase("mapa do brasil"))
        {
            System.out.println("""
            
                  _ _ _ _ _ _ _ _ Salvador (BA)            
                 /                    |   
                /                     |
            Brasília (DF) --- Belo Horizonte (MG) --- Rio de Janeiro (RJ)
                                      |                     /
                                      |                    /   
                                São Paulo (SP) _ _ _ _ _  /
                                      |
                                      |
                              Curitiba (PR)
            """);
        }
        else if (itemToUse.getDescription().equalsIgnoreCase("denúncia anônima"))
        {
            System.out.println("""
            Em uma ligação telefônica foi informado que carmen deseja encontrar um suspeito na terra onde o sol se põe entre as montanhas.
            """);
        }
        else if ((itemToUse.getDescription().equalsIgnoreCase("algemas") && (roomLog.peek() == Rooms.get(4))))
        {
            System.out.println("Você capturou a ladra iternacional Carmen Sandiego!");
            player.setCurrentRoom(Rooms.get(0));
            look(command);
        }
        else if ((itemToUse.getDescription().equalsIgnoreCase("algemas") && (!(roomLog.peek() == Rooms.get(4)))))
        {
            System.out.print("Voce deve usar a algema para prender Carmen Sandiego!");
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