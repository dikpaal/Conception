package model;

import java.util.Iterator;
import java.util.List;

import static model.Direction.*;

public abstract class Furniture {

    // fields
    Dimension dimension; // the dimension of the chair
    Direction direction; // the direction in which it is facing
    Color color; // the color of the chair
    FurnitureType type; // the type of the furniture
    int spot; // the spot where the furniture (chair) has been placed
    List<Integer> spots; // the spots where the furniture has been placed (only for sofa and centre table)

    // GETTERS
//
//    // EFFECTS: returns the dimension of the furniture
//    public Dimension getDimension() {
//        return this.dimension;
//    }
//
//
    //  EFFECTS: returns the direction of the furniture
    public Direction getDirection() {

        if (this.direction == DOWN) {
            return this.direction;
        } else {
            return NEUTRAL;
        }
    }

    // EFFECTS: returns the color of the furniture
    public Color getColor() {
        return this.color;
    }

    // EFFECTS: returns the type of the furniture
    public FurnitureType getType() {
        return this.type;
    }

    // EFFECTS: returns the spot of the furniture if it is a CHAIR
    public Integer getSpot() {
        return this.spot;
    }

    // EFFECTS: returns the spot of the furniture if it is a SOFA
    public Integer getSofaSpots() {
        return this.spots.get(0);
    }

    // EFFECTS: returns the next spot of the furniture if it is a SOFA
    public Integer getSecondSofaSpots() {
        return this.spots.get(1);
    }

    // EFFECTS: returns the spot of the furniture if it is a CENTRE TABLE
    public Integer getCentreTableSpots() {
        return this.spots.get(0);
    }

    // EFFECTS: returns all the spots of the furniture
    public List<Integer> getAllSpots() {
        return this.spots;
    }

    // SETTERS

//
//    // EFFECTS: sets the dimension of the furniture to dim
//    public void setDimension(Dimension dim) {
//        this.dimension = dim;
//    }
//
    // EFFECTS: sets the direction of the furniture to dir
    public void setDirection(Direction dir) {
        this.direction = dir;
    }

//    // EFFECTS: sets the color of the furniture to c
//    public void setColor(Color c) {
//        this.color = c;
//    }
//
    // EFFECTS: sets the spot of the furniture to s
    public void setSpot(int s) {
        this.spot = s;
    }

    // EFFECTS: sets the spots of the sofa to s1, s2
    public void setSpotsForSofa(int s1, int s2) {
        this.spots.add(s1);
        this.spots.add(s2);
    }

    // EFFECTS: sets the spots of the center table to s1, s2
    public void setSpotsForCenterTable(int topLeftSpot, int roomLength) {

        int topLeftSpotInt = topLeftSpot;
        int topRightSpotInt = topLeftSpotInt + 1;
        int bottomLeftSpotInt = topLeftSpotInt + (roomLength - 1);
        int bottomRightSpotInt = bottomLeftSpotInt + 1;

        this.spots.add(topLeftSpotInt);
        this.spots.add(topRightSpotInt);
        this.spots.add(bottomLeftSpotInt);
        this.spots.add(bottomRightSpotInt);
    }
}