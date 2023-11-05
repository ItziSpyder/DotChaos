package io.github.itzispyder.dotchaos.gui;

import javax.swing.*;
import java.awt.*;
import java.util.function.BiConsumer;

public class Window extends JFrame {

    public static final Rectangle DEFAULT_BOUNDS = new Rectangle(0, 0, 800, 500);
    public Screen currentScreen;
    public Point lastMousePosition;

    public Window(String title) {
        super(title);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setBounds(DEFAULT_BOUNDS);
        this.setLocationRelativeTo(null);
        this.lastMousePosition = new Point();
    }

    public Window() {
        this("Untitled window");
    }

    public void setCurrentScreen(Screen currentScreen) {
        this.remove(currentScreen);
        this.currentScreen = currentScreen;
        this.add(currentScreen);
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
}
