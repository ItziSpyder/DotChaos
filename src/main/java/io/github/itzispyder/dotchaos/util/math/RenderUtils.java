package io.github.itzispyder.dotchaos.util.math;

import java.awt.*;

public class RenderUtils {

    /**
     *
     * @param g graphics instance
     * @param x circle center x
     * @param y circle center y
     * @param rS radius start
     * @param rE radius end
     * @param sm smoothness 0.0F - 1.0F percentage
     * @param c color
     */
    public static void drawRadiusGradient(Graphics2D g, int x, int y, int rS, int rE, float sm, Color c) {
        if (rS >= rE) {
            throw new IllegalArgumentException("radius start cannot be greater than or equal to radius end");
        }
        float f = 10 * sm;
        c = new Color(c.getRed(), c.getGreen(), c.getBlue(), 10);

        g.setColor(c);
        for (int i = rS; i <= rE; i += f) {
            g.fillOval(x - i, y - i, i * 2, i * 2);
        }
    }

    public static void drawRadiusGradient(Graphics2D g, int x, int y, int rS, int rE, Color c) {
        drawRadiusGradient(g, x, y, rS, rE, 0.4F, c);
    }
}
