package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static model.FurnitureType.CHAIR;
import static org.junit.jupiter.api.Assertions.*;

// Represents a class that tests the Room class
public class RoomTest {

    Room room;
    Furniture chair;
    Furniture sofa;
    Furniture centreTable;

    @BeforeEach
    public void setup() {

        // Room
        room = new Room(3);
        // Furniture
        chair = new Chair();
        sofa = new Sofa();
        centreTable = new CenterTable();
    }

    @Test
    public void testConstructor() {
        // test dimension
        Dimension dimension = new Dimension(5, 5);
        int length = dimension.getLength();
        int width = dimension.getWidth();
        assertEquals(length, room.getDimension().getLength());
        assertEquals(width, room.getDimension().getWidth());

        // test numberedPlane
        assertTrue(room.getNumberedPlane().isEmpty());

        // test furnitureList
        assertTrue(room.getFurnitureList().isEmpty());

        // test numberedAndFurnitureList
        assertTrue(room.getNumberedAndFurnitureList().isEmpty());
    }

    @Test
    public void testCreateNumberedPlane() {
        room.setNumberedPlane(room.createNumberedPlane());
        room.initiateNumberedAndFurnitureList();
        assertEquals(4, room.getNumberedPlane().size());
        assertEquals(4, room.getNumberedPlane().get(0).size());
        assertEquals(4, room.getNumberedPlane().get(1).size());
        assertEquals(4, room.getNumberedPlane().get(2).size());
        assertEquals(4, room.getNumberedPlane().get(3).size());
    }

    @Test
    public void testCreateInvertedPlane() {
        room.setNumberedPlane(room.createNumberedPlane());
        room.initiateNumberedAndFurnitureList();
        List<List<String>> tempList = new ArrayList<>();
        List<String> subList1 = new ArrayList<>();
        List<String> subList2 = new ArrayList<>();
        List<String> subList3 = new ArrayList<>();
        List<String> subList4 = new ArrayList<>();
        subList1.add(Integer.toString(1));
        subList1.add(Integer.toString(5));
        subList1.add(Integer.toString(9));
        subList1.add(Integer.toString(13));
        subList2.add(Integer.toString(2));
        subList2.add(Integer.toString(6));
        subList2.add(Integer.toString(10));
        subList2.add(Integer.toString(14));
        subList3.add(Integer.toString(3));
        subList3.add(Integer.toString(7));
        subList3.add(Integer.toString(11));
        subList3.add(Integer.toString(15));
        subList4.add(Integer.toString(4));
        subList4.add(Integer.toString(8));
        subList4.add(Integer.toString(12));
        subList4.add(Integer.toString(16));
        tempList.add(subList1);
        tempList.add(subList2);
        tempList.add(subList3);
        tempList.add(subList4);
        assertEquals(tempList, room.createInvertedPlane());

    }

    @Test
    public void testGetFurnitureListWithSpotsForChair() {
        List<String> tempList = new ArrayList<>();
        chair.setSpot(1);
        Furniture chair2 = new Chair();
        chair2.setSpot(2);
        tempList.add("C in spot " + chair.getSpot());
        tempList.add("C in spot " + chair2.getSpot());

        room.addToFurnitureList(chair);
        room.addToFurnitureList(chair2);
        assertEquals(tempList, room.getFurnitureListWithSpots());
    }

    @Test
    public void testGetFurnitureListWithSpotsForSofa() {
        List<String> tempList = new ArrayList<>();
        sofa.setSpotsForSofa(3, 4);
        Furniture sofa2 = new Sofa();
        sofa2.setSpotsForSofa(5, 9);
        tempList.add("S in spot " + sofa.getSofaSpots());
        tempList.add("S in spot " + sofa2.getSofaSpots());

        room.addToFurnitureList(sofa);
        room.addToFurnitureList(sofa2);
        assertEquals(tempList, room.getFurnitureListWithSpots());
    }

    @Test
    public void testGetFurnitureListWithSpotsForCentreTable() {
        List<String> tempList = new ArrayList<>();
        centreTable.setSpotsForCenterTable(6, 5);
        tempList.add("T in spot " + centreTable.getCentreTableSpots());

        room.addToFurnitureList(centreTable);
        assertEquals(tempList, room.getFurnitureListWithSpots());
    }

