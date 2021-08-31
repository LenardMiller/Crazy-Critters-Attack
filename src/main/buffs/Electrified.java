package main.buffs;

import main.damagingThings.arcs.YellowArc;
import main.enemies.Enemy;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.sound.SoundFile;

import static main.Main.*;
import static main.misc.Utilities.secondsToFrames;
import static main.sound.SoundUtilities.playSoundRandomSpeed;

public class Electrified extends Buff {

    private final int DAMAGE;
    private final SoundFile SOUND;

    public Electrified(PApplet p, int enId, int damage, float duration, Turret turret) {
        super(p, enId, turret);
        particleChance = 4;
        effectDelay = secondsToFrames(1);
        effectTimer = 0;
        DAMAGE = damage;
        SOUND = sounds.get("teslaFire");
        lifeDuration = secondsToFrames(duration);
        particle = "nuclear";
        name = "electrified";
    }

    @Override
    public void effect() {
        if (enId < 0) buffs.remove(this);
        else {
            Enemy enemy = enemies.get(enId);
            arcs.add(new YellowArc(p, enemy.position.x, enemy.position.y, turret, DAMAGE, 4, 150,
              (int) p.random(3)));
            playSoundRandomSpeed(p, SOUND, 1);
        }
    }
}
