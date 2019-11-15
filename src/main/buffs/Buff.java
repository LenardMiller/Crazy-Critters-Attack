package main.buffs;

import main.enemies.Enemy;
import main.particles.BuffParticle;
import processing.core.PApplet;

import static main.Main.buffs;
import static main.Main.enemies;
import static main.Main.particles;

public abstract class Buff {

    public PApplet p;

    int effectDelay;
    private int effectTimer;
    private int lifeTimer;
    int lifeDuration;
    public String particle;
    public int enId;

    Buff(PApplet p, int enId){
        this.p = p;

        effectDelay = 60; //frames
        effectTimer = p.frameCount + effectDelay;
        int lifeDuration = 600; //frames
        lifeTimer = p.frameCount + lifeDuration;
        particle = "null";
        this.enId = enId;
    }

    public void main(int i){
        end(i);
        if (p.frameCount > effectTimer){
            effect();
            effectTimer = p.frameCount + effectDelay;
        }
        display();
    }

    private void end(int i){ //ends if at end of lifespan
        if (p.frameCount > lifeTimer){
            buffs.remove(i);
        }
    }

    private void effect(){ //prints enemies id
        System.out.print(enId + " ");
    }

    private void display(){ //particles around enemy
        Enemy enemy = enemies.get(enId);
        int num = (int)(p.random(0,8));
        if (num == 0){
            particles.add(new BuffParticle(p,(float)(enemy.position.x+2.5+p.random((enemy.size.x/2)*-1,(enemy.size.x/2))), (float)(enemy.position.y+2.5+p.random((enemy.size.x/2)*-1,(enemy.size.x/2))), p.random(0,360), particle));
        }
    }
}