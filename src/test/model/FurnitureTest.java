package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static model.Direction.*;
import static model.Color.*;
import static model.FurnitureType.*;
import static org.junit.jupiter.api.Assertions.*;

// Tests for the model classes
public class FurnitureTest {

    Furniture chair;
    Furniture sofa;
    Furniture centreTable;
    Dimension d;

    @BeforeEach
    void setup() {
        chair = new Chair();
        sofa = new Sofa();
        centreTable = new CenterTable();
    }

    @Test
    void testChairConstructor() {
        assertEquals(DOWN, chair.getDirection());
        assertEquals(CHAIR, chair.getType());
        assertEquals(BEIGE, chair.getColor());
    }

    @Test
    void testSofaConstructor() {
        List<Integer> emptyList = new ArrayList<>();
        assertEquals(DOWN, sofa.getDirection());
        assertEquals(SOFA, sofa.getType());
        assertEquals(BEIGE, sofa.getColor());
        assertEquals(emptyList, sofa.getAllSpots());
    }

    @Test
    void testCentreTableConstructor() {
        List<Integer> emptyList = new ArrayList<>();
        assertEquals(DOWN, centreTable.getDirection());
        assertEquals(CENTRETABLE, centreTable.getType());
        assertEquals(WHITE, centreTable.getColor());
        assertEquals(emptyList, centreTable.getAllSpots());
    }

    @Test
    void testDimensionConstructor() {
        d = new Dimension(5, 5);
        assertEquals(5, d.getLength());
        assertEquals(5, d.getWidth());
    }

    @Test
    void testGetDirection() {
        assertEquals(DOWN, sofa.getDirection());
        sofa.setDirection(NEUTRAL);
        assertEquals(NEUTRAL, sofa.getDirection());
    }

    @Test
    public void testGetSpot() {
        chair.setSpot(10);
        assertEquals(10, chair.getSpot());
    }

    @Test
    public void testGetAllSpots() {
        List<Integer> tempList = new ArrayList<>();
        tempList.add(10);
        tempList.add(11);
        sofa.setSpotsForSofa(10, 11);
        assertEquals(tempList, sofa.getAllSpots());
    }

    @Test
    public void testGetSofaSpots() {
        sofa.setSpotsForSofa(10, 11);
        assertEquals(10, sofa.getSofaSpots());
    }

    @Test
    public void testGetSecondSofaSpots() {
        sofa.setSpotsForSofa(10, 11);
        assertEquals(11, sofa.getSecondSofaSpots());
    }

    @Test
    public void testGetCentreTableSpots() {
        centreTable.setSpotsForCenterTable(6, 5);
        assertEquals(6, centreTable.getCentreTableSpots());
    }
}
