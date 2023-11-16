package ui;

import model.Room;

import javax.swing.*;
import java.awt.*;
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
        consoleUI.setRoom(r);

        // STARTS THE CONSOLE APPLICATION
//        consoleUI.mainUserInput();

        // STARTS THE GUI APPLICATION
        // The main GUI Frame
        FrameGUI guiFrame = new FrameGUI(username);

        // The canvas that is a grid and represents the room
        Canvas canvas = new Canvas(r);

        // The message panel that contains the messages for the user
        MessagePanel messagePanel = new MessagePanel(0, 440, 400, 50, r);

        // The button panel that contains all the buttons
        PanelGUI buttonPanel = new ButtonPanel(guiFrame.getWidth(), 40, canvas, messagePanel);

        // The canvas panel that contains the canvas in which the designing takes place
        PanelGUI canvasPanel = new CanvasPanel(0, 40, 400, 400);

        canvasPanel.add(canvas);

        guiFrame.add(buttonPanel);
        guiFrame.add(canvasPanel);
        guiFrame.add(messagePanel);
        guiFrame.setVisible(true); // makes the frame visible
    }
}