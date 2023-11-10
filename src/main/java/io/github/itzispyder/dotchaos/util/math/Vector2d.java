package io.github.itzispyder.dotchaos.util.math;

public final class Vector2d {

    public double x, y;

    public Vector2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2d() {
        this(0, 0);
    }

    public double distanceTo(Vector2d other) {
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

    public Vector2d normalize() {
        double d = length();
        x /= d;
        y /= d;
        return this;
    }

    public Vector2d multiply(Vector2d other) {
        x *= other.x;
        y *= other.y;
        return this;
    }

    public Vector2d multiply(double x, double y) {
        return multiply(new Vector2d(x, y));
    }

    public Vector2d multiply(double i) {
        return multiply(i, i);
    }

    public Vector2d subtract(Vector2d other) {
        x -= other.x;
        y -= other.y;
        return this;
    }

    public Vector2d subtract(double x, double y) {
        return subtract(new Vector2d(x, y));
    }

    public Vector2d subtract(double i) {
        return subtract(i, i);
    }

    public Vector2d add(Vector2d other) {
        x += other.x;
        y += other.y;
        return this;
    }

    public Vector2d add(double x, double y) {
        return add(new Vector2d(x, y));
    }

    public Vector2d add(double i) {
        return add(i, i);
    }

    public Vector2d divide(Vector2d other) {
        x /= other.x;
        y /= other.y;
        return this;
    }

    public Vector2d divide(double x, double y) {
        return divide(new Vector2d(x, y));
    }

    public Vector2d divide(double i) {
        return divide(i, i);
    }

    public Vector2d negate() {
        x = -x;
        y = -y;
        return this;
    }

    public Vector2d vectorTo(Vector2d dest) {
        return new Vector2d(dest.x - this.x, dest.y - this.y);
    }

    public Vector2i toInt() {
        return new Vector2i(x, y);
    }

    @Override
    public String toString() {
        return "{x=%s, y=%s}".formatted(x, y);
    }
}
