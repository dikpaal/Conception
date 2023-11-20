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

import static model.FurnitureType.*;

public class Canvas extends PanelGUI {
    Room room;
    List<Button> allButtons;
    MessagePanel messagePanel;
    GridBagConstraints gbc;
    private int firstSofaSpot;

    private boolean chairPositionBeingSelected = false;
    private boolean firstSofaPositionBeingSelected = false;
    private boolean secondSofaPositionBeingSelected = false;
    private boolean centreTablePositionBeingSelected = false;

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
        System.out.println(room.getDimension().getLength());
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
                    Image newSquareImg = squareImg.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
                    ImageIcon newSquareIcon = new ImageIcon(newSquareImg);
                    Button squareButtonChair = new Button(count, null, FurnitureType.CHAIR, this, j, i);
                    squareButtonChair.setIcon(newSquareIcon);
                    squareButtonChair.setPreferredSize(new Dimension(40, 40));
                    squareButtonChair.setBackground(Color.white);
                    squareButtonChair.setForeground(Color.black);
                    squareButtonChair.setFocusable(false);

                    gbc.gridx = squareButtonChair.getGridX();
                    gbc.gridy = squareButtonChair.getGridY();
                    gbc.gridheight = 1;
                    gbc.gridwidth = 1;
                    this.add(squareButtonChair, gbc);
                    allButtons.add(squareButtonChair);
                } else if (number.equals("Sv")) {
                    drawSofa(count, j, i);
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

                                Button squareButtonCentreTable = new Button(null, ids, FurnitureType.CENTRETABLE, this, j, i);
                                squareButtonCentreTable.setIcon(newSquareIconCentreTable);
                                squareButtonCentreTable.setPreferredSize(new Dimension(80, 80));
                                squareButtonCentreTable.setBackground(Color.white);
                                squareButtonCentreTable.setForeground(Color.black);
                                squareButtonCentreTable.setFocusable(false);
                                gbc.gridx = squareButtonCentreTable.getGridX();
                                gbc.gridy = squareButtonCentreTable.getGridY();
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
                    Image newSquareImg = squareImg.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
                    ImageIcon newSquareIcon = new ImageIcon(newSquareImg);
                    Button squareButton = new Button(count, null, FurnitureType.SQUARE, this, j, i);
                    squareButton.setIcon(newSquareIcon);
                    squareButton.setPreferredSize(new Dimension(40, 40));
                    squareButton.setBackground(Color.white);
                    squareButton.setForeground(Color.black);
                    squareButton.setFocusable(false);

                    gbc.gridx = squareButton.getGridX();
                    gbc.gridy = squareButton.getGridY();
                    gbc.gridheight = 1;
                    gbc.gridwidth = 1;
                    this.add(squareButton, gbc);
                    allButtons.add(squareButton);
                }
            }
        }
    }

    private void drawSofa(int count, int j, int i) {
        List<Furniture> furnitureList = room.getFurnitureList();

        ImageIcon squareIconSofa;
        Image squareImgSofa;
        Image newSquareImgSofa;
        ImageIcon newSquareIconSofa;
        Button squareButtonSofa;
        List<Integer> ids;

        for (Furniture f : furnitureList) {
            if (f.getType() == SOFA) {
                if (f.getAllSpots().contains(count) && f.getAllSpots().contains(count + 1)) {
                    squareIconSofa = new ImageIcon("src/main/ui/images/sofa.png");
                    squareImgSofa = squareIconSofa.getImage();
                    newSquareImgSofa = squareImgSofa.getScaledInstance(80, 40, java.awt.Image.SCALE_SMOOTH);
                    newSquareIconSofa = new ImageIcon(newSquareImgSofa);

                    ids = f.getAllSpots();

                    squareButtonSofa = new Button(null, ids, FurnitureType.SOFA, this, j, i);
                    squareButtonSofa.setIcon(newSquareIconSofa);
                    squareButtonSofa.setPreferredSize(new Dimension(80, 40));
                    squareButtonSofa.setBackground(Color.white);
                    squareButtonSofa.setForeground(Color.black);
                    squareButtonSofa.setFocusable(false);
                    gbc.gridx = squareButtonSofa.getGridX();
                    gbc.gridy = squareButtonSofa.getGridY();
                    gbc.gridheight = 1;
                    gbc.gridwidth = 2;
                    gbc.fill = GridBagConstraints.HORIZONTAL;
                    this.add(squareButtonSofa, gbc);
                    allButtons.add(squareButtonSofa);

                } else if (f.getAllSpots().contains(count)
                        && f.getAllSpots().contains(count - 1 + room.getDimension().getLength())) {
                    squareIconSofa = new ImageIcon("src/main/ui/images/sofaTilted.png");
                    squareImgSofa = squareIconSofa.getImage();
                    newSquareImgSofa = squareImgSofa.getScaledInstance(40, 80, java.awt.Image.SCALE_SMOOTH);
                    newSquareIconSofa = new ImageIcon(newSquareImgSofa);

                    ids = f.getAllSpots();

                    squareButtonSofa = new Button(null, ids, FurnitureType.SOFA, this, j, i);
                    squareButtonSofa.setIcon(newSquareIconSofa);
                    squareButtonSofa.setPreferredSize(new Dimension(40, 80));
                    squareButtonSofa.setBackground(Color.white);
                    squareButtonSofa.setForeground(Color.black);
                    squareButtonSofa.setFocusable(false);
                    gbc.gridx = squareButtonSofa.getGridX();
                    gbc.gridy = squareButtonSofa.getGridY();
                    gbc.gridheight = 2;
                    gbc.gridwidth = 1;
                    gbc.fill = GridBagConstraints.VERTICAL;
                    this.add(squareButtonSofa, gbc);
                    allButtons.add(squareButtonSofa);

                }
            }
        }
    }

    // highlights all the available spots for a chair
    public void highlightAllAvailableSpotsForChair() {
        chairPositionBeingSelected = true;
        firstSofaPositionBeingSelected = false;
        centreTablePositionBeingSelected = false;
        ImageIcon squareIcon = new ImageIcon("src/main/ui/images/greenSquare.png");
        Image squareImg = squareIcon.getImage();
        Image newSquareImg = squareImg.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
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
        chairPositionBeingSelected = false;
        firstSofaPositionBeingSelected = true;
        centreTablePositionBeingSelected = false;

        List<List<String>> availableSpots = this.getRoom().spaceForASofa();

        List<Integer> firstSpots = new ArrayList<>();
        List<Integer> secondSpots = new ArrayList<>();

        for (List<String> spots : availableSpots) {
            firstSpots.add(Integer.parseInt(spots.get(0)));
            secondSpots.add(Integer.parseInt(spots.get(1)));
        }

        ImageIcon squareIcon = new ImageIcon("src/main/ui/images/greenSquare.png");
        Image squareImg = squareIcon.getImage();
        Image newSquareImg = squareImg.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
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
                    }
                }
            } else {
                button.setEnabled(false);
            }
        }
    }

    // highlights all the available spots for a centre table
    public void highlightAllAvailableSpotsForCentreTable() {

        chairPositionBeingSelected = false;
        firstSofaPositionBeingSelected = false;
        centreTablePositionBeingSelected = true;

        ImageIcon squareIcon = new ImageIcon("src/main/ui/images/greenSquare.png");
        Image squareImg = squareIcon.getImage();
        Image newSquareImg = squareImg.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
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
        System.out.println("REVERT");
        chairPositionBeingSelected = false;
        firstSofaPositionBeingSelected = false;
        secondSofaPositionBeingSelected = false;
        centreTablePositionBeingSelected = false;
        firstSofaSpot = 0;
        ImageIcon squareIcon = new ImageIcon("src/main/ui/images/square.png");
        Image squareImg = squareIcon.getImage();
        Image newSquareImg = squareImg.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
        ImageIcon newSquareIcon = new ImageIcon(newSquareImg);

        for (Button button : this.getAllButtons()) {

            if (button.getType() == FurnitureType.GREENSQUARE
                    || button.getType() == REDSQUARE
                    || button.getType() == FurnitureType.SQUARE) {
                button.setIcon(newSquareIcon);
                button.setPreferredSize(new Dimension(40, 40));
                button.setBackground(Color.white);
                button.setForeground(Color.black);
                button.setFocusable(false);
                button.setType(FurnitureType.SQUARE);
            } else if (button.getType() == SOFA && button.getIds().size() == 0) {
                button.setIcon(newSquareIcon);
                button.setPreferredSize(new Dimension(40, 40));
                button.setBackground(Color.white);
                button.setForeground(Color.black);
                button.setFocusable(false);
                button.setType(FurnitureType.SQUARE);
                button.setType(SQUARE);
            } else {
                button.setEnabled(true);
            }
        }
    }

    // highlights the other spot for a sofa
    public void highlightTheOtherSpotsForSofa(int spot) {
        chairPositionBeingSelected = false;
        firstSofaPositionBeingSelected = false;
        secondSofaPositionBeingSelected = true;
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
            if (button.getType() == FurnitureType.GREENSQUARE || button.getType() == FurnitureType.SQUARE) {
                if (button.getId() == spot1 || button.getId() == spot2) {
                    ImageIcon squareIcon = new ImageIcon("src/main/ui/images/redSquare.png");
                    Image squareImg = squareIcon.getImage();
                    Image newSquareImg = squareImg.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
                    ImageIcon newSquareIcon = new ImageIcon(newSquareImg);
                    button.setIcon(newSquareIcon);
                    button.setPreferredSize(new Dimension(40, 40));
                    button.setBackground(Color.white);
                    button.setForeground(Color.black);
                    button.setFocusable(false);
                    button.setType(REDSQUARE);
                }
            }
        }
    }

    public void alterSofaPositionBeingSelected() {
        if (firstSofaPositionBeingSelected) {
            firstSofaPositionBeingSelected = false;
            secondSofaPositionBeingSelected = true;
        } else {
            firstSofaPositionBeingSelected = true;
            secondSofaPositionBeingSelected = false;
        }
    }

    public void changeGbcOfSofaButtonHorizontal(Button b, GridBagConstraints gbc) {
        int smallerId;

        if (b.getIds().get(0) < b.getIds().get(1)) {
            smallerId = b.getIds().get(0);
        } else {
            smallerId = b.getIds().get(1);
        }

        int row = smallerId / (room.getDimension().getLength() - 1) + 1;
        int col = smallerId % (room.getDimension().getLength() - 1);

        System.out.println("Row" + row + " " + "Col" + col);

        b.setGridX(col - 1);
        b.setGridY(row - 1);

        System.out.println(b.getGridX());
        System.out.println(b.getGridY());

        gbc.gridx = b.getGridX();
        gbc.gridy = b.getGridY();
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        ((GridBagLayout) getLayout()).setConstraints(b, gbc);

        revalidate();
        repaint();
//
//        for (Button b : allButtons) {
//            System.out.println();
//        }

    }

    public void changeGbcOfSofaButtonVertical(Button b, GridBagConstraints gbc) {
        int smallerId;

        System.out.println(b.getIds());

        if (b.getIds().get(0) < b.getIds().get(1)) {
            smallerId = b.getIds().get(0);
        } else {
            smallerId = b.getIds().get(1);
        }

        int row = smallerId / (room.getDimension().getLength() - 1) + 1;
        int col = smallerId % (room.getDimension().getLength() - 1);

        if (row == 2 && col == 0) {
            col = room.getDimension().getLength() - 1;
            row--;
        } else if (col == 0) {
            col = ((row * (room.getDimension().getLength() - 1)) / 2) - 2;
            row--;
        }

        System.out.println("Row" + row + " " + "Col" + col);

        b.setGridX(col - 1);
        b.setGridY(row - 1);

        System.out.println(b.getGridX());
        System.out.println(b.getGridY());

        gbc.gridx = b.getGridX();
        gbc.gridy = b.getGridY();
        gbc.gridheight = 2;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.VERTICAL;

        ((GridBagLayout) getLayout()).setConstraints(b, gbc);
        revalidate();
        repaint();
    }

    public GridBagConstraints getGBC() {
        return this.gbc;
    }

    // returns the room
    public Room getRoom() {
        return this.room;
    }

    public MessagePanel getMessagePanel() {
        return this.messagePanel;
    }

    // returns a list of allButtons
    public List<Button> getAllButtons() {
        return this.allButtons;
    }

    // returns whether chair is being selected or not
    public boolean chairPositionBeingSelected() {
        return this.chairPositionBeingSelected;
    }

    // returns whether sofa is being selected or not
    public boolean firstSofaPositionBeingSelected() {
        return this.firstSofaPositionBeingSelected;
    }

    // returns whether sofa is being selected or not
    public boolean secondSofaPositionBeingSelected() {
        return this.secondSofaPositionBeingSelected;
    }

    // returns whether centre table is being selected or not
    public boolean centreTablePositionBeingSelected() {
        return this.centreTablePositionBeingSelected;
    }

    public int getFirstSofaSpot() {
        return this.firstSofaSpot;
    }

    public void setFirstSofaSpot(int s) {
        this.firstSofaSpot = s;
    }

    public void setAllButtons(List<Button> list) {
        this.allButtons = list;
    }

    // JSON

    // REQUIRES: nothing
    // MODIFIES: this
    // EFFECTS: loads room from file
    private void loadRoom() {
        try {
            room = jsonReader.read();
            System.out.println("Loaded " + room.getUsername() + "'s room from " + JSON_STORE);
            System.out.println(room.getNumberedAndFurnitureList());
            System.out.println(room.getFurnitureList());
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
