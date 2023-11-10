package io.github.itzispyder.dotchaos.gui.screens.lab;

import io.github.itzispyder.dotchaos.Main;
import io.github.itzispyder.dotchaos.gui.Screen;
import io.github.itzispyder.dotchaos.util.Timer;
import io.github.itzispyder.dotchaos.util.math.MathUtils;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class GameOverScreen extends Screen {

    public static final Set<Timer.End> pastRecords = new HashSet<>();
    public BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR);
    public Color overlay = new Color(0, 0, 0, 0);
    public final Timer.End finishTime;

    public GameOverScreen(Timer.End finishTime) {
        this.finishTime = finishTime;
        try {
            Robot robot = new Robot();
            Rectangle r = Main.window.getBounds();
            Insets i = Main.window.getInsets();
            r.x += i.left;
            r.y += i.top;
            r.width -= i.left + i.right;
            r.height -= i.top + i.bottom;
            image = robot.createScreenCapture(r);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onRender(Graphics2D g) {
        renderBackground(g);
        renderDetails(g);
    }

    public void renderBackground(Graphics2D g) {
        g.drawImage(image, getX(), getY(), getWidth(), getHeight(), null);
        g.setColor(overlay);
        g.fillRect(getX(), getY(), getWidth(), getHeight());
    }

    public void renderDetails(Graphics2D g) {
        g.setColor(Color.GRAY);
        g.setFont(new Font("Impact", Font.BOLD, 100));

        String info = "Game Over!";
        Rectangle2D r = g.getFont().getStringBounds(info, g.getFontRenderContext());
        int x = (int)(getWidth() / 2 - r.getWidth() / 2);
        int y = getHeight() / 3;
        g.drawString(info, x, y);

        g.setColor(g.getColor().darker());
        g.setFont(new Font("Impact", Font.PLAIN, 50));
        info = "Your Survived:    %s".formatted(finishTime.getStampPrecise());
        r = g.getFont().getStringBounds(info, g.getFontRenderContext());
        x = (int)(getWidth() / 2 - r.getWidth() / 2);
        y += 100;
        g.drawString(info, x, y);

        info = "Best:    %s".formatted(pastRecords.stream().max(Comparator.comparing(Timer.End::timePassed)).orElse(Timer.start().end()).getStampPrecise());
        r = g.getFont().getStringBounds(info, g.getFontRenderContext());
        x = (int)(getWidth() / 2 - r.getWidth() / 2);
        y += 50;
        g.drawString(info, x, y);

        g.setFont(new Font("Impact", Font.PLAIN, 30));
        info = ">   click anywhere to continue   <";
        r = g.getFont().getStringBounds(info, g.getFontRenderContext());
        x = (int)(getWidth() / 2 - r.getWidth() / 2);
        y += 100;
        g.drawString(info, x, y);
    }

    @Override
    public void tick() {
        overlay = new Color(overlay.getRed(), overlay.getGreen(), overlay.getBlue(), MathUtils.clamp(overlay.getAlpha() + 5, 0, 200));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Main.window.setCurrentScreen(new LabScreen());
    }
}
