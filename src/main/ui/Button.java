package ui;

import model.FurnitureType;

import javax.swing.*;

public class Button extends JButton {
    private int id;
    private FurnitureType type;

    public Button(int id, FurnitureType type) {
        this.id = id;
        this.type = type;
        this.addActionListener(e -> squareButtonPressed(this));
    }

    public void squareButtonPressed(Button button) {
        System.out.println(button.getId());
    }

    public int getId() {
        return this.id;
    }
}
