package ui;

import model.Room;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Canvas extends PanelGUI {

    Button button;

    public Canvas(int l) {
        this.setLayout(new GridLayout(l, l));
        for (int i = 0; i < l * l; i++) {
            button = new Button("" + (i + 1));
            button.setPreferredSize(new Dimension(40,40));
            button.setFocusable(false);
            button.setBackground(Color.white);
            button.setEnabled(false);
            this.add(button);
        }
    }
}
