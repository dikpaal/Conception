package ui;

import javax.swing.*;
import java.awt.*;

public class FurniturePanel extends PanelGUI {

    public FurniturePanel(int w, int h) {
        JLabel chairLabel = new JLabel();
        ImageIcon chairIcon = new ImageIcon("src/main/ui/images/chair.png");
        Image chairImg = chairIcon.getImage();
        Image newChairImg = chairImg.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);
        ImageIcon newChairIcon = new ImageIcon(newChairImg);
        chairLabel.setIcon(newChairIcon);

        JLabel sofaLabel = new JLabel();
        ImageIcon sofaIcon = new ImageIcon("src/main/ui/images/sofa.png");
        Image sofaImg = sofaIcon.getImage();
        Image newSofaImg = sofaImg.getScaledInstance(80, 50,  java.awt.Image.SCALE_SMOOTH);
        ImageIcon newSofaIcon = new ImageIcon(newSofaImg);
        sofaLabel.setIcon(newSofaIcon);

        JLabel centreTableLabel = new JLabel();
        ImageIcon centreTableIcon = new ImageIcon("src/main/ui/images/centretable.png");
        Image centreTableImg = centreTableIcon.getImage();
        Image newCentreTableImg = centreTableImg.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);
        ImageIcon newCentreTableIcon = new ImageIcon(newCentreTableImg);
        centreTableLabel.setIcon(newCentreTableIcon);

        chairLabel.setBounds((w / 2) - 25, h / 3, 50, 50);
        sofaLabel.setBounds((w / 2) - 40, (h / 3) + (h / 6), 80, 50);
        centreTableLabel.setBounds((w / 2) - 25, (h / 3) + (h / 3), 50, 50);

        this.setLayout(null);
        this.add(chairLabel);
        this.add(sofaLabel);
        this.add(centreTableLabel);

        this.setBackground(Color.white);
        this.setBounds(0, 0, w, h);
    }
}

