package ui;

import model.Room;

import javax.swing.*;
import java.awt.*;

public class MessagePanel extends PanelGUI {

    String message;
    Room room;
    JLabel label;

    public MessagePanel(int x, int y, int w, int h, Room r) {

        this.room = r;

        this.setLayout(null);
        this.setBackground(Color.white);
        this.setBounds(x, y, w, h);

        if (this.message == null) {
            label = new JLabel("Messages will be prompted here!");
        } else {
            label = new JLabel(this.message);
        }

        label.setBounds(0, 0, w, 20);
        label.setForeground(Color.black);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        this.add(label);
    }

    public String getMessage() {
        return this.message;
    }

    public JLabel getLabel() {
        return this.label;
    }
}
