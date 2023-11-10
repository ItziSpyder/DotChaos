package io.github.itzispyder.dotchaos.gui.screens;

import io.github.itzispyder.dotchaos.Main;
import io.github.itzispyder.dotchaos.data.Sounds;
import io.github.itzispyder.dotchaos.data.Textures;
import io.github.itzispyder.dotchaos.gui.Screen;
import io.github.itzispyder.dotchaos.gui.widgets.lab.BeadWidget;
import io.github.itzispyder.dotchaos.gui.widgets.lab.BulletDentWidget;
import io.github.itzispyder.dotchaos.util.Randomizer;
import io.github.itzispyder.dotchaos.util.Timer;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LabScreen extends Screen {

    public static int score = 10;
    public final ConcurrentLinkedQueue<BeadWidget> beads = new ConcurrentLinkedQueue<>();
    public final ConcurrentLinkedQueue<BulletDentWidget> dents = new ConcurrentLinkedQueue<>();
    public int mouseX, mouseY, gridX, gridY, gridThreshold = 50;
    public long lastShot, lastDamage;
    public final Randomizer random = new Randomizer();
    public int beadTimer;
    public boolean mouseDown, neverMoved = true;
    public Timer timer = Timer.start();

    public LabScreen() {

    }

    @Override
    public void onRender(Graphics2D g) {
        renderBackground(g);

        dents.forEach(d -> d.render(g));

        renderScore(g);

        beads.forEach(b -> b.renderBloom(g));
        beads.forEach(b -> b.render(g));

        renderTitleBar(g);
        renderMouse(g);
    }

    public void renderMouse(Graphics2D g) {
        Point p = Main.window.getMousePosition();
        int x = mouseX = p.x;
        int y = mouseY = p.y;
        int r;

        if (System.currentTimeMillis() - lastShot < 30L) {
            r = 100;
            double theta = random.getRandomDouble(0.0, 6.28);
            g.rotate(theta, x, y);
            g.drawImage(Textures.Icons.BLAST, x - r, y - r, 2 * r, 2 * r, null);
            g.rotate(-theta, x, y);
        }

        if (mouseDown) {
            g.setColor(new Color(255, 255, 255, 10));
            for (int i = 50; i >= 20; i -= 4) {
                g.fillOval(x - i, y - i, i * 2, i * 2);
            }
        }
        else {
            g.setColor(new Color(231, 85, 85, 5));
            for (int i = 100; i >= 20; i -= 4) {
                g.fillOval(x - i, y - i, i * 2, i * 2);
            }
        }

        r = 3;
        g.setColor(Color.RED);
        g.fillOval(x - r, y - r, r * 2, r * 2);
    }

    public void renderBackground(Graphics2D g) {
        g.setColor(Color.DARK_GRAY.darker().darker());
        g.fillRect(getX(), getY(), getWidth() + 1, getHeight() + 1);
        g.setColor(Color.DARK_GRAY.darker());

        Rectangle r = getBounds();
        r.x -= gridThreshold;
        r.y -= gridThreshold;
        r.width += gridThreshold * 2;
        r.height += gridThreshold * 2;

        for (int x = r.x + gridX; x < r.x + r.width + gridX; x += 25) {
            g.drawLine(x, r.y, x, r.y + r.height);
        }
        for (int y = r.y + gridY; y < r.y + r.height + gridY; y += 25) {
            g.drawLine(r.x, y, r.x + r.width, y);
        }
    }

    public void renderTitleBar(Graphics2D g) {
        g.setColor(new Color(128, 128, 128, 150));
        g.fillRect(getX(), getY(), getWidth(), 50);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Impact", Font.BOLD, 30));
        g.drawString("ItziSpyder's Lab", 10, 40);

        String info;

        info = "beads: %s,   memory: %s".formatted(beads.size(), Main.getMemPercentage()) + "%";
        g.setColor(Color.LIGHT_GRAY);
        g.setFont(new Font("Impact", Font.PLAIN, 20));
        g.drawString(info, 250, 40);

        info = "Your score: %s".formatted(score);
        g.setColor(Color.WHITE);
        g.drawString(info, 500, 40);
    }

    public void renderScore(Graphics2D g) {
        g.setFont(new Font("Impact", Font.PLAIN, 100));

        String info = "Score: %s".formatted(score);
        Rectangle2D rect = g.getFont().getStringBounds(info, g.getFontRenderContext());
        int x = (int)(getWidth() / 2 - rect.getWidth() / 2);
        int y = getHeight() / 3;

        g.setColor(Color.BLACK);
        g.drawString(info, x + 20, y + 20);
        g.setColor(lastDamage + 500L >= System.currentTimeMillis() ? Color.RED : Color.DARK_GRAY);
        g.drawString(info, x, y);

        g.setFont(new Font("Impact", Font.PLAIN, 30));
        info = "Time:   %s".formatted(timer.end().getStamp(false, false, true, true, false));
        rect = g.getFont().getStringBounds(info, g.getFontRenderContext());
        x = (int)(getWidth() / 2 - rect.getWidth() / 2);
        y += 50;
        g.setColor(Color.BLACK);
        g.drawString(info, x + 20, y + 20);
        g.setColor(Color.DARK_GRAY);
        g.drawString(info, x, y);

        if (neverMoved) {
            g.setFont(new Font("Impact", Font.PLAIN, 40));
            info = "<    drag screen to navigate    >";
            rect = g.getFont().getStringBounds(info, g.getFontRenderContext());
            x = (int)(getWidth() / 2 - rect.getWidth() / 2);
            y += 50;
            g.setColor(Color.BLACK);
            g.drawString(info, x + 20, y + 20);
            g.setColor(Color.DARK_GRAY.brighter());
            g.drawString(info, x, y);
        }
    }

    @Override
    public void tick() {
        dents.removeIf(dent -> System.currentTimeMillis() > dent.destroyAt);

        for (BeadWidget bead : beads) {
            if (System.currentTimeMillis() > bead.destroyAt) {
                this.decrementScore(bead);
                beads.remove(bead);
            }
            checkCollision(bead);
            bead.onTick();
        }

        if (beadTimer++ >= 60) {
            beads.add(new BeadWidget(-400));
            beadTimer = 0;
        }
    }

    public void checkCollision(BeadWidget bead) {
        shootGun();

        for (BeadWidget other : beads) {
            if (other.overlaps(bead)) {
                other.flickAgainst(bead);
                break;
            }
        }
    }

    public void incrementScore(BeadWidget bead) {
        score += bead.getRadius();
    }

    public void decrementScore(BeadWidget bead) {
        Sounds.play(Sounds.DAMAGE);
        lastDamage = System.currentTimeMillis();
        score -= bead.getRadius() * 5;
        if (score <= 0) {
            score = 0;
            Timer.End end = timer.end();
            GameOverScreen.pastRecords.add(end);
            Main.window.setCurrentScreen(new GameOverScreen(end));
        }
    }

    public void shootGun() {
        for (BeadWidget bead : beads) {
            int r = bead.getRadius();
            if (mouseX > bead.x - r && mouseX < bead.x + r && mouseY > bead.y - r && mouseY < bead.y + r) {
                this.incrementScore(bead);
                beads.remove(bead);
                dents.add(new BulletDentWidget(mouseX, mouseY));
                lastShot = System.currentTimeMillis();

                bead.tryExplodeInLab(this);
                Sounds.play(Sounds.EXPLOSION);
                break;
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseDown = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseDown = false;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int dx = e.getX() - Main.window.getInsets().left - mouseX;
        int dy = e.getY() - Main.window.getInsets().top - mouseY;

        if (dx != 0) {
            if (gridX >= gridThreshold || gridX <= -gridThreshold) {
                gridX = 0;
            }
            int del = dx > 0 ? -1 : 1;
            gridX += del;
            dents.forEach(d -> d.x += del);
        }
        if (dy != 0) {
            if (gridY >= gridThreshold || gridY <= -gridThreshold) {
                gridY = 0;
            }
            int del = dy > 0 ? -1 : 1;
            gridY += del;
            dents.forEach(d -> d.y += del);
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
            neverMoved = false;
        }
        if (dy != 0) {
            if (gridY >= gridThreshold || gridY <= -gridThreshold) {
                gridY = 0;
            }
            int del = dy > 0 ? 1 : -1;
            gridY += del;
            dents.forEach(d -> d.y += del);
            neverMoved = false;
        }
    }
}
