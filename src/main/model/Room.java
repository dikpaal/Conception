package model;

import java.util.ArrayList;
import java.util.List;

import static model.FurnitureType.*;

// Represents a room
public class Room {

    String username; // the name of the user
    Dimension dimension; // the dimension of the room
    List<List<String>> numberedPlane; // the numbered plane showcasing the numbers of each block
    List<Furniture> furnitureList; // the list of furniture placed in the room so far
    List<List<String>> numberedAndFurnitureList; // the list to be used to find remaining space for a furniture

    // REQUIRES: d > 0 and d is odd
    // MODIFIES: nothing
    // EFFECTS: constructs a room with dimension d and empty numberedPlane, furnitureList, and numberedAndFurnitureList
    public Room(int d) {
        this.dimension = new Dimension(d, d);
        this.numberedPlane = new ArrayList<>();
        this.furnitureList = new ArrayList<>();
        this.numberedAndFurnitureList = new ArrayList<>();
    }

    // REQUIRES: nothing
    // MODIFIES: this
    // EFFECTS: creates a (getDimension.getLength() - 1) x (getDimension.getLength() - 1) matrix of numbers
    //          from 01 to (getDimension().getLength - 1)^2
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
    // EFFECTS: prints out a list of furniture so far with the names of the furniture and the spots at which they are
    //          placed
    public List<String> getFurnitureListWithSpots() {
        List<String> tempList = new ArrayList<>();

        for (int i = 0; i < getFurnitureList().size(); i++) {
            Furniture f = getFurnitureList().get(i);

            if (f.getType() == CHAIR) {
                tempList.add("C in spot " + f.getSpot());
            } else if (f.getType() == SOFA) {
                tempList.add("S in spot " + f.getSofaSpots());
            } else {
                tempList.add("T in spot " + f.getCentreTableSpots());
            }
        }
        return tempList;
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: returns true, if f can be placed in the room (is there space for it or not),
    //          otherwise returns false
    public boolean isThereSpaceAnyMore(Furniture f) {
        if (f.getType() == CHAIR) {
            return isThereSpaceAnymoreForAChair();
        } else if (f.getType() == FurnitureType.SOFA) {
            return isThereSpaceAnymoreForASofa();
        } else {
            return isThereSpaceAnymoreForACentreTable();
        }
    }


    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: returns true, if chair f can be placed in the room (is there space for it or not),
    //          otherwise returns false
    public boolean isThereSpaceAnymoreForAChair() {
        if (spaceForAChair().isEmpty()) {
            System.out.println("Sorry, no space for a chair anymore!");
            return false;
        } else {
            System.out.println("You can place a chair in: " + spaceForAChair());
            return true;
        }
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: returns true, if sofa f can be placed in the room (is there space for it or not),
    //          otherwise returns false
    public boolean isThereSpaceAnymoreForASofa() {
        if (spaceForASofa().isEmpty()) {
            System.out.println("Sorry, no space for a sofa anymore!");
            return false;
        } else {
            System.out.println("You can place a sofa in: " + spaceForASofa());
            return true;
        }
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: returns true, if centre table f can be placed in the room (is there space for it or not),
    //          otherwise returns false
    public boolean isThereSpaceAnymoreForACentreTable() {
        if (spaceForACentreTable().isEmpty()) {
            System.out.println("Sorry, no space for a centre table anymore!");
            return false;
        } else {
            System.out.println("You can place a centre table in between: " + spaceForACentreTable());
            return true;
        }
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: returns the spots where a chair can be placed
    public List<String> spaceForAChair() {
        List<String> availableSpots = new ArrayList<>();
        for (List<String> subList : getNumberedAndFurnitureList()) {
            for (String s : subList) {
                try {
                    int number = Integer.parseInt(s); // required because
                    // if s is not a number, it will still be added to availableSpots().
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
    public List<List<String>> spaceForASofa() {
        List<List<String>> availableSpotsInOriginalPlane = spaceForASofaInOriginalPlane();
        List<List<String>> availableSpotsInInvertedPlane = spaceForASofaInInvertedPlane();

        return mergeLists(availableSpotsInOriginalPlane, availableSpotsInInvertedPlane);
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: returns the list of available spots FOR A SOFA in the original plane
    public List<List<String>> spaceForASofaInOriginalPlane() {
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
    public List<List<String>> spaceForASofaInInvertedPlane() {
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
    // EFFECTS: returns the spots where a centre table can be placed
    public List<String> spaceForACentreTable() {
        Dimension d = getDimension();
        int w = (((d.getLength() - 1) / 2) * d.getLength()) - (d.getLength() - 1);
        int x = w + 1;
        int y = w + (d.getLength() - 1);
        int z = y + 1;

        List<String> subListWX = new ArrayList<>();
        subListWX.add(Integer.toString(w));
        subListWX.add(Integer.toString(x));

        List<String> subListWY = new ArrayList<>();
        subListWY.add(Integer.toString(w));
        subListWY.add(Integer.toString(y));

        List<String> subListYZ = new ArrayList<>();
        subListYZ.add(Integer.toString(y));
        subListYZ.add(Integer.toString(z));

        List<String> subListXZ = new ArrayList<>();
        subListXZ.add(Integer.toString(x));
        subListXZ.add(Integer.toString(z));

        List<String> availableSpots = new ArrayList<>();
        availableSpots.add(Integer.toString(w));
        availableSpots.add(Integer.toString(x));
        availableSpots.add(Integer.toString(y));
        availableSpots.add(Integer.toString(z));

        return returnAvailableSpots(availableSpots, subListWX, subListWY, subListYZ, subListXZ);
    }

    // REQUIRES: availableSpots, subListWX, subListWY, subListYZ, and subListXZ are not empty
    // MODIFIES: nothing
    // EFFECTS: returns the available spots to the spaceForACentreTable() method
    public List<String> returnAvailableSpots(List<String> availableSpots,
                                             List<String> subListWX,
                                             List<String> subListWY,
                                             List<String> subListYZ,
                                             List<String> subListXZ) {
        List<String> emptyList = new ArrayList<>();
        List<List<String>> sofaList = spaceForASofa();

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
    // MODIFIES: nothing
    // EFFECTS: concatenates l1 and l2 and returns the merged list
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


    // REQUIRES: spot > 0 and spot in numberedList()
    // MODIFIES: nothing
    // EFFECTS: sets the chair c in spot in the numberedAndFurnitureList
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

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: initiates/creates the numberedAndFurnitureList
    public void initiateNumberedAndFurnitureList() {

        List<List<String>> tempList = new ArrayList<>();
        for (int i = 0; i < getNumberedPlane().size(); i++) {
            List<String> tempSubList = new ArrayList<>();
            List<String> subList = getNumberedPlane().get(i);
            for (int j = 0; j < subList.size(); j++) {
                tempSubList.add(subList.get(j));
            }
            tempList.add(tempSubList);
        }
        this.numberedAndFurnitureList = tempList;
    }

    // REQUIRES: spot > 0 and spot in numberedList()
    // MODIFIES: nothing
    // EFFECTS: gets the other spot for the sofa from the original and inverted plane and returns the spots
    public String getTheOtherSpot(String spot) {
        List<List<String>> spotsInOriginalList = spaceForASofaInOriginalPlane();
        List<List<String>> spotsInInvertedList = spaceForASofaInInvertedPlane();

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

    // REQUIRES: spot1 > 0 and spot2 > 0 and spot1 and spot2 in the numberedList()
    // MODIFIES: nothing
    // EFFECTS: sets the sofa s in spot1 and spot2 in the numberedAndFurnitureList
    public void setSofaInNumberedAndFurnitureList(Furniture s, int spot1, int spot2) {
        List<String> tempSubList = new ArrayList<>();
        tempSubList.add(Integer.toString(spot1));
        tempSubList.add(Integer.toString(spot2));

        int count = 0;

        for (int i = 0; i < getNumberedAndFurnitureList().size(); i++) {
            List<String> subList = getNumberedAndFurnitureList().get(i);
            for (int j = 0; j < subList.size(); j++) {
                String str = subList.get(j);
                for (String spot : tempSubList) {
                    if (spot.equals(str)) {
                        if (count == 0) {
                            subList.set(j, "Sv");
                            count++;
                        } else {
                            subList.set(j, "vS");
                            s.setSpotsForSofa(spot1, spot2);
                            addToFurnitureList(s);
                            setNumberedAndFurnitureList(i, subList);
                        }
                    }
                }
            }
        }
    }

    // REQUIRES: topLeftSpot != ""
    // MODIFIES: nothing
    // EFFECTS: sets the centre table in the numberedAndFurnitureList in the topLeftSpot, topRightSpot,
    //          bottomLeftSpot, and bottomRightSpot
    public void setCenterTableInNumberedAndFurnitureList(Furniture ct, String topLeftSpot) {

        int roomLength = getDimension().getLength();
        int topLeftSpotInt = Integer.parseInt(topLeftSpot);
        int bottomLeftSpotInt = topLeftSpotInt + (roomLength - 1);

        String bottomLeftSpot = Integer.toString(bottomLeftSpotInt);

        List<List<String>> numberedAndFurnitureList = getNumberedAndFurnitureList();

        for (int i = 0; i < numberedAndFurnitureList.size(); i++) {
            List<String> subList = numberedAndFurnitureList.get(i);
            for (int j = 0; j < numberedAndFurnitureList.size(); j++) {
                String spot = subList.get(j);

                if (spot.equals(topLeftSpot)) {
                    subList.set(j, "Tv");
                    subList.set(j + 1, "vT");
                    ct.setSpotsForCenterTable(topLeftSpotInt, roomLength);
                    addToFurnitureList(ct);
                    setNumberedAndFurnitureList(i, subList);
                }

                if (spot.equals(bottomLeftSpot)) {
                    subList.set(j, "Tv");
                    subList.set(j + 1, "vT");
                    setNumberedAndFurnitureList(i, subList);
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

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: removes the chair from the spot in the furnitureList
    public void removeChairFromSpot(String spot) {
        for (int i = 0; i < getFurnitureList().size(); i++) {
            Furniture f = getFurnitureList().get(i);
            if (f.getType() == CHAIR) {
                if (f.getSpot() == Integer.parseInt(spot)) {
                    setSingleSpotInNumberedAndFurnitureList(spot);
                    furnitureList.remove(f);
                }
            }
        }
    }

    // REQUIRES: Integer,parseInt(spot1) > 0 and Integer,parseInt(spot2) > 0 and spot1 and spot2 in numberedList()
    // MODIFIES: nothing
    // EFFECTS: removes the sofa from spot1 and spot2 in the furnitureList
    public void removeSofaFromSpot(String spot1, String spot2) {
        for (int i = 0; i < getFurnitureList().size(); i++) {
            Furniture f = getFurnitureList().get(i);
            if (f.getType() == SOFA) {
                if ((f.getSofaSpots() == Integer.parseInt(spot1))
                        && (f.getSecondSofaSpots() == Integer.parseInt(spot2))) {
                    setSingleSpotInNumberedAndFurnitureList(spot1);
                    setSingleSpotInNumberedAndFurnitureList(spot2);
                    furnitureList.remove(f);
                }
            }
        }
    }

    // REQUIRES: Integer.parseInt(spot1) > 0 and spot1 in numberedList()
    // MODIFIES: nothing
    // EFFECTS: removes the centre table from all the necessary spots based on spot1 in the furnitureList
    public void removeCentreTableFromSpot(String spot1) {
        for (int i = 0; i < getFurnitureList().size(); i++) {
            Furniture f = getFurnitureList().get(i);
            if (f.getType() == CENTRETABLE) {
                if (f.getCentreTableSpots() == Integer.parseInt(spot1)) {
                    setCentreTableSpotsInNumberedAndFurnitureList(Integer.parseInt(spot1));
                    furnitureList.remove(f);
                }
            }
        }
    }

    // REQUIRES: spot > 0
    // MODIFIES: nothing
    // EFFECTS: resets the spot numbers in the numberedAndFurnitureList for a centre table
    public void setCentreTableSpotsInNumberedAndFurnitureList(int spot1) {
        int spot2 = spot1 + 1;
        int spot3 = spot1 + (getDimension().getLength() - 1);
        int spot4 = spot3 + 1;

        setSingleSpotInNumberedAndFurnitureList(Integer.toString(spot1));
        setSingleSpotInNumberedAndFurnitureList(Integer.toString(spot2));
        setSingleSpotInNumberedAndFurnitureList(Integer.toString(spot3));
        setSingleSpotInNumberedAndFurnitureList(Integer.toString(spot4));
    }

    // REQUIRES: Integer.parseInt(spot1) > 0 and spot1 in numberedList()
    // MODIFIES: nothing
    // EFFECTS: resets a single spot to spot in the numberedAndFurnitureList
    public void setSingleSpotInNumberedAndFurnitureList(String spot) {
        List<List<String>> numberedList = getNumberedPlane();

        int rowCount = 0;
        int colCount = 0;

        for (int i = 0; i < numberedList.size(); i++) {
            List<String> subList = numberedList.get(i);
            for (int j = 0; j < subList.size(); j++) {
                String number = subList.get(j);

                if (number.equals(spot)) {
                    rowCount = i;
                    colCount = j;
                }
            }
        }

        List<List<String>> numberedAndFurnitureList = getNumberedAndFurnitureList();

        for (int i = 0; i < numberedAndFurnitureList.size(); i++) {
            List<String> subList1 = numberedAndFurnitureList.get(i);
            for (int j = 0; j < subList1.size(); j++) {
                if ((i == rowCount) && (j == colCount)) {
                    subList1.set(j, spot);
                    setNumberedAndFurnitureList(i, subList1);
                }
            }
        }
    }

    // REQUIRES: Integer.parseInt(spot1) > 0 and spot1 in numberedList()
    // MODIFIES: nothing
    // EFFECTS: returns the second spot of the sofa
    public String getSpot2Sofa(String spot1) {
        List<Furniture> furnitureList = getFurnitureList();

        String spot2 = "";

        for (Furniture f : furnitureList) {
            if (f.getType() == SOFA) {
                if (f.getSofaSpots() == Integer.parseInt(spot1)) {
                    spot2 = Integer.toString(f.getSecondSofaSpots());
                }
            }
        }
        return spot2;
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: returns a list of all the chairs that has been added so far
    public List<String> getListOfAllTheAddedChairs() {
        List<String> lst = getFurnitureListWithSpots();
        List<String> tempList = new ArrayList<>();
        for (String s : lst) {
            if (Character.toString(s.charAt(0)).equals("C")) {
                tempList.add(s);
            }
        }
        return tempList;
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: returns a list of all sofas that has been added so far
    public List<String> getListOfAllTheAddedSofas() {
        List<String> lst = getFurnitureListWithSpots();
        List<String> tempList = new ArrayList<>();
        for (String s : lst) {
            if (Character.toString(s.charAt(0)).equals("S")) {
                tempList.add(s);
            }
        }
        return tempList;
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: returns a list of the centre table that has been added so far
    public List<String> getListOfAllTheAddedCentreTable() {
        List<String> lst = getFurnitureListWithSpots();
        List<String> tempList = new ArrayList<>();
        for (String s : lst) {
            if (Character.toString(s.charAt(0)).equals("T")) {
                tempList.add(s);
            }
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
    // MODIFIES: nothing
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

    // REQUIRES: d > 0 and d is odd
    // MODIFIES: this
    // EFFECTS: sets the dimension of the room to new Dimension(d, d)
    public void setDimension(int d) {
        this.dimension = new Dimension(d, d);
    }

    // REQUIRES: lst is not empty
    // MODIFIES: this
    // EFFECTS: sets the numberedPlane of the room to lst
    public void setNumberedPlane(List<List<String>> lst) {
        this.numberedPlane = lst;
    }

    // REQUIRES:  0 <= index < numberedAndFurnitureList.size() and subList is not empty
    // MODIFIES: this
    // EFFECTS: replaces the list in the index position with subList
    public void setNumberedAndFurnitureList(int index, List<String> subList) {
        this.numberedAndFurnitureList.set(index, subList);
    }
}
