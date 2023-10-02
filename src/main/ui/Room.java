package ui;

import model.Dimension;

public class Room {
    Dimension dimension = new Dimension(10, 10);

    public void printRoom() {
        System.out.print("/ ");
        System.out.print(".".repeat((dimension.getLength() - 1) * 4));
        System.out.println(". \\");

        for (int i = 0; i < dimension.getWidth(); i++) {
            System.out.print("|");
            System.out.println(" _ |".repeat(dimension.getLength()));

        }
        System.out.print("\\ ");
        System.out.print(".".repeat((dimension.getLength() - 1) * 4));
        System.out.println(". /");
    }
}