    @Test
    public void testIsThereSpaceAnymoreChair() {
        room.setNumberedPlane(room.createNumberedPlane());
        room.initiateNumberedAndFurnitureList();
        assertTrue(room.isThereSpaceAnyMore(chair));
        room.addToFurnitureList(chair);
        assertTrue(room.isThereSpaceAnyMore(chair));
        room.addToFurnitureList(sofa);
        assertTrue(room.isThereSpaceAnyMore(chair));
    }

    @Test
    public void testIsThereSpaceAnymoreSofa() {
        room.setNumberedPlane(room.createNumberedPlane());
        room.initiateNumberedAndFurnitureList();
        assertTrue(room.isThereSpaceAnyMore(sofa));
        room.addToFurnitureList(sofa);
        assertTrue(room.isThereSpaceAnyMore(sofa));
        room.addToFurnitureList(chair);
        assertTrue(room.isThereSpaceAnyMore(sofa));
    }

    @Test
    public void testIsThereSpaceAnymoreCentreTable() {
        room.setNumberedPlane(room.createNumberedPlane());
        room.initiateNumberedAndFurnitureList();
        assertTrue(room.isThereSpaceAnyMore(centreTable));
        room.addToFurnitureList(centreTable);
        room.setCenterTableInNumberedAndFurnitureList(centreTable, "6");
        assertFalse(room.isThereSpaceAnyMore(centreTable));
    }

    @Test
    public void testIsThereSpaceAnymoreCentreTableReturnsTrue() {
        Room tempRoom = new Room(3);
        tempRoom.setNumberedPlane(tempRoom.createNumberedPlane());
        tempRoom.initiateNumberedAndFurnitureList();
        assertTrue(tempRoom.isThereSpaceAnymoreForACentreTable());
    }

    @Test
    public void testIsThereSpaceAnymoreCentreTableReturnsFalse() {
        Room tempRoom = new Room(3);
        tempRoom.setNumberedPlane(tempRoom.createNumberedPlane());
        tempRoom.initiateNumberedAndFurnitureList();
        centreTable.setSpotsForCenterTable(1, 3);
        tempRoom.addToFurnitureList(centreTable);
        tempRoom.setCenterTableInNumberedAndFurnitureList(centreTable, "1");
        assertFalse(tempRoom.isThereSpaceAnymoreForACentreTable());
    }

    @Test
    public void testIsThereSpaceAnymoreForAChair() {
        Room tempRoom = new Room(1);
        tempRoom.addToFurnitureList(chair);
        assertFalse(tempRoom.isThereSpaceAnymoreForAChair());
    }

    @Test
    public void testIsThereSpaceAnymoreForASofaReturnsFalse() {
        Room tempRoom = new Room(3);
        tempRoom.setNumberedPlane(tempRoom.createNumberedPlane());
        tempRoom.initiateNumberedAndFurnitureList();
        sofa.setSpotsForSofa(1, 2);
        tempRoom.addToFurnitureList(sofa);
        tempRoom.setSofaInNumberedAndFurnitureList(sofa, 1, 2);

        Furniture sofa2 = new Sofa();
        sofa2.setSpotsForSofa(3, 4);
        tempRoom.addToFurnitureList(sofa2);
        tempRoom.setSofaInNumberedAndFurnitureList(sofa2, 3, 4);


        assertFalse(tempRoom.isThereSpaceAnymoreForASofa());
    }

    @Test
    public void testIsThereSpaceAnymoreForASofaReturnsTrue() {
        Room tempRoom = new Room(3);
        tempRoom.setNumberedPlane(tempRoom.createNumberedPlane());
        tempRoom.initiateNumberedAndFurnitureList();
        sofa.setSpotsForSofa(1, 2);
        tempRoom.addToFurnitureList(sofa);
        tempRoom.setSofaInNumberedAndFurnitureList(sofa, 1, 2);
        assertTrue(tempRoom.isThereSpaceAnymoreForASofa());
    }

