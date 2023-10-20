package ui;

import model.Room;

import java.util.Scanner;

// Represents the class with the main method that runs the app.
public class Main {
    public static void main(String[] args) {

        // INTRODUCTION OF THE USER
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
        System.out.println("What would the dimension of your room be? (Select an odd number only) ");
        String dimensionString = s.nextLine();
        int dimensionInt = Integer.parseInt(dimensionString);
        r.setDimension(dimensionInt);

        // THE PROGRAM STARTS HERE
        ConsoleUI consoleUI = new ConsoleUI();
        consoleUI.mainUserInput(r);
    }
}
