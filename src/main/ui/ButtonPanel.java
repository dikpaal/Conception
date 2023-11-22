package ui;

import model.Chair;
import model.Furniture;
import model.FurnitureType;

import javax.accessibility.AccessibleValue;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class ButtonPanel extends PanelGUI {

    private Canvas canvas;
    private MessagePanel messagePanel;
    private boolean chairButtonSelected = false;
    private boolean sofaButtonSelected = false;
    private boolean centreTableButtonSelected = false;

    public ButtonPanel(int w, int h, Canvas canvas, MessagePanel messagePanel) {

        this.canvas = canvas;
        this.messagePanel = messagePanel;

        ImageIcon saveIcon = new ImageIcon("src/main/ui/images/save.png");
        Image saveImg = saveIcon.getImage();
        Image newSaveImg = saveImg.getScaledInstance(25, 20,  java.awt.Image.SCALE_SMOOTH);
        ImageIcon newSaveIcon = new ImageIcon(newSaveImg);
        JButton saveButton = new JButton();
        saveButton.setBounds(15, h / 3, 25, 20);
        saveButton.setIcon(newSaveIcon);
        saveButton.addActionListener(e -> saveButtonPressed());

        ImageIcon chairIcon = new ImageIcon("src/main/ui/images/chair.png");
        Image chairImg = chairIcon.getImage();
        Image newChairImg = chairImg.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH);
        ImageIcon newChairIcon = new ImageIcon(newChairImg);
        JButton chairButton = new JButton();
        chairButton.setBounds(w - 110, h / 3, 20, 20);
        chairButton.setIcon(newChairIcon);
        chairButton.addActionListener(e -> chairButtonPressed());

        ImageIcon sofaIcon = new ImageIcon("src/main/ui/images/sofa.png");
        Image sofaImg = sofaIcon.getImage();
        Image newSofaImg = sofaImg.getScaledInstance(40, 20,  java.awt.Image.SCALE_SMOOTH);
        ImageIcon newSofaIcon = new ImageIcon(newSofaImg);
        JButton sofaButton = new JButton();
        sofaButton.setBounds(w - 80, h / 3, 40, 20);
        sofaButton.setIcon(newSofaIcon);
        sofaButton.addActionListener(e -> sofaButtonPressed());

        ImageIcon centreTableIcon = new ImageIcon("src/main/ui/images/centretable.png");
        Image centreTableImg = centreTableIcon.getImage();
        Image newCentreTableImg = centreTableImg.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH);
        ImageIcon newCentreTableIcon = new ImageIcon(newCentreTableImg);
        JButton centreTableButton = new JButton();
        centreTableButton.setBounds(w - 30, h / 3, 20, 20);
        centreTableButton.setIcon(newCentreTableIcon);
        centreTableButton.addActionListener(e -> centreTableButtonPressed());

        this.setLayout(null);
        this.add(saveButton);
        this.add(chairButton);
        this.add(sofaButton);
        this.add(centreTableButton);
        this.setBackground(Color.white);
        this.setBounds(0, 0, w, h);
    }

    public void saveButtonPressed() {
        chairButtonSelected = false;
        sofaButtonSelected = false;
        centreTableButtonSelected = false;

        System.out.println(canvas.getRoom().getUsername());
        System.out.println(canvas.getRoom().getFurnitureList());
        System.out.println(canvas.getRoom().getNumberedAndFurnitureList());
        canvas.saveRoom();

    }

    public void chairButtonPressed() {

        sofaButtonSelected = false;
        centreTableButtonSelected = false;

        if (!chairButtonSelected) {
            chairButtonSelected = true;
            canvas.revertHighlighting();
            canvas.highlightAllAvailableSpotsForChair();
            this.messagePanel.getLabel().setText("Chair selected. Now select a spot...");
        } else {
            chairButtonSelected = false;
            canvas.revertHighlighting();
            this.messagePanel.getLabel().setText("Nothing selected.");
        }
    }

    public void sofaButtonPressed() {

        chairButtonSelected = false;
        centreTableButtonSelected = false;


        if (!sofaButtonSelected) {
            sofaButtonSelected = true;
            canvas.revertHighlighting();
            canvas.highlightAllAvailableSpotsForSofa();
            this.messagePanel.getLabel().setText("Sofa selected. Now select a spot...");

        } else {
            sofaButtonSelected = false;
            canvas.revertHighlighting();
            this.messagePanel.getLabel().setText("Nothing selected.");

        }

    }

    public void centreTableButtonPressed() {

        chairButtonSelected = false;
        sofaButtonSelected = false;

        if (!centreTableButtonSelected) {
            centreTableButtonSelected = true;
            canvas.revertHighlighting();
            canvas.highlightAllAvailableSpotsForCentreTable();
            this.messagePanel.getLabel().setText("Centre table selected. Now select a spot...");

        } else {
            centreTableButtonSelected = false;
            canvas.revertHighlighting();
            this.messagePanel.getLabel().setText("Nothing selected.");

        }

    }

    public boolean isChairButtonSelected() {
        return chairButtonSelected;
    }

    public boolean isSofaButtonSelected() {
        return sofaButtonSelected;
    }

    public boolean isCentreTableButtonSelected() {
        return centreTableButtonSelected;
    }
}
