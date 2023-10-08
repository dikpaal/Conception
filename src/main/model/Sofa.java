package model;

import static model.Color.BEIGE;
import static model.Direction.DOWN;
import static model.FurnitureType.SOFA;

public class Sofa extends Furniture {

    public Sofa() {
        this.direction = DOWN;
        this.type = SOFA;
        this.color = BEIGE;
    }
}