    @Test
    public void testSpaceForAChair() {
        Room tempRoom = new Room(3);
        tempRoom.setNumberedPlane(tempRoom.createNumberedPlane());
        tempRoom.initiateNumberedAndFurnitureList();
        Furniture chair2 = new Chair();
        Furniture chair3 = new Chair();
        Furniture chair4 = new Chair();
        chair.setSpot(1);
        chair2.setSpot(2);
        chair3.setSpot(3);
        chair4.setSpot(4);
        tempRoom.addToFurnitureList(chair);
        tempRoom.addToFurnitureList(chair2);
        tempRoom.addToFurnitureList(chair3);
        tempRoom.addToFurnitureList(chair4);
        tempRoom.setChairInNumberedAndFurnitureList(chair, 1);
        tempRoom.setChairInNumberedAndFurnitureList(chair2, 2);
        tempRoom.setChairInNumberedAndFurnitureList(chair3, 3);
        tempRoom.setChairInNumberedAndFurnitureList(chair4, 4);

        try {
            tempRoom.spaceForAChair();
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testIsThereSpaceAnymoreForASofaInOriginalPlane() {
        Room tempRoom = new Room(3);
        tempRoom.setNumberedPlane(tempRoom.createNumberedPlane());
        tempRoom.initiateNumberedAndFurnitureList();
        Furniture sofa2 = new Sofa();
        sofa.setSpotsForSofa(1, 2);
        sofa2.setSpotsForSofa(3, 4);
        tempRoom.addToFurnitureList(sofa);
        tempRoom.setSofaInNumberedAndFurnitureList(sofa, 1, 2);
        tempRoom.addToFurnitureList(sofa2);
        tempRoom.setSofaInNumberedAndFurnitureList(sofa2, 3, 4);

        try {
            tempRoom.spaceForASofaInOriginalPlane();
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testIsThereSpaceAnymoreForASofaInInvertedPlane() {
        Room tempRoom = new Room(3);
        tempRoom.setNumberedPlane(tempRoom.createNumberedPlane());
        tempRoom.initiateNumberedAndFurnitureList();
        Furniture sofa2 = new Sofa();
        sofa.setSpotsForSofa(1, 3);
        sofa2.setSpotsForSofa(2, 4);
        tempRoom.addToFurnitureList(sofa);
        tempRoom.setSofaInNumberedAndFurnitureList(sofa, 1, 3);
        tempRoom.addToFurnitureList(sofa2);
        tempRoom.setSofaInNumberedAndFurnitureList(sofa2, 2, 4);

        try {
            tempRoom.spaceForASofaInInvertedPlane();
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testReturnAvailableSpots() {
        room.setNumberedPlane(room.createNumberedPlane());
        room.initiateNumberedAndFurnitureList();
        centreTable.setSpotsForCenterTable(6, 5);
        room.addToFurnitureList(centreTable);
        List<String> emptyList = new ArrayList<>();
        List<String> tempList = new ArrayList<>();
        List<String> subList1 = new ArrayList<>();
        List<String> subList2 = new ArrayList<>();
        tempList.add("9");
        tempList.add("7");
        tempList.add("10");
        tempList.add("11");
        subList1.add("9");
        subList1.add("7");
        subList2.add("10");
        subList2.add("11");

        assertEquals(emptyList, room.returnAvailableSpots(tempList, subList1, subList2, subList1, subList2));
    }

    @Test
    public void testSetChairInNumberedAndFurnitureList() {
        Room tempRoom = new Room(3);
        tempRoom.setNumberedPlane(tempRoom.createNumberedPlane());
        tempRoom.initiateNumberedAndFurnitureList();

        List<List<String>> tempList = new ArrayList<>();
        List<String> subList1 = new ArrayList<>();
        List<String> subList2 = new ArrayList<>();
        subList1.add("1");
        subList1.add("2");
        subList2.add("Cv");
        subList2.add("4");
        tempList.add(subList1);
        tempList.add(subList2);
        tempRoom.setChairInNumberedAndFurnitureList(chair, 3);
        assertEquals(tempList, tempRoom.getNumberedAndFurnitureList());
    }

    @Test
    public void testGetTheOtherSpot() {
        Room tempRoom = new Room(3);
        tempRoom.setNumberedPlane(tempRoom.createNumberedPlane());
        tempRoom.initiateNumberedAndFurnitureList();

        List<String> subList1 = new ArrayList<>();
        List<String> subList2 = new ArrayList<>();
        subList1.add("1");
        subList1.add("2");
        subList2.add("3");
        subList2.add("4");

        assertEquals("2 or 3", tempRoom.getTheOtherSpot("1"));
        assertEquals("1 or 4", tempRoom.getTheOtherSpot("2"));
        assertEquals("3 or 2", tempRoom.getTheOtherSpot("4"));
    }

    @Test
    public void testSetSofaInNumberedAndFurnitureList() {
        Room tempRoom = new Room(3);
        tempRoom.setNumberedPlane(tempRoom.createNumberedPlane());
        tempRoom.initiateNumberedAndFurnitureList();

        List<List<String>> tempList = new ArrayList<>();
        List<String> subList1 = new ArrayList<>();
        List<String> subList2 = new ArrayList<>();
        subList1.add("Sv");
        subList1.add("vS");
        subList2.add("3");
        subList2.add("4");
        tempList.add(subList1);
        tempList.add(subList2);
        tempRoom.setSofaInNumberedAndFurnitureList(sofa, 1, 2);
        assertEquals(tempList, tempRoom.getNumberedAndFurnitureList());
    }

    @Test
    public void testSetCenterTableInNumberedAndFurnitureList() {
        Room tempRoom = new Room(3);
        tempRoom.setNumberedPlane(tempRoom.createNumberedPlane());
        tempRoom.initiateNumberedAndFurnitureList();

        List<List<String>> tempList = new ArrayList<>();
        List<String> subList1 = new ArrayList<>();
        List<String> subList2 = new ArrayList<>();
        subList1.add("Tv");
        subList1.add("vT");
        subList2.add("Tv");
        subList2.add("vT");
        tempList.add(subList1);
        tempList.add(subList2);
        tempRoom.setCenterTableInNumberedAndFurnitureList(centreTable, "1");
        assertEquals(tempList, tempRoom.getNumberedAndFurnitureList());
    }

    @Test
    public void testRemoveChairFromSpot() {
        Room tempRoom = new Room(3);
        tempRoom.setNumberedPlane(tempRoom.createNumberedPlane());
        tempRoom.initiateNumberedAndFurnitureList();

        chair.setSpot(1);
        tempRoom.setChairInNumberedAndFurnitureList(chair, 1);
        tempRoom.addToFurnitureList(chair);
        tempRoom.removeChairFromSpot("1");

        List<List<String>> tempList = new ArrayList<>();
        List<String> subList1 = new ArrayList<>();
        List<String> subList2 = new ArrayList<>();
        subList1.add("1");
        subList1.add("2");
        subList2.add("3");
        subList2.add("4");
        tempList.add(subList1);
        tempList.add(subList2);


        assertEquals(tempList, tempRoom.getNumberedAndFurnitureList());
    }

    @Test
    public void testRemoveSofaFromSpot() {
        Room tempRoom = new Room(3);
        tempRoom.setNumberedPlane(tempRoom.createNumberedPlane());
        tempRoom.initiateNumberedAndFurnitureList();

        sofa.setSpotsForSofa(1, 2);
        tempRoom.setSofaInNumberedAndFurnitureList(sofa, 1, 2);
        tempRoom.addToFurnitureList(sofa);
        tempRoom.removeSofaFromSpot("1", "2");


        List<List<String>> tempList1 = new ArrayList<>();
        List<String> tempSubList1 = new ArrayList<>();
        List<String> tempSubList2 = new ArrayList<>();
        tempSubList1.add("1");
        tempSubList1.add("2");
        tempSubList2.add("3");
        tempSubList2.add("4");
        tempList1.add(tempSubList1);
        tempList1.add(tempSubList2);

        assertEquals(tempList1, tempRoom.spaceForASofaInOriginalPlane());

        List<List<String>> tempList = new ArrayList<>();
        List<String> subList1 = new ArrayList<>();
        List<String> subList2 = new ArrayList<>();
        subList1.add("1");
        subList1.add("2");
        subList2.add("3");
        subList2.add("4");
        tempList.add(subList1);
        tempList.add(subList2);

        assertEquals(tempList, tempRoom.getNumberedAndFurnitureList());
    }

    @Test
    public void testRemoveCentreTableFromSpot() {
        Room tempRoom = new Room(3);
        tempRoom.setNumberedPlane(tempRoom.createNumberedPlane());
        tempRoom.initiateNumberedAndFurnitureList();

        centreTable.setSpotsForCenterTable(1, 4);
        tempRoom.setCenterTableInNumberedAndFurnitureList(centreTable, "1");
        tempRoom.addToFurnitureList(centreTable);
        tempRoom.removeCentreTableFromSpot("1");

        List<List<String>> tempList = new ArrayList<>();
        List<String> subList1 = new ArrayList<>();
        List<String> subList2 = new ArrayList<>();
        subList1.add("1");
        subList1.add("2");
        subList2.add("3");
        subList2.add("4");
        tempList.add(subList1);
        tempList.add(subList2);

        assertEquals(tempList, tempRoom.getNumberedAndFurnitureList());
    }

    @Test
    public void testGetSpot2Sofa() {
        room.setNumberedPlane(room.createNumberedPlane());
        room.initiateNumberedAndFurnitureList();
        sofa.setSpotsForSofa(1, 2);
        room.addToFurnitureList(sofa);
        assertEquals("2", room.getSpot2Sofa("1"));
    }

    @Test
    public void testGetListOfAllAddedChairs() {
        room.setNumberedPlane(room.createNumberedPlane());
        room.initiateNumberedAndFurnitureList();
        chair.setSpot(1);
        room.addToFurnitureList(chair);
        Furniture chair2 = new Chair();
        chair2.setSpot(2);
        room.addToFurnitureList(chair2);
        List<String> tempList = new ArrayList<>();
        String c1 = "C in spot 1";
        String c2 = "C in spot 2";
        tempList.add(c1);
        tempList.add(c2);
        assertEquals(tempList, room.getListOfAllTheAddedChairs());
    }

    @Test
    public void testGetListOfAllAddedSofas() {
        room.setNumberedPlane(room.createNumberedPlane());
        room.initiateNumberedAndFurnitureList();
        sofa.setSpotsForSofa(1, 2);
        room.addToFurnitureList(sofa);
        Furniture sofa2 = new Sofa();
        sofa2.setSpotsForSofa(3, 4);
        room.addToFurnitureList(sofa2);
        List<String> tempList = new ArrayList<>();
        String c1 = "S in spot 1";
        String c2 = "S in spot 3";
        tempList.add(c1);
        tempList.add(c2);
        assertEquals(tempList, room.getListOfAllTheAddedSofas());
    }

    @Test
    public void testGetListOfAllAddedCentreTable() {
        room.setNumberedPlane(room.createNumberedPlane());
        room.initiateNumberedAndFurnitureList();
        centreTable.setSpotsForCenterTable(1, 4);
        room.addToFurnitureList(centreTable);
        List<String> tempList = new ArrayList<>();
        String c1 = "T in spot 1";
        tempList.add(c1);
        assertEquals(tempList, room.getListOfAllTheAddedCentreTable());
    }

    // TESTS FOR UNTESTED GETTER AND SETTER METHODS

    @Test
    public void testGetUsername() {
        room.setUsername("Dikpaal");
        assertEquals("Dikpaal", room.getUsername());
    }

    @Test
    public void testSetDimension() {
        room.setDimension(5);
        assertEquals(5, room.getDimension().getLength());
    }

    @Test
    public void testToJson() {
        room.setNumberedPlane(room.createNumberedPlane());
        room.initiateNumberedAndFurnitureList();
        room.setUsername("Dikpaal");
        JSONObject json = room.toJson();
        assertEquals(room.getUsername(), json.get("name"));
    }

}