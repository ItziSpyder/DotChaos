package io.github.itzispyder.dotchaos.gui;

import javax.swing.*;
import java.awt.*;

public abstract class Screen extends JPanel {

    public Screen() {

    }

    public abstract void onRender(Graphics2D g);

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.WHITE);
        g.fillRect(getX(), getY(), getWidth(), getHeight());
        this.onRender((Graphics2D) g);
    }

    public void tick() {

    }
}
