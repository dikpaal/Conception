package model;

import java.util.ArrayList;
import java.util.List;

import static model.Color.BEIGE;
import static model.Direction.DOWN;
import static model.FurnitureType.SOFA;

// Represent a sofa (Furniture)
public class Sofa extends Furniture {

    // EFFECTS: creates a sofa facing down, type sofa, color beige, and empty spots
    public Sofa() {
        setDimension(new Dimension(2, 1));
        this.direction = DOWN;
        this.type = SOFA;
        this.color = BEIGE;
        this.spots = new ArrayList<>();
    }
}