package io.github.itzispyder.dotchaos.data;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.InputStream;

public class Sounds {

    public static final String EXPLOSION = "sounds/explode.wav";
    public static final String POP = "sounds/pop.wav";
    public static final String DING = "sounds/ding.wav";

    public static void play(String path) {
        try {
            InputStream is = ResourceFinder.read(path);
            if (is == null) {
                return;
            }

            AudioInputStream ais = AudioSystem.getAudioInputStream(is);
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            clip.start();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
