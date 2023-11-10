package io.github.itzispyder.dotchaos.gui;

import io.github.itzispyder.dotchaos.Main;
import io.github.itzispyder.dotchaos.util.math.MathUtils;
import io.github.itzispyder.dotchaos.util.math.Vector2d;
import io.github.itzispyder.dotchaos.util.math.Vector2i;

import java.awt.*;

public abstract class PhysicalWidget extends Widget {

    public Vector2d velocity;
    public boolean airResistance;

    public PhysicalWidget(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.velocity = new Vector2d();
    }

    @Override
    public void onTick() {
        travelVec(velocity);
        if (airResistance) {
            velocity = MathUtils.reduce(velocity);
        }
    }

    public void travelVec(Vector2d vec) {
        Rectangle r = Main.window.currentScreen.getBounds();
        int x = (int)(getX() + vec.x);
        int y = (int)(getY() - vec.y);
        setX(MathUtils.clampX(x, r));
        setY(MathUtils.clampY(y, r));
    }

    public void setX(double x) {
        setX((int)x);
    }

    public void setY(double y) {
        setY((int)y);
    }

    public void setWidth(double width) {
        setWidth((int)width);
    }

    public void setHeight(double height) {
        setHeight((int)height);
    }

    public Vector2d getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2d vel) {
        this.velocity = vel;
    }

    public Vector2d getPos() {
        return new Vector2d(getX(), getY());
    }

    public void setPos(Vector2d vec) {
        setX((int)vec.x);
        setY((int)vec.y);
    }

    public Vector2i getPosInt() {
        return new Vector2i(getX(), getY());
    }

    public void setPos(Vector2i vec) {
        setX(vec.x);
        setY(vec.y);
    }

    public boolean obeysAirResistance() {
        return airResistance;
    }

    public void setObeyAirResistance(boolean airResistance) {
        this.airResistance = airResistance;
    }

    @Override
    public String toString() {
        return "{x=%s, y=%s, width=%s, height=%s, rendering=%s, velocity=%s}".formatted(x, y, width, height, rendering, velocity);
    }
}
