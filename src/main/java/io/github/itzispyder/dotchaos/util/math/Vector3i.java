package io.github.itzispyder.dotchaos.util.math;

public final class Vector3i {

    public int x, y, z;

    public Vector3i(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3i() {
        this(0, 0, 0);
    }

    public double distanceTo(Vector3i other) {
        int x = other.x - this.x;
        int y = other.y - this.y;
        int z = other.z - this.z;
        return Math.sqrt(x * x + y * y + z * z);
    }

    public double length() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public int lengthSquared() {
        return x * x + y * y + z * z;
    }

    public Vector3i normalize() {
        double d = length();
        x /= d;
        y /= d;
        z /= d;
        return this;
    }

    public Vector3i multiply(Vector3i other) {
        x *= other.x;
        y *= other.y;
        z *= other.z;
        return this;
    }

    public Vector3i multiply(int x, int y, int z) {
        return multiply(new Vector3i(x, y, z));
    }

    public Vector3i multiply(int i) {
        return multiply(i, i, i);
    }

    public Vector3i subtract(Vector3i other) {
        x -= other.x;
        y -= other.y;
        z -= other.z;
        return this;
    }

    public Vector3i subtract(int x, int y, int z) {
        return subtract(new Vector3i(x, y, z));
    }

    public Vector3i subtract(int i) {
        return subtract(i, i, i);
    }

    public Vector3i add(Vector3i other) {
        x += other.x;
        y += other.y;
        z += other.z;
        return this;
    }

    public Vector3i add(int x, int y, int z) {
        return add(new Vector3i(x, y, z));
    }

    public Vector3i add(int i) {
        return add(i, i, i);
    }

    public Vector3i divide(Vector3i other) {
        x /= other.x;
        y /= other.y;
        z /= other.z;
        return this;
    }

    public Vector3i divide(int x, int y, int z) {
        return divide(new Vector3i(x, y, z));
    }

    public Vector3i divide(int i) {
        return divide(i, i, i);
    }

    public Vector3i negate() {
        x = -x;
        y = -y;
        z = -z;
        return this;
    }

    public Vector3i vectorTo(Vector3i dest) {
        return new Vector3i(dest.x - this.x, dest.y - this.y, dest.z - this.z);
    }

    public Vector3d toDouble() {
        return new Vector3d(x, y, z);
    }

    @Override
    public String toString() {
        return "{x=%s, y=%s, z=%s}".formatted(x, y, z);
    }
}
