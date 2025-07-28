package com.artcomp;

import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(String imagePath) {
        this.backgroundImage = new ImageIcon(imagePath).getImage();
        setLayout(null); // So we can position labels/buttons manually
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the image scaled to panel size
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
