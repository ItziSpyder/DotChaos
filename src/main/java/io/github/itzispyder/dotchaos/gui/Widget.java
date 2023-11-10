package io.github.itzispyder.dotchaos.gui;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class Widget implements KeyListener, MouseListener {

    public int x, y, width, height;
    public Screen parentScreen;
    public Widget parent;
    public final ConcurrentLinkedQueue<Widget> children;
    public boolean rendering;

    public Widget(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.children = new ConcurrentLinkedQueue<>();
        this.rendering = true;
    }

    public abstract void onRender(Graphics2D g);

    public void onPostRender(Graphics2D g) {

    }

    public void onTick() {

    }

    public void render(Graphics2D g) {
        if (rendering) {
            onRender(g);
            this.children.forEach(c -> c.render(g));
            onPostRender(g);
        }
    }

    public void clearChildren() {
        children.clear();
    }

    public ConcurrentLinkedQueue<Widget> getChildren() {
        return children;
    }

    public void addChild(Widget child) {
        if (child != null && child != this) {
            this.children.add(child);
            child.parent = this;
        }
    }

    public void removeChild(Widget child) {
        if (child != null) {
            child.parent = null;
            child.parentScreen = null;
            this.children.remove(child);
        }
    }

    public boolean hasParent() {
        return parent != null;
    }

    public Widget getParent() {
        return parent;
    }

    public boolean hasParentScreen() {
        return parentScreen != null;
    }

    public Screen getParentScreen() {
        return parentScreen;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
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
    public String toString() {
        return "{x=%s, y=%s, width=%s, height=%s, rendering=%s}".formatted(x, y, width, height, rendering);
    }
}
