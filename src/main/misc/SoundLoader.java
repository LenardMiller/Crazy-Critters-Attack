package main.misc;

import processing.core.PApplet;
import processing.sound.SoundFile;

import static main.Main.soundsH;

public class SoundLoader {

    public SoundLoader() {}

    public static void loadSounds(PApplet p) {
        //enemy
        soundsH.put("squish", new SoundFile(p, "sounds/enemies/squish.wav"));
        soundsH.put("squash", new SoundFile(p, "sounds/enemies/squash.wav"));
        soundsH.put("crunch", new SoundFile(p, "sounds/enemies/crunch.wav"));
        soundsH.put("bigCrunch", new SoundFile(p, "sounds/enemies/bigCrunch.wav"));
        soundsH.put("leaves", new SoundFile(p, "sounds/enemies/leaves.wav"));
        soundsH.put("leavesImpact", new SoundFile(p, "sounds/enemies/leavesImpact.wav"));
        soundsH.put("bigLeaves", new SoundFile(p, "sounds/enemies/bigLeaves.wav"));
        soundsH.put("bigLeavesImpact", new SoundFile(p, "sounds/enemies/bigLeavesImpact.wav"));
        soundsH.put("snakeCrunch", new SoundFile(p, "sounds/enemies/snakeCrunch.wav"));
        soundsH.put("snakeSquish", new SoundFile(p, "sounds/enemies/snakeSquish.wav"));
        //projectiles
        soundsH.put("smallImpact", new SoundFile(p, "sounds/projectiles/smallImpact.wav"));
        soundsH.put("mediumImpact", new SoundFile(p, "sounds/projectiles/mediumImpact.wav"));
        soundsH.put("largeImpact", new SoundFile(p, "sounds/projectiles/largeImpact.wav"));
        soundsH.put("whooshImpact", new SoundFile(p, "sounds/projectiles/whooshImpact.wav"));
        soundsH.put("squishImpact", new SoundFile(p, "sounds/projectiles/squishImpact.wav"));
        soundsH.put("fireImpact", new SoundFile(p, "sounds/projectiles/fireImpact.wav"));
        //turrets
        soundsH.put("woodPlace", new SoundFile(p, "sounds/turrets/woodPlace.wav"));
        //ui
        soundsH.put("waveEnd", new SoundFile(p,"sounds/ui/waveEnd.wav"));
        //walls
        soundsH.put("woodDamage", new SoundFile(p, "sounds/walls/woodDamage.wav"));
        soundsH.put("woodBreak", new SoundFile(p, "sounds/walls/woodBreak.wav"));
        soundsH.put("woodPlaceShort", new SoundFile(p, "sounds/walls/woodPlaceShort.wav"));
    }
}
