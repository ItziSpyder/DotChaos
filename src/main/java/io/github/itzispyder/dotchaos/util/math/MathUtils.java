package io.github.itzispyder.dotchaos.util.math;

public class MathUtils {

    public static int clamp(int val, int min, int max) {
        return Math.max(Math.min(max, val), min);
    }

    public static double clamp(double val, double min, double max) {
        return Math.max(Math.min(max, val), min);
    }
}
