package model;

public abstract class Furniture {

    // fields
    Dimension dimension; // the dimension of the chair
    Direction direction; // the direction in which it is facing
    Color color; // the color of the chair
    FurnitureType type; // the type of the furniture

    // getters

    // EFFECTS: returns the dimension of the chair
    public Dimension getDimension() {
        // TODO: implement this getter method
        return this.dimension;
    }

    // EFFECTS: returns the direction of the chair
    public Direction getDirection() {
        // TODO: implement this getter method
        return this.direction;
    }

    // EFFECTS: returns the color of the chair
    public Color getColor() {
        // TODO: implement this getter method
        return this.color;
    }

    // setters

    // EFFECTS: sets the dimension of the chair to dim
    public void setDimension(Dimension dim) {
        // TODO: implement this setter method
        // stub
    }

    // EFFECTS: sets the direction of the chair to dir
    public void setDirection(Direction dir) {
        // TODO: implement this setter method
        // stub
    }

    // EFFECTS: sets the color of the chair to c
    public void setColor(Color c) {
        // TODO: implement this setter method
        // stub
    }
}
