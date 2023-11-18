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

import static model.FurnitureType.CENTRETABLE;
import static model.FurnitureType.SOFA;

public class Canvas extends PanelGUI {
    Room room;
    List<Button> allButtons;
    MessagePanel messagePanel;
    GridBagConstraints gbc;

    private boolean sofaPositionBeingSelected = false;

    private static final String JSON_STORE = "./data/workroom.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    public Canvas(Room r, MessagePanel messagePanel) {
        this.room = r;
        this.messagePanel = messagePanel;
        allButtons = new ArrayList<>();

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
        boolean centreTablePlaced = false;
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
                    Button squareButtonChair = new Button(count, null, FurnitureType.CHAIR, this);
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
                    allButtons.add(squareButtonChair);
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

                                List<Integer> ids = f.getAllSpots();

                                squareButtonSofa = new Button(null, ids, FurnitureType.SOFA, this);
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
                                allButtons.add(squareButtonSofa);
                                break;
                            } else {
                                squareIconSofa = new ImageIcon("src/main/ui/images/sofaTilted.png");
                                squareImgSofa = squareIconSofa.getImage();
                                newSquareImgSofa = squareImgSofa.getScaledInstance(40, 80,  java.awt.Image.SCALE_SMOOTH);
                                newSquareIconSofa = new ImageIcon(newSquareImgSofa);

                                List<Integer> ids = f.getAllSpots();

                                squareButtonSofa = new Button(null, ids, FurnitureType.SOFA, this);
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
                                allButtons.add(squareButtonSofa);
                                break;
                            }
                        }
                    }
                } else if (number.equals("Tv")) {
                    if (!centreTablePlaced) {
                        List<Furniture> furnitureList = room.getFurnitureList();
                        for (Furniture f : furnitureList) {
                            if (f.getType() == CENTRETABLE) {
                                ImageIcon squareIconCentreTable = new ImageIcon("src/main/ui/images/centretable.png");
                                Image squareImgCentreTable = squareIconCentreTable.getImage();
                                Image newSquareImgCentreTable = squareImgCentreTable.getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH);
                                ImageIcon newSquareIconCentreTable = new ImageIcon(newSquareImgCentreTable);

                                List<Integer> ids = f.getAllSpots();

                                Button squareButtonCentreTable = new Button(null, ids, FurnitureType.CENTRETABLE, this);
                                squareButtonCentreTable.setIcon(newSquareIconCentreTable);
                                squareButtonCentreTable.setPreferredSize(new Dimension(80, 80));
                                squareButtonCentreTable.setBackground(Color.white);
                                squareButtonCentreTable.setForeground(Color.black);
                                squareButtonCentreTable.setFocusable(false);
                                gbc.gridx = j;
                                gbc.gridy = i;
                                gbc.gridheight = 2;
                                gbc.gridwidth = 2;
                                this.add(squareButtonCentreTable, gbc);
                                allButtons.add(squareButtonCentreTable);
                                centreTablePlaced = true;
                            }
                        }
                    } else {
                        // do nothing
                    }
                } else {
                    ImageIcon squareIcon = new ImageIcon("src/main/ui/images/square.png");
                    Image squareImg = squareIcon.getImage();
                    Image newSquareImg = squareImg.getScaledInstance(40, 40,  java.awt.Image.SCALE_SMOOTH);
                    ImageIcon newSquareIcon = new ImageIcon(newSquareImg);
                    Button squareButton = new Button(count, null, FurnitureType.SQUARE, this);
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
                    allButtons.add(squareButton);
                }
            }
        }
    }

    // highlights all the available spots for a chair
    public void highlightAllAvailableSpotsForChair() {
        ImageIcon squareIcon = new ImageIcon("src/main/ui/images/greenSquare.png");
        Image squareImg = squareIcon.getImage();
        Image newSquareImg = squareImg.getScaledInstance(40, 40,  java.awt.Image.SCALE_SMOOTH);
        ImageIcon newSquareIcon = new ImageIcon(newSquareImg);

        for (Button button : this.getAllButtons()) {

            if (button.getType() == FurnitureType.SQUARE) {

                button.setIcon(newSquareIcon);
                button.setPreferredSize(new Dimension(40, 40));
                button.setBackground(Color.white);
                button.setForeground(Color.black);
                button.setFocusable(false);
                button.setType(FurnitureType.GREENSQUARE);
            } else {
                button.setEnabled(false);
            }
        }
    }

    // highlights all the available spots for a sofa
    public void highlightAllAvailableSpotsForSofa() {
        List<List<String>> availableSpots = this.getRoom().spaceForASofa();

        List<Integer> firstSpots = new ArrayList<>();
        List<Integer> secondSpots = new ArrayList<>();

        for (List<String> spots : availableSpots) {
            firstSpots.add(Integer.parseInt(spots.get(0)));
            secondSpots.add(Integer.parseInt(spots.get(1)));
        }

        ImageIcon squareIcon = new ImageIcon("src/main/ui/images/greenSquare.png");
        Image squareImg = squareIcon.getImage();
        Image newSquareImg = squareImg.getScaledInstance(40, 40,  java.awt.Image.SCALE_SMOOTH);
        ImageIcon newSquareIcon = new ImageIcon(newSquareImg);

        for (Button button : this.getAllButtons()) {
            if (button.getType() == FurnitureType.SQUARE) {
                for (int spot : firstSpots) {
                    if (button.getId() == spot) {
                        button.setIcon(newSquareIcon);
                        button.setPreferredSize(new Dimension(40, 40));
                        button.setBackground(Color.white);
                        button.setForeground(Color.black);
                        button.setFocusable(false);
                        button.setType(FurnitureType.GREENSQUARE);
                        sofaPositionBeingSelected = true;
                    }
                }

            } else {
                button.setEnabled(false);
            }
        }
    }

    // highlights all the available spots for a centre table
    public void highlightAllAvailableSpotsForCentreTable() {
        ImageIcon squareIcon = new ImageIcon("src/main/ui/images/greenSquare.png");
        Image squareImg = squareIcon.getImage();
        Image newSquareImg = squareImg.getScaledInstance(40, 40,  java.awt.Image.SCALE_SMOOTH);
        ImageIcon newSquareIcon = new ImageIcon(newSquareImg);

        List<String> spots = room.spaceForACentreTable();

        if (spots.isEmpty()) {
            //
        } else {
            int spot1 = Integer.parseInt(spots.get(0));
            int spot2 = Integer.parseInt(spots.get(1));
            int spot3 = Integer.parseInt(spots.get(2));
            int spot4 = Integer.parseInt(spots.get(3));

            for (Button button : this.getAllButtons()) {
                if (button.getType() == FurnitureType.SQUARE) {
                    if (button.getId() == spot1
                            || button.getId() == spot2
                            || button.getId() == spot3
                            || button.getId() == spot4) {
                        button.setIcon(newSquareIcon);
                        button.setPreferredSize(new Dimension(40, 40));
                        button.setBackground(Color.white);
                        button.setForeground(Color.black);
                        button.setFocusable(false);
                        button.setType(FurnitureType.GREENSQUARE);
                    }
                } else {
                    button.setEnabled(false);
                }
            }
        }
    }

    // reverts the highlighting of the canvas squares
    public void revertHighlighting() {
        sofaPositionBeingSelected = false;
        ImageIcon squareIcon = new ImageIcon("src/main/ui/images/square.png");
        Image squareImg = squareIcon.getImage();
        Image newSquareImg = squareImg.getScaledInstance(40, 40,  java.awt.Image.SCALE_SMOOTH);
        ImageIcon newSquareIcon = new ImageIcon(newSquareImg);

        for (Button button : this.getAllButtons()) {

            if (button.getType() == FurnitureType.GREENSQUARE || button.getType() == FurnitureType.REDSQUARE) {
                button.setIcon(newSquareIcon);
                button.setPreferredSize(new Dimension(40, 40));
                button.setBackground(Color.white);
                button.setForeground(Color.black);
                button.setFocusable(false);
                button.setType(FurnitureType.SQUARE);
            }  else {
                button.setEnabled(true);
            }
        }
    }

    // highlights the other spot for a sofa
    public void highlightTheOtherSpotsForSofa(int spot) {

        String spots = this.getRoom().getTheOtherSpot(Integer.toString(spot));

        int spot1;
        int spot2;

        if (spots.contains(" or ")) {
            String[] spotList = spots.split(" or ");
            List<Integer> intSpotList = new ArrayList<>();

            for (String s : spotList) {
                intSpotList.add(Integer.parseInt(s));
            }

            spot1 = intSpotList.get(0);
            spot2 = intSpotList.get(1);

        } else {
            spot1 = 0;
            spot2 = Integer.parseInt(spots);
        }

        for (Button button : this.getAllButtons()) {
            if (button.getType() == FurnitureType.SQUARE) {
                if (button.getId() == spot1 || button.getId() == spot2) {
                    ImageIcon squareIcon = new ImageIcon("src/main/ui/images/redSquare.png");
                    Image squareImg = squareIcon.getImage();
                    Image newSquareImg = squareImg.getScaledInstance(40, 40,  java.awt.Image.SCALE_SMOOTH);
                    ImageIcon newSquareIcon = new ImageIcon(newSquareImg);
                    button.setIcon(newSquareIcon);
                    button.setPreferredSize(new Dimension(40, 40));
                    button.setBackground(Color.white);
                    button.setForeground(Color.black);
                    button.setFocusable(false);
                    button.setType(FurnitureType.REDSQUARE);
                }
            }
        }
    }

    // returns the room
    public Room getRoom() {
        return this.room;
    }

    // returns a list of allButtons
    public List<Button> getAllButtons() {
        return this.allButtons;
    }

    // returns whether sofa is being selected or not
    public boolean sofaPositionBeingSelected() {
        return this.sofaPositionBeingSelected;
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
