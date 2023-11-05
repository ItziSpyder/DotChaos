package io.github.itzispyder.dotchaos.fun.object;

import io.github.itzispyder.dotchaos.Main;
import io.github.itzispyder.dotchaos.fun.FunObject;
import io.github.itzispyder.dotchaos.gui.screens.LabScreen;
import io.github.itzispyder.dotchaos.util.Randomizer;
import io.github.itzispyder.dotchaos.util.math.MathUtils;
import io.github.itzispyder.dotchaos.util.math.Vec2d;

import java.awt.*;

public class Bead extends FunObject {

    public static final Randomizer random = new Randomizer();
    public static final long stayTime = 20_000L;
    public final long createdAt = System.currentTimeMillis();
    public final long destroyAt = createdAt + stayTime;
    public static final Color to = new Color(64, 64, 64, 255);
    public final Color from;
    public Color color;
    public Vec2d velocity;

    public Bead(int x, int y, int size, Color color) {
        super(x, y, size, size);
        this.color = color;
        this.velocity = new Vec2d(0, 0);
        this.from = color;
    }

    public Bead(int x, int y, int size) {
        this(x, y, size, new Color(random.getRandomInt(20, 235), random.getRandomInt(20, 235), random.getRandomInt(20, 235)));
    }

    public Bead(int x, int y) {
        this(x, y, random.getRandomInt(100, 180));
    }

    public Bead(int y) {
        this(0, y);
        setX(random.getRandomInt(getRadius(), (int)Main.window.getWidth() - getRadius()));
    }

    @Override
    public void onRender(Graphics2D g) {
        int r = getRadius();
        int rSmaller = (r / 4 * 3);
        Stroke s = g.getStroke();
        g.setColor(getColor());
        g.fillOval(x - r, y - r, width, height);

        g.setColor(getColor().brighter());
        g.setStroke(new BasicStroke(rSmaller / 4.0F));
        g.drawArc(x - rSmaller, y - rSmaller, 2 * rSmaller, 2 * rSmaller, 90, 45);
        g.setStroke(s);
    }

    public void renderBloom(Graphics2D g) {
        int r = getRadius();
        Color c = getColor().brighter().brighter();

        g.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), 15));
        for (int i = r + 20; i >= r; i -= 3) {
            g.fillOval(x - i, y - i, i * 2, i * 2);
        }
    }

    @Override
    public void onTick() {
        Rectangle rect = Main.window.currentScreen.getBounds();
        int r = getRadius();

        x += velocity.x;
        y = (int)MathUtils.clamp(y + -velocity.y, rect.y, rect.y + rect.height - r);
        velocity.y -= r / 100.0;
        velocity.x += velocity.x != 0 ? (velocity.x < 0 ? 1 : -1) : (0);
        velocity.x = (int)velocity.x;

        double ratio = (destroyAt - System.currentTimeMillis()) / (double)stayTime;
        setColor(MathUtils.getColorInRatio(ratio, from, to));
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getRadius() {
        return width / 2;
    }

    public void setRadius(int radius) {
        setWidth(radius * 2);
        setHeight(radius * 2);
    }

    public void setVelocity(Vec2d velocity) {
        this.velocity = velocity;
    }

    public Vec2d getVelocity() {
        return velocity;
    }

    public boolean overlaps(Bead other) {
        return other != this && getPos().distanceTo(other.getPos()) <= (getRadius() + other.getRadius());
    }

    public boolean overlapsAny(LabScreen lab) {
        return lab.beads.stream().anyMatch(this::overlaps);
    }

    public boolean sittingOn(Bead other) {
        return this.y + this.getRadius() >= other.y - other.getRadius() && overlaps(other);
    }

    public boolean sittingOnAny(LabScreen lab) {
        return lab.beads.stream().anyMatch(this::sittingOn);
    }

    public void flickAgainst(Bead other) {
        double bounceThis = 100.0 / this.getRadius();
        double bounceOther = 100.0 / other.getRadius();

        if (other.x > this.x) {
            other.velocity.x += bounceOther;
            this.velocity.x -= bounceThis;
        }
        else {
            other.velocity.x -= bounceOther;
            this.velocity.x += bounceThis;
        }

        if (Main.window.currentScreen instanceof LabScreen lab && sittingOnAny(lab)) {
            this.velocity.y = 0;
        }
    }

    public void tryExplodeInLab(LabScreen lab) {
        int r = getRadius();
        int max = 10;

        if (r / 2 >= 30) {
            for (int i = 0; i < 2; i++) {
                Bead bead = new Bead(x, y - r, width / 4 * 3, color);
                bead.setColor(this.from);
                bead.velocity.add(random.getRandomDouble(-max, max), random.getRandomDouble(-max, max));
                lab.beads.add(bead);
            }
        }
    }
}
