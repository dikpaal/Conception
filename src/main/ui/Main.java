package ui;

import model.Room;

import java.util.Scanner;

// Represents the class with the main method that runs the app.
public class Main {
    public static void main(String[] args) {

        // STARTS THE CONSOLE APPLICATION
//        new MainConsoleUI();


        Room r = new Room(3);

        r.setUsername(username);
        System.out.println("Hello, " + r.getUsername());

        // USER SELECTS THE DIMENSION OF THE ROOM
        System.out.println();
        System.out.println("What would the dimension of your room be? (Select an odd number only) ");
        String dimensionString = s.nextLine();
        int dimensionInt = Integer.parseInt(dimensionString);
        r.setDimension(dimensionInt);

        // THE APP STARTS HERE
        r.mainUserInput();

        // STARTS THE GUI APPLICATION
        new GUI();

    }
}