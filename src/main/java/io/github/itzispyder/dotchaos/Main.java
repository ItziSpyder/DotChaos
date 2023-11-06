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
            while (true) {
                try {
                    runRenderThreadTick();
                    // 60 frames per second
                    Thread.sleep(1000 / 60);
                }
                catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }, "Render Thread");
        renderThread.start();

        gameThread = new Thread(() -> {
            while (true) {
                try {
                    runGameThreadTick();
                    // 50 times per second
                    Thread.sleep(1000 / 50);
                }
                catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }, "Game Thread");
        gameThread.start();
    }

    public static synchronized void runRenderThreadTick() {
        try {
            if (!gameLoopPaused.get() && window.isFocused()) {
                window.runOnCurrentScreen((window1, screen) -> screen.repaint());
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static synchronized void runGameThreadTick() {
        try {
            if (!gameLoopPaused.get() && window.isFocused()) {
                window.runOnCurrentScreen((window1, screen) -> screen.tick());
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static double getMemPercentage() {
        Runtime r = Runtime.getRuntime();
        double per = (double)(r.maxMemory() - r.freeMemory()) / r.maxMemory();
        return Math.floor(per * 10000) / 100;
    }
}