package ui;

import model.Room;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;


// Represents the canvas panel that houses the canvas
public class CanvasPanel extends PanelGUI {

    // EFFECTS: constructs a canvas panel object with x, y, w, and h
    public CanvasPanel(int x, int y, int w, int h) {
        this.setBackground(Color.white);
        this.setBounds(x, y, w, h);
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
    }
}
