package model;

import static model.Color.BEIGE;
import static model.Direction.DOWN;
import static model.FurnitureType.CHAIR;

// Represents a Chair (Furniture)
public class Chair extends Furniture {


    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: creates a chair facing down, type chair, and color beige
    public Chair() {
        this.direction = DOWN;
        this.type = CHAIR;
        this.color = BEIGE;
    }
}
