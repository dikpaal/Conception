package ui;

import model.Room;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Canvas extends PanelGUI {

    Room room;

    public Canvas(Room r) {
        this.room = r;
        this.setLayout(new GridLayout(room.getDimension().getLength(), room.getDimension().getLength()));
        setup();
    }


    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: calls to create a numberedPlane, initiate a numberedAndFurnitureList, and prints the room
    public void setup() {
        List<List<String>> numberedPlane = room.createNumberedPlane();
        room.setNumberedPlane(numberedPlane);
        room.initiateNumberedAndFurnitureList();
//        loadRoom();
//        System.out.println("Here's your room!");
        drawRoom();
    }

    // draws the room onto the canvas
    public void drawRoom() {
        int l = room.getDimension().getLength();
        for (int i = 0; i < l * l; i++) {
            ImageIcon squareIcon = new ImageIcon("src/main/ui/images/centretable.png");
            Image squareImg = squareIcon.getImage();
            Image newSquareImg = squareImg.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH);
            ImageIcon newSquareIcon = new ImageIcon(newSquareImg);
            JButton squareButton = new JButton();
            squareButton.setIcon(newSquareIcon);
            squareButton.addActionListener(e -> System.out.println("Square button pressed!"));
            squareButton.setPreferredSize(new Dimension(40,40));
            squareButton.setBackground(Color.white);
            squareButton.setForeground(Color.black);
            squareButton.setFocusable(false);
            this.add(squareButton);
        }
    }

}
