package io.github.itzispyder.dotchaos.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.function.BiConsumer;

public class Window extends JFrame implements MouseListener, KeyListener, MouseMotionListener, MouseWheelListener {

    public static final GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
    public static final Rectangle DEFAULT_BOUNDS = environment.getMaximumWindowBounds();
    public Screen currentScreen;
    public Point lastMousePosition;

    public Window(String title) {
        super(title);
        this.addMouseListener(this);
        this.addKeyListener(this);
        this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);

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
            this.remove(this.currentScreen);
        }

        this.add(currentScreen);
        this.currentScreen = currentScreen;
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
        runOnCurrentScreen((window, screen) -> screen.mouseClicked(e));
    }

    @Override
    public void mousePressed(MouseEvent e) {
        runOnCurrentScreen((window, screen) -> screen.mousePressed(e));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        runOnCurrentScreen((window, screen) -> screen.mouseReleased(e));
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        runOnCurrentScreen((window, screen) -> screen.mouseEntered(e));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        runOnCurrentScreen((window, screen) -> screen.mouseExited(e));
    }

    @Override
    public void keyTyped(KeyEvent e) {
        runOnCurrentScreen((window, screen) -> screen.keyTyped(e));
    }

    @Override
    public void keyPressed(KeyEvent e) {
        runOnCurrentScreen((window, screen) -> screen.keyPressed(e));
    }

    @Override
    public void keyReleased(KeyEvent e) {
        runOnCurrentScreen((window, screen) -> screen.keyReleased(e));
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        runOnCurrentScreen((window, screen) -> screen.mouseDragged(e));
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        runOnCurrentScreen((window, screen) -> screen.mouseMoved(e));
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        runOnCurrentScreen((window, screen) -> screen.mouseWheelMoved(e));
    }
}
