package ui;

import model.CenterTable;
import model.Chair;
import model.Furniture;
import model.Sofa;

import java.util.Scanner;

// Main function that runs.
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
        r.mainUserInput();
    }
}
