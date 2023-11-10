package io.github.itzispyder.dotchaos.gui.widgets.lab;

import io.github.itzispyder.dotchaos.data.Textures;
import io.github.itzispyder.dotchaos.gui.Widget;

import java.awt.*;

public class BulletDentWidget extends Widget {

    public final long createdAt = System.currentTimeMillis();
    public final long destroyAt = createdAt + 600_000L;

    public BulletDentWidget(int x, int y) {
        this(x, y, 30);
    }

    public BulletDentWidget(int x, int y, int size) {
        super(x, y, size, size);
    }

    @Override
    public void onRender(Graphics2D g) {
        int r = width / 2;
        g.drawImage(Textures.Icons.BULLET_DENT, x - r, y - r, width, height, null);
    }
}
