package io.github.itzispyder.dotchaos.fun.object;

import io.github.itzispyder.dotchaos.Main;
import io.github.itzispyder.dotchaos.fun.FunObject;
import io.github.itzispyder.dotchaos.gui.Window;
import io.github.itzispyder.dotchaos.gui.screens.LabScreen;
import io.github.itzispyder.dotchaos.util.Randomizer;
import io.github.itzispyder.dotchaos.util.math.MathUtils;
import io.github.itzispyder.dotchaos.util.math.Vec2d;

import java.awt.*;

public class Bead extends FunObject {

    public final Randomizer random = new Randomizer();
    public Color color;
    public Vec2d velocity;
    public final long createdAt = System.currentTimeMillis();
    public final long destroyAt = createdAt + 120_000L;

    public Bead(int x, int y, int size, Color color) {
        super(x, y, size, size);
        this.color = color;
        this.velocity = new Vec2d(0, 0);
    }

    public Bead(int x, int y, int size) {
        this(x, y, size, Color.BLACK);
        setColor(new Color(random.getRandomInt(0, 255), random.getRandomInt(0, 255), random.getRandomInt(0, 255)));
    }

    public Bead(int x, int y) {
        this(x, y, 10);
        setRadius(random.getRandomInt(20, 69));
    }

    public Bead(int y) {
        this(0, y);
        setX(random.getRandomInt(getRadius(), (int)Window.DEFAULT_BOUNDS.getWidth() - getRadius()));
    }

    @Override
    public void onRender(Graphics2D g) {
        int r = getRadius();
        g.setColor(getColor());
        g.fillOval(x - r, y - r, width, height);
        //g.drawRect(x - r, y - r, r * 2, r * 2);
    }

    @Override
    public void onTick() {
        Rectangle rect = Window.DEFAULT_BOUNDS;
        int r = getRadius();

        x += velocity.x;
        y = (int)MathUtils.clamp(y + -velocity.y, rect.y, rect.y + rect.height - r);

        velocity.y -= r / 100.0;
        if (velocity.x != 0) {
            if (velocity.x < 0) {
                velocity.x++;
            }
            else {
                velocity.x--;
            }
        }
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

    public boolean onTopOf(Bead other) {
        return this.y + this.getRadius() >= other.y - other.getRadius() && overlaps(other);
    }

    public boolean sittingOnAny(LabScreen lab) {
        return lab.beads.stream().anyMatch(this::onTopOf);
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

        if (r / 2 >= 10) {
            for (int i = 0; i < 2; i++) {
                Bead bead = new Bead(x, y - r, width / 4 * 3, color);
                bead.velocity.add(random.getRandomDouble(-max, max), random.getRandomDouble(-max, max));
                lab.beads.add(bead);
            }
        }
    }

    /*
    public boolean flickAgainst(Rectangle bounds) {
        boolean bounced = false;
        if (x <= bounds.x) {
            velocity.x++;
            bounced = true;
        }
        if (x + getRadius() >= bounds.x + bounds.width) {
            velocity.x--;
            bounced = true;
        }

        if (y <= bounds.y + 50) {
            velocity.y--;
            bounced = true;
        }
        if (y + height >= bounds.y + bounds.height) {
            if (velocity.y < 0) {
                velocity.y = 0;
            }
            velocity.y++;
            bounced = true;
        }

        return bounced;
    }
     */
}
