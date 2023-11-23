package ui;

import javax.swing.*;
import java.awt.*;

// Represents a GUI frame
public class FrameGUI extends JFrame {

    // Constructs a JFrame object with a username
    public FrameGUI(String username) {
        this.setTitle(username + "'s Conception"); // frame title
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exits the app on hitting close
        this.setResizable(true); // prevent frame from being resized
        this.setSize(400, 490); // sets the x and y dimension of the frame
        this.setLayout(null);
    }
}