package main.misc;

import processing.core.PApplet;
import processing.sound.SoundFile;

import static main.Main.soundsH;

public class SoundLoader {

    public SoundLoader() {}

    public static void loadSounds(PApplet p) {
        //walls
        soundsH.put("woodDamage", new SoundFile(p, "sounds/walls/woodDamage.wav"));
        soundsH.put("woodBreak", new SoundFile(p, "sounds/walls/woodBreak.wav"));
        //enemy
        soundsH.put("squish", new SoundFile(p, "sounds/enemies/squish.wav"));
        soundsH.put("squash", new SoundFile(p, "sounds/enemies/squash.wav"));
        soundsH.put("crunch", new SoundFile(p, "sounds/enemies/crunch.wav"));
        soundsH.put("leaves", new SoundFile(p, "sounds/enemies/leaves.wav"));
        soundsH.put("leavesImpact", new SoundFile(p, "sounds/enemies/leavesImpact.wav"));
        soundsH.put("bigLeaves", new SoundFile(p, "sounds/enemies/bigLeaves.wav"));
        soundsH.put("bigLeavesImpact", new SoundFile(p, "sounds/enemies/bigLeavesImpact.wav"));
    }
}
