package main.sound;

import processing.core.PApplet;
import processing.sound.SoundFile;

import static main.Main.*;
import static main.misc.Utilities.secondsToFrames;

public class SoundLoader {

    public static void loadSounds(PApplet p) {
        //critter die
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
        sounds.put("frostDie", new SoundFile(p, "sounds/enemies/frostDie.wav"));
        sounds.put("mammoth", new SoundFile(p, "sounds/enemies/mammoth.wav"));
        sounds.put("mammothSquash", new SoundFile(p, "sounds/enemies/mammothSquash.wav"));
        sounds.put("mudDie", new SoundFile(p, "sounds/enemies/mudDie.wav"));
        sounds.put("mudSquish", new SoundFile(p, "sounds/enemies/mudSquish.wav"));
        //critter attack
        sounds.put("spit", new SoundFile(p, "sounds/enemies/spit.wav"));
        sounds.put("badMagic", new SoundFile(p, "sounds/enemies/badMagic.wav"));
        sounds.put("bugGrowlQuick", new SoundFile(p, "sounds/enemies/bugGrowlQuick.wav"));
        sounds.put("bugGrowlVeryQuick", new SoundFile(p, "sounds/enemies/bugGrowlVeryQuick.wav"));
        sounds.put("bugGrowlSlow", new SoundFile(p, "sounds/enemies/bugGrowlSlow.wav"));
        sounds.put("whipCrack", new SoundFile(p, "sounds/enemies/whipCrack.wav"));
        sounds.put("smallWhipCrack", new SoundFile(p, "sounds/enemies/smallWhipCrack.wav"));
        sounds.put("snakeStrike", new SoundFile(p, "sounds/enemies/snakeStrike.wav"));
        sounds.put("flap", new SoundFile(p, "sounds/enemies/flap.wav"));
        sounds.put("bite", new SoundFile(p, "sounds/enemies/bite.wav"));
        sounds.put("biteGrowl", new SoundFile(p, "sounds/enemies/biteGrowl.wav"));
        sounds.put("biteGrowlSlow", new SoundFile(p, "sounds/enemies/biteGrowlSlow.wav"));
        sounds.put("swooshPunch", new SoundFile(p, "sounds/enemies/swooshPunch.wav"));
        sounds.put("swooshPunchSlow", new SoundFile(p, "sounds/enemies/swooshPunchSlow.wav"));
        sounds.put("dogAttack", new SoundFile(p, "sounds/enemies/dogAttack.wav"));
        sounds.put("slime", new SoundFile(p, "sounds/enemies/slime.wav"));
        sounds.put("frostFreeze", new SoundFile(p, "sounds/enemies/frostFreeze.wav"));
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
        sounds.put("cash", new SoundFile(p, "sounds/turrets/cash.wav"));
        sounds.put("railgun", new SoundFile(p, "sounds/turrets/railgun.wav"));
        sounds.put("nightmareFire", new SoundFile(p, "sounds/turrets/nightmare.wav"));
        //ui
        sounds.put("waveEnd", new SoundFile(p,"sounds/ui/waveEnd.wav"));
        sounds.put("clickIn", new SoundFile(p, "sounds/ui/clickIn.wav"));
        sounds.put("clickOut", new SoundFile(p, "sounds/ui/clickOut.wav"));
        //material
        sounds.put("woodDamage", new SoundFile(p, "sounds/material/woodDamage.wav"));
        sounds.put("woodBreak", new SoundFile(p, "sounds/material/woodBreak.wav"));
        sounds.put("woodPlace", new SoundFile(p, "sounds/material/woodPlace.wav"));
        sounds.put("woodPlaceShort", new SoundFile(p, "sounds/material/woodPlaceShort.wav"));

        sounds.put("stoneDamage", new SoundFile(p, "sounds/material/stoneDamage.wav"));
        sounds.put("stoneBreak", new SoundFile(p, "sounds/material/stoneBreak.wav"));
        sounds.put("stonePlace", new SoundFile(p, "sounds/material/stonePlace.wav"));
        sounds.put("stonePlaceShort", new SoundFile(p, "sounds/material/stonePlaceShort.wav"));

        sounds.put("metalDamage", new SoundFile(p, "sounds/material/metalDamage.wav"));
        sounds.put("metalBreak", new SoundFile(p, "sounds/material/metalBreak.wav"));
        sounds.put("metalPlace", new SoundFile(p, "sounds/material/metalPlace.wav"));
        sounds.put("metalPlaceShort", new SoundFile(p, "sounds/material/metalPlaceShort.wav"));

        sounds.put("crystalDamage", new SoundFile(p, "sounds/material/crystalDamage.wav"));
        sounds.put("crystalBreak", new SoundFile(p, "sounds/material/crystalBreak.wav"));
        sounds.put("crystalPlace", new SoundFile(p, "sounds/material/crystalPlace.wav"));
        sounds.put("crystalPlaceShort", new SoundFile(p, "sounds/material/crystalPlaceShort.wav"));

        sounds.put("iceDamage", new SoundFile(p, "sounds/material/iceDamage.wav"));
        sounds.put("iceBreak", new SoundFile(p, "sounds/material/iceBreak.wav"));

        sounds.put("titaniumBreak", new SoundFile(p, "sounds/material/titaniumBreak.wav"));
        sounds.put("titaniumDamage", new SoundFile(p, "sounds/material/titaniumDamage.wav"));
        sounds.put("titaniumPlace", new SoundFile(p, "sounds/material/titaniumPlace.wav"));
        sounds.put("titaniumPlaceShort", new SoundFile(p, "sounds/material/titaniumPlaceShort.wav"));
        //loops
        startStopSoundLoops.put("smallExplosion", new StartStopSoundLoop(p, "smallExplosion/", secondsToFrames(1), false));
        fadeSoundLoops.put("flamethrower", new FadeSoundLoop(p, "flamethrower", FRAMERATE/6));
        //alts
        soundsWithAlts.put("thunder", new SoundWithAlts(p, "thunder", 3));
        //stackables
        stackableSounds.put("beam", new StackableSound(p, "beam", 5));
        //move sounds
        moveSoundLoops.put("smallBugLoop", new MoveSoundLoop(p, "smallBugLoop", 20));
        moveSoundLoops.put("bigBugLoop", new MoveSoundLoop(p, "bigBugLoop", 3));
        moveSoundLoops.put("leafyStepsLoop", new MoveSoundLoop(p, "leafyStepsLoop", 10));
        moveSoundLoops.put("bigLeafyStepsLoop", new MoveSoundLoop(p, "bigLeafyStepsLoop", 3));
        moveSoundLoops.put("smallWingbeats", new MoveSoundLoop(p, "smallWingbeats", 20));
        moveSoundLoops.put("wingbeats", new MoveSoundLoop(p, "wingbeats", 10));
        moveSoundLoops.put("bigWingbeats", new MoveSoundLoop(p, "bigWingbeats", 3));
        moveSoundLoops.put("buzz", new MoveSoundLoop(p, "buzz", 3));
        moveSoundLoops.put("crystals", new MoveSoundLoop(p, "crystals", 10));
        moveSoundLoops.put("bigCrystals", new MoveSoundLoop(p, "bigCrystals", 3));
        moveSoundLoops.put("ominousWind", new MoveSoundLoop(p, "ominousWind", 3));
        moveSoundLoops.put("bigDig", new MoveSoundLoop(p, "bigDig", 3));
        moveSoundLoops.put("littleDig", new MoveSoundLoop(p, "littleDig", 10));
    }
}
