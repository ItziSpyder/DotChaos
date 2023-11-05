package io.github.itzispyder.dotchaos.data;

import io.github.itzispyder.dotchaos.Main;

import java.io.InputStream;

public class ResourceFinder {

    public static InputStream read(String path) {
        try {
            ClassLoader loader = Main.class.getClassLoader();
            String location = "assets/dotchaos/" + path;
            return loader.getResourceAsStream(location);
        }
        catch (Exception ex) {
            return null;
        }
    }
}
