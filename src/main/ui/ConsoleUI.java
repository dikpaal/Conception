package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

// Represents a console UI
public class ConsoleUI {
    private static final String JSON_STORE = "./data/workroom.json";
    private Room room;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: constructs the consoleUI
    public ConsoleUI() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: simulates a user interface which accepts input from the user
    public void mainUserInput() {
        setup();
        menu();
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: simulates the main menu of the app
    public void menu() {
        while (true) {
            Scanner s = new Scanner(System.in);
            System.out.println("Would you like to edit your room? (y) or (n):");
            String userChoice = s.nextLine();

            if (userChoice.equals("y")) {
                String userChoice2 = printChoices(s);

                if (userChoice2.equals("1")) {
                    userWantsToSeeTheRoomOrNot(s);
                } else if (userChoice2.equals("2")) {
                    removeFurniture();
                } else if (userChoice2.equals("3")) {
                    furnitureListWithSpotsIsEmpty();
                } else {
                    System.out.println("Invalid choice!");
                }

            } else if (userChoice.equals("n")) {
                System.out.println("Okay, here's your final room!!");
                printRoom();
                saveRoom();
                System.out.println("Bye, " + room.getUsername());
                break;
            }
        }
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: prints out the furniture list with spots if furnitureList is not empty,
    //          - otherwise prints a message.
    public void furnitureListWithSpotsIsEmpty() {
        if (room.getFurnitureListWithSpots().isEmpty()) {
            System.out.println("You have not added any furniture yet!");
        } else {
            System.out.println(room.getFurnitureListWithSpots());
        }
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: calls to create a numberedPlane, initiate a numberedAndFurnitureList, and prints the room
    public void setup() {
        List<List<String>> numberedPlane = room.createNumberedPlane();
        room.setNumberedPlane(numberedPlane);
        room.initiateNumberedAndFurnitureList();
        loadRoom();
        System.out.println("Here's your room!");
        printRoom();
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: prints the room based on whether the user wants to see the room or not.
    //          If "y", then prints, otherwise doesn't
    public void userWantsToSeeTheRoomOrNot(Scanner s) {
        editRoom();
        System.out.println("Would you like to see your room? (y) or (n):");
        String userChoice3 = s.nextLine();
        if (userChoice3.equals("y")) {
            printRoom();
        }
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: prints the menu options for the user
    public String printChoices(Scanner s) {
        System.out.println("1. Would you like to add furniture to your room?");
        System.out.println("2. Would you like to remove furniture from your room?");
        System.out.println("3. Would you like to see a list of all the furniture in your room?");
        String userChoice2 = s.nextLine();
        return userChoice2;
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: prints the room to the console
    public void printRoom() {
        List<List<String>> numberedAndFurnitureList = room.getNumberedAndFurnitureList();
        for (int i = 0; i < numberedAndFurnitureList.size(); i++) {
            List<String> subList = numberedAndFurnitureList.get(i);
            printDashes();
            System.out.print("    ");
            for (int j = 0; j < subList.size(); j++) {
                String number = subList.get(j);
                if (number.equals("Cv")
                        || number.equals("Sv")
                        || number.equals("vS")
                        || number.equals("Tv")
                        || number.equals("vT")) {
                    System.out.print(" " + number + "  ");
                } else if (Integer.parseInt(number) < 10) {
                    System.out.print(" " + "0" + number + "  ");
                } else {
                    System.out.print(" " + number + "  ");
                }
            }
            System.out.println();
        }
        printDashes();
        System.out.println();
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: prints the dashes for the printRoom() method
    public void printDashes() {
        for (int k = 0; k < room.getDimension().getLength(); k++) {
            System.out.print("   - ");
        }
        System.out.println();
    }

    // REQUIRES: nothing
    // MODIFIES: this
    // EFFECTS: allows the user to add/remove Furniture from the Room
    public void editRoom() {
        Scanner s = new Scanner(System.in);
        while (true) {
            System.out.println("What would you like to place? Chair (c), Sofa (s), Centre Table (t):");
            String userChoice = s.nextLine();
            String userChoice2;

            if (userChoice.equals("c")) {
                userChoice2 = userWantsToPlaceAChair(s);

            } else if (userChoice.equals("s")) {
                userChoice2 = userWantsToPlaceASofa(s);
            } else if (userChoice.equals("t")) {
                userChoice2 = userWantsToPlaceACentreTable(s);
            } else {
                System.out.println("Invalid choice!");
                break;
            }
            if (userChoice2.equals("n")) {
                break;
            }
        }
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: places a chair and returns whether the user wants to add any more furniture
    public String userWantsToPlaceAChair(Scanner s) {
        Furniture chair = new Chair();
        placeChair(chair);
        System.out.println("Would you like to add more furniture? (y) or (n): ");
        String userChoice2 = s.nextLine();
        return userChoice2;
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: places a sofa and returns whether the user wants to add any more furniture
    public String userWantsToPlaceASofa(Scanner s) {
        Furniture sofa = new Sofa();
        placeSofa(sofa);
        System.out.println("Would you like to add more furniture? (y) or (n): ");
        String userChoice2 = s.nextLine();
        return userChoice2;
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: places a centre table and returns whether the user wants to add any more furniture
    public String userWantsToPlaceACentreTable(Scanner s) {
        Furniture ct = new CenterTable();
        placeCenterTable(ct);
        System.out.println("Would you like to add more furniture? (y) or (n): ");
        String userChoice2 = s.nextLine();
        return userChoice2;
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: places a chair in the spot that the user chooses
    public void placeChair(Furniture chair) {
        room.isThereSpaceAnyMore(chair);
        Scanner s = new Scanner(System.in);

        System.out.println("Choose a spot: ");
        String userChoice = s.nextLine();
        int spot = Integer.parseInt(userChoice);
        room.setChairInNumberedAndFurnitureList(chair, spot);
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: places a sofa in the spot that the user chooses
    public void placeSofa(Furniture sofa) {
        room.isThereSpaceAnyMore(sofa);
        Scanner s = new Scanner(System.in);

        System.out.println("Choose two spots: ");
        String userChoice1 = s.nextLine();

        System.out.println(room.getTheOtherSpot(userChoice1));

        String userChoice2 = s.nextLine();
        int spot1 = Integer.parseInt(userChoice1);
        int spot2 = Integer.parseInt(userChoice2);
        room.setSofaInNumberedAndFurnitureList(sofa, spot1, spot2);
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: places a centre table ct in the spot that the user chooses
    public void placeCenterTable(Furniture ct) {

        if (room.isThereSpaceAnyMore(ct)) {

            Scanner s = new Scanner(System.in);
            System.out.println("Do you want to place the center table here? (y) or (n): ");
            String userChoice = s.nextLine();

            if (userChoice.equals("y")) {
                List<String> spots = room.spaceForACentreTable();
                String topLeftSpot = spots.get(0);
                room.setCenterTableInNumberedAndFurnitureList(ct, topLeftSpot);
            }
        }
    }

    // REQUIRES: nothing
    // MODIFIES: this
    // EFFECTS: removes the furniture that the user wants from the room
    public void removeFurniture() {
        Scanner s = new Scanner(System.in);

        while (true) {

            System.out.println("What would you like to remove? (c) or (s) or (t): ");
            String userChoice = s.nextLine();

            if (userChoice.equals("c")) {
                userWantsToRemoveAChair();
            } else if (userChoice.equals("s")) {
                userWantsToRemoveASofa();
            } else if (userChoice.equals("t")) {
                userWantsToRemoveACentreTable();
            } else {
                System.out.println("Invalid choice!");
            }
            if (!wouldYouLikeToRemoveAnyMoreFurniture()) {
                break;
            }
        }
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: checks if there are any chairs in the furnitureList.
    //          If there are chairs, then allows the user to remove a chair
    public void userWantsToRemoveAChair() {
        if (room.getListOfAllTheAddedFurniture("CHAIR").isEmpty()) {
            System.out.println("You have not added any chairs yet!");
        } else {
            System.out.println(room.getListOfAllTheAddedFurniture("CHAIR"));
            String spot = selectSpot();
            room.removeChairFromSpot(spot);
            printRoom();
        }
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: checks if there are any sofas in the furnitureList.
    //          If there are sofas, then allows the user to remove a sofa
    public void userWantsToRemoveASofa() {
        if (room.getListOfAllTheAddedFurniture("SOFA").isEmpty()) {
            System.out.println("You have not added any sofas yet!");
        } else {
            System.out.println(room.getListOfAllTheAddedFurniture("SOFA"));
            String spot1 = selectSpot();
            String spot2 = room.getSpot2Sofa(spot1);
            room.removeSofaFromSpot(spot1, spot2);
            printRoom();
        }
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: checks if there are any centre tables in the furnitureList.
    //          If there are centre tables, then allows the user to remove a centre table
    public void userWantsToRemoveACentreTable() {
        if (room.getListOfAllTheAddedFurniture("CT").isEmpty()) {
            System.out.println("You have not added a centre table yet!");
        } else {
            System.out.println(room.getListOfAllTheAddedFurniture("CT"));
            String spot3 = selectSpot();
            room.removeCentreTableFromSpot(spot3);
            printRoom();
        }
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: returns the spot that the user wants to remove the furniture from
    public String selectSpot() {
        Scanner s = new Scanner(System.in);
        System.out.println("Which spot do you want to remove it from? ");
        String userChoice = s.nextLine();
        return userChoice;
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: returns true if the user is done removing furniture from the room
    //          otherwise returns false
    public boolean wouldYouLikeToRemoveAnyMoreFurniture() {
        Scanner s = new Scanner(System.in);

        System.out.println("Would you like to remove any more furniture? (y) or (n): ");
        String userChoice = s.nextLine();

        if (userChoice.equals("y")) {
            return true;
        } else {
            return false;
        }
    }

    // REQUIRES: nothing
    // MODIFIES: this
    // EFFECTS: sets the room to r
    public void setRoom(Room r) {
        this.room = r;
    }

    // SAVING DATA


    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: saves the room to the file
    private void saveRoom() {

        Scanner s = new Scanner(System.in);
        while (true) {
            System.out.println("Would you like to save the room?");
            String userChoice = s.nextLine();

            if (userChoice.equals("y")) {
                try {
                    jsonWriter.open();
                    jsonWriter.write(room);
                    jsonWriter.close();
                    System.out.println("Saved " + room.getUsername() + "'s room to " + JSON_STORE);
                    System.out.println("Bye, " + room.getUsername());
                } catch (FileNotFoundException e) {
                    System.out.println("Unable to write to file: " + JSON_STORE);
                }
                break;
            } else if (userChoice.equals("n")) {
                System.out.println("Bye, " + room.getUsername());
                break;
            }
        }
    }

    // REQUIRES: nothing
    // MODIFIES: this
    // EFFECTS: loads room from file
    private void loadRoom() {

        Scanner s = new Scanner(System.in);
        while (true) {
            System.out.println("Would you like to load your previous room?");
            String userChoice = s.nextLine();

            if (userChoice.equals("y")) {
                try {
                    room = jsonReader.read();
                    System.out.println("Loaded " + room.getUsername() + "'s room from " + JSON_STORE);
                    System.out.println(room.getNumberedAndFurnitureList());
                    System.out.println(room.getFurnitureList());
                } catch (IOException e) {
                    System.out.println("Unable to read from file: " + JSON_STORE);
                }
                break;
            } else if (userChoice.equals("n")) {
                System.out.println("Okay!");
                break;
            }
        }
    }
}