package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static model.FurnitureType.*;

// Represents the canvas all the furniture can be placed in or removed from
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

    // EFFECTS: constructs the canvas with load, r, messagePanel
    public Canvas(Boolean load, Room r, MessagePanel messagePanel) {
        this.room = r;
        this.messagePanel = messagePanel;
        allButtons = new ArrayList<>();

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        this.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        setup(load);
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: calls to create a numberedPlane, initiate a numberedAndFurnitureList, and prints the room
    public void setup(Boolean load) {
        List<List<String>> numberedPlane = room.createNumberedPlane();
        room.setNumberedPlane(numberedPlane);
        room.initiateNumberedAndFurnitureList();

        if (load) {
            loadRoom();
            drawRoom();
        } else {
            drawRoom();
        }
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: draws the room on the canvas
    public void drawRoom() {
        int count = 0;
        boolean centreTablePlaced = false;
        for (int i = 0; i < room.getNumberedAndFurnitureList().size(); i++) {
            for (int j = 0; j < room.getNumberedAndFurnitureList().get(i).size(); j++) {
                count++;
                if (room.getNumberedAndFurnitureList().get(i).get(j).equals("Cv")) {
                    drawChair(count, j, i);
                } else if (room.getNumberedAndFurnitureList().get(i).get(j).equals("Sv")) {
                    drawSofa(count, j, i);
                } else if (room.getNumberedAndFurnitureList().get(i).get(j).equals("Tv")) {
                    if (!centreTablePlaced) {
                        for (Furniture f : room.getFurnitureList()) {
                            if (f.getType() == CENTRETABLE) {
                                drawCentreTable(f, j, i);
                                centreTablePlaced = true;
                            }
                        }
                    }
                } else {
                    drawSquare(count, j, i);
                }
            }
        }
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: draws the chair on the canvas
    private void drawChair(int count, int j, int i) {
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
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: draws the sofa on the canvas
    private void drawSofa(int count, int j, int i) {
        List<Furniture> furnitureList = room.getFurnitureList();

        for (Furniture f : furnitureList) {
            if (f.getType() == SOFA) {
                if (f.getAllSpots().contains(count) && f.getAllSpots().contains(count + 1)) {
                    drawNormalSofa(f, j, i);

                } else if (f.getAllSpots().contains(count)
                        && f.getAllSpots().contains(count - 1 + room.getDimension().getLength())) {
                    drawTiltedSofa(f, j, i);

                }
            }
        }
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: draws the normal horizontal sofa on the canvas
    private void drawNormalSofa(Furniture f, int j, int i) {
        ImageIcon squareIconSofa;
        Image squareImgSofa;
        Image newSquareImgSofa;
        ImageIcon newSquareIconSofa;
        Button squareButtonSofa;

        squareIconSofa = new ImageIcon("src/main/ui/images/sofa.png");
        squareImgSofa = squareIconSofa.getImage();
        newSquareImgSofa = squareImgSofa.getScaledInstance(80, 40, java.awt.Image.SCALE_SMOOTH);
        newSquareIconSofa = new ImageIcon(newSquareImgSofa);

        List<Integer> ids = f.getAllSpots();

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
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: draws the tilted vertical sofa on the canvas
    private void drawTiltedSofa(Furniture f, int j, int i) {

        ImageIcon squareIconSofa;
        Image squareImgSofa;
        Image newSquareImgSofa;
        ImageIcon newSquareIconSofa;
        Button squareButtonSofa;

        squareIconSofa = new ImageIcon("src/main/ui/images/sofaTilted.png");
        squareImgSofa = squareIconSofa.getImage();
        newSquareImgSofa = squareImgSofa.getScaledInstance(40, 80, java.awt.Image.SCALE_SMOOTH);
        newSquareIconSofa = new ImageIcon(newSquareImgSofa);

        List<Integer> ids = f.getAllSpots();

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

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: draws the centre table on the canvas
    private void drawCentreTable(Furniture f, int j, int i) {
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
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: draws the square on the canvas
    private void drawSquare(int count, int j, int i) {
        ImageIcon squareIcon = new ImageIcon("src/main/ui/images/square.png");
        Image squareImg = squareIcon.getImage();
        Image newSquareImg = squareImg.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
        ImageIcon newSquareIcon = new ImageIcon(newSquareImg);
        Button squareButton = new Button(count, new ArrayList<>(), FurnitureType.SQUARE, this, j, i);
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

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: highlights all the available spots for a chair
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

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: highlights all the available spots for a sofa
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
        parseAndHighlightFirstSofaSpot(firstSpots, secondSpots);
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: parses through allButtons and highlights all the spots where a sofa can be placed
    private void parseAndHighlightFirstSofaSpot(List<Integer> firstSpots, List<Integer> secondSpots) {

        System.out.println();

        for (Button button : this.getAllButtons()) {
            if (button.getType() == FurnitureType.SQUARE) {
                for (int spot : firstSpots) {
                    if (button.getId() == spot) {
                        button.changeToGreenSquare();
                    }
                }

                for (int spot : secondSpots) {
                    if (button.getId() == spot) {
                        button.changeToGreenSquare();
                    }
                }
            } else {
                button.setEnabled(false);
            }
        }
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: highlights all the available spots for a centre table
    public void highlightAllAvailableSpotsForCentreTable() {

        chairPositionBeingSelected = false;
        firstSofaPositionBeingSelected = false;
        centreTablePositionBeingSelected = true;

        List<String> spots = room.spaceForACentreTable();

        if (spots.isEmpty()) {
            //
        } else {
            int spot1 = Integer.parseInt(spots.get(0));

            for (Button button : this.getAllButtons()) {
                if (button.getType() == FurnitureType.SQUARE) {
                    if (button.getId() == spot1) {
                        button.changeToGreenSquare();
                    }
                } else {
                    button.setEnabled(false);
                }
            }
        }
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: reverts the highlighting of the canvas squares
    public void revertHighlighting() {
        chairPositionBeingSelected = false;
        firstSofaPositionBeingSelected = false;
        secondSofaPositionBeingSelected = false;
        centreTablePositionBeingSelected = false;
        firstSofaSpot = 0;


        for (Button button : this.getAllButtons()) {

            if (button.getType() == FurnitureType.GREENSQUARE
                    || button.getType() == REDSQUARE
                    || button.getType() == FurnitureType.SQUARE
                    || (button.getType() == SOFA && button.getIds().size() == 0)) {
                button.changeToSquare();
            } else {
                button.setEnabled(true);
            }
        }
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: highlights the other spot for a sofa
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

        parseAndHighlight(spot1, spot2);
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: preses through allButtons and highlights the necessary squares
    private void parseAndHighlight(int spot1, int spot2) {
        for (Button button : this.getAllButtons()) {
            if (button.getType() == FurnitureType.GREENSQUARE || button.getType() == FurnitureType.SQUARE) {
                if (button.getId() == spot1 || button.getId() == spot2) {
                    button.changeToRedSquare();
                }
            }
        }
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: changes firstSofaPositionBeingSelected and secondSofaPositionBeingSelected to the opposite boolean value
    public void alterSofaPositionBeingSelected() {
        if (firstSofaPositionBeingSelected) {
            firstSofaPositionBeingSelected = false;
            secondSofaPositionBeingSelected = true;
        } else {
            firstSofaPositionBeingSelected = true;
            secondSofaPositionBeingSelected = false;
        }
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: changes the gbc of b to make it a horizontal sofa
    public void changeGbcOfSofaButtonHorizontal(Button b, GridBagConstraints gbc) {
        int smallerId;

        if (b.getIds().get(0) < b.getIds().get(1)) {
            smallerId = b.getIds().get(0);
        } else {
            smallerId = b.getIds().get(1);
        }

        int row = smallerId / (room.getDimension().getLength() - 1) + 1;
        int col = smallerId % (room.getDimension().getLength() - 1);

        b.setGridX(col - 1);
        b.setGridY(row - 1);

        gbc.gridx = b.getGridX();
        gbc.gridy = b.getGridY();
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        ((GridBagLayout) getLayout()).setConstraints(b, gbc);

        revalidate();
        repaint();
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: changes the gbc of b to make it a vertical sofa
    public void changeGbcOfSofaButtonVertical(Button b, GridBagConstraints gbc) {
        int smallerId = getSmallerId(b.getIds().get(0), b.getIds().get(1));

        int row = smallerId / (room.getDimension().getLength() - 1) + 1;
        int col = smallerId % (room.getDimension().getLength() - 1);

        if (row == 2 && col == 0) {
            col = room.getDimension().getLength() - 1;
            row--;
        } else if (row == room.getDimension().getLength() - 1 && col == 0) {
            col = room.getDimension().getLength() - 1;
            row--;
        } else if (col == 0) {
            col = ((row * (room.getDimension().getLength() - 1)) / 2) - 2;
            row--;
        }

        b.setGridX(col - 1);
        b.setGridY(row - 1);

        gbc.gridx = b.getGridX();
        gbc.gridy = b.getGridY();
        gbc.gridheight = 2;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.VERTICAL;

        ((GridBagLayout) getLayout()).setConstraints(b, gbc);
        revalidate();
        repaint();
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: reverts back the gbc of b to 1x1
    public void revertGbcOfSofaButtonHorizontal(Button b) {
        int smallerId = b.getId();

        int row = smallerId / (room.getDimension().getLength() - 1) + 1;
        int col = smallerId % (room.getDimension().getLength() - 1);

        b.setGridX(col - 1);
        b.setGridY(row - 1);

        gbc.gridx = b.getGridX();
        gbc.gridy = b.getGridY();
        gbc.gridheight = 1;
        gbc.gridwidth = 1;

        ((GridBagLayout) getLayout()).setConstraints(b, gbc);

        revalidate();
        repaint();
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: reverts back the gbc of b to 1x1
    public void revertGbcOfSofaButtonVertical(Button b) {
        int smallerId = b.getId();

        int row = smallerId / (room.getDimension().getLength() - 1) + 1;
        int col = smallerId % (room.getDimension().getLength() - 1);

        if (row == 2 && col == 0) {
            col = room.getDimension().getLength() - 1;
            row--;
        } else if (row == room.getDimension().getLength() - 1 && col == 0) {
            col = room.getDimension().getLength() - 1;
            row--;
        } else if (col == 0) {
            col = ((row * (room.getDimension().getLength() - 1)) / 2) - 2;
            row--;
        }

        b.setGridX(col - 1);
        b.setGridY(row - 1);

        gbc.gridx = b.getGridX();
        gbc.gridy = b.getGridY();
        gbc.gridheight = 1;
        gbc.gridwidth = 1;

        ((GridBagLayout) getLayout()).setConstraints(b, gbc);
        revalidate();
        repaint();
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: changes the gbc of b to make it a 2x2 centre table
    public void changeGbcOfCentreTableButton(Button b, GridBagConstraints gbc) {
        int smallerId;

        if (b.getIds().get(0) < b.getIds().get(1)) {
            smallerId = b.getIds().get(0);
        } else {
            smallerId = b.getIds().get(1);
        }

        int row = smallerId / (room.getDimension().getLength() - 1) + 1;
        int col = smallerId % (room.getDimension().getLength() - 1);

        b.setGridX(col - 1);
        b.setGridY(row - 1);

        gbc.gridx = b.getGridX();
        gbc.gridy = b.getGridY();
        gbc.gridheight = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        ((GridBagLayout) getLayout()).setConstraints(b, gbc);

        revalidate();
        repaint();
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: reverts back the gbc of b to 1x1
    public void revertGbcOfCentreTableButton(Button b) {
        int smallerId;

        if (b.getIds().get(0) < b.getIds().get(1)) {
            smallerId = b.getIds().get(0);
        } else {
            smallerId = b.getIds().get(1);
        }

        int row = smallerId / (room.getDimension().getLength() - 1) + 1;
        int col = smallerId % (room.getDimension().getLength() - 1);

        b.setGridX(col - 1);
        b.setGridY(row - 1);

        gbc.gridx = b.getGridX();
        gbc.gridy = b.getGridY();
        gbc.gridheight = 1;
        gbc.gridwidth = 1;

        ((GridBagLayout) getLayout()).setConstraints(b, gbc);

        revalidate();
        repaint();
        b.changeToSquare();
        b.setId(b.getIds().get(0));
        b.setIds(new ArrayList<>());
    }

    // REQUIRES: b.getType != CHAIR
    // MODIFIES: this, b
    // EFFECTS: adds the chair to the canvas and to the furniture list
    public void addChair(Button b) {
        Furniture chair = new Chair();
        room.setChairInNumberedAndFurnitureList(chair, b.getId());
        b.setType(CHAIR);
        ImageIcon squareIcon = new ImageIcon("src/main/ui/images/chair.png");
        Image squareImg = squareIcon.getImage();
        Image newSquareImg = squareImg.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
        ImageIcon newSquareIcon = new ImageIcon(newSquareImg);
        b.setIcon(newSquareIcon);
        b.setPreferredSize(new Dimension(40, 40));
        b.setBackground(Color.white);
        b.setForeground(Color.black);
        b.setFocusable(false);
    }

    // REQUIRES: b.getType != SOFA
    // MODIFIES: this, b
    // EFFECTS: adds the sofa to the canvas and to the furniture list
    public void addSofa(Button b) {
        b.setType(REDSQUARE);
        ImageIcon squareIcon = new ImageIcon("src/main/ui/images/redSquare.png");
        Image squareImg = squareIcon.getImage();
        Image newSquareImg = squareImg.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
        ImageIcon newSquareIcon = new ImageIcon(newSquareImg);
        b.setIcon(newSquareIcon);
        b.setPreferredSize(new Dimension(40, 40));
        b.setBackground(Color.white);
        b.setForeground(Color.black);
        b.setFocusable(false);
        alterSofaPositionBeingSelected();
        setFirstSofaSpot(b.getId());
        highlightTheOtherSpotsForSofa(b.getId());
    }

    // REQUIRES: b.getType != SOFA
    // MODIFIES: this, b
    // EFFECTS: finally adds the sofa to the canvas and to the furniture list
    public void addFinalSofa(Button button) {

        List<Integer> spotList = new ArrayList<>();

        setSofa(spotList, button);

        List<Button> buttonList = new ArrayList<>();

        for (Button b : getAllButtons()) {

            if (b.getIds().size() != 0) {

                int id1 = b.getIds().get(0);
                int id2 = b.getIds().get(1);
                int smallerId = getSmallerId(id1, id2);

                if (spotList.get(0) == smallerId) {
                    int largerId = getLargerId(smallerId, id1, id2);

                    if (largerId == smallerId + 1) {
                        // HORIZONTAL SOFA
                        buttonList = makeHorizontalSofa(buttonList, b);
                    } else if (largerId == smallerId + room.getDimension().getLength() - 1) {
                        // VERTICAL SOFA
                        buttonList = makeVerticalSofa(buttonList, b);
                    }
                }
            } else {
                buttonList.add(b);
            }
        }

        revertHighlighting();
        setAllButtons(allButtons);
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: returns min(id1, id2)
    public int getSmallerId(int id1, int id2) {
        if (id1 < id2) {
            return id1;
        } else {
            return id2;
        }
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: returns max(id1, id2)
    public int getLargerId(int smallerId, int id1, int id2) {
        if (smallerId == id1) {
            return id2;
        } else {
            return id1;
        }
    }

    // EFFECTS: sets the sofa
    private void setSofa(List<Integer> spotList, Button button) {

        Furniture sofa = new Sofa();
        if (getFirstSofaSpot() < button.getId()) {
            room.setSofaInNumberedAndFurnitureList(sofa, getFirstSofaSpot(), button.getId());
            setSofaInFirstSpot(spotList, button);

        } else if (getFirstSofaSpot() > button.getId()) {
            spotList.add(button.getId());
            spotList.add(getFirstSofaSpot());
            room.setSofaInNumberedAndFurnitureList(sofa, button.getId(), getFirstSofaSpot());
            setSofaInSecondSpot(button);
        } else {
            revertHighlighting();
        }
    }

    // REQUIRES: b.getType != CENTRETABLE
    // MODIFIES: this, b
    // EFFECTS: adds the centre table to the canvas and to the furniture list
    public void addCentreTable(Button b) {

        Furniture centreTable = new CenterTable();
        room.setCenterTableInNumberedAndFurnitureList(centreTable, Integer.toString(b.getId()));
        b.setType(CENTRETABLE);

        List<Button> buttonList = new ArrayList<>();

        for (Button button : getAllButtons()) {
            if (button.getType() == CENTRETABLE) {
                button.setIds(centreTable.getAllSpots());
                button.setId(0);
            } else {
                buttonList.add(button);
            }
        }

        ImageIcon squareIcon = new ImageIcon("src/main/ui/images/centreTable.png");
        Image squareImg = squareIcon.getImage();
        Image newSquareImg = squareImg.getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH);
        ImageIcon newSquareIcon = new ImageIcon(newSquareImg);
        b.setIcon(newSquareIcon);
        b.setPreferredSize(new Dimension(80, 80));
        b.setBackground(Color.white);
        b.setForeground(Color.black);
        b.setFocusable(false);
        changeGbcOfCentreTableButton(b, gbc);
        revertHighlighting();
    }

    // REQUIRES: nothing
    // MODIFIES: this, b
    // EFFECTS: makes a horizontal sofa to be placed in the canvas
    public List<Button> makeHorizontalSofa(List<Button> buttonList, Button b) {
        b.setType(SOFA);
        ImageIcon squareIcon = new ImageIcon("src/main/ui/images/sofa.png");
        Image squareImg = squareIcon.getImage();
        Image newSquareImg = squareImg.getScaledInstance(80, 40, java.awt.Image.SCALE_SMOOTH);
        ImageIcon newSquareIcon = new ImageIcon(newSquareImg);
        b.setIcon(newSquareIcon);
        b.setPreferredSize(new Dimension(80, 40));
        b.setBackground(Color.white);
        b.setForeground(Color.black);
        b.setFocusable(false);
        buttonList.add(b);
        revertHighlighting();
        changeGbcOfSofaButtonHorizontal(b, gbc);

        return buttonList;
    }

    // REQUIRES: nothing
    // MODIFIES: this, b
    // EFFECTS: makes a vertical sofa to be placed in the canvas
    public List<Button> makeVerticalSofa(List<Button> buttonList, Button b) {
        b.setType(SOFA);
        ImageIcon squareIcon = new ImageIcon("src/main/ui/images/sofaTilted.png");
        Image squareImg = squareIcon.getImage();
        Image newSquareImg = squareImg.getScaledInstance(40, 80, java.awt.Image.SCALE_SMOOTH);
        ImageIcon newSquareIcon = new ImageIcon(newSquareImg);
        b.setIcon(newSquareIcon);
        b.setPreferredSize(new Dimension(40, 80));
        b.setBackground(Color.white);
        b.setForeground(Color.black);
        b.setFocusable(false);
        buttonList.add(b);
        revertHighlighting();
        changeGbcOfSofaButtonVertical(b, gbc);


        return buttonList;
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: changes the attributes of the button selected as the first sofa spot
    public void setSofaInFirstSpot(List<Integer> spotList, Button button) {
        for (Button b : getAllButtons()) {
            if (b.getId() == getFirstSofaSpot()) {

                spotList.add(getFirstSofaSpot());
                spotList.add(button.getId());

                List<Integer> tempList = new ArrayList<>();
                tempList.add(getFirstSofaSpot());
                tempList.add(button.getId());
                b.setIds(tempList);

                setFirstSofaSpot(0);
                b.setId(0);
                alterSofaPositionBeingSelected();
            }
        }
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: changes the attributes of the button selected as the second sofa spot
    public void setSofaInSecondSpot(Button button) {

        List<Integer> tempList = new ArrayList<>();
        tempList.add(getFirstSofaSpot());
        tempList.add(button.getId());
        button.setIds(tempList);

        setFirstSofaSpot(0);
        button.setId(0);
        alterSofaPositionBeingSelected();
    }

    // GETTERS AND SETTERS

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

    // returns first sofa spot
    public int getFirstSofaSpot() {
        return this.firstSofaSpot;
    }

    // sets first sofa spot to s
    public void setFirstSofaSpot(int s) {
        this.firstSofaSpot = s;
    }

    // sets allButtons to list
    public void setAllButtons(List<Button> list) {
        this.allButtons = list;
    }

    // JSON

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: saves the room to the file
    public void saveRoom() {
        try {
            jsonWriter.open();
            jsonWriter.write(room);
            jsonWriter.close();
            System.out.println("Saved " + room.getUsername() + "'s room to " + JSON_STORE);
            System.out.println("Bye, " + room.getUsername());
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // REQUIRES: nothing
    // MODIFIES: this
    // EFFECTS: loads room from file
    public void loadRoom() {
        try {
            room = jsonReader.read();
            System.out.println("Loaded " + room.getUsername() + "'s room from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
