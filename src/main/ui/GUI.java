package ui;

import model.Room;

import javax.swing.*;

// Represents the Graphical User Interface (GUI) of the app
public class GUI {

    boolean loadRoom;

    // EFFECTS: constructs a GUI object
    public GUI() {

        Canvas c = new Canvas(loadRoom, new Room(5), new MessagePanel(1, 1, 1, 1, new Room(3)));

        try {
            c.loadRoom();
            JFrame loadRoomOrNewRoomFrame = new JFrame();
            loadRoomOrNewRoom(loadRoomOrNewRoomFrame);
        } catch (Exception e) {
            System.out.println(c.room.getNumberedAndFurnitureList());
            JFrame onlyNewRoomFrame = new JFrame();
            onlyNewRoom(onlyNewRoomFrame);
        }
    }

    private void onlyNewRoom(JFrame frame) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exits the app on hitting close
        frame.setResizable(false); // prevent frame from being resized
        frame.setSize(200, 200); // sets the x and y dimension of the frame
        frame.setLayout(null);
        frame.setResizable(false);

        JButton newUserButton = new JButton();
        newUserButton.setText("New Room");
        newUserButton.setBounds(10, 30, 180, 80);
        newUserButton.setFocusable(false);
        newUserButton.addActionListener(e -> newRoomButtonPressed(frame));

        frame.add(newUserButton);
        frame.setVisible(true);
    }

    private void loadRoomOrNewRoom(JFrame frame) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exits the app on hitting close
        frame.setResizable(false); // prevent frame from being resized
        frame.setSize(200, 200); // sets the x and y dimension of the frame
        frame.setLayout(null);
        frame.setResizable(false);

        JButton newUserButton = new JButton();
        newUserButton.setText("New Room");
        newUserButton.setBounds(10, 10, 180, 80);
        newUserButton.setFocusable(false);
        newUserButton.addActionListener(e -> newRoomButtonPressed(frame));

        JButton oldUserButton = new JButton();
        oldUserButton.setText("Load Room");
        oldUserButton.setBounds(10, 85, 180, 80);
        oldUserButton.setFocusable(false);
        oldUserButton.addActionListener(e -> loadRoomButtonPressed(frame));

        frame.add(newUserButton);
        frame.add(oldUserButton);
        frame.setVisible(true);
    }

    private void loadRoomButtonPressed(JFrame frame) {
        loadRoom = true;
        frame.setVisible(false);
        getUsernameFromUser();

    }

    private void newRoomButtonPressed(JFrame frame) {
        loadRoom = false;
        frame.setVisible(false);
        getUsernameFromUser();
    }

    private void getUsernameFromUser() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exits the app on hitting close
        frame.setResizable(false); // prevent frame from being resized
        frame.setSize(200, 200); // sets the x and y dimension of the frame
        frame.setLayout(null);
        frame.setResizable(false);

        JTextField textField = new JTextField("Enter username", 30);
        textField.setBounds(10, 30, 180, 30);
        frame.add(textField);

        JButton button = new JButton();
        button.setText("Done");
        button.setFocusable(false);
        button.setBounds(55, 80, 90, 40);

        button.addActionListener(e -> getUsername(textField, frame));
        frame.add(button);
        frame.setVisible(true);
    }

    private void getUsername(JTextField textField, JFrame frame) {
        textField.getText();
        System.out.println(textField.getText());
        frame.setVisible(false);
        getRoomFromUser(textField.getText());
    }

    private void getRoomFromUser(String username) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exits the app on hitting close
        frame.setResizable(false); // prevent frame from being resized
        frame.setSize(200, 200); // sets the x and y dimension of the frame
        frame.setLayout(null);
        frame.setResizable(false);

        JTextField textField = new JTextField("Enter the dimension", 30);
        textField.setBounds(10, 30, 180, 30);
        frame.add(textField);

        JButton button = new JButton();
        button.setText("Done");
        button.setFocusable(false);
        button.setBounds(55, 80, 90, 40);

        button.addActionListener(e -> getRoom(textField, frame, username));
        frame.add(button);
        frame.setVisible(true);
    }

    private void getRoom(JTextField textField, JFrame frame, String username) {
        textField.getText();
        System.out.println(textField.getText());
        frame.setVisible(false);

        Room r = new Room(Integer.parseInt(textField.getText()));
        r.setUsername(username);

        createNewCanvas(loadRoom, username, r);
    }

    private void createNewCanvas(boolean load, String username, Room r) {

        // The main GUI Frame
        FrameGUI guiFrame = new FrameGUI(username);

        // The message panel that contains the messages for the user
        MessagePanel messagePanel = new MessagePanel(0, 440, 400, 50, r);

        // The canvas panel that contains the canvas in which the designing takes place
        PanelGUI canvasPanel = new CanvasPanel(0, 40, 400, 400);

        // The canvas that is a grid and represents the room
        Canvas canvas = new Canvas(load, r, messagePanel);

        // The button panel that contains all the buttons
        PanelGUI buttonPanel = new ButtonPanel(guiFrame.getWidth(), 40, canvas, messagePanel);

        canvasPanel.add(canvas);

        guiFrame.add(buttonPanel);
        guiFrame.add(canvasPanel);
        guiFrame.add(messagePanel);
        guiFrame.setVisible(true); // makes the frame visible
    }

    private void windowClosing() {

    }

}
