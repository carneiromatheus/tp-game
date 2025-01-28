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
        Room outside, theater, pub, lab, office;

        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");

        outside.setExit("east", theater);
        outside.setExit("south", lab);
        outside.setExit("west", pub);

        theater.setExit("west", outside);

        pub.setExit("east", outside);
        lab.setExit("north", outside);
        lab.setExit("east", office);
        office.setExit("west", lab);

        outside.addItem(new Item("a rusty key", 0.5));
        outside.addItem(new Item("a map", 0.2));
        lab.addItem(new Item("a mysterious book", 1.2));
        lab.addItem(new Item("a broken computer", 3.5));

        currentRoom = outside;
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
        System.out.println("\nWelcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.\n");
        printLocationInfo();
        System.out.println();
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
        else if (commandWord.equals("eat")) eat();
        else if (commandWord.equals("quit")) wantToQuit = quit(command);
        else if (commandWord.equals("back")) back(command);
        else if (commandWord.equals("take")) take(command);
        else if (commandWord.equals("drop")) drop(command);
        else if (commandWord.equals("items")) items(command);

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

    public void take(Command command)
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
            player.addItemToInventory(itemToTake);
            currentRoom.removeItem(itemToTake);
            System.out.println("You picked up: " + itemToTake.getDetails());
        }
    }
    
    public void drop(Command command)
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

    private void eat()
    {
        System.out.println("Você já comeu e não está com fome mais.");
    }

    private void items(Command command)
    {
        System.out.println();
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