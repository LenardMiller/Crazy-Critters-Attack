package main.buffs;

import main.enemies.Enemy;
import main.towers.turrets.Turret;
import processing.core.PApplet;

import static main.Main.*;
import static main.misc.Utilities.secondsToFrames;
import static main.particles.Particle.ParticleTypes.MiscParticle;

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

    public void main(int i){
        end(i);
        if (!paused) {
            effectTimer++;
            if (effectTimer > effectDelay){
                effect();
                effectTimer = 0;
            }
            display();
        }
    }

    /**
     * Ends if at end of lifespan.
     * @param i buff id
     */
    protected void end(int i) {
        if (!paused) lifeTimer++;
        if (lifeTimer > lifeDuration) buffs.remove(i);
    }

    public abstract void effect();

    /**
     * Particles around enemy.
     */
    protected void display() {
        if (particle != null) {
            if (enId < 0) buffs.remove(this);
            else {
                Enemy enemy = enemies.get(enId);
                int num = (int) (p.random(0, particleChance));
                if (num == 0) {
                    topParticles.add(MiscParticle.create(p,
                            (float) (enemy.position.x + 2.5 + p.random(
                                    (enemy.size.x / 2) * -1,
                                    (enemy.size.x / 2))),
                            (float) (enemy.position.y + 2.5 + p.random(
                                    (enemy.size.x / 2) * -1,
                                    (enemy.size.x / 2))),
                            p.random(0, 360),
                            particle
                    ));
                }
            }
        }
    }

    public void dieEffect() {}
}