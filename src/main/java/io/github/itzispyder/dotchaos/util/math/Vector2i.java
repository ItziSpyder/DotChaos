package io.github.itzispyder.dotchaos.util.math;

public final class Vector2i {

    public int x, y;

    public Vector2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector2i(double x, double y) {
        this((int)x, (int)y);
    }

    public Vector2i() {
        this(0, 0);
    }

    public double distanceTo(Vector2i other) {
        int x = other.x - this.x;
        int y = other.y - this.y;
        return Math.sqrt(x * x + y * y);
    }

    public double length() {
        return Math.sqrt(x * x + y * y);
    }

    public double lengthSquared() {
        return x * x + y * y;
    }

    public Vector2i normalize() {
        double d = length();
        x /= d;
        y /= d;
        return this;
    }

    public Vector2i multiply(Vector2i other) {
        x *= other.x;
        y *= other.y;
        return this;
    }

    public Vector2i multiply(int x, int y) {
        return multiply(new Vector2i(x, y));
    }

    public Vector2i multiply(int i) {
        return multiply(i, i);
    }

    public Vector2i subtract(Vector2i other) {
        x -= other.x;
        y -= other.y;
        return this;
    }

    public Vector2i subtract(int x, int y) {
        return subtract(new Vector2i(x, y));
    }

    public Vector2i subtract(int i) {
        return subtract(i, i);
    }

    public Vector2i add(Vector2i other) {
        x += other.x;
        y += other.y;
        return this;
    }

    public Vector2i add(int x, int y) {
        return add(new Vector2i(x, y));
    }

    public Vector2i add(int i) {
        return add(i, i);
    }

    public Vector2i divide(Vector2i other) {
        x /= other.x;
        y /= other.y;
        return this;
    }

    public Vector2i divide(int x, int y) {
        return divide(new Vector2i(x, y));
    }

    public Vector2i divide(int i) {
        return divide(i, i);
    }

    public Vector2i negate() {
        x = -x;
        y = -y;
        return this;
    }

    public Vector2i vectorTo(Vector2i dest) {
        return new Vector2i(dest.x - this.x, dest.y - this.y);
    }

    public Vector2d toDouble() {
        return new Vector2d(x, y);
    }

    @Override
    public String toString() {
        return "{x=%s, y=%s}".formatted(x, y);
    }
}
