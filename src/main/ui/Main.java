package ui;

import javax.swing.*;
import java.awt.*;

// Represents the class with the main method that runs the app.
public class Main {
    public static void main(String[] args) {

//        // INTRODUCTION OF THE USER
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
//        System.out.println("What would the dimension of your room be? (Select an odd number only) ");
//        String dimensionString = s.nextLine();
//        int dimensionInt = Integer.parseInt(dimensionString);
//        r.setDimension(dimensionInt);

        // THE PROGRAM STARTS HERE
//        ConsoleUI consoleUI = new ConsoleUI();
//        consoleUI.setRoom(r);
//        consoleUI.mainUserInput();

        // The main GUI Frame
        FrameGUI guiFrame = new FrameGUI();

        // The button panel that contains all the buttons
        PanelGUI buttonPanel = new ButtonPanel(guiFrame.getWidth(), guiFrame.getHeight() / 20);

        // The furniture panel that contains all the furniture
        PanelGUI furniturePanel = new FurniturePanel(guiFrame.getWidth() / 4, guiFrame.getHeight());

        // The canvas panel that contains the canvas in which the designing takes place
        PanelGUI canvasPanel = new CanvasPanel(guiFrame.getWidth() / 4,
                guiFrame.getHeight() / 20,
                guiFrame.getWidth() * 3 / 4,
                guiFrame.getHeight() * 19 / 20);

        guiFrame.add(buttonPanel);
        guiFrame.add(furniturePanel);
        guiFrame.add(canvasPanel);

        guiFrame.setVisible(true); // makes the frame visible


    }
}