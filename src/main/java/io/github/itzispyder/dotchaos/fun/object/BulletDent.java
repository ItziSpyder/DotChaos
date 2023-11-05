package io.github.itzispyder.dotchaos.fun.object;

import io.github.itzispyder.dotchaos.data.Textures;
import io.github.itzispyder.dotchaos.fun.FunObject;

import java.awt.*;

public class BulletDent extends FunObject {

    public final long createdAt = System.currentTimeMillis();
    public final long destroyAt = createdAt + 2000L;

    public BulletDent(int x, int y) {
        this(x, y, 30);
    }

    public BulletDent(int x, int y, int size) {
        super(x, y, size, size);
    }

    @Override
    public void onRender(Graphics2D g) {
        int r = width / 2;
        g.drawImage(Textures.Icons.BULLET_DENT, x - r, y - r, width, height, null);
    }
}
