package io.github.itzispyder.dotchaos.fun.object;

import io.github.itzispyder.dotchaos.fun.FunObject;
import io.github.itzispyder.dotchaos.gui.Window;
import io.github.itzispyder.dotchaos.util.Randomizer;
import io.github.itzispyder.dotchaos.util.math.MathUtils;
import io.github.itzispyder.dotchaos.util.math.Vec2d;

import java.awt.*;

public class Bead extends FunObject {

    public final Randomizer random = new Randomizer();
    public Color color;
    public Vec2d velocity;

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
        setRadius(random.getRandomInt(10, 69));
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
    }

    @Override
    public void onTick() {
        Rectangle rect = Window.DEFAULT_BOUNDS;
        x = (int)MathUtils.clamp(x + velocity.x, rect.x, rect.x + rect.width);
        y = (int)MathUtils.clamp(y + -velocity.y, rect.y, rect.y + rect.height);

        velocity.y -= getRadius() / 200.0;
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
        return getPos().distanceTo(other.getPos()) < (getRadius() + other.getRadius());
    }

    public void flickAgainst(Bead other) {
        if (other.x + other.getRadius() > this.x + this.getRadius()) {
            other.velocity.x++;
            this.velocity.x--;
        }
        else {
            other.velocity.x--;
            this.velocity.x++;
        }
    }

    public boolean flickAgainst(Rectangle bounds) {
        boolean bounced = false;
        if (x <= bounds.x) {
            velocity.x++;
            bounced = true;
        }
        if (x + width >= bounds.x + bounds.width) {
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
}
