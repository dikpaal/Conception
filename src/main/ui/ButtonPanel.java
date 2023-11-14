package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ButtonPanel extends PanelGUI {

    public ButtonPanel(int w, int h) {
        ImageIcon chairIcon = new ImageIcon("src/main/ui/images/chair.png");
        Image chairImg = chairIcon.getImage();
        Image newChairImg = chairImg.getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH);
        ImageIcon newChairIcon = new ImageIcon(newChairImg);

        ImageIcon sofaIcon = new ImageIcon("src/main/ui/images/sofa.png");
        Image sofaImg = sofaIcon.getImage();
        Image newSofaImg = sofaImg.getScaledInstance(60, 30,  java.awt.Image.SCALE_SMOOTH);
        ImageIcon newSofaIcon = new ImageIcon(newSofaImg);

        ImageIcon centreTableIcon = new ImageIcon("src/main/ui/images/centretable.png");
        Image centreTableImg = centreTableIcon.getImage();
        Image newCentreTableImg = centreTableImg.getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH);
        ImageIcon newCentreTableIcon = new ImageIcon(newCentreTableImg);

        JButton chairButton = new JButton();
        JButton sofaButton = new JButton();
        JButton centreTableButton = new JButton();

        chairButton.setBounds(w - 230, h / 6, 30, 30);
        sofaButton.setBounds(w - 180, h / 6, 60, 30);
        centreTableButton.setBounds(w - 100, h / 6, 30, 30);

        chairButton.setIcon(newChairIcon);
        chairButton.addActionListener(e -> System.out.println("Chair button pressed!"));

        sofaButton.setIcon(newSofaIcon);
        sofaButton.addActionListener(e -> System.out.println("Sofa button pressed!"));

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
