package ui;

public class Main {
    public static void main(String[] args) {

        Room r = new Room(5);
        r.setDashedPlane(r.createDashedPlane());
        r.setNumberedPlane(r.createNumberedPlane());
//        r.mainUserInput();
        r.printRoom();
    }
}
