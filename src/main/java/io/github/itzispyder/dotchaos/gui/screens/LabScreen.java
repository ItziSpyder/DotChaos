package io.github.itzispyder.dotchaos.gui.screens;

import io.github.itzispyder.dotchaos.Main;
import io.github.itzispyder.dotchaos.data.Textures;
import io.github.itzispyder.dotchaos.fun.object.Bead;
import io.github.itzispyder.dotchaos.fun.object.BulletDent;
import io.github.itzispyder.dotchaos.gui.Screen;
import io.github.itzispyder.dotchaos.gui.Window;
import io.github.itzispyder.dotchaos.util.Randomizer;

import java.awt.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LabScreen extends Screen {

    public final ConcurrentLinkedQueue<Bead> beads = new ConcurrentLinkedQueue<>();
    public final ConcurrentLinkedQueue<BulletDent> dents = new ConcurrentLinkedQueue<>();
    public int mouseX, mouseY;
    public long lastShot;
    public final Randomizer random = new Randomizer();
    public int beadTimer;

    public LabScreen() {

    }

    @Override
    public void onRender(Graphics2D g) {
        renderBackground(g);

        dents.forEach(d -> d.onRender(g));
        beads.forEach(b -> b.onRender(g));

        renderTitleBar(g);
        renderMouse(g);
        renderWater(g);
    }

    public void renderMouse(Graphics2D g) {
        Point p = Main.window.getMousePosition();
        int x = mouseX = p.x;
        int y = mouseY = p.y;
        int r = 50;

        if (System.currentTimeMillis() - lastShot < 30L) {
            r = 100;
            double theta = random.getRandomDouble(0.0, 6.28);
            g.rotate(theta, x, y);
            g.drawImage(Textures.Icons.BLAST, x - r, y - r, 2 * r, 2 * r, null);
            g.rotate(-theta, x, y);
            r = 50;
        }

        g.setColor(new Color(224, 116, 116, 94));
        g.fillOval(x - r, y - r, r * 2, r * 2);
        r = 25;
        g.fillOval(x - r, y - r, r * 2, r * 2);
        r = 3;
        g.setColor(Color.RED);
        g.fillOval(x - r, y - r, r * 2, r * 2);
    }

    public void renderBackground(Graphics2D g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(getX(), getY(), getWidth(), getHeight());
        g.setColor(Color.DARK_GRAY.brighter());
        for (int x = getX(); x < getX() + getWidth(); x += 10) {
            g.drawLine(x, getY(), x, getY() + getHeight());
        }
        for (int y = getY(); y < getY() + getHeight(); y += 10) {
            g.drawLine(getX(), y, getX() + getWidth(), y);
        }
    }

    public void renderWater(Graphics2D g) {
        Rectangle b = Window.DEFAULT_BOUNDS;
        int h = 100;

        g.setColor(new Color(132, 157, 211, 94));
        g.fillRect(b.x, b.y + b.height - h, b.width, h);
        h -= 10;
        g.fillRect(b.x, b.y + b.height - h, b.width, h);
        h -= 10;
        g.fillRect(b.x, b.y + b.height - h, b.width, h);
        h -= 10;

        g.fillRect(b.x, b.y + b.height - h, b.width, h);
    }

    public void renderTitleBar(Graphics2D g) {
        g.setColor(Color.GRAY);
        g.fillRect(getX(), getY(), getWidth(), 50);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Impact", Font.BOLD, 30));
        g.drawString("ItziSpyder's Lab", 10, 40);

        String info = "beads: %s,   memory: %s".formatted(beads.size(), Main.getMemPercentage()) + "%";
        g.setColor(Color.LIGHT_GRAY);
        g.setFont(new Font("Impact", Font.PLAIN, 20));
        g.drawString(info, 250, 40);
    }

    @Override
    public void tick() {
        dents.removeIf(dent -> System.currentTimeMillis() > dent.destroyAt);

        for (Bead bead : beads) {
            if (System.currentTimeMillis() > bead.destroyAt) {
                beads.remove(bead);
            }
            checkCollision(bead);
            bead.onTick();
        }

        if (beadTimer++ >= 10) {
            beads.add(new Bead(0));
            beadTimer = 0;
        }
    }

    public void checkCollision(Bead bead) {
        bead.flickAgainst(Window.DEFAULT_BOUNDS);
        shootGun();

        for (Bead other : beads) {
            if (other.overlaps(bead)) {
                other.flickAgainst(bead);
                break;
            }
        }
    }

    public void shootGun() {
        for (Bead bead : beads) {
            int r = bead.getRadius();
            if (mouseX > bead.x - r && mouseX < bead.x + r && mouseY > bead.y - r && mouseY < bead.y + r) {
                beads.remove(bead);
                dents.add(new BulletDent(mouseX, mouseY));
                lastShot = System.currentTimeMillis();
                break;
            }
        }

    }
}
