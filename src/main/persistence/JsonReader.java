package persistence;

import model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import model.Dimension;
import org.json.*;

import static model.FurnitureType.CHAIR;
import static model.FurnitureType.SOFA;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Room read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseRoom(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private Room parseRoom(JSONObject jsonObject) {
        String username = jsonObject.getString("name");
        int dimension = Integer.parseInt(jsonObject.getString("dimension"));
        List<List<String>> numberedPlane = parseNumberedPlane(jsonObject.getJSONObject("numberedPlane"));
        List<Furniture> furnitureList = parseFurnitureList(jsonObject.getJSONArray("furnitureList"));
        List<List<String>> numberedAndFurnitureList =
                parseNumberedAndFurnitureList(jsonObject.getJSONObject("numberedAndFurnitureList"));

        Room r = new Room(dimension);
        r.setUsername(username);
        r.setNumberedPlane(numberedPlane);
        r.setFurnitureList(furnitureList);
        r.setNumberedAndFurnitureList(numberedAndFurnitureList);
        return r;
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: parses the jsonObject and returns a numberedPlane
    private List<List<String>> parseNumberedPlane(JSONObject jsonObject) {

        List<List<String>> tempList = new ArrayList<>();
        List<String> tempSubList;

        for (int i = 0; i < jsonObject.length(); i++) {
            tempSubList = parseSubList(jsonObject.getJSONArray(Integer.toString(i)));
            tempList.add(tempSubList);
        }
        return tempList;
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: returns the parsed furnitureList
    private List<Furniture> parseFurnitureList(JSONArray jsonArray) {
        List<Furniture> tempList = new ArrayList<>();
        for (Object f : jsonArray) {
            JSONObject furniture = (JSONObject) f;
            Furniture tempFurniture = parseFurniture(furniture);
            tempList.add(tempFurniture);
        }
        return tempList;
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: parses the jsonObject and returns a Furniture
    private Furniture parseFurniture(JSONObject jsonObject) {
        int d = Integer.parseInt(jsonObject.getString("dimension"));
        Dimension dimension = new Dimension(d, d);
        Direction direction = Direction.valueOf(jsonObject.getString("direction"));
        Color color = Color.valueOf(jsonObject.getString("color"));
        FurnitureType type = FurnitureType.valueOf(jsonObject.getString("type"));

        Furniture furniture = setSpotOrSpots(jsonObject, type);

        furniture.setDimension(dimension);
        furniture.setDirection(direction);
        furniture.setColor(color);
        furniture.setType(type);

        return furniture;
    }

    // EFFECTS: if type is CHAIR, then sets spot and leaves out spots,
    //          otherwise, sets spots and leaves out spot
    private Furniture setSpotOrSpots(JSONObject jsonObject, FurnitureType type) {
        Furniture furniture;
        if (type == CHAIR) {
            int spot = Integer.parseInt(jsonObject.getString("spot"));
            furniture = new Chair();
            furniture.setSpot(spot);
        } else if (type == SOFA) {
            List<Integer> spots = parseSpots(jsonObject.getJSONArray("spots"));
            furniture = new Sofa();
            furniture.setSpots(spots);
        } else {
            List<Integer> spots = parseSpots(jsonObject.getJSONArray("spots"));
            furniture = new CenterTable();
            furniture.setSpots(spots);
        }

        return furniture;
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: return the parsed list of spots
    private List<Integer> parseSpots(JSONArray jsonArray) {

        List<Integer> tempList = new ArrayList<>();

        for (Object o : jsonArray) {
            String spotString = o.toString();
            int spotInt = Integer.parseInt(spotString);
            tempList.add(spotInt);
        }
        return tempList;
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: parses the jsonObject and returns a numberedAndFurnitureList
    private List<List<String>> parseNumberedAndFurnitureList(JSONObject jsonObject) {

        List<List<String>> tempList = new ArrayList<>();
        List<String> tempSubList;

        for (int i = 0; i < jsonObject.length(); i++) {
            tempSubList = parseSubList(jsonObject.getJSONArray(Integer.toString(i)));
            tempList.add(tempSubList);
        }
        return tempList;
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: parses the jsonArray and returns a subList
    private List<String> parseSubList(JSONArray jsonArray) {

        List<String> tempList = new ArrayList<>();

        for (Object o : jsonArray) {
            String string = o.toString();
            tempList.add(string);
        }
        return tempList;
    }
}