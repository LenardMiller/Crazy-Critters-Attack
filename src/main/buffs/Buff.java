package main.buffs;

import main.enemies.Enemy;
import main.particles.BuffParticle;
import main.towers.turrets.Turret;
import processing.core.PApplet;

import static main.Main.*;

public abstract class Buff {

    public PApplet p;

    int effectDelay;
    public int effectTimer;
    int lifeTimer;
    int lifeDuration;
    int particleChance;
    public String particle;
    public int enId;
    public String name;
    public Turret turret;

    Buff(PApplet p, int enId, Turret turret){
        this.p = p;

        particleChance = 8;
        effectDelay = 60; //frames
        lifeDuration = 600; //frames
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
    void end(int i) {
        if (!paused) lifeTimer++;
        if (lifeTimer > lifeDuration) buffs.remove(i);
    }

    public void effect() {
        System.out.print(enId + " ");
    }

    /**
     * Particles around enemy.
     */
    void display() {
        if (particle != null) {
            Enemy enemy = enemies.get(enId);
            int num = (int) (p.random(0, particleChance));
            if (num == 0) {
                particles.add(new BuffParticle(p, (float) (enemy.position.x + 2.5 + p.random((enemy.size.x / 2) * -1, (enemy.size.x / 2))), (float) (enemy.position.y + 2.5 + p.random((enemy.size.x / 2) * -1, (enemy.size.x / 2))), p.random(0, 360), particle));
            }
        }
    }

    public void dieEffect() {}
}