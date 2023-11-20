package ui;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import static model.FurnitureType.*;

public class Button extends JButton {
    private Integer id;
    private List<Integer> ids;
    private FurnitureType type;
    private Canvas canvas;

    private int gridX;
    private int gridY;


    public Button(Integer id, List<Integer> ids, FurnitureType type, Canvas canvas, int gridX, int gridY) {
        this.id = id;
        this.ids = ids;
        this.type = type;
        this.canvas = canvas;
        this.gridX = gridX;
        this.gridY = gridY;
        this.addActionListener(e -> squareButtonPressed(this));
    }

    public void squareButtonPressed(Button button) {

        if (this.type == FurnitureType.GREENSQUARE) {
            if (canvas.chairPositionBeingSelected()) {
                Furniture chair = new Chair();
                canvas.getRoom().setChairInNumberedAndFurnitureList(chair, this.id);
                this.type = CHAIR;
                ImageIcon squareIcon = new ImageIcon("src/main/ui/images/chair.png");
                Image squareImg = squareIcon.getImage();
                Image newSquareImg = squareImg.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
                ImageIcon newSquareIcon = new ImageIcon(newSquareImg);
                this.setIcon(newSquareIcon);
                this.setPreferredSize(new Dimension(40, 40));
                this.setBackground(Color.white);
                this.setForeground(Color.black);
                this.setFocusable(false);
            } else if (canvas.firstSofaPositionBeingSelected()) {
                this.type = REDSQUARE;
                ImageIcon squareIcon = new ImageIcon("src/main/ui/images/redSquare.png");
                Image squareImg = squareIcon.getImage();
                Image newSquareImg = squareImg.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
                ImageIcon newSquareIcon = new ImageIcon(newSquareImg);
                this.setIcon(newSquareIcon);
                this.setPreferredSize(new Dimension(40, 40));
                this.setBackground(Color.white);
                this.setForeground(Color.black);
                this.setFocusable(false);
                canvas.alterSofaPositionBeingSelected();
                canvas.setFirstSofaSpot(this.id);
                canvas.highlightTheOtherSpotsForSofa(this.id);
            }

        } else if (this.type == FurnitureType.REDSQUARE) {
            List<Integer> spotList = new ArrayList<>();

            if (canvas.secondSofaPositionBeingSelected()) {

                Furniture sofa = new Sofa();

                if (canvas.getFirstSofaSpot() < this.id) {
                    canvas.getRoom().setSofaInNumberedAndFurnitureList(sofa, canvas.getFirstSofaSpot(), this.id);

                    for (Button b : canvas.getAllButtons()) {
                        if (b.getId() == canvas.getFirstSofaSpot()) {

                            spotList.add(canvas.getFirstSofaSpot());
                            spotList.add(this.id);

                            b.ids = new ArrayList<>();
                            b.ids.add(canvas.getFirstSofaSpot());
                            b.ids.add(this.id);
                            canvas.setFirstSofaSpot(0);
                            b.id = 0;
                            canvas.alterSofaPositionBeingSelected();
                        }
                    }

                } else if (canvas.getFirstSofaSpot() > this.id) {
                    spotList.add(this.id);
                    spotList.add(canvas.getFirstSofaSpot());

                    canvas.getRoom().setSofaInNumberedAndFurnitureList(sofa, this.id, canvas.getFirstSofaSpot());

                    this.ids = new ArrayList<>();
                    this.ids.add(canvas.getFirstSofaSpot());
                    this.ids.add(this.id);
                    canvas.setFirstSofaSpot(0);
                    this.id = 0;
                    canvas.alterSofaPositionBeingSelected();

                } else {
                    canvas.revertHighlighting();
                }


                List<Button> buttonList = new ArrayList<>();

                for (Button b : canvas.getAllButtons()) {
                    int spot1 = spotList.get(0);
                    int spot2 = spotList.get(1);

                    if (b.getIds().size() != 0) {

                        int id1 = b.getIds().get(0);
                        int id2 = b.getIds().get(1);
                        int smallerId;


                        if (id1 < id2) {
                            smallerId = id1;
                        } else {
                            smallerId = id2;
                        }

                        System.out.println(smallerId);

                        int buttonId = smallerId;
                        if (spot1 == buttonId) {

                            int largerId;

                            if (smallerId == id1) {
                                largerId = id2;
                            } else {
                                largerId = id1;
                            }

                            if (largerId == smallerId + 1) {
                                // HORIZONTAL SOFA

                                b.type = SOFA;
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
                                canvas.revertHighlighting();
                                canvas.changeGbcOfSofaButtonHorizontal(b, canvas.getGBC());
                            } else if (largerId == smallerId + canvas.getRoom().getDimension().getLength() - 1) {
                                // VERTICAL SOFA
                                b.type = SOFA;
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
                                canvas.revertHighlighting();
                                canvas.changeGbcOfSofaButtonVertical(b, canvas.getGBC());
                            }
                        }
                    } else {
                        buttonList.add(b);
                    }
                }

                canvas.revertHighlighting();
                canvas.setAllButtons(buttonList);

            } else {
                this.type = GREENSQUARE;
                ImageIcon squareIcon = new ImageIcon("src/main/ui/images/greenSquare.png");
                Image squareImg = squareIcon.getImage();
                Image newSquareImg = squareImg.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
                ImageIcon newSquareIcon = new ImageIcon(newSquareImg);
                this.setIcon(newSquareIcon);
                this.setPreferredSize(new Dimension(40, 40));
                this.setBackground(Color.white);
                this.setForeground(Color.black);
                this.setFocusable(false);
            }
        }
    }

    // modifies: this
    // sets the type of the furniture to type
    public void setType(FurnitureType type) {
        this.type = type;
    }

    public int getId() {
        if (this.id == null) {
            return 0;
        }
        return this.id;
    }

    public List<Integer> getIds() {
        if (this.ids == null) {
            return new ArrayList<>();
        }
        return this.ids;
    }

    public FurnitureType getType() {
        return this.type;
    }

    public int getGridX() {
        return this.gridX;
    }

    public int getGridY() {
        return this.gridY;
    }

    public void setGridX(int x) {
        this.gridX = x;
    }

    public void setGridY(int y) {
        this.gridY = y;
    }
}
