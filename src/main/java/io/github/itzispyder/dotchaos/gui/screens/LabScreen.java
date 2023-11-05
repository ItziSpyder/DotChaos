package io.github.itzispyder.dotchaos.gui.screens;

import io.github.itzispyder.dotchaos.Main;
import io.github.itzispyder.dotchaos.fun.object.Bead;
import io.github.itzispyder.dotchaos.gui.Screen;
import io.github.itzispyder.dotchaos.gui.Window;

import java.awt.*;
import java.util.ConcurrentModificationException;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LabScreen extends Screen {

    public final ConcurrentLinkedQueue<Bead> beads = new ConcurrentLinkedQueue<>() {{
        /*
        for (int i = 0; i < 10; i++) {
            this.add(new Bead(0));
        }
         */
    }};

    public LabScreen() {

    }

    @Override
    public void onRender(Graphics2D g) {
        renderBackground(g);
        beads.forEach(b -> b.onRender(g));

        renderTitleBar(g);
        renderMouse(g);
        renderWater(g);
    }

    public void renderMouse(Graphics2D g) {
        Point p = Main.window.getMousePosition();
        int x = p.x;
        int y = p.y;
        int r = 50;

        g.setColor(new Color(0x30FFFFFF, true));
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
        int h = 69;
        g.setColor(new Color(132, 157, 211, 140));
        g.fillRect(b.x, b.y + b.height - h, b.width, h);
        h = 64;
        g.fillRect(b.x, b.y + b.height - h, b.width, h);
        h = 50;
        g.fillRect(b.x, b.y + b.height - h, b.width, h);
        h = 42;
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
        g.drawString(info, Main.window.getWidth() / 3, 40);
    }

    @Override
    public void tick() {
        for (Bead bead : beads) {
            checkCollision(bead);
            bead.onTick();
        }

        try {
            beads.add(new Bead(0));
        }
        catch (ConcurrentModificationException ignore) {}
    }

    public void checkCollision(Bead bead) {
        bead.flickAgainst(Window.DEFAULT_BOUNDS);
        for (Bead other : beads) {
            if (other.overlaps(bead)) {
                other.flickAgainst(bead);
                break;
            }
        }
    }
}
