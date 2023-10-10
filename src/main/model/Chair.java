package model;

import static model.Color.BEIGE;
import static model.Direction.DOWN;
import static model.FurnitureType.CHAIR;

// represents a Chair (Furniture)
public class Chair extends Furniture {

    public Chair() {
        this.direction = DOWN;
        this.type = CHAIR;
        this.color = BEIGE;
    }
}
