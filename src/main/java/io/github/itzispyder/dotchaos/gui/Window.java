package io.github.itzispyder.dotchaos.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.function.BiConsumer;

public class Window extends JFrame implements MouseListener {

    public static final GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
    public static final Rectangle DEFAULT_BOUNDS = environment.getMaximumWindowBounds();
    public Screen currentScreen;
    public Point lastMousePosition;

    public Window(String title) {
        super(title);
        this.addMouseListener(this);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(DEFAULT_BOUNDS);
        this.setLocationRelativeTo(null);
        this.lastMousePosition = new Point();
        this.setVisible(true);
    }

    public Window() {
        this("Untitled window");
    }

    public void setCurrentScreen(Screen currentScreen) {
        if (this.currentScreen != null) {
            this.removeMouseListener(this.currentScreen);
            this.removeMouseMotionListener(this.currentScreen);
            this.remove(this.currentScreen);
        }

        this.currentScreen = currentScreen;
        this.add(currentScreen);
        this.addMouseListener(currentScreen);
        this.addMouseMotionListener(currentScreen);
        this.setVisible(true);
    }

    public Screen getCurrentScreen() {
        return currentScreen;
    }

    public void runOnCurrentScreen(BiConsumer<Window, Screen> action) {
        if (currentScreen != null) {
            action.accept(this, currentScreen);
        }
    }

    @Override
    public Point getMousePosition() throws HeadlessException {
        Point p = super.getMousePosition();
        if (p == null) {
            return lastMousePosition;
        }
        else {
            Insets i = getInsets();
            int x = p.x - i.left;
            int y = p.y - i.top;
            lastMousePosition = new Point(x, y);
            return lastMousePosition;
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
}
