package io.github.itzispyder.dotchaos.util.math;

public final class Vector3d {

    public double x, y, z;

    public Vector3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3d() {
        this(0, 0, 0);
    }

    public double distanceTo(Vector3d other) {
        double x = other.x - this.x;
        double y = other.y - this.y;
        double z = other.z - this.z;
        return Math.sqrt(x * x + y * y + z * z);
    }

    public double length() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public double lengthSquared() {
        return x * x + y * y + z * z;
    }

    public Vector3d normalize() {
        double d = length();
        x /= d;
        y /= d;
        z /= d;
        return this;
    }

    public Vector3d multiply(Vector3d other) {
        x *= other.x;
        y *= other.y;
        z *= other.z;
        return this;
    }

    public Vector3d multiply(double x, double y, double z) {
        return multiply(new Vector3d(x, y, z));
    }

    public Vector3d multiply(double i) {
        return multiply(i, i, i);
    }

    public Vector3d subtract(Vector3d other) {
        x -= other.x;
        y -= other.y;
        z -= other.z;
        return this;
    }

    public Vector3d subtract(double x, double y, double z) {
        return subtract(new Vector3d(x, y, z));
    }

    public Vector3d subtract(double i) {
        return subtract(i, i, i);
    }

    public Vector3d add(Vector3d other) {
        x += other.x;
        y += other.y;
        z += other.z;
        return this;
    }

    public Vector3d add(double x, double y, double z) {
        return add(new Vector3d(x, y, z));
    }

    public Vector3d add(double i) {
        return add(i, i, i);
    }

    public Vector3d divide(Vector3d other) {
        x /= other.x;
        y /= other.y;
        z /= other.z;
        return this;
    }

    public Vector3d divide(double x, double y, double z) {
        return divide(new Vector3d(x, y, z));
    }

    public Vector3d divide(double i) {
        return divide(i, i, i);
    }

    public Vector3d negate() {
        x = -x;
        y = -y;
        z = -z;
        return this;
    }

    public Vector3d vectorTo(Vector3d dest) {
        return new Vector3d(dest.x - this.x, dest.y - this.y, dest.z - this.z);
    }

    public Vector3i toInt() {
        return new Vector3i((int)x, (int)y, (int)z);
    }

    @Override
    public String toString() {
        return "{x=%s, y=%s, z=%s}".formatted(x, y, z);
    }
}
