package ui;

import model.Room;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Canvas extends PanelGUI {

    public Canvas(int l) {
        this.setLayout(new GridLayout(l, l));
        for (int i = 0; i < l * l; i++) {
            Button button = new Button("" + (i + 1));
            button.setPreferredSize(new Dimension(40,40));
            button.setBackground(Color.white);
            button.setForeground(Color.black);
            button.setFocusable(false);
//            button.setEnabled(false);
            this.add(button);
        }
    }
}
