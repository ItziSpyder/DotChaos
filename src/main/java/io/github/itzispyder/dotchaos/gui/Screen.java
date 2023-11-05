package io.github.itzispyder.dotchaos.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public abstract class Screen extends JPanel implements MouseListener, MouseMotionListener {

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

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
