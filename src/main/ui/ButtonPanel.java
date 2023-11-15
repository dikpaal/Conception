package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ButtonPanel extends PanelGUI {

    public ButtonPanel(int w, int h) {
        ImageIcon chairIcon = new ImageIcon("src/main/ui/images/chair.png");
        Image chairImg = chairIcon.getImage();
        Image newChairImg = chairImg.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH);
        ImageIcon newChairIcon = new ImageIcon(newChairImg);
        JButton chairButton = new JButton();
        chairButton.setBounds(w - 110, h / 3, 20, 20);
        chairButton.setIcon(newChairIcon);
        chairButton.addActionListener(e -> System.out.println("Chair button pressed!"));

        ImageIcon sofaIcon = new ImageIcon("src/main/ui/images/sofa.png");
        Image sofaImg = sofaIcon.getImage();
        Image newSofaImg = sofaImg.getScaledInstance(40, 20,  java.awt.Image.SCALE_SMOOTH);
        ImageIcon newSofaIcon = new ImageIcon(newSofaImg);
        JButton sofaButton = new JButton();
        sofaButton.setBounds(w - 80, h / 3, 40, 20);
        sofaButton.setIcon(newSofaIcon);
        sofaButton.addActionListener(e -> System.out.println("Sofa button pressed!"));

        ImageIcon centreTableIcon = new ImageIcon("src/main/ui/images/centretable.png");
        Image centreTableImg = centreTableIcon.getImage();
        Image newCentreTableImg = centreTableImg.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH);
        ImageIcon newCentreTableIcon = new ImageIcon(newCentreTableImg);
        JButton centreTableButton = new JButton();
        centreTableButton.setBounds(w - 30, h / 3, 20, 20);
        centreTableButton.setIcon(newCentreTableIcon);
        centreTableButton.addActionListener(e -> System.out.println("Centre Table button pressed!"));

        this.setLayout(null);
        this.add(chairButton);
        this.add(sofaButton);
        this.add(centreTableButton);
        this.setBackground(Color.white);
        this.setBounds(0, 0, w, h);
    }
}
