package ui;

import model.Chair;
import model.Furniture;

public class Main {
    public static void main(String[] args) {

        Room r = new Room(5);
        Furniture c = new Chair();
        r.setDashedPlane(r.createDashedPlane());
        r.setNumberedPlane(r.createNumberedPlane());
//        r.mainUserInput();
        r.printRoom();
        System.out.println(r.getNumberedPlane());
        r.isThereSpaceAnyMore(c);
    }
}
