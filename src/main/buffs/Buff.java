package main.buffs;

import main.enemies.Enemy;
import main.particles.BuffParticle;
import main.towers.turrets.Turret;
import processing.core.PApplet;

import static main.Main.buffs;
import static main.Main.enemies;
import static main.Main.particles;

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
        effectTimer = p.frameCount + effectDelay;
        lifeDuration = 600; //frames
        lifeTimer = p.frameCount + lifeDuration;
        particle = "null";
        name = "null";
        this.enId = enId;
        this.turret = turret;
    }

    public void main(int i){
        end(i);
        if (p.frameCount > effectTimer){
            effect();
            effectTimer = p.frameCount + effectDelay;
        }
        display();
    }

    void end(int i){ //ends if at end of lifespan
        if (p.frameCount > lifeTimer) buffs.remove(i);
    }

    public void effect(){ //prints enemies id
        System.out.print(enId + " ");
    }

    void display() { //particles around enemy
        if (particle != null) {
            Enemy enemy = enemies.get(enId);
            int num = (int) (p.random(0, particleChance));
            if (num == 0) {
                particles.add(new BuffParticle(p, (float) (enemy.position.x + 2.5 + p.random((enemy.size.x / 2) * -1, (enemy.size.x / 2))), (float) (enemy.position.y + 2.5 + p.random((enemy.size.x / 2) * -1, (enemy.size.x / 2))), p.random(0, 360), particle));
            }
        }
    }
}