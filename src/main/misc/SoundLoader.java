package main.misc;

import processing.core.PApplet;
import processing.sound.SoundFile;

import static main.Main.soundsH;

public class SoundLoader {

    public SoundLoader() {}

    public static void loadSounds(PApplet p) {
        soundsH.put("woodDamage", new SoundFile(p, "sounds/woodDamage.wav"));
        soundsH.put("woodBreak", new SoundFile(p, "sounds/woodBreak.wav"));
        soundsH.put("squish", new SoundFile(p, "sounds/squish.wav"));
        soundsH.put("squash", new SoundFile(p, "sounds/squash.wav"));
    }
}
