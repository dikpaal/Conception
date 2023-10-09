package model;

import java.util.ArrayList;
import java.util.List;

import static model.Color.BEIGE;
import static model.Direction.DOWN;
import static model.FurnitureType.SOFA;

public class Sofa extends Furniture {

    public Sofa() {
        this.direction = DOWN;
        this.type = SOFA;
        this.color = BEIGE;
        this.spots = new ArrayList<>();
    }
}