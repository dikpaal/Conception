package model;

// Represents the dimension of a Furniture or Room
public class Dimension {
    private int length;
    private int width;

    // REQUIRES:
    //  - for furniture: 0 < l, w <= 4
    //  - for room: l = w, 3 <= l, w <= 7 (l, w are odd)
    // MODIFIES: nothing
    // EFFECTS: constructs a dimension with the given length and width
    public Dimension(int l, int w) {
        this.length = l;
        this.width = w;
    }

    // getter

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: returns the length
    public int getLength() {
        return this.length;// stub
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: returns the width
    public int getWidth() {
        return this.width;
    }
}
