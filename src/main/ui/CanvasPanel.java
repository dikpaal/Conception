package ui;

import model.Room;

import javax.swing.*;
import java.awt.*;

public class CanvasPanel extends PanelGUI {

    public CanvasPanel(int x, int y, int w, int h) {
        this.setBackground(Color.white);
        this.setBounds(x, y, w, h);
    }
}
