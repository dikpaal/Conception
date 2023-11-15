package ui;

import javax.swing.*;
import java.awt.*;

// Represents a GUI frame
public class FrameGUI extends JFrame {

    public FrameGUI() {
        this.setTitle("Conception"); // frame title
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exits the app on hitting close
        this.setResizable(false); // prevent frame from being resized
        this.setSize(400, 420); // sets the x and y dimension of the frame
        this.setLayout(null);
    }
}