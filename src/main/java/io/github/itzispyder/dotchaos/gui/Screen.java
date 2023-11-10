package io.github.itzispyder.dotchaos.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class Screen extends JPanel implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

    public final ConcurrentLinkedQueue<Widget> children;
    public boolean invertZ;

    public Screen() {
        this.children = new ConcurrentLinkedQueue<>();
    }

    public abstract void onRender(Graphics2D g);

    public void onPostRender(Graphics2D g) {

    }

    public void tick() {
        this.children.forEach(Widget::onTick);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.WHITE);
        g.fillRect(getX(), getY(), getWidth() + 1, getHeight() + 1);

        Graphics2D g2 = (Graphics2D)g;

        this.onRender(g2);
        this.paintChildren(g2);
        this.onPostRender(g2);
    }

    public void paintChildren(Graphics2D g) {
        if (invertZ) {
            List<Widget> children = new ArrayList<>(this.children);
            for (int i = children.size() - 1; i >= 0; i--) {
                children.get(i).render(g);
            }
        }
        else {
            children.forEach(c -> c.render(g));
        }
    }

    public void clearChildren() {
        children.clear();
    }

    public ConcurrentLinkedQueue<Widget> getChildren() {
        return children;
    }

    public void addChild(Widget child) {
        if (child != null) {
            this.addMouseListener(child);
            this.addKeyListener(child);
            this.children.add(child);
            child.parentScreen = this;
        }
    }

    public void removeChild(Widget child) {
        if (child != null) {
            this.removeMouseListener(child);
            this.removeKeyListener(child);
            child.parent = null;
            child.parentScreen = null;
            this.children.remove(child);
        }
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

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

    }
}
