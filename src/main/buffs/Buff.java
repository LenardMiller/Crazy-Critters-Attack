package main.buffs;

import main.enemies.Enemy;
import main.particles.MiscParticle;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.getRandomPointInRange;
import static main.misc.Utilities.secondsToFrames;

public abstract class Buff {

    public int effectTimer;
    public PApplet p;
    public String particle;
    public String name;
    public Turret turret;

    protected int effectDelay;
    protected int lifeTimer;
    protected int lifeDuration;
    protected int particleChance;
    protected float effectLevel;
    protected Enemy target;

    protected Buff(PApplet p, Enemy target, Turret turret){
        this.p = p;

        particleChance = 8;
        effectDelay = secondsToFrames(1);
        lifeDuration = secondsToFrames(10);
        particle = "null";
        name = "null";
        this.turret = turret;
        this.target = target;
    }

    public void update() {
        updateTimer();
        if (!isPaused) {
            effectTimer++;
            if (effectTimer > effectDelay){
                effect();
                effectTimer = 0;
            }
            spawnParticles();
        }
    }

    public void display() {}

    /**
     * Ends if at end of lifespan.
     */
    protected void updateTimer() {
        if (!isPaused) lifeTimer++;
        if (lifeTimer > lifeDuration) target.buffs.remove(this);
    }

    public abstract void effect();

    /**
     * Particles around enemy.
     */
    protected void spawnParticles() {
        if (particle != null) {
            if (p.random(particleChance) < 1) {
                PVector pos = getRandomPointInRange(p, target.position, target.size.mag() * 0.4f);
                topParticles.add(new MiscParticle(p, pos.x, pos.y, p.random(360), particle));
            }
        }
    }

    public void dieEffect() {}

    public boolean matches(Enemy other) {
        return other == target;
    }
}