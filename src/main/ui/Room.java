package ui;

import model.*;

import java.time.chrono.IsoChronology;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static model.FurnitureType.CHAIR;
import static model.FurnitureType.SOFA;

// Represents a room
public class Room {
    String username;
    Dimension dimension;
    List<List<String>> numberedPlane; // the numbered plane showcasing the numbers of each block
    List<Furniture> furnitureList; // the list of furniture placed in the room so far
    List<List<String>> numberedAndFurnitureList; // the list to be used to find remaining space for a furniture


    // REQUIRES: d > 0 and d is odd
    // MODIFIES: this
    // EFFECTS: constructs a room with dimension d and empty
    public Room(int d) {
        this.dimension = new Dimension(d, d);
        this.numberedPlane = new ArrayList<>();
        this.furnitureList = new ArrayList<>();
        this.numberedAndFurnitureList = new ArrayList<>();
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: simulates a user interface which accepts input from the user
    public void mainUserInput() {
        introductionOfUser();
        Room room = userChoosesDimensionAndNewRoomIsCreated();

        List<List<String>> numberedPlane = room.createNumberedPlane();

        room.setNumberedPlane(numberedPlane);

        room.initiateNumberedAndFurnitureList();
        room.printRoom();
        room.editRoom();
        room.printRoom();
        System.out.println(room.getNumberedAndFurnitureList());
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
    // EFFECTS: prints the room to the console
    public void printRoom() {
        List<List<String>> numberedAndFurnitureList = getNumberedAndFurnitureList();
        for (int i = 0; i < numberedAndFurnitureList.size(); i++) {
            List<String> subList = numberedAndFurnitureList.get(i);
            for (int k = 0; k < getDimension().getLength(); k++) {
                System.out.print("   — ");
            }

            System.out.println();
            System.out.print("    ");
            for (int j = 0; j < subList.size(); j++) {
                String number = subList.get(j);
                if (number.equals("Cv")
                        || number.equals("Sv")
                        || number.equals("vS")
                        || number.equals("Sv=")
                        || number.equals("=vS")
                        || number.equals("Tv")) {

                    System.out.print(" " + number + "  ");
                } else if (Integer.parseInt(number) < 10) {
                    System.out.print(" " + "0" + number + "  ");
                } else {
                    System.out.print(" " + number + "  ");
                }
            }
            System.out.println();
        }
        for (int k = 0; k < getDimension().getLength(); k++) {
            System.out.print("   — ");
        }
        System.out.println();
    }

    // REQUIRES: nothing
    // MODIFIES: this
    // EFFECTS: creates a numbered plane for a given dimension (used for placing furniture)
    public List<List<String>> createNumberedPlane() {
        int numberedFactor = 0;

        List<List<String>> tempList = new ArrayList<>();

        for (int i = 1; i <= getDimension().getLength() - 1; i++) {
            List<String> subList = new ArrayList<>();

            for (int j = 1; j <= getDimension().getWidth() - 1; j++) {
                String number = Integer.toString(numberedFactor + j);
                subList.add(number);
            }
            numberedFactor += getDimension().getLength() - 1;
            tempList.add(subList);
        }
        return tempList;
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: returns true, if f can be placed in the room (is there space for it or not),
    //          otherwise returns false
    public boolean isThereSpaceAnyMore(Furniture f) {
        if (f.getType() == CHAIR) {
            if (isThereSpaceForAChair().isEmpty()) {
                System.out.println("Sorry, no space for a chair anymore!");
                return false;
            } else {
                System.out.println("You can place a chair in: " + isThereSpaceForAChair());
                return true;
            }
        } else if (f.getType() == FurnitureType.SOFA) {
            if (isThereSpaceForASofa().isEmpty()) {
                System.out.println("Sorry, no space for a sofa anymore!");
                return false;
            } else {
                System.out.println("You can place a sofa in: " + isThereSpaceForASofa());
                return true;
            }
        } else if (f.getType() == FurnitureType.CENTRETABLE) {
            if (isThereSpaceForACentreTable().isEmpty()) {
                System.out.println("Sorry, no space for a centre table anymore!");
                return false;
            } else {
                System.out.println("You can place a centre table in between: " + isThereSpaceForACentreTable());
                return true;
            }
        } else {
            return false;
        }
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: returns the spots where a chair can be placed
    public List<String> isThereSpaceForAChair() {
        List<String> availableSpots = new ArrayList<>();
        for (List<String> subList : getNumberedAndFurnitureList()) {
            for (String s : subList) {
                try {
                    int number = Integer.parseInt(s);
                    availableSpots.add(s);
                } catch (Exception e) {
                    // nothing here!
                }
            }
        }
        return availableSpots;
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: returns the spots where a sofa can be placed
    public List<List<String>> isThereSpaceForASofa() {
        List<List<String>> originalPlane = getNumberedAndFurnitureList();
        List<List<String>> invertedPlane = createInvertedPlane();

        List<List<String>> availableSpotsInOriginalPlane = isThereSpaceForASofaInOriginalPlane();
        List<List<String>> availableSpotsInInvertedPlane = isThereSpaceForASofaInInvertedPlane();

        return mergeLists(availableSpotsInOriginalPlane, availableSpotsInInvertedPlane);
    }

    // EFFECTS: returns the inverted numberedAndFurnitureList
    public List<List<String>> createInvertedPlane() {
        List<List<String>> availableSpots = new ArrayList<>();

        List<List<String>> tempList = getNumberedAndFurnitureList();
        int index = 0;
        int maxSize = tempList.get(0).size();

        if (index < maxSize) {
            for (int i = 0; i < maxSize; i++) {
                List<String> subList = new ArrayList<>();
                for (int j = 0; j < tempList.size(); j++) {
                    List<String> actualSubList = tempList.get(j);
                    subList.add(actualSubList.get(index));
                }
                index++;
                availableSpots.add(subList);
            }
        }
        return availableSpots;
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: returns the list of available spots FOR A SOFA in the original plane
    public List<List<String>> isThereSpaceForASofaInOriginalPlane() {
        List<List<String>> availableSpots = new ArrayList<>();
        List<List<String>> originalPlane = getNumberedAndFurnitureList();
        String acc;

        for (int i = 0; i < originalPlane.size(); i++) {
            List<String> subList = originalPlane.get(i);
            for (int j = 1; j < subList.size(); j++) {
                List<String> tempSubList = new ArrayList<>();
                acc = subList.get(j - 1);
                String curr = subList.get(j);
                try {
                    int numberAcc = Integer.parseInt(acc);
                    int numberCurr = Integer.parseInt(curr);

                    if (numberCurr == numberAcc + 1) {
                        tempSubList.add(acc);
                        tempSubList.add(curr);
                        availableSpots.add(tempSubList);
                    }
                } catch (Exception e) {
                    // nothing here!
                }

            }
        }
        return availableSpots;
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: returns the list of available spots FOR A SOFA in the inverted plane
    public List<List<String>> isThereSpaceForASofaInInvertedPlane() {
        List<List<String>> availableSpots = new ArrayList<>();
        List<List<String>> invertedPlane = createInvertedPlane();
        String acc;

        for (int i = 0; i < invertedPlane.size(); i++) {
            List<String> subList = invertedPlane.get(i);
            for (int j = 1; j < subList.size(); j++) {
                List<String> tempSubList = new ArrayList<>();
                acc = subList.get(j - 1);
                String curr = subList.get(j);
                try {
                    int numberAcc = Integer.parseInt(acc);
                    int numberCurr = Integer.parseInt(curr);

                    if (numberCurr == numberAcc + (getDimension().getLength() - 1)) {
                        tempSubList.add(acc);
                        tempSubList.add(curr);
                        availableSpots.add(tempSubList);
                    }
                } catch (Exception e) {
                    // nothing here!
                }
            }
        }
        return availableSpots;
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: merges l1 and l2
    public List<List<String>> mergeLists(List<List<String>> l1, List<List<String>> l2) {
        List<List<String>> mergedList = new ArrayList<>();

        for (List<String> subList : l1) {
            mergedList.add(subList);
        }
        for (List<String> subList : l2) {
            mergedList.add(subList);
        }
        return mergedList;
    }

    // REQUIRES: nothing
    // EFFECTS: nothing
    // EFFECTS: returns the spots where a centre table can be placed
    public List<String> isThereSpaceForACentreTable() {
        Dimension d = getDimension();
        int length = d.getLength();
        int w = (((length - 1) / 2) * length) - (length - 1);
        int x = w + 1;
        int y = w + (length - 1);
        int z = y + 1;

        String sw = Integer.toString(w);
        String sx = Integer.toString(x);
        String sy = Integer.toString(y);
        String sz = Integer.toString(z);

        List<String> subListWX = new ArrayList<>();
        subListWX.add(sw);
        subListWX.add(sx);

        List<String> subListWY = new ArrayList<>();
        subListWY.add(sw);
        subListWY.add(sy);

        List<String> subListYZ = new ArrayList<>();
        subListYZ.add(sy);
        subListYZ.add(sz);

        List<String> subListXZ = new ArrayList<>();
        subListXZ.add(sx);
        subListXZ.add(sz);

        List<String> availableSpots = new ArrayList<>();
        availableSpots.add(sw);
        availableSpots.add(sx);
        availableSpots.add(sy);
        availableSpots.add(sz);

        List<String> emptyList = new ArrayList<>();
        List<List<String>> sofaList = isThereSpaceForASofa();

        if (sofaList.contains(subListWX)
                && sofaList.contains(subListWY)
                && sofaList.contains(subListYZ)
                && sofaList.contains(subListXZ)) {
            return availableSpots;
        } else {
            return emptyList;
        }
    }

    // REQUIRES: nothing
    // MODIFIES: this
    // EFFECTS: allows the user to add/remove Furniture from the Room
    public void editRoom() {
        Scanner s = new Scanner(System.in);
        System.out.println("What would you like to place? Chair (c), Sofa (s), Centre Table (t):");
        String userChoice = s.nextLine();

        if (userChoice.equals("c")) {
            Furniture chair = new Chair();
            placeChair(chair);
        } else if (userChoice.equals("s")) {
            Furniture sofa = new Sofa();
            placeSofa(sofa);
        } else if (userChoice.equals("t")) {
            //placeCentreTable();
            //
        } else {
            System.out.println("Invalid choice!");
        }
    }

    public void placeChair(Furniture chair) {
        isThereSpaceAnyMore(chair);
        Scanner s = new Scanner(System.in);

        System.out.println("Choose a spot: ");
        String userChoice = s.nextLine();
        int spot = Integer.parseInt(userChoice);
        setChairInNumberedAndFurnitureList(chair, spot);
    }

    public void setChairInNumberedAndFurnitureList(Furniture c, int spot) {
        List<List<String>> numberedAndFurnitureList = getNumberedAndFurnitureList();
        String stringSpot = Integer.toString(spot);

        for (int i = 0; i < numberedAndFurnitureList.size(); i++) {
            List<String> subList = numberedAndFurnitureList.get(i);
            for (int j = 0; j < subList.size(); j++) {
                if (subList.get(j).equals(stringSpot)) {
                    subList.set(j, "Cv");
                    c.setSpot(spot);
                    addToFurnitureList(c);
                    setNumberedAndFurnitureList(i, subList);
                }
            }
        }
    }

    public void initiateNumberedAndFurnitureList() {

        for (List<String> subNumberedList : getNumberedPlane()) {
            this.numberedAndFurnitureList.add(subNumberedList);
        }
    }

    public void placeSofa(Furniture sofa) {
        isThereSpaceAnyMore(sofa);
        Scanner s = new Scanner(System.in);

        System.out.println("Choose two spots: ");
        String userChoice1 = s.nextLine();

        System.out.println(getTheOtherSpot(userChoice1));

        String userChoice2 = s.nextLine();
        int spot1 = Integer.parseInt(userChoice1);
        int spot2 = Integer.parseInt(userChoice2);
        setSofaInNumberedAndFurnitureList(sofa, spot1, spot2);
    }

    public String getTheOtherSpot(String spot) {
        List<List<String>> spotsInOriginalList = isThereSpaceForASofaInOriginalPlane();
        List<List<String>> spotsInInvertedList = isThereSpaceForASofaInInvertedPlane();

        List<String> listOfEligibleSpots = new ArrayList<>();

        for (List<String> subList : spotsInOriginalList) {
            String first = subList.get(0);
            String second = subList.get(1);

            if (first.equals(spot)) {
                listOfEligibleSpots.add(second);
            } else if (second.equals(spot)) {
                listOfEligibleSpots.add(first);
            }
        }

        for (List<String> subList : spotsInInvertedList) {
            String first = subList.get(0);
            String second = subList.get(1);

            if (first.equals(spot)) {
                listOfEligibleSpots.add(second);
            } else if (second.equals(spot)) {
                listOfEligibleSpots.add(first);
            }
        }
        return (listOfEligibleSpots.get(0) + " or " + listOfEligibleSpots.get(1));
    }

    public void setSofaInNumberedAndFurnitureList(Furniture s, int spot1, int spot2) {
        List<List<String>> numberedAndFurnitureList = getNumberedAndFurnitureList();
        String stringSpot1 = Integer.toString(spot1);
        String stringSpot2 = Integer.toString(spot2);

        List<String> tempSubList = new ArrayList<>();
        tempSubList.add(stringSpot1);
        tempSubList.add(stringSpot2);

        int count = 0;

        for (int i = 0; i < numberedAndFurnitureList.size(); i++) {
            List<String> subList = numberedAndFurnitureList.get(i);
            for (int j = 0; j < subList.size(); j++) {
                String str = subList.get(j);
                for (String spot : tempSubList) {
                    if (spot.equals(str)) {
                        if (count == 0) {
                            subList.set(j, "Sv");
                            count++;
                        } else {
                            subList.set(j, "vS");
                            s.setSpots(spot1, spot2);
                            addToFurnitureList(s);
                            setNumberedAndFurnitureList(i, subList);
                        }
                    }
                }
            }
        }
    }

    // REQUIRES: nothing
    // MODIFIES: this
    // EFFECTS: add f to furnitureList
    public void addToFurnitureList(Furniture f) {
        this.furnitureList.add(f);
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
    // MODIFIES: nothing
    // EFFECTS: returns the numberedPlane of the room
    public List<List<String>> getNumberedPlane() {
        return this.numberedPlane;
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: returns the furnitureList of the room
    public List<Furniture> getFurnitureList() {
        return this.furnitureList;
    }

    // REQUIRES: nothing
    // MODIFIES: this
    // EFFECTS: returns the numberedAndFurnitureList of the room
    public List<List<String>> getNumberedAndFurnitureList() {
        return this.numberedAndFurnitureList;
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
    // EFFECTS: sets the numberedPlane of the room to lst
    public void setNumberedPlane(List<List<String>> lst) {
        this.numberedPlane = lst;
    }

    // REQUIRES: subList is not empty
    // MODIFIES: this
    // EFFECTS: replaces the list in the index position with subList
    public void setNumberedAndFurnitureList(int index, List<String> subList) {
        this.numberedAndFurnitureList.set(index, subList);
    }
}