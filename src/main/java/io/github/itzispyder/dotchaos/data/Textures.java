package io.github.itzispyder.dotchaos.data;

import io.github.itzispyder.dotchaos.Main;

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
            String location = "assets/dotchaos/" + path;
            ClassLoader loader = Main.class.getClassLoader();

            if (loader == null) {
                throw new IllegalStateException("main class loader is null");
            }

            InputStream resource = loader.getResourceAsStream(location);

            if (resource == null) {
                throw new IllegalStateException("resource does not exist");
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

    public static void init() {

    }
}
