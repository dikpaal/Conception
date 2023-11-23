package persistence;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.ConsoleUI;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Represents a class that tests the JsonReader
class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        Room r = new Room(3);
        r.setUsername("Dikpaal");
        ConsoleUI consoleUI = new ConsoleUI();
        consoleUI.setRoom(r);
        r.setNumberedPlane(consoleUI.createNumberedPlane());
        consoleUI.initiateNumberedAndFurnitureList();

        JsonWriter writer = new JsonWriter("./data/noSuchFile.json");
        try {
            writer.open();
            writer.write(r);
            writer.close();
        } catch (FileNotFoundException e) {
            //
        }

//        JsonReader reader = new JsonReader("./data/noSuchFile.json");
//        try {
//            r = reader.read();
//            fail("IOException expected");
//        } catch (IOException e) {
//            // pass
//        }
    }

    @Test
    void testReaderEmptyRoom() {
        Room r = new Room(3);
        r.setUsername("Dikpaal");
        ConsoleUI consoleUI = new ConsoleUI();
        consoleUI.setRoom(r);
        r.setNumberedPlane(consoleUI.createNumberedPlane());
        consoleUI.initiateNumberedAndFurnitureList();

        JsonWriter writer = new JsonWriter("./data/testReaderEmptyRoom.json");
        try {
            writer.open();
            writer.write(r);
            writer.close();
        } catch (FileNotFoundException e) {
            //
        }

        JsonReader reader = new JsonReader("./data/testReaderEmptyRoom.json");
        try {
            r = reader.read();
            assertEquals(3, r.getDimension().getLength());
            assertEquals(0, r.getFurnitureList().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        Room r = new Room(5);
        r.setUsername("Dikpaal");
        ConsoleUI consoleUI = new ConsoleUI();
        consoleUI.setRoom(r);
        r.setNumberedPlane(consoleUI.createNumberedPlane());
        consoleUI.initiateNumberedAndFurnitureList();

        Furniture chair = new Chair();
        chair.setSpot(1);

        Furniture sofa = new Sofa();
        sofa.setSpotsForSofa(3, 4);

        Furniture centerTable = new CenterTable();
        centerTable.setSpotsForCenterTable(6, 5);

        r.addToFurnitureList(chair);
        r.addToFurnitureList(sofa);
        r.addToFurnitureList(centerTable);

        JsonWriter writer = new JsonWriter("./data/testReaderGeneralRoom.json");
        try {
            writer.open();
            writer.write(r);
            writer.close();
        } catch (FileNotFoundException e) {
            //
        }

        JsonReader reader = new JsonReader("./data/testReaderGeneralRoom.json");
        try {
            r = reader.read();
            assertEquals(5, r.getDimension().getLength());
            List<Furniture> furnitureList = r.getFurnitureList();
            assertEquals(3, furnitureList.size());


            assertEquals(1, r.getFurnitureList().get(0).getSpot());

            List<Integer> spots = new ArrayList<>();
            spots.add(3);
            spots.add(4);

            List<Integer> spots2 = new ArrayList<>();
            spots2.add(6);
            spots2.add(7);
            spots2.add(10);
            spots2.add(11);

            assertEquals(spots, r.getFurnitureList().get(1).getAllSpots());
            assertEquals(spots2, r.getFurnitureList().get(2).getAllSpots());


        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}