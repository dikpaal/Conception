package model;

import java.util.ArrayList;

import static model.Color.WHITE;
import static model.Direction.DOWN;
import static model.FurnitureType.CENTRETABLE;

public class CenterTable extends Furniture {

    // EFFECTS: creates a centre table facing down, type centre table, color white, and empty spots
    public CenterTable() {
        this.direction = DOWN;
        this.type = CENTRETABLE;
        this.color = WHITE;
        this.spots = new ArrayList<>();
    }
}
