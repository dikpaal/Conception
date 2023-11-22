package ui;

import model.Room;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.Scanner;

// Represents the class with the main method that runs the app.
public class Main {
    public static void main(String[] args) {

//      STARTS THE CONSOLE APPLICATION
//        Scanner s = new Scanner(System.in);
//        System.out.println();
//        System.out.println("What do we call you?");
//        String username = s.nextLine();
//        System.out.println();
//
//        Room r = new Room(3);
//        r.setUsername(username);
//        System.out.println("Hello, " + r.getUsername());
//
//        // USER SELECTS THE DIMENSION OF THE ROOM
//        System.out.println();
//        System.out.println("What would the dimension of your room be? (Select an even number only)");
//        String dimensionString = s.nextLine();
//        int dimensionInt = Integer.parseInt(dimensionString);
//        r.setDimension(dimensionInt + 1);
//
//        ConsoleUI consoleUI = new ConsoleUI();
//        consoleUI.setRoom(r);
//        consoleUI.mainUserInput();

        // STARTS THE GUI APPLICATION
        new GUI();
    }
}