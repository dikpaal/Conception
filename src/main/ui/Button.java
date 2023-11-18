package ui;

import model.FurnitureType;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Button extends JButton {
    private Integer id;
    private List<Integer> ids;
    private FurnitureType type;
    private Canvas canvas;

    public Button(Integer id, List<Integer> ids, FurnitureType type, Canvas canvas) {
        this.id = id;
        this.ids = ids;
        this.type = type;
        this.canvas = canvas;
        this.addActionListener(e -> squareButtonPressed(this));
    }

    public void squareButtonPressed(Button button) {
        System.out.println(button.getId());
        System.out.println(button.getIds());

        if (this.type == FurnitureType.GREENSQUARE) {
            if (canvas.sofaPositionBeingSelected()) {
                canvas.highlightTheOtherSpotsForSofa(this.id);
            }

            this.setType(FurnitureType.REDSQUARE);
            ImageIcon squareIcon = new ImageIcon("src/main/ui/images/redSquare.png");
            Image squareImg = squareIcon.getImage();
            Image newSquareImg = squareImg.getScaledInstance(40, 40,  java.awt.Image.SCALE_SMOOTH);
            ImageIcon newSquareIcon = new ImageIcon(newSquareImg);
            this.setIcon(newSquareIcon);
            this.setPreferredSize(new Dimension(40, 40));
            this.setBackground(Color.white);
            this.setForeground(Color.black);
            this.setFocusable(false);
        } else if (this.type == FurnitureType.REDSQUARE) {
            this.setType(FurnitureType.GREENSQUARE);
            ImageIcon squareIcon = new ImageIcon("src/main/ui/images/greenSquare.png");
            Image squareImg = squareIcon.getImage();
            Image newSquareImg = squareImg.getScaledInstance(40, 40,  java.awt.Image.SCALE_SMOOTH);
            ImageIcon newSquareIcon = new ImageIcon(newSquareImg);
            this.setIcon(newSquareIcon);
            this.setPreferredSize(new Dimension(40, 40));
            this.setBackground(Color.white);
            this.setForeground(Color.black);
            this.setFocusable(false);
        }

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

}
