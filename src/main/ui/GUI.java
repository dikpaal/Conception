package ui;

import model.Room;

import javax.swing.*;

// Represents the Graphical User Interface (GUI) of the app
public class GUI {

    boolean loadRoom;

    // EFFECTS: constructs a GUI object
    public GUI() {

        Room r = new Room(5);
        ConsoleUI consoleUI = new ConsoleUI();
        consoleUI.setRoom(r);
        Canvas c = new Canvas(loadRoom, r, new MessagePanel(1, 1, 1, 1, r), consoleUI);

        try {
            c.loadRoom();
            JFrame loadRoomOrNewRoomFrame = new JFrame();
            loadRoomOrNewRoomFrame.setLocationRelativeTo(null);
            loadRoomOrNewRoom(loadRoomOrNewRoomFrame);
        } catch (Exception e) {
            JFrame onlyNewRoomFrame = new JFrame();
            onlyNewRoomFrame.setLocationRelativeTo(null);
            onlyNewRoom(onlyNewRoomFrame);
        }
    }

    // EFFECTS: creates a new room button and adds it to the frame
    private void onlyNewRoom(JFrame frame) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exits the app on hitting close
        frame.setResizable(false); // prevent frame from being resized
        frame.setSize(300, 200); // sets the x and y dimension of the frame
        frame.setLayout(null);
        frame.setResizable(false);

        JButton newUserButton = new JButton();
        newUserButton.setText("New Room");
        newUserButton.setBounds(10, 30, 280, 80);
        newUserButton.setFocusable(false);
        newUserButton.addActionListener(e -> newRoomButtonPressed(frame));

        frame.add(newUserButton);
        frame.setVisible(true);
    }

    // EFFECTS: creates a new room button and a load room button and adds them to the frame
    private void loadRoomOrNewRoom(JFrame frame) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exits the app on hitting close
        frame.setResizable(false); // prevent frame from being resized
        frame.setSize(300, 200); // sets the x and y dimension of the frame
        frame.setLayout(null);
        frame.setResizable(false);

        JButton newUserButton = new JButton();
        newUserButton.setText("New Room");
        newUserButton.setBounds(10, 10, 280, 80);
        newUserButton.setFocusable(false);
        newUserButton.addActionListener(e -> newRoomButtonPressed(frame));

        JButton oldUserButton = new JButton();
        oldUserButton.setText("Load Room");
        oldUserButton.setBounds(10, 85, 280, 80);
        oldUserButton.setFocusable(false);
        oldUserButton.addActionListener(e -> loadRoomButtonPressed(frame));

        frame.add(newUserButton);
        frame.add(oldUserButton);
        frame.setVisible(true);
    }

    // EFFECTS: Action listener for the load button
    private void loadRoomButtonPressed(JFrame frame) {
        loadRoom = true;
        frame.setVisible(false);
        getUsernameFromUser();

    }

    // EFFECTS: Action listener for the new button
    private void newRoomButtonPressed(JFrame frame) {
        loadRoom = false;
        frame.setVisible(false);
        getUsernameFromUser();
    }

    // EFFECTS: Takes in the username from the user
    private void getUsernameFromUser() {
        JFrame frame = new JFrame();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exits the app on hitting close
        frame.setResizable(false); // prevent frame from being resized
        frame.setSize(300, 200); // sets the x and y dimension of the frame
        frame.setLayout(null);
        frame.setResizable(false);

        JTextField textField = new JTextField("Enter username", 30);
        textField.setBounds(10, 30, 280, 30);
        frame.add(textField);

        JButton button = new JButton();
        button.setText("Done");
        button.setFocusable(false);
        button.setBounds(55, 80, 190, 40);

        button.addActionListener(e -> getUsername(textField, frame));
        frame.add(button);
        frame.setVisible(true);
    }

    // EFFECTS: Action listener for the Done button
    private void getUsername(JTextField textField, JFrame frame) {
        textField.getText();
        frame.setVisible(false);
        getRoomFromUser(textField.getText());
    }

    // EFFECTS: Takes in the username from the user
    private void getRoomFromUser(String username) {
        JFrame frame = new JFrame();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exits the app on hitting close
        frame.setResizable(false); // prevent frame from being resized
        frame.setSize(300, 200); // sets the x and y dimension of the frame
        frame.setLayout(null);
        frame.setResizable(false);

        JTextField textField = new JTextField("Enter dimension (odd number)", 30);
        textField.setBounds(10, 30, 280, 30);
        frame.add(textField);

        JButton button = new JButton();
        button.setText("Done");
        button.setFocusable(false);
        button.setBounds(55, 80, 190, 40);

        button.addActionListener(e -> getRoom(textField, frame, username));
        frame.add(button);
        frame.setVisible(true);
    }

    // EFFECTS: Action listener for the Done button
    private void getRoom(JTextField textField, JFrame frame, String username) {
        textField.getText();
        frame.setVisible(false);

        Room r = new Room(Integer.parseInt(textField.getText()));
        r.setUsername(username);

        createNewCanvas(loadRoom, username, r);
    }

    // EFFECTS: creates a new canvas that the user can begin editing
    private void createNewCanvas(boolean load, String username, Room r) {

        // The main GUI Frame
        FrameGUI guiFrame = new FrameGUI(username, r);

        // The message panel that contains the messages for the user
        MessagePanel messagePanel = new MessagePanel(0, 440, 400, 50, r);

        // The canvas panel that contains the canvas in which the designing takes place
        PanelGUI canvasPanel = new CanvasPanel(0, 40, 400, 400);

        ConsoleUI consoleUI = new ConsoleUI();
        consoleUI.setRoom(r);

        // The canvas that is a grid and represents the room
        Canvas canvas = new Canvas(load, r, messagePanel, consoleUI);

        // The button panel that contains all the buttons
        PanelGUI buttonPanel = new ButtonPanel(guiFrame.getWidth(), 40, canvas, messagePanel);

        canvasPanel.add(canvas);

        guiFrame.add(buttonPanel);
        guiFrame.add(canvasPanel);
        guiFrame.add(messagePanel);
        guiFrame.setVisible(true); // makes the frame visible
    }
}
