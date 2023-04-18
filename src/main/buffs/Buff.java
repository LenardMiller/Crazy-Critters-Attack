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

    public int enId;
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

    protected Buff(PApplet p, int enId, Turret turret){
        this.p = p;

        particleChance = 8;
        effectDelay = secondsToFrames(1);
        lifeDuration = secondsToFrames(10);
        particle = "null";
        name = "null";
        this.enId = enId;
        this.turret = turret;
    }

    public void update(int i){
        updateTimer(i);
        if (!paused) {
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
     * @param i buff id
     */
    protected void updateTimer(int i) {
        if (!paused) lifeTimer++;
        if (lifeTimer > lifeDuration) buffs.remove(i);
    }

    public abstract void effect();

    /**
     * Particles around enemy.
     */
    protected void spawnParticles() {
        if (particle != null) {
            if (enId < 0) buffs.remove(this);
            else {
                Enemy enemy = enemies.get(enId);
                if (p.random(particleChance) < 1) {
                    PVector pos = getRandomPointInRange(p, enemy.position, enemy.size.mag() * 0.4f);
                    topParticles.add(new MiscParticle(p, pos.x, pos.y, p.random(360), particle));
                }
            }
        }
    }

    public void dieEffect() {}
}