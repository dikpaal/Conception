package ui;

import model.Dimension;

import java.util.List;

public class Room {
    Dimension dimension = new Dimension(10, 10);
    List<List<String>> plane;

    public void printRoom() {

        int count = 0;

        for (int i = 0; i < dimension.getLength() * dimension.getWidth(); i++) {
            int number = i + 1;

            if (number < 10) {
                System.out.print("   " + number + " ");
            } else if (number < 100) {
                System.out.print("  " + number + " ");
            }
            if (number >= 100) {
                System.out.print(" " + number + " ");
            }
            count++;

            if (count == dimension.getWidth()) {
                System.out.println("");
                count = 0;
            }
//            System.out.print(".");

//
//            if (count == dimension.getWidth() / 2) {
//                System.out.println(".".repeat(3 * dimension.getLength() + 1));
//            }
        }
    }
}
