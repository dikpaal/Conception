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
        this.length = l;
        this.width = w;
    }

    // getter

    // EFFECTS: returns the length
    public int getLength() {
        return this.length;// stub
    }

    // EFFECTS: returns the width
    public int getWidth() {
        return this.width;
    }

    // setter

//    // REQUIRES: l > 0 and (l is odd for room)
//    // EFFECTS: sets the length to l
//    public void setLength(int l) {
//        this.length = l;
//    }
//
//    // REQUIRES: w > 0 nad (w is odd for room)
//    // EFFECTS: sets the width to w
//    public void setWidth(int w) {
//        this.width = w;
//    }
}
