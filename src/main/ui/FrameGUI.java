package ui;

import model.EventLog;
import model.Room;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

// Represents a GUI frame
public class FrameGUI extends JFrame {

    // Constructs a JFrame object with a username
    public FrameGUI(String username, Room r) {
        this.setTitle(username + "'s Conception"); // frame title
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exits the app on hitting close
        this.setResizable(true); // prevent frame from being resized
        this.setSize(400, 490); // sets the x and y dimension of the frame
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                r.printLogsToConsole();
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
    }
}