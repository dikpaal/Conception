package ui;

import model.Room;

import java.util.Scanner;

// Represents the main Console UI
public class MainConsoleUI {

    // EFFECTS: constructs a main console UI object
    public MainConsoleUI() {
        Scanner s = new Scanner(System.in);
        System.out.println();
        System.out.println("What do we call you?");
        String username = s.nextLine();
        System.out.println();

        Room r = new Room(3);
        r.setUsername(username);
        System.out.println("Hello, " + r.getUsername());

        // USER SELECTS THE DIMENSION OF THE ROOM
        System.out.println();
        System.out.println("What would the dimension of your room be? (Select an even number only)");
        String dimensionString = s.nextLine();
        int dimensionInt = Integer.parseInt(dimensionString);
        r.setDimension(dimensionInt + 1);

        ConsoleUI consoleUI = new ConsoleUI();
        consoleUI.setRoom(r);
        consoleUI.mainUserInput();
    }
}
