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
