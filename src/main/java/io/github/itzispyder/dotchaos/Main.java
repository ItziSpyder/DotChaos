package io.github.itzispyder.dotchaos;

import io.github.itzispyder.dotchaos.data.Textures;
import io.github.itzispyder.dotchaos.gui.Window;
import io.github.itzispyder.dotchaos.gui.screens.LabScreen;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {

    public static final AtomicBoolean gameLoopPaused = new AtomicBoolean(false);
    public static Window window = new Window("ItziSpyder's Lab");
    public static Thread renderThread;
    public static Thread gameThread;

    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "false");
        initGame();
        startGameLoop();
    }

    public static void initGame() {
        window.setResizable(true);
        window.setCurrentScreen(new LabScreen());
        window.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        window.setIconImage(Textures.Icons.BLAST);
        BufferedImage image = Textures.Icons.BLAST;
    }

    public static void startGameLoop() {
        renderThread = new Thread(() -> {
            try {
                while (true) {
                    if (!gameLoopPaused.get() && window.isFocused()) {
                        window.runOnCurrentScreen((window1, screen) -> screen.repaint());
                        // 60 frames per second
                        Thread.sleep(1000 / 60);
                    }
                }
            }
            catch (InterruptedException ex) {
                ex.printStackTrace();
                System.exit(-1);
            }
        }, "Render Thread");
        renderThread.start();

        gameThread = new Thread(() -> {
            try {
                while (true) {
                    if (!gameLoopPaused.get() && window.isFocused()) {
                        window.runOnCurrentScreen((window1, screen) -> screen.tick());
                        // 50 times per second
                        Thread.sleep(1000 / 50);
                    }
                }
            }
            catch (InterruptedException ex) {
                ex.printStackTrace();
                System.exit(-1);
            }
        }, "Game Thread");
        gameThread.start();
    }

    public static double getMemPercentage() {
        Runtime r = Runtime.getRuntime();
        double per = (double)(r.maxMemory() - r.freeMemory()) / r.maxMemory();
        return Math.floor(per * 10000) / 100;
    }
}