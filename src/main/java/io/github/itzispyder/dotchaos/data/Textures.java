package io.github.itzispyder.dotchaos.data;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Textures {

    public static class Icons {
        public static final BufferedImage BULLET_DENT = register("textures/icons/bullet_dent.png");
        public static final BufferedImage BLAST = register("textures/icons/blast.png");
    }

    public static BufferedImage register(String path) {
        try {
            InputStream resource = ResourceFinder.read(path);
            if (resource == null) {
                return null;
            }

            BufferedImage image = ImageIO.read(resource);
            resource.close();
            return image;
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
