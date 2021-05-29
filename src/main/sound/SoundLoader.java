package main.sound;

import processing.core.PApplet;
import processing.sound.SoundFile;

import static main.Main.*;
import static main.misc.Utilities.secondsToFrames;

public class SoundLoader {

    public SoundLoader() {}

    public static void loadSounds(PApplet p) { //todo: load dynamically?
        //enemy
        sounds.put("squish", new SoundFile(p, "sounds/enemies/squish.wav"));
        sounds.put("squash", new SoundFile(p, "sounds/enemies/squash.wav"));
        sounds.put("squashRoar", new SoundFile(p, "sounds/enemies/squashRoar.wav"));
        sounds.put("crunch", new SoundFile(p, "sounds/enemies/crunch.wav"));
        sounds.put("bigCrunch", new SoundFile(p, "sounds/enemies/bigCrunch.wav"));
        sounds.put("bigCrunchRoar", new SoundFile(p, "sounds/enemies/bigCrunchRoar.wav"));
        sounds.put("leaves", new SoundFile(p, "sounds/enemies/leaves.wav"));
        sounds.put("leavesImpact", new SoundFile(p, "sounds/enemies/leavesImpact.wav"));
        sounds.put("bigLeaves", new SoundFile(p, "sounds/enemies/bigLeaves.wav"));
        sounds.put("bigLeavesImpact", new SoundFile(p, "sounds/enemies/bigLeavesImpact.wav"));
        sounds.put("hiss", new SoundFile(p, "sounds/enemies/hiss.wav"));
        sounds.put("hissSquish", new SoundFile(p, "sounds/enemies/hissSquish.wav"));
        sounds.put("squeak", new SoundFile(p, "sounds/enemies/squeak.wav"));
        sounds.put("squeakSquish", new SoundFile(p, "sounds/enemies/squeakSquish.wav"));
        sounds.put("bigSqueak", new SoundFile(p, "sounds/enemies/bigSqueak.wav"));
        sounds.put("bigSqueakSquash", new SoundFile(p, "sounds/enemies/bigSqueakSquash.wav"));
        sounds.put("bark", new SoundFile(p, "sounds/enemies/bark.wav"));
        sounds.put("barkSquish", new SoundFile(p, "sounds/enemies/barkSquish.wav"));
        sounds.put("dino", new SoundFile(p, "sounds/enemies/dino.wav"));
        sounds.put("dinoSquish", new SoundFile(p, "sounds/enemies/dinoSquish.wav"));
        sounds.put("smallCrystalBreak", new SoundFile(p, "sounds/enemies/smallCrystalBreak.wav"));
        //misc
        sounds.put("smallExplosion", new SoundFile(p, "sounds/misc/smallExplosion.wav"));
        sounds.put("hugeExplosion", new SoundFile(p, "sounds/misc/hugeExplosion.wav"));
        //projectiles
        sounds.put("smallImpact", new SoundFile(p, "sounds/projectiles/smallImpact.wav"));
        sounds.put("mediumImpact", new SoundFile(p, "sounds/projectiles/mediumImpact.wav"));
        sounds.put("largeImpact", new SoundFile(p, "sounds/projectiles/largeImpact.wav"));
        sounds.put("whooshImpact", new SoundFile(p, "sounds/projectiles/whooshImpact.wav"));
        sounds.put("squishImpact", new SoundFile(p, "sounds/projectiles/squishImpact.wav"));
        sounds.put("fireImpact", new SoundFile(p, "sounds/projectiles/fireImpact.wav"));
        sounds.put("energyImpact", new SoundFile(p, "sounds/projectiles/energyImpact.wav"));
        sounds.put("darkImpact", new SoundFile(p, "sounds/projectiles/darkImpact.wav"));
        sounds.put("magicImpact", new SoundFile(p, "sounds/projectiles/magicImpact.wav"));
        //turrets
        sounds.put("woodPlace", new SoundFile(p, "sounds/turrets/woodPlace.wav"));
        sounds.put("stonePlace", new SoundFile(p, "sounds/turrets/stonePlace.wav"));
        sounds.put("metalPlace", new SoundFile(p, "sounds/turrets/metalPlace.wav"));
        sounds.put("crystalPlace", new SoundFile(p, "sounds/turrets/crystalPlace.wav"));
        sounds.put("slingshot", new SoundFile(p, "sounds/turrets/slingshot.wav"));
        sounds.put("crossbow", new SoundFile(p, "sounds/turrets/crossbow.wav"));
        sounds.put("shotbow", new SoundFile(p, "sounds/turrets/shotbow.wav"));
        sounds.put("luggageBlaster", new SoundFile(p, "sounds/turrets/luggageBlaster.wav"));
        sounds.put("seismicSlam", new SoundFile(p, "sounds/turrets/seismicSlam.wav"));
        sounds.put("glueFire", new SoundFile(p, "sounds/turrets/glueFire.wav"));
        sounds.put("energyBlasterFire", new SoundFile(p, "sounds/turrets/energyBlasterFire.wav"));
        sounds.put("teslaFire", new SoundFile(p, "sounds/turrets/teslaFire.wav"));
        sounds.put("magicMissleer", new SoundFile(p, "sounds/turrets/magicMissileer.wav"));
        sounds.put("iceFire", new SoundFile(p, "sounds/turrets/iceFire.wav"));
        //ui
        sounds.put("waveEnd", new SoundFile(p,"sounds/ui/waveEnd.wav"));
        sounds.put("clickIn", new SoundFile(p, "sounds/ui/clickIn.wav"));
        sounds.put("clickOut", new SoundFile(p, "sounds/ui/clickOut.wav"));
        //walls
        sounds.put("woodDamage", new SoundFile(p, "sounds/walls/woodDamage.wav"));
        sounds.put("woodBreak", new SoundFile(p, "sounds/walls/woodBreak.wav"));
        sounds.put("woodPlaceShort", new SoundFile(p, "sounds/walls/woodPlaceShort.wav"));
        sounds.put("stoneDamage", new SoundFile(p, "sounds/walls/stoneDamage.wav"));
        sounds.put("metalDamage", new SoundFile(p, "sounds/walls/metalDamage.wav"));
        sounds.put("stoneBreak", new SoundFile(p, "sounds/walls/stoneBreak.wav"));
        sounds.put("metalBreak", new SoundFile(p, "sounds/walls/metalBreak.wav"));
        sounds.put("stonePlaceShort", new SoundFile(p, "sounds/walls/stonePlaceShort.wav"));
        sounds.put("metalPlaceShort", new SoundFile(p, "sounds/walls/metalPlaceShort.wav"));
        sounds.put("crystalPlaceShort", new SoundFile(p, "sounds/walls/crystalPlaceShort.wav"));
        sounds.put("crystalDamage", new SoundFile(p, "sounds/walls/crystalDamage.wav"));
        sounds.put("crystalBreak", new SoundFile(p, "sounds/walls/crystalBreak.wav"));
        //loops
        startStopSoundLoops.put("smallExplosion", new StartStopSoundLoop(p, "smallExplosion/", secondsToFrames(1), false));
        fadeSoundLoops.put("flamethrower", new FadeSoundLoop(p, "flamethrower", FRAMERATE/6));
        //alts
        soundsWithAlts.put("thunder", new SoundWithAlts(p, "thunder", 3));
    }
}
