package model;

import static model.Color.BEIGE;
import static model.FurnitureType.CHAIR;

// represents a Chair (Furniture)
public class Chair extends Furniture {

    public Chair() {
        this.type = CHAIR;
        this.color = BEIGE;
    }

}
