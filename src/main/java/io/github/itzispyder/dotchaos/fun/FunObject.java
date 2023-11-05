package io.github.itzispyder.dotchaos.fun;

import io.github.itzispyder.dotchaos.util.math.Vec2d;
import io.github.itzispyder.dotchaos.util.math.Vec2i;

import java.awt.*;

public abstract class FunObject {

    public int x, y, width, height;

    public FunObject(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public abstract void onRender(Graphics2D g);

    public void onTick() {

    }

    public Vec2i getPosInt() {
        return new Vec2i(x, y);
    }

    public Vec2d getPos() {
        return new Vec2d(x, y);
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
}
