package io.github.itzispyder.dotchaos.util.math;

import java.awt.*;

public class MathUtils {

    public static int clamp(int val, int min, int max) {
        return Math.max(Math.min(max, val), min);
    }

    public static double clamp(double val, double min, double max) {
        return Math.max(Math.min(max, val), min);
    }

    public static double range(double percentage, double min, double max) {
        min = clamp(min, min, max);
        max = clamp(max, min, max);
        return (min - max) * clamp(percentage, 0.0, 100.0);
    }

    public static Color clampColor(int r, int g, int b, int a) {
        return new Color(clamp(r, 0, 255), clamp(g, 0, 255), clamp(b, 0, 255), clamp(a, 0, 255));
    }

    public static Color clampColor(int r, int g, int b) {
        return clampColor(r, g, b, 255);
    }

    public static int clampX(int x, Rectangle bounds) {
        return clamp(x, bounds.x, bounds.x + bounds.width);
    }

    public static int clampY(int y, Rectangle bounds) {
        return clamp(y, bounds.y, bounds.y + bounds.height);
    }

    public static Color getColorInRatio(double ratio, Color from, Color to) {
        int red = (int)Math.abs((from.getRed() * ratio) + (to.getRed() * (1 - ratio)));
        int green = (int)Math.abs((from.getGreen() * ratio) + (to.getGreen() * (1 - ratio)));
        int blue = (int)Math.abs((from.getBlue() * ratio) + (to.getBlue() * (1 - ratio)));
        int alpha = (int)Math.abs((from.getAlpha() * ratio) + (to.getAlpha() * (1 - ratio)));
        return clampColor(red, green, blue, alpha);
    }
    
    public static Vec2i reduce(Vec2i vec) {
        if (vec.x != 0) {
            vec.x = vec.x + (vec.x > 0 ? -1 : 1);
        }
        if (vec.y != 0) {
            vec.y = vec.y + (vec.y > 0 ? -1 : 1);
        }
        return vec;
    }

    public static Vec2d reduce(Vec2d vec) {
        return reduce(vec.toInt()).toDouble();
    }
}
