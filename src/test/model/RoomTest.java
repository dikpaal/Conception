package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static model.Color.BEIGE;
import static model.Direction.DOWN;
import static model.FurnitureType.CHAIR;
import static model.FurnitureType.SOFA;
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
        Dimension dimension = new Dimension(3, 3);
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
        assertEquals(2, room.getNumberedPlane().size());
        assertEquals(2, room.getNumberedPlane().get(0).size());
        assertEquals(2, room.getNumberedPlane().get(1).size());
    }

    @Test
    public void testCreateInvertedPlane() {
        room.setNumberedPlane(room.createNumberedPlane());
        room.initiateNumberedAndFurnitureList();
        List<List<String>> invertedList2 = room.createInvertedPlane();
        assertEquals(2, invertedList2.size());
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

    }

    @Test
    public void testReturnAvailableSpots() {
        room.setNumberedPlane(room.createNumberedPlane());
        room.initiateNumberedAndFurnitureList();
        centreTable.setSpotsForCenterTable(6, 5);
        room.addToFurnitureList(centreTable);
        room.setCentreTableSpotsInNumberedAndFurnitureList(6);
        room.setCenterTableInNumberedAndFurnitureList(centreTable, "6");
        List<String> emptyList = new ArrayList<>();
        List<String> availableSpots = new ArrayList<>();
        List<List<String>> sofaList = new ArrayList<>();
        List<String> subListWX = new ArrayList<>();
        List<String> subListWY = new ArrayList<>();
        List<String> subListYZ = new ArrayList<>();
        List<String> subListXZ = new ArrayList<>();

        availableSpots.add("6");
        availableSpots.add("7");
        availableSpots.add("10");
        availableSpots.add("11");

        subListWX.add("6");
        subListWX.add("7");
        subListWY.add("6");
        subListWY.add("11");
        subListYZ.add("10");
        subListYZ.add("11");
        subListXZ.add("7");
        subListXZ.add("11");

        sofaList.add(subListWX);
        sofaList.add(subListWY);
        sofaList.add(subListYZ);
        sofaList.add(subListXZ);

        assertTrue(sofaList.contains(subListWX));
        assertTrue(sofaList.contains(subListWY));
        assertTrue(sofaList.contains(subListXZ));
        assertTrue(sofaList.contains(subListYZ));
        assertEquals(emptyList, room.returnAvailableSpots(availableSpots,
                subListWX, subListWY, subListYZ, subListXZ));
        assertEquals(emptyList, room.returnAvailableSpots(emptyList, subListWX, subListWY, subListYZ, subListXZ));

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
    public void testGetListOfAllAddedFurniture1() {
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
        assertEquals(tempList, room.getListOfAllTheAddedFurniture("CHAIR"));
    }

    @Test
    public void testGetListOfAllAddedFurniture2() {
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
        assertEquals(tempList, room.getListOfAllTheAddedFurniture("SOFA"));
    }

    @Test
    public void testGetListOfAllAddedFurniture3() {
        room.setNumberedPlane(room.createNumberedPlane());
        room.initiateNumberedAndFurnitureList();
        centreTable.setSpotsForCenterTable(1, 4);
        room.addToFurnitureList(centreTable);
        List<String> tempList = new ArrayList<>();
        String c1 = "T in spot 1";
        tempList.add(c1);
        assertEquals(tempList, room.getListOfAllTheAddedFurniture("CT"));
    }

    @Test
    public void testToJson() {
        room.setNumberedPlane(room.createNumberedPlane());
        room.initiateNumberedAndFurnitureList();
        room.setUsername("Dikpaal");
        JSONObject json = room.toJson();
        assertEquals(room.getUsername(), json.get("name"));
    }

    @Test
    public void testFurnitureToJson() {
        room.setNumberedPlane(room.createNumberedPlane());
        room.initiateNumberedAndFurnitureList();

        chair.setSpot(2);
        sofa.setSpotsForSofa(3, 4);

        JSONObject chairObject = room.furnitureToJson(chair);
        JSONObject sofaObject = room.furnitureToJson(sofa);

        assertEquals("1", chairObject.get("dimension"));
        assertEquals("DOWN", chairObject.get("direction"));
        assertEquals("BEIGE", chairObject.get("color"));
        assertEquals("CHAIR", chairObject.get("type"));
        assertEquals("2", chairObject.get("spot"));

        assertEquals("2", sofaObject.get("dimension"));
        assertEquals("DOWN", sofaObject.get("direction"));
        assertEquals("BEIGE", sofaObject.get("color"));
        assertEquals("SOFA", sofaObject.get("type"));
    }

    @Test
    public void testFurnitureListToJson() {
        room.setNumberedPlane(room.createNumberedPlane());
        room.initiateNumberedAndFurnitureList();

        chair.setSpot(2);
        sofa.setSpotsForSofa(3, 4);

        room.addToFurnitureList(chair);
        room.addToFurnitureList(sofa);

        JSONArray furnitureListJsonArray = room.furnitureListToJson();

        JSONObject chairObject = furnitureListJsonArray.getJSONObject(0);
        JSONObject sofaObject = furnitureListJsonArray.getJSONObject(1);

        assertEquals("1", chairObject.get("dimension"));
        assertEquals("DOWN", chairObject.get("direction"));
        assertEquals("BEIGE", chairObject.get("color"));
        assertEquals("CHAIR", chairObject.get("type"));
        assertEquals("2", chairObject.get("spot"));

        assertEquals("2", sofaObject.get("dimension"));
        assertEquals("DOWN", sofaObject.get("direction"));
        assertEquals("BEIGE", sofaObject.get("color"));
        assertEquals("SOFA", sofaObject.get("type"));
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
    public void testSetNumberedAndFurnitureList() {
        List<List<String>> numberedAndFurnitureList = new ArrayList<>();
        List<String> subList1 = new ArrayList<>();
        List<String> subList2 = new ArrayList<>();

        subList1.add("1");
        subList1.add("2");
        subList2.add("3");
        subList2.add("4");

        numberedAndFurnitureList.add(subList1);
        numberedAndFurnitureList.add(subList2);

        room.setNumberedAndFurnitureList(numberedAndFurnitureList);

        assertEquals(numberedAndFurnitureList, room.getNumberedAndFurnitureList());


    }

    @Test
    public void testSetFurnitureList() {
        List<Furniture> furnitureList = new ArrayList<>();

        furnitureList.add(chair);
        furnitureList.add(sofa);

        room.setFurnitureList(furnitureList);

        assertEquals(furnitureList, room.getFurnitureList());


    }
}