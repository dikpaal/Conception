package ui;

import model.*;

import java.util.List;
import java.util.Scanner;

// Represents a console UI
public class ConsoleUI {

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: simulates a user interface which accepts input from the user
    public void mainUserInput(Room r) {
        setup(r);
        menu(r);
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: simulates the main menu of the app
    public void menu(Room r) {
        while (true) {
            Scanner s = new Scanner(System.in);
            System.out.println("Would you like to edit your room? (y) or (n):");
            String userChoice = s.nextLine();

            if (userChoice.equals("y")) {
                String userChoice2 = printChoices(s);

                if (userChoice2.equals("1")) {
                    userWantsToSeeTheRoomOrNot(r, s);
                } else if (userChoice2.equals("2")) {
                    removeFurniture(r);
                } else if (userChoice2.equals("3")) {
                    if (r.getFurnitureListWithSpots().isEmpty()) {
                        System.out.println("You have not added any furniture yet!");
                    } else {
                        System.out.println(r.getFurnitureListWithSpots());
                    }
                } else {
                    System.out.println("Invalid choice!");
                }

            } else if (userChoice.equals("n")) {
                System.out.println("Okay, here's your final room!!");
                System.out.println();
                printRoom(r);
                System.out.println("Bye, " + r.getUsername());
                break;
            }
        }
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: calls to create a numberedPlane, initiate a numberedAndFurnitureList, and prints the room
    public void setup(Room r) {
        List<List<String>> numberedPlane = r.createNumberedPlane();
        r.setNumberedPlane(numberedPlane);
        r.initiateNumberedAndFurnitureList();
        System.out.println("Here's your room!");
        printRoom(r);
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: prints the room based on whether the user wants to see the room or not.
    //          If "y", then prints, otherwise doesn't
    public void userWantsToSeeTheRoomOrNot(Room r, Scanner s) {
        editRoom(r);
        System.out.println("Would you like to see your room? (y) or (n):");
        String userChoice3 = s.nextLine();
        if (userChoice3.equals("y")) {
            printRoom(r);
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
    public void printRoom(Room r) {
        List<List<String>> numberedAndFurnitureList = r.getNumberedAndFurnitureList();
        for (int i = 0; i < numberedAndFurnitureList.size(); i++) {
            List<String> subList = numberedAndFurnitureList.get(i);
            printDashes(r);
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
        printDashes(r);
        System.out.println();
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: prints the dashes for the printRoom() method
    public void printDashes(Room r) {
        for (int k = 0; k < r.getDimension().getLength(); k++) {
            System.out.print("   - ");
        }
        System.out.println();
    }

    // REQUIRES: nothing
    // MODIFIES: this
    // EFFECTS: allows the user to add/remove Furniture from the Room
    public void editRoom(Room r) {
        Scanner s = new Scanner(System.in);
        while (true) {
            System.out.println("What would you like to place? Chair (c), Sofa (s), Centre Table (t):");
            String userChoice = s.nextLine();
            String userChoice2;

            if (userChoice.equals("c")) {
                userChoice2 = userWantsToPlaceAChair(r, s);

            } else if (userChoice.equals("s")) {
                userChoice2 = userWantsToPlaceASofa(r, s);
            } else if (userChoice.equals("t")) {
                userChoice2 = userWantsToPlaceACentreTable(r, s);
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
    public String userWantsToPlaceAChair(Room r, Scanner s) {
        Furniture chair = new Chair();
        placeChair(r, chair);
        System.out.println("Would you like to add more furniture? (y) or (n): ");
        String userChoice2 = s.nextLine();
        return userChoice2;
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: places a sofa and returns whether the user wants to add any more furniture
    public String userWantsToPlaceASofa(Room r, Scanner s) {
        Furniture sofa = new Sofa();
        placeSofa(r, sofa);
        System.out.println("Would you like to add more furniture? (y) or (n): ");
        String userChoice2 = s.nextLine();
        return userChoice2;
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: places a centre table and returns whether the user wants to add any more furniture
    public String userWantsToPlaceACentreTable(Room r, Scanner s) {
        Furniture ct = new CenterTable();
        placeCenterTable(r, ct);
        System.out.println("Would you like to add more furniture? (y) or (n): ");
        String userChoice2 = s.nextLine();
        return userChoice2;
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: places a chair in the spot that the user chooses
    public void placeChair(Room r, Furniture chair) {
        r.isThereSpaceAnyMore(chair);
        Scanner s = new Scanner(System.in);

        System.out.println("Choose a spot: ");
        String userChoice = s.nextLine();
        int spot = Integer.parseInt(userChoice);
        r.setChairInNumberedAndFurnitureList(chair, spot);
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: places a sofa in the spot that the user chooses
    public void placeSofa(Room r, Furniture sofa) {
        r.isThereSpaceAnyMore(sofa);
        Scanner s = new Scanner(System.in);

        System.out.println("Choose two spots: ");
        String userChoice1 = s.nextLine();

        System.out.println(r.getTheOtherSpot(userChoice1));

        String userChoice2 = s.nextLine();
        int spot1 = Integer.parseInt(userChoice1);
        int spot2 = Integer.parseInt(userChoice2);
        r.setSofaInNumberedAndFurnitureList(sofa, spot1, spot2);
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: places a centre table ct in the spot that the user chooses
    public void placeCenterTable(Room r, Furniture ct) {

        if (r.isThereSpaceAnyMore(ct)) {

            Scanner s = new Scanner(System.in);
            System.out.println("Do you want to place the center table here? (y) or (n): ");
            String userChoice = s.nextLine();

            if (userChoice.equals("y")) {
                List<String> spots = r.spaceForACentreTable();
                String topLeftSpot = spots.get(0);
                r.setCenterTableInNumberedAndFurnitureList(ct, topLeftSpot);
            }
        }
    }

    // REQUIRES: nothing
    // MODIFIES: this
    // EFFECTS: removes the furniture that the user wants from the room
    public void removeFurniture(Room r) {
        Scanner s = new Scanner(System.in);

        while (true) {

            System.out.println("What would you like to remove? (c) or (s) or (t): ");
            String userChoice = s.nextLine();

            if (userChoice.equals("c")) {
                userWantsToRemoveAChair(r);
            } else if (userChoice.equals("s")) {
                userWantsToRemoveASofa(r);
            } else if (userChoice.equals("t")) {
                userWantsToRemoveACentreTable(r);
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
    public void userWantsToRemoveAChair(Room r) {
        if (r.getListOfAllTheAddedChairs().isEmpty()) {
            System.out.println("You have not added any chairs yet!");
        } else {
            System.out.println(r.getListOfAllTheAddedChairs());
            String spot = selectSpot();
            r.removeChairFromSpot(spot);
            printRoom(r);
        }
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: checks if there are any sofas in the furnitureList.
    //          If there are sofas, then allows the user to remove a sofa
    public void userWantsToRemoveASofa(Room r) {
        if (r.getListOfAllTheAddedSofas().isEmpty()) {
            System.out.println("You have not added any sofas yet!");
        } else {
            System.out.println(r.getListOfAllTheAddedSofas());
            String spot1 = selectSpot();
            String spot2 = r.getSpot2Sofa(spot1);
            r.removeSofaFromSpot(spot1, spot2);
            printRoom(r);
        }
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: checks if there are any centre tables in the furnitureList.
    //          If there are centre tables, then allows the user to remove a centre table
    public void userWantsToRemoveACentreTable(Room r) {
        if (r.getListOfAllTheAddedCentreTable().isEmpty()) {
            System.out.println("You have not added a centre table yet!");
        } else {
            System.out.println(r.getListOfAllTheAddedCentreTable());
            String spot3 = selectSpot();
            r.removeCentreTableFromSpot(spot3);
            printRoom(r);
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

}