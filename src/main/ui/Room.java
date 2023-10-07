package ui;

import model.Dimension;
import model.Furniture;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static model.FurnitureType.CHAIR;
import static model.FurnitureType.SOFA;

// Represents a room
public class Room {
    String username;
    Dimension dimension;
    List<List<String>> dashedPlane;
    List<List<String>> numberedPlane;
    List<List<Furniture>> furnitureList;


    public Room(int d) {
        this.dimension = new Dimension(d, d);
        List<List<String>> dashedPlane = new ArrayList<>();
        List<List<String>> numberedPlane = new ArrayList<>();
        List<List<Furniture>> furnitureList = new ArrayList<>();
    }


    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: simulates a user interface which accepts input from the user
    public void mainUserInput() {
        introductionOfUser();
        Room room = userChoosesDimensionAndNewRoomIsCreated();

        List<List<String>> numberedPlane = room.createNumberedPlane();
        List<List<String>> dashedPlane = room.createDashedPlane();

        room.setNumberedPlane(numberedPlane);
        room.setDashedPlane(dashedPlane);

        room.printRoom();
        room.editRoom();
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
    public Room userChoosesDimensionAndNewRoomIsCreated() {
        Scanner s = new Scanner(System.in);
        System.out.println();
        System.out.println("What would the dimension of your room be? (Select an odd number only) ");
        String dimensionString = s.nextLine();
        int dimensionInt = Integer.parseInt(dimensionString);
        return new Room(dimensionInt);
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
    public void printRoom() {
        List<List<String>> numberedList = getNumberedPlane();
        List<List<String>> dashedList = getDashedPlane();

        int numberedFactor = 0;

        List<String> tempList = new ArrayList<>();

        System.out.println();
        for (List<String> subDashedList : dashedList) {
            for (String dash : subDashedList) {
                System.out.print("   — ");
            }

            if (numberedFactor == (getDimension().getLength() - 1) * (getDimension().getLength() - 1)) {
                break;
            }

            System.out.println();
            for (List<String> subNumberedList : numberedList) {
                System.out.print("    ");
                for (String number : subNumberedList) {
                    if ((numberedFactor + Integer.parseInt(number)) < 10) {
                        System.out.print(" " + "0" + (numberedFactor + Integer.parseInt(number)) + "  ");
                    } else {
                        System.out.print(" " + (numberedFactor + Integer.parseInt(number)) + "  ");
                    }
                }
                numberedFactor += getDimension().getLength() - 1;
                break;
            }
            System.out.println();
        }
        System.out.println();
    }


    // REQUIRES: nothing
    // MODIFIES: this
    // EFFECTS: creates a numbered plane for a given dimension (used for placing furniture)
    public List<List<String>> createNumberedPlane() {
        int count = 0;

        List<List<String>> tempList = new ArrayList<>();
        for (int i = 1; i < dimension.getWidth(); i++) {
            List<String> subTempList = new ArrayList<>();
            for (int j = 1; j < dimension.getLength(); j++) {
                subTempList.add("" + (count + j) + "");
            }
            tempList.add(subTempList);
            count += dimension.getLength();
        }
        return tempList;
    }

    // REQUIRES: nothing
    // MODIFIES: this
    // EFFECTS: creates a plane for a given dimension
    public List<List<String>> createDashedPlane() {
        int count = 0;

        List<List<String>> tempList = new ArrayList<>();
        for (int i = 1; i <= dimension.getWidth(); i++) {
            List<String> subTempList = new ArrayList<>();
            for (int j = 1; j <= dimension.getLength(); j++) {
                subTempList.add("_");
            }
            tempList.add(subTempList);
            count += dimension.getLength();
        }
        return tempList;
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: returns true, if f can be placed in the room (is there space for it or not),
    //          otherwise returns false
    public boolean isThereSpace(Furniture f) {
        if (f.getType() == CHAIR) {
            List<String> availableSpots = new ArrayList<>();
            for (List<String> subNumberedList : getNumberedPlane()) {


            }
        }
    }


    // REQUIRES: nothing
    // MODIFIES: this
    // EFFECTS: allows the user to add/remove Furniture from the Room
    public void editRoom() {
        // stub
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

    // REQUIRES: nothing
    // MODIFIES: this
    // EFFECTS: returns the dashedPlane of the room
    public List<List<String>> getDashedPlane() {
        return this.dashedPlane;
    }

    // REQUIRES: nothing
    // MODIFIES: this
    // EFFECTS: returns the numberedPlane of the room
    public List<List<String>> getNumberedPlane() {
        return this.numberedPlane;
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

    // REQUIRES: lst is not empty
    // MODIFIES: this
    // EFFECTS: sets the dashedPlane of the room to lst
    public void setDashedPlane(List<List<String>> lst) {
        this.dashedPlane = lst;
    }

    // REQUIRES: lst is not empty
    // MODIFIES: this
    // EFFECTS: sets the numberedPlane of the room to lst
    public void setNumberedPlane(List<List<String>> lst) {
        this.numberedPlane = lst;
    }
}