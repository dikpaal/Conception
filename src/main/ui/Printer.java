package ui;

import model.Event;
import model.EventLog;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

// Represents a printer for printing event log to console.
public class Printer extends JInternalFrame {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;
    private JTextArea logArea;


    // EFFECTS: Sets up window in which log will be printed on screen
    public Printer() {
        super("Event log", false, true, false, false);
        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        add(scrollPane);
        setSize(WIDTH, HEIGHT);
        setVisible(false);
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: prints out the events in el
    public void printLog(EventLog el) {
        for (Event next : el) {
            System.out.println(next.toString() + "\n\n");
        }
    }
}
