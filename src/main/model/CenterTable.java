package model;

import static model.Color.WHITE;
import static model.Direction.DOWN;
import static model.FurnitureType.CENTRETABLE;

public class CenterTable extends Furniture {

    public CenterTable() {
        this.direction = DOWN;
        this.type = CENTRETABLE;
        this.color = WHITE;
    }
}
