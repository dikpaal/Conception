package ui;

import model.Dimension;

import java.util.ArrayList;
import java.util.List;

public class Room {
    Dimension dimension = new Dimension(10, 10);
    List<List<String>> plane;

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
}
