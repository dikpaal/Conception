package ui;

import model.Furniture;
import model.FurnitureType;
import model.Room;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static model.FurnitureType.SOFA;

public class Canvas extends PanelGUI {
    Room room;
    MessagePanel messagePanel;
    GridBagConstraints gbc;

    private static final String JSON_STORE = "./data/workroom.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    public Canvas(Room r, MessagePanel messagePanel) {
        this.room = r;
        this.messagePanel = messagePanel;

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
//        this.setLayout(new GridLayout(room.getDimension().getLength() - 1,
//                room.getDimension().getLength() - 1, 0, 0));
        this.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        setup();
    }


    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: calls to create a numberedPlane, initiate a numberedAndFurnitureList, and prints the room
    public void setup() {
        List<List<String>> numberedPlane = room.createNumberedPlane();
        room.setNumberedPlane(numberedPlane);
        room.initiateNumberedAndFurnitureList();
        loadRoom();
        drawRoom();
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: draws the room on the canvas
    public void drawRoom() {
        List<List<String>> numberedAndFurnitureList = room.getNumberedAndFurnitureList();
        int count = 0;
        for (int i = 0; i < numberedAndFurnitureList.size(); i++) {
            List<String> subList = numberedAndFurnitureList.get(i);
            for (int j = 0; j < subList.size(); j++) {
                count++;
                String number = subList.get(j);
                if (number.equals("Cv")) {
                    ImageIcon squareIcon = new ImageIcon("src/main/ui/images/chair.png");
                    Image squareImg = squareIcon.getImage();
                    Image newSquareImg = squareImg.getScaledInstance(40, 40,  java.awt.Image.SCALE_SMOOTH);
                    ImageIcon newSquareIcon = new ImageIcon(newSquareImg);
                    Button squareButtonChair = new Button(count, FurnitureType.CHAIR);
                    squareButtonChair.setIcon(newSquareIcon);
                    squareButtonChair.setPreferredSize(new Dimension(40,40));
                    squareButtonChair.setBackground(Color.white);
                    squareButtonChair.setForeground(Color.black);
                    squareButtonChair.setFocusable(false);

                    gbc.gridx = j;
                    gbc.gridy = i;
                    gbc.gridheight = 1;
                    gbc.gridwidth = 1;
                    this.add(squareButtonChair, gbc);
                } else if (number.equals("Sv")) {
                    List<Furniture> furnitureList = room.getFurnitureList();

                    ImageIcon squareIconSofa;
                    Image squareImgSofa;
                    Image newSquareImgSofa;
                    ImageIcon newSquareIconSofa;
                    Button squareButtonSofa;

                    for (Furniture f : furnitureList) {
                        if (f.getType() == SOFA) {
                            if (f.getAllSpots().contains(count) && f.getAllSpots().contains(count + 1)) {
                                squareIconSofa = new ImageIcon("src/main/ui/images/sofa.png");
                                squareImgSofa = squareIconSofa.getImage();
                                newSquareImgSofa = squareImgSofa.getScaledInstance(80, 40,  java.awt.Image.SCALE_SMOOTH);
                                newSquareIconSofa = new ImageIcon(newSquareImgSofa);
                                squareButtonSofa = new Button(count, FurnitureType.SOFA);
                                squareButtonSofa.setIcon(newSquareIconSofa);
                                squareButtonSofa.setPreferredSize(new Dimension(80,40));
                                squareButtonSofa.setBackground(Color.white);
                                squareButtonSofa.setForeground(Color.black);
                                squareButtonSofa.setFocusable(false);
                                gbc.gridx = j;
                                gbc.gridy = i;
                                gbc.gridheight = 1;
                                gbc.gridwidth = 2;
                                gbc.fill = GridBagConstraints.HORIZONTAL;
                                this.add(squareButtonSofa, gbc);
                                break;
                            } else {
                                squareIconSofa = new ImageIcon("src/main/ui/images/sofaTilted.png");
                                squareImgSofa = squareIconSofa.getImage();
                                newSquareImgSofa = squareImgSofa.getScaledInstance(40, 80,  java.awt.Image.SCALE_SMOOTH);
                                newSquareIconSofa = new ImageIcon(newSquareImgSofa);
                                squareButtonSofa = new Button(count, FurnitureType.SOFA);
                                squareButtonSofa.setIcon(newSquareIconSofa);
                                squareButtonSofa.setPreferredSize(new Dimension(40,80));
                                squareButtonSofa.setBackground(Color.white);
                                squareButtonSofa.setForeground(Color.black);
                                squareButtonSofa.setFocusable(false);
                                gbc.gridx = j;
                                gbc.gridy = i;
                                gbc.gridheight = 2;
                                gbc.gridwidth = 1;
                                gbc.fill = GridBagConstraints.VERTICAL;
                                this.add(squareButtonSofa, gbc);
                                break;
                            }
                        }
                    }
                } else {
                    ImageIcon squareIcon = new ImageIcon("src/main/ui/images/square.png");
                    Image squareImg = squareIcon.getImage();
                    Image newSquareImg = squareImg.getScaledInstance(40, 40,  java.awt.Image.SCALE_SMOOTH);
                    ImageIcon newSquareIcon = new ImageIcon(newSquareImg);
                    Button squareButton = new Button(count, FurnitureType.SQUARE);
                    squareButton.setIcon(newSquareIcon);
                    squareButton.setPreferredSize(new Dimension(40,40));
                    squareButton.setBackground(Color.white);
                    squareButton.setForeground(Color.black);
                    squareButton.setFocusable(false);

                    gbc.gridx = j;
                    gbc.gridy = i;
                    gbc.gridheight = 1;
                    gbc.gridwidth = 1;
                    this.add(squareButton, gbc);
                }
            }
        }
    }



    // JSON

    // REQUIRES: nothing
    // MODIFIES: this
    // EFFECTS: loads room from file
    private void loadRoom() {

        Scanner s = new Scanner(System.in);
        while (true) {
            System.out.println("Would you like to load your previous room?");
            String userChoice = s.nextLine();

            if (userChoice.equals("y")) {
                try {
                    room = jsonReader.read();
                    System.out.println("Loaded " + room.getUsername() + "'s room from " + JSON_STORE);
                    System.out.println(room.getNumberedAndFurnitureList());
                    System.out.println(room.getFurnitureList());
                } catch (IOException e) {
                    System.out.println("Unable to read from file: " + JSON_STORE);
                }
                break;
            } else if (userChoice.equals("n")) {
                System.out.println("Okay!");
                break;
            }
        }
    }
}
