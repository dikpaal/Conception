package ui;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import static model.FurnitureType.*;

public class Button extends JButton {
    private Integer id;
    private List<Integer> ids;
    private FurnitureType type;
    private Canvas canvas;

    private int gridX;
    private int gridY;


    public Button(Integer id, List<Integer> ids, FurnitureType type, Canvas canvas, int gridX, int gridY) {
        this.id = id;
        this.ids = ids;
        this.type = type;
        this.canvas = canvas;
        this.gridX = gridX;
        this.gridY = gridY;
        this.addActionListener(e -> squareButtonPressed());
    }

    public void squareButtonPressed() {

        if (this.type == FurnitureType.GREENSQUARE) {
            greenSquarePressed();
        } else if (this.type == FurnitureType.REDSQUARE) {
            redSquarePressed();
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

    // modifies: this
    // sets the type of the furniture to type
    public void setType(FurnitureType type) {
        this.type = type;
    }

    public int getId() {
        if (this.id == null) {
            return 0;
        }
        return this.id;
    }

    public List<Integer> getIds() {
        if (this.ids == null) {
            return new ArrayList<>();
        }
        return this.ids;
    }

    public FurnitureType getType() {
        return this.type;
    }

    public int getGridX() {
        return this.gridX;
    }

    public int getGridY() {
        return this.gridY;
    }

    public void setGridX(int x) {
        this.gridX = x;
    }

    public void setGridY(int y) {
        this.gridY = y;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

}
