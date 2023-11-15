package ui;

import javax.swing.*;
import java.awt.*;

public class MessagePanel extends PanelGUI {

    String message;

    public MessagePanel(int x, int y, int w, int h) {

        this.setLayout(null);
        this.setBackground(Color.white);
        this.setBounds(x, y, w, h);

        message = "Messages will be prompted here!";
        JLabel label = new JLabel(message);
        label.setBounds(0, 0, w, 20);
        label.setForeground(Color.black);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);


        this.add(label);
    }

    public void setMessage(String s) {
        message = s;
    }
}
