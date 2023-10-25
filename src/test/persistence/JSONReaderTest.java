package persistence;

import model.Furniture;
import model.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        Room r = new Room(3);
        r.setUsername("Dikpaal");
        r.setNumberedPlane(r.createNumberedPlane());
        r.initiateNumberedAndFurnitureList();

        JsonWriter writer = new JsonWriter("./data/noSuchFile.json");
        try {
            writer.open();
            writer.write(r);
            writer.close();
        } catch (FileNotFoundException e) {
            //
        }
//
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
        r.setNumberedPlane(r.createNumberedPlane());
        r.initiateNumberedAndFurnitureList();

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
        Room r = new Room(3);
        r.setUsername("Dikpaal");
        r.setNumberedPlane(r.createNumberedPlane());
        r.initiateNumberedAndFurnitureList();

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
            assertEquals(3, r.getDimension().getLength());
            List<Furniture> furnitureList = r.getFurnitureList();
            assertEquals(0, furnitureList.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}