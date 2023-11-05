package io.github.itzispyder.dotchaos.gui.screens;

import io.github.itzispyder.dotchaos.Main;
import io.github.itzispyder.dotchaos.data.Textures;
import io.github.itzispyder.dotchaos.fun.object.Bead;
import io.github.itzispyder.dotchaos.fun.object.BulletDent;
import io.github.itzispyder.dotchaos.gui.Screen;
import io.github.itzispyder.dotchaos.util.Randomizer;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LabScreen extends Screen {

    public final ConcurrentLinkedQueue<Bead> beads = new ConcurrentLinkedQueue<>();
    public final ConcurrentLinkedQueue<BulletDent> dents = new ConcurrentLinkedQueue<>();
    public int mouseX, mouseY, gridX, gridY, gridThreshold = 50;
    public long lastShot;
    public final Randomizer random = new Randomizer();
    public int beadTimer;

    public LabScreen() {

    }

    @Override
    public void onRender(Graphics2D g) {
        renderBackground(g);

        dents.forEach(d -> d.render(g));
        beads.forEach(b -> b.render(g));

        renderTitleBar(g);
        renderMouse(g);
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

        Rectangle r = getBounds();
        r.x -= gridThreshold;
        r.y -= gridThreshold;
        r.width += gridThreshold * 2;
        r.height += gridThreshold * 2;

        for (int x = r.x + gridX; x < r.x + r.width + gridX; x += 10) {
            g.drawLine(x, r.y, x, r.y + r.height);
        }
        for (int y = r.y + gridY; y < r.y + r.height + gridY; y += 10) {
            g.drawLine(r.x, y, r.x + r.width, y);
        }
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

        if (beadTimer++ >= 40) {
            beads.add(new Bead(-50));
            beadTimer = 0;
        }
    }

    public void checkCollision(Bead bead) {
        //bead.flickAgainst(Window.DEFAULT_BOUNDS);
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
                bead.tryExplodeInLab(this);
                dents.add(new BulletDent(mouseX, mouseY));
                lastShot = System.currentTimeMillis();
                break;
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int dx = e.getX() - Main.window.getInsets().left - mouseX;
        int dy = e.getY() - Main.window.getInsets().top - mouseY;
        beads.forEach(d -> d.x += dx);

        if (dx != 0) {
            if (gridX >= gridThreshold || gridX <= -gridThreshold) {
                gridX = 0;
            }
            int del = dx > 0 ? 1 : -1;
            gridX += del;
            dents.forEach(d -> d.x += del);
        }
        if (dy != 0) {
            if (gridY >= gridThreshold || gridY <= -gridThreshold) {
                gridY = 0;
            }
            int del = dy > 0 ? 1 : -1;
            gridY += del;
            dents.forEach(d -> d.y += del);
        }
    }
}
