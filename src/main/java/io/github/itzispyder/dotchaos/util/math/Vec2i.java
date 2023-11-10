package io.github.itzispyder.dotchaos.util.math;

public final class Vec2i {

    public int x, y;

    public Vec2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vec2i(double x, double y) {
        this((int)x, (int)y);
    }

    public Vec2i() {
        this(0, 0);
    }

    public double distanceTo(Vec2i other) {
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

    public Vec2i normalize() {
        double d = length();
        x /= d;
        y /= d;
        return this;
    }

    public Vec2i multiply(Vec2i other) {
        x *= other.x;
        y *= other.y;
        return this;
    }

    public Vec2i multiply(int x, int y) {
        return multiply(new Vec2i(x, y));
    }

    public Vec2i multiply(int i) {
        return multiply(i, i);
    }

    public Vec2i subtract(Vec2i other) {
        x -= other.x;
        y -= other.y;
        return this;
    }

    public Vec2i subtract(int x, int y) {
        return subtract(new Vec2i(x, y));
    }

    public Vec2i subtract(int i) {
        return subtract(i, i);
    }

    public Vec2i add(Vec2i other) {
        x += other.x;
        y += other.y;
        return this;
    }

    public Vec2i add(int x, int y) {
        return add(new Vec2i(x, y));
    }

    public Vec2i add(int i) {
        return add(i, i);
    }

    public Vec2i divide(Vec2i other) {
        x /= other.x;
        y /= other.y;
        return this;
    }

    public Vec2i divide(int x, int y) {
        return divide(new Vec2i(x, y));
    }

    public Vec2i divide(int i) {
        return divide(i, i);
    }

    public Vec2i negate() {
        x = -x;
        y = -y;
        return this;
    }

    public Vec2i vectorTo(Vec2i dest) {
        return new Vec2i(dest.x - this.x, dest.y - this.y);
    }

    public Vec2d toDouble() {
        return new Vec2d(x, y);
    }

    @Override
    public String toString() {
        return "{x=%s, y=%s}".formatted(x, y);
    }
}
