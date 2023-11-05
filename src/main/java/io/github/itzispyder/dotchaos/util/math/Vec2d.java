package io.github.itzispyder.dotchaos.util.math;

public class Vec2d {

    public double x, y;

    public Vec2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vec2d() {
        this(0, 0);
    }

    public double distanceTo(Vec2d other) {
        double x = other.x - this.x;
        double y = other.y - this.y;
        return Math.sqrt(x * x + y * y);
    }

    public double length() {
        return Math.sqrt(x * x + y * y);
    }

    public double lengthSquared() {
        return x * x + y * y;
    }

    public Vec2d normalize() {
        double d = length();
        x /= d;
        y /= d;
        return this;
    }

    public Vec2d multiply(Vec2d other) {
        x *= other.x;
        y *= other.y;
        return this;
    }

    public Vec2d multiply(double x, double y) {
        return multiply(new Vec2d(x, y));
    }

    public Vec2d multiply(double i) {
        return multiply(i, i);
    }

    public Vec2d subtract(Vec2d other) {
        x -= other.x;
        y -= other.y;
        return this;
    }

    public Vec2d subtract(double x, double y) {
        return subtract(new Vec2d(x, y));
    }

    public Vec2d subtract(double i) {
        return subtract(i, i);
    }

    public Vec2d add(Vec2d other) {
        x += other.x;
        y += other.y;
        return this;
    }

    public Vec2d add(double x, double y) {
        return add(new Vec2d(x, y));
    }

    public Vec2d add(double i) {
        return add(i, i);
    }

    public Vec2d divide(Vec2d other) {
        x /= other.x;
        y /= other.y;
        return this;
    }

    public Vec2d divide(double x, double y) {
        return divide(new Vec2d(x, y));
    }

    public Vec2d divide(double i) {
        return divide(i, i);
    }

    public Vec2d negate() {
        x = -x;
        y = -y;
        return this;
    }

    public Vec2d vectorTo(Vec2d dest) {
        return new Vec2d(dest.x - this.x, dest.y - this.y);
    }

    public Vec2i toInt() {
        return new Vec2i(x, y);
    }
}
