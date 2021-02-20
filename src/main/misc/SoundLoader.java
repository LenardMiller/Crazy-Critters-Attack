package main.misc;

import processing.core.PApplet;
import processing.sound.SoundFile;

import static main.Main.soundLoopsH;
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
        soundsH.put("squeak", new SoundFile(p, "sounds/enemies/squeak.wav"));
        soundsH.put("squeakSquish", new SoundFile(p, "sounds/enemies/squeakSquish.wav"));
        soundsH.put("bigSqueak", new SoundFile(p, "sounds/enemies/bigSqueak.wav"));
        soundsH.put("bigSqueakSquash", new SoundFile(p, "sounds/enemies/bigSqueakSquash.wav"));
        //misc
        soundsH.put("smallExplosion", new SoundFile(p, "sounds/misc/smallExplosion.wav"));
        //projectiles
        soundsH.put("smallImpact", new SoundFile(p, "sounds/projectiles/smallImpact.wav"));
        soundsH.put("mediumImpact", new SoundFile(p, "sounds/projectiles/mediumImpact.wav"));
        soundsH.put("largeImpact", new SoundFile(p, "sounds/projectiles/largeImpact.wav"));
        soundsH.put("whooshImpact", new SoundFile(p, "sounds/projectiles/whooshImpact.wav"));
        soundsH.put("squishImpact", new SoundFile(p, "sounds/projectiles/squishImpact.wav"));
        soundsH.put("fireImpact", new SoundFile(p, "sounds/projectiles/fireImpact.wav"));
        soundsH.put("energyImpact", new SoundFile(p, "sounds/projectiles/energyImpact.wav"));
        //turrets
        soundsH.put("woodPlace", new SoundFile(p, "sounds/turrets/woodPlace.wav"));
        soundsH.put("stonePlace", new SoundFile(p, "sounds/turrets/stonePlace.wav"));
        soundsH.put("metalPlace", new SoundFile(p, "sounds/turrets/metalPlace.wav"));
        soundsH.put("slingshot", new SoundFile(p, "sounds/turrets/slingshot.wav"));
        soundsH.put("crossbow", new SoundFile(p, "sounds/turrets/crossbow.wav"));
        soundsH.put("shotbow", new SoundFile(p, "sounds/turrets/shotbow.wav"));
        soundsH.put("luggageBlaster", new SoundFile(p, "sounds/turrets/luggageBlaster.wav"));
        soundsH.put("seismicSlam", new SoundFile(p, "sounds/turrets/seismicSlam.wav"));
        soundsH.put("glueFire", new SoundFile(p, "sounds/turrets/glueFire.wav"));
        soundsH.put("energyBlasterFire", new SoundFile(p, "sounds/turrets/energyBlasterFire.wav"));
        soundsH.put("teslaFire", new SoundFile(p, "sounds/turrets/teslaFire.wav"));
        //ui
        soundsH.put("waveEnd", new SoundFile(p,"sounds/ui/waveEnd.wav"));
        soundsH.put("clickIn", new SoundFile(p, "sounds/ui/clickIn.wav"));
        soundsH.put("clickOut", new SoundFile(p, "sounds/ui/clickOut.wav"));
        //walls
        soundsH.put("woodDamage", new SoundFile(p, "sounds/walls/woodDamage.wav"));
        soundsH.put("woodBreak", new SoundFile(p, "sounds/walls/woodBreak.wav"));
        soundsH.put("woodPlaceShort", new SoundFile(p, "sounds/walls/woodPlaceShort.wav"));
        soundsH.put("stoneDamage", new SoundFile(p, "sounds/walls/stoneDamage.wav"));
        soundsH.put("metalDamage", new SoundFile(p, "sounds/walls/metalDamage.wav"));
        soundsH.put("stoneBreak", new SoundFile(p, "sounds/walls/stoneBreak.wav"));
        soundsH.put("metalBreak", new SoundFile(p, "sounds/walls/metalBreak.wav"));
        soundsH.put("stonePlaceShort", new SoundFile(p, "sounds/walls/stonePlaceShort.wav"));
        soundsH.put("metalPlaceShort", new SoundFile(p, "sounds/walls/metalPlaceShort.wav"));
        //loops
        soundLoopsH.put("smallExplosion", new SoundLoop(p, "sounds/loops/smallExplosion", 60));
    }
}
