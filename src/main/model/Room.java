package model;

import org.json.JSONArray;
import org.json.JSONObject;

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
                    setNumberedAndFurnitureListSubListAtGivenIndex(i, subList);
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
                            setNumberedAndFurnitureListSubListAtGivenIndex(i, subList);
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
                    setNumberedAndFurnitureListSubListAtGivenIndex(i, subList);
                }

                if (spot.equals(bottomLeftSpot)) {
                    subList.set(j, "Tv");
                    subList.set(j + 1, "vT");
                    setNumberedAndFurnitureListSubListAtGivenIndex(i, subList);
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
                    setNumberedAndFurnitureListSubListAtGivenIndex(i, subList1);
                }
            }
        }
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: returns the json data of the room
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", this.username);
        json.put("dimension", Integer.toString(this.dimension.getLength()));
        json.put("numberedPlane", numberedPlaneToJson());
        json.put("furnitureList", furnitureListToJson());
        json.put("numberedAndFurnitureList", numberedAndFurnitureListToJsonArray());
        return json;
    }

    // EFFECTS: returns the json data of the numberedPlane
    private JSONObject numberedPlaneToJson() {

        JSONObject jsonObject = new JSONObject();

        for (int i = 0; i < numberedPlane.size(); i++) {
            String key = Integer.toString(i);
            List<String> subList = numberedPlane.get(i);
            jsonObject.put(key, parseSubList(subList));
        }

        return jsonObject;
    }

    // EFFECTS: returns the json data of the furniture list
    public JSONArray furnitureListToJson() {
        JSONArray jsonArray = new JSONArray();
        Object furnitureObject;

        for (Furniture f : furnitureList) {
            furnitureObject = furnitureToJson(f);
            jsonArray.put(furnitureObject);
        }
        return jsonArray;
    }

    // EFFECTS: returns the json data of f
    public JSONObject furnitureToJson(Furniture f) {
        JSONObject furnitureObject = new JSONObject();

        furnitureObject.put("dimension", Integer.toString(f.getDimension().getLength()));
        furnitureObject.put("direction", f.getDirection().name());
        furnitureObject.put("color", f.getColor().name());
        furnitureObject.put("type", f.getType().name());
        if (f.getType() == CHAIR) {
            furnitureObject.put("spot", Integer.toString(f.getSpot()));
        } else {
            furnitureObject.put("spots", spotsToJsonArray(f.getAllSpots()));
        }
        return furnitureObject;
    }

    // EFFECTS: returns the json data of spots
    public JSONArray spotsToJsonArray(List<Integer> spots) {

        JSONArray jsonArray = new JSONArray();

        for (int spot : spots) {
            String spotString = Integer.toString(spot);
            jsonArray.put(spotString);
        }
        return jsonArray;
    }

    // EFFECTS: returns the json data of the numberedAndFurnitureList
    private JSONObject numberedAndFurnitureListToJsonArray() {

        JSONObject jsonObject = new JSONObject();

        for (int i = 0; i < numberedAndFurnitureList.size(); i++) {
            String key = Integer.toString(i);
            List<String> subList = numberedAndFurnitureList.get(i);
            jsonObject.put(key, parseSubList(subList));
        }

        return jsonObject;
    }

    // EFFECTS: returns the json data of the subList
    private JSONArray parseSubList(List<String> subList) {

        JSONArray jsonArray = new JSONArray();

        for (String s : subList) {
            jsonArray.put(s);
        }
        return jsonArray;
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
    public void setNumberedAndFurnitureListSubListAtGivenIndex(int index, List<String> subList) {
        this.numberedAndFurnitureList.set(index, subList);
    }

    // REQUIRES: nothing
    // MODIFIES: this
    // EFFECTS: sets the numberedAndFurnitureList to numberedAndFurnitureList
    public void setNumberedAndFurnitureList(List<List<String>> numberedAndFurnitureList) {
        this.numberedAndFurnitureList = numberedAndFurnitureList;
    }

    // REQUIRES: nothing
    // MODIFIES: this
    // EFFECTS: sets the furnitureList to furnitureList
    public void setFurnitureList(List<Furniture> furnitureList) {
        this.furnitureList = furnitureList;
    }

    // REQUIRES: nothing
    // MODIFIES: this
    // EFFECTS: removes f from furnitureList
    public void removeFromFurnitureList(Furniture f) {
        this.furnitureList.remove(f);
    }
}
