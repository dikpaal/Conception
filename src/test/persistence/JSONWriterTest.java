//package persistence;
//
//import com.sun.tools.jconsole.JConsoleContext;
//import model.Chair;
//import model.Furniture;
//import model.Room;
//import model.Sofa;
//import org.junit.jupiter.api.Test;
//import ui.ConsoleUI;
//
//import java.io.IOException;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//// Represents a class that tests the JsonWriter
//class JsonWriterTest {
//    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
//    //write data to a file and then use the reader to read it back in and check that we
//    //read in a copy of what was written out.
//
//    @Test
//    void testWriterInvalidFile() {
//        try {
//            Room r = new Room(3);
//            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
//            writer.open();
//            fail("IOException was expected");
//        } catch (IOException e) {
//            // pass
//        }
//    }
//
//    @Test
//    void testWriterEmptyWorkroom() {
//        try {
//            Room r = new Room(3);
//            r.setUsername("Dikpaal");
//            ConsoleUI consoleUI = new ConsoleUI();
//            consoleUI.setRoom(r);
//            r.setNumberedPlane(consoleUI.createNumberedPlane());
//            consoleUI.initiateNumberedAndFurnitureList();
//            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkroom.json");
//            writer.open();
//            writer.write(r);
//            writer.close();
//
//            JsonReader reader = new JsonReader("./data/testWriterEmptyWorkroom.json");
//            r = reader.read();
//            assertEquals(3, r.getDimension().getLength());
//            assertEquals(0, r.getFurnitureList().size());
//        } catch (IOException e) {
//            fail("Exception should not have been thrown");
//        }
//    }
//
//    @Test
//    void testWriterGeneralWorkroom() {
//        try {
//            Room r = new Room(3);
//            r.setUsername("Dikpaal");
//            ConsoleUI consoleUI = new ConsoleUI();
//            consoleUI.setRoom(r);
//            r.setNumberedPlane(consoleUI.createNumberedPlane());
//            consoleUI.initiateNumberedAndFurnitureList();
//            r.addToFurnitureList(new Chair());
//            r.addToFurnitureList(new Sofa());
//            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWorkroom.json");
//            writer.open();
//            writer.write(r);
//            writer.close();
//
//            JsonReader reader = new JsonReader("./data/testWriterGeneralWorkroom.json");
//            r = reader.read();
//            assertEquals(3, r.getDimension().getLength());
//            List<Furniture> thingies = r.getFurnitureList();
//            assertEquals(2, thingies.size());
//        } catch (IOException e) {
//            fail("Exception should not have been thrown");
//        }
//    }
//}