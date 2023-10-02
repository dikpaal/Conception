package model;

// determines the dimension of the entity (Room or Furniture) with length and width
public class Dimension {
    private int length;
    private int width;

    // REQUIRES:
    //  - for furniture: 0 < l, w <= 4
    //  - for room: l = w, 10 <= l,w <= 40
    // MODIFIES: nothing
    // EFFECTS: constructs a dimension with the given length and width
    public Dimension(int l, int w) {
        // TODO: implement this constructor
        this.length = l;
        this.width = w;
    }

    // getter

    // EFFECTS: returns the length
    public int getLength() {
        // TODO: implement this getter method
        return this.length;// stub
    }

    // EFFECTS: returns the width
    public int getWidth() {
        // TODO: implement this getter method
        return this.width;
    }

    // setter

    // REQUIRES: l > 0
    // EFFECTS: sets the length to l
    public void setLength(int l) {
        // TODO: implement this getter method
        // stub
    }

    // REQUIRES: w > 0
    // EFFECTS: sets the width to w
    public void setWidth(int w) {
        // TODO: implement this getter method
        // stub
    }
}
