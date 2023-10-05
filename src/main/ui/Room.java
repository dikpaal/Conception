package ui;

import model.Dimension;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Room {
    String username;
    Dimension dimension;
    List<List<String>> plane;


    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: simulates a user interface which accepts input from the user
    public void mainUserInput() {
        introductionOfUser();
        userChoosesDimension();
        if (userChoosesDashOrNumberedRoom()) {
            printRoomWithNumbers();
        } else {
            printRoomWithDashes();
        }
    }


    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: inputs and stores the username from the user
    public void introductionOfUser() {
        Scanner s = new Scanner(System.in);
        System.out.println();
        System.out.println("What do we call you?");
        String username = s.nextLine();
        System.out.println();
        setUsername(username);
        System.out.println("Hello, " + getUsername());
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: inputs and stores the dimension from the user
    public void userChoosesDimension() {
        Scanner s = new Scanner(System.in);
        System.out.println();
        System.out.println("What would the dimension of your room be? ");
        String dimensionString = s.nextLine();
        int dimensionInt = Integer.parseInt(dimensionString);
        setDimension(dimensionInt);
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: returns whether the user wishes to see a dashed room or a numbered room
    //          - returns false if the user chooses a dashed view
    //          - returns true if the user chooses a numbered view
    public boolean userChoosesDashOrNumberedRoom() {
        int temp = 0;

        Scanner s = new Scanner(System.in);
        System.out.println();
        System.out.println("Would you like a dashed view (0) or a numbered view (1)? ");
        int view = Integer.parseInt(s.next());
        if (view == 0) {
            return false;
        } else {
            return true;
        }
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: prints the room to the console
    public void printRoomWithNumbers() {
        List<List<String>> tempList = createPlane();

        for (List<String> lst : tempList) {
            for (String s : lst) {
                int number = Integer.parseInt(s);
                if (number < 10) {
                    System.out.print("   " + number + " ");
                } else if (number < 100) {
                    System.out.print("  " + number + " ");
                }
                if (number >= 100) {
                    System.out.print(" " + number + " ");
                }
            }
            System.out.println();
        }
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: prints a room with numbers substituted with dashes
    public void printRoomWithDashes() {
        List<List<String>> tempList = createPlane();

        for (List<String> lst : tempList) {
            for (String s : lst) {
                int number = Integer.parseInt(s);
                System.out.print(" _ ");
            }
            System.out.println();
        }
    }

    // REQUIRES: nothing
    // MODIFIES: this
    // EFFECTS: creates a plane for a given dimension
    public List createPlane() {
        int count = 0;

        List<List<String>> tempList = new ArrayList<>();
        for (int i = 1; i <= dimension.getWidth(); i++) {
            List<String> subTempList = new ArrayList<>();
            for (int j = 1; j <= dimension.getLength(); j++) {
                subTempList.add("" + (count + j) + "");
            }
            tempList.add(subTempList);
            count += dimension.getLength();
        }
        return tempList;
    }

    // GETTERS

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: returns the username of the user who created this room
    public String getUsername() {
        return this.username;
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: returns the dimension of the room
    public Dimension getDimension() {
        return this.dimension;
    }

    // SETTERS

    // REQUIRES: s != ""
    // MODIFIES: this
    // EFFECTS: sets the username of the user who created this room to s
    public void setUsername(String s) {
        this.username = s;
    }

    // REQUIRES: d > 0
    // MODIFIES: this
    // EFFECTS: sets the dimension of the room to new DImension(d, d)
    public void setDimension(int d) {
        this.dimension = new Dimension(d, d);
    }
}