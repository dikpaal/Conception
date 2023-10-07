package model;

public abstract class Furniture {

    // fields
    Dimension dimension; // the dimension of the chair
    Direction direction; // the direction in which it is facing
    Color color; // the color of the chair
    FurnitureType type; // the type of the furniture

    // GETTERS

    // EFFECTS: returns the dimension of the furniture
    public Dimension getDimension() {
        return this.dimension;
    }

    // EFFECTS: returns the direction of the furniture
    public Direction getDirection() {
        return this.direction;
    }

    // EFFECTS: returns the color of the furniture
    public Color getColor() {
        return this.color;
    }

    // EFFECTS: returns the type of the furniture
    public FurnitureType getType() {
        return this.type;
    }

    // SETTERS

    // EFFECTS: sets the dimension of the chair to dim
    public void setDimension(Dimension dim) {
        this.dimension = dim;
    }

    // EFFECTS: sets the direction of the chair to dir
    public void setDirection(Direction dir) {
        this.direction = dir;
    }

    // EFFECTS: sets the color of the chair to c
    public void setColor(Color c) {
        this.color = c;
    }
}
