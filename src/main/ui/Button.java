package ui;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import static model.FurnitureType.*;

// Represents a Button
public class Button extends JButton {
    private Integer id;
    private List<Integer> ids;
    private FurnitureType type;
    private Canvas canvas;

    private int gridX;
    private int gridY;

    // EFFECTS: constructs a button object with id, ids, type, canvas, gridX, and gridY
    public Button(Integer id, List<Integer> ids, FurnitureType type, Canvas canvas, int gridX, int gridY) {
        this.id = id;
        this.ids = ids;
        this.type = type;
        this.canvas = canvas;
        this.gridX = gridX;
        this.gridY = gridY;
        this.addActionListener(e -> squareButtonPressed());
    }

    // REQUIRES: nothing
    // MODIFIES: this
    // EFFECTS: modifies the button and performs tasks based on the current type of the button
    public void squareButtonPressed() {

        if (this.type == FurnitureType.GREENSQUARE) {
            greenSquarePressed();
        } else if (this.type == FurnitureType.REDSQUARE) {
            redSquarePressed();
        } else if (this.type == CHAIR) {
            removeChair();
        } else if (this.type == SOFA) {
            removeSofa();
        } else if (this.type == CENTRETABLE) {
            removeCentreTable();
        }
    }

    // REQUIRES: nothing
    // MODIFIES: this
    // EFFECTS: removes the chair from the canvas
    public void removeChair() {
        if (canvas.chairPositionBeingSelected()) {
            canvas.getRoom().removeChairFromSpot(Integer.toString(this.id));
            changeToGreenSquare();
        } else {
            canvas.getRoom().removeChairFromSpot(Integer.toString(this.id));
            changeToSquare();
        }
    }

    // REQUIRES: nothing
    // MODIFIES: this
    // EFFECTS: removes the sofa from the canvas
    public void removeSofa() {

        int smallerId = canvas.getSmallerId(this.ids.get(0), this.ids.get(1));
        int largerId = canvas.getLargerId(smallerId, this.ids.get(0), this.ids.get(1));

        canvas.getRoom().removeSofaFromSpot(Integer.toString(smallerId), Integer.toString(largerId));

        changeToSquaresSofa(smallerId, largerId);
    }

    // REQUIRES: nothing
    // MODIFIES: this
    // EFFECTS: removes the centre table from the canvas
    public void removeCentreTable() {
        canvas.getRoom().removeCentreTableFromSpot(Integer.toString(this.ids.get(0)));
        canvas.revertGbcOfCentreTableButton(this);
    }

    // REQUIRES: nothing
    // MODIFIES: this
    // EFFECTS: changes the sofa squares back to normal 1x1 squares
    public void changeToSquaresSofa(int spot1, int spot2) {
        for (Button b : canvas.getAllButtons()) {
            if (b.getIds().contains(spot1) && b.getIds().contains(spot2)) {

                b.setId(spot1);

                List<Integer> tempList = new ArrayList<>();
                b.setIds(tempList);
                b.setType(SQUARE);

                if (spot2 == spot1 + 1) {
                    canvas.revertGbcOfSofaButtonHorizontal(b);
                } else {
                    canvas.revertGbcOfSofaButtonVertical(b);
                }
            }
        }

        for (Button b : canvas.getAllButtons()) {
            if (b.getType() == SQUARE) {
                b.changeToSquare();
            }
        }
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: based on what furniture is being selected, according methods are called
    private void greenSquarePressed() {
        if (canvas.chairPositionBeingSelected()) {
            canvas.addChair(this);
        } else if (canvas.firstSofaPositionBeingSelected()) {
            canvas.addSofa(this);
        } else if (canvas.centreTablePositionBeingSelected()) {
            canvas.addCentreTable(this);
        }
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: if sofa is being placed, then sofa is added, otherwise change to green square
    private void redSquarePressed() {

        if (canvas.secondSofaPositionBeingSelected()) {
            canvas.addFinalSofa(this);

        } else {
            changeToGreenSquare();
        }
    }

    // REQUIRES: nothing
    // MODIFIES: this
    // EFFECTS: changes the type and color of the square to green square
    public void changeToGreenSquare() {
        this.type = GREENSQUARE;
        ImageIcon squareIcon = new ImageIcon("src/main/ui/images/greenSquare.png");
        Image squareImg = squareIcon.getImage();
        Image newSquareImg = squareImg.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
        ImageIcon newSquareIcon = new ImageIcon(newSquareImg);
        this.setIcon(newSquareIcon);
        this.setPreferredSize(new Dimension(40, 40));
        this.setBackground(Color.white);
        this.setForeground(Color.black);
        this.setFocusable(false);
    }

    // REQUIRES: nothing
    // MODIFIES: this
    // EFFECTS: changes the type and color of the square to red square
    public void changeToRedSquare() {
        ImageIcon squareIcon = new ImageIcon("src/main/ui/images/redSquare.png");
        Image squareImg = squareIcon.getImage();
        Image newSquareImg = squareImg.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
        ImageIcon newSquareIcon = new ImageIcon(newSquareImg);
        this.setIcon(newSquareIcon);
        this.setPreferredSize(new Dimension(40, 40));
        this.setBackground(Color.white);
        this.setForeground(Color.black);
        this.setFocusable(false);
        this.setType(REDSQUARE);
    }

    // REQUIRES: nothing
    // MODIFIES: this
    // EFFECTS: changes the type and color of the square to square
    public void changeToSquare() {
        ImageIcon squareIcon = new ImageIcon("src/main/ui/images/square.png");
        Image squareImg = squareIcon.getImage();
        Image newSquareImg = squareImg.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
        ImageIcon newSquareIcon = new ImageIcon(newSquareImg);
        this.setIcon(newSquareIcon);
        this.setPreferredSize(new Dimension(40, 40));
        this.setBackground(Color.white);
        this.setForeground(Color.black);
        this.setFocusable(false);
        this.setType(FurnitureType.SQUARE);
    }



    // GETTERS AND SETTERS

    // EFFECTS: returns the id of the button
    public int getId() {
        if (this.id == null) {
            return 0;
        }
        return this.id;
    }

    // EFFECTS: returns the ids of the button
    public List<Integer> getIds() {
        if (this.ids == null) {
            return new ArrayList<>();
        }
        return this.ids;
    }

    // EFFECTS: returns the type of the button
    public FurnitureType getType() {
        return this.type;
    }

    // EFFECTS: returns the gridX of the button
    public int getGridX() {
        return this.gridX;
    }

    // EFFECTS: returns the gridY of the button
    public int getGridY() {
        return this.gridY;
    }

    // MODIFIES: this
    // EFFECTS: sets the gridX to x
    public void setGridX(int x) {
        this.gridX = x;
    }

    // MODIFIES: this
    // EFFECTS: sets the gridY to y
    public void setGridY(int y) {
        this.gridY = y;
    }

    // MODIFIES: this
    // EFFECTS: sets the id to id
    public void setId(int id) {
        this.id = id;
    }

    // MODIFIES: this
    // EFFECTS: sets the ids to ids
    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    // MODIFIES: this
    // EFFECTS: sets the type of the furniture to type
    public void setType(FurnitureType type) {
        this.type = type;
    }

}
