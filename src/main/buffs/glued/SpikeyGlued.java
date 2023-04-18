package main.buffs.glued;

import main.projectiles.fragments.GlueSpike;
import main.enemies.Enemy;
import main.particles.MiscParticle;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.getRandomPointInRange;

public class SpikeyGlued extends Glued {

    private final Spike[] SPIKES;

    public SpikeyGlued(PApplet p, int enId, float speedMod, float duration, Turret turret) {
        super(p,enId,speedMod,duration,turret);
        SPIKES = new Spike[enemies.get(enId).pfSize * 3];
        for (int i = 0; i < SPIKES.length; i++) {
            Enemy enemy = enemies.get(enId);
            PVector pos = getRandomPointInRange(p, new PVector(), enemy.size.mag() * 0.4f);
            SPIKES[i] = new Spike(pos.x, pos.y, p.random(0,360));
        }
    }

    @Override
    public void display() {
        for (Spike spike : SPIKES) spike.display(enemies.get(enId).position);
    }

    /**particles around enemy*/
    @Override
    protected void spawnParticles() {
        if (particle != null) {
            Enemy enemy = enemies.get(enId);
            if (p.random(particleChance) < 1) {
                PVector pos = getRandomPointInRange(p, enemy.position, enemy.size.mag() * 0.4f);
                topParticles.add(new MiscParticle(p, pos.x, pos.y, p.random(360), particle));
            }
        }
        for (Spike spike : SPIKES) spike.display(enemies.get(enId).position);
    }

    /**shoots a bunch of glue spikes*/
    @Override
    public void dieEffect() {
        int numSpikes = 24;
        int spikeDamage = 100;
        Enemy enemy = enemies.get(enId);
        for (int i = 0; i < numSpikes; i++) {
            projectiles.add(new GlueSpike(p, enemy.position.x, enemy.position.y, p.random(0,360), turret, spikeDamage));
        }
    }

    private class Spike {

        private final PVector POSITION;
        private final float ANGLE;
        private final PImage SPRITE;
        private final PVector SIZE;

        /**
         * Little glue spikes that stick to enemy
         * @param x x pos relative to enemy
         * @param y y pos relative to enemy
         * @param angle rotation relative to screen
         */
        private Spike(float x, float y, float angle) {
            POSITION = new PVector(x,y);
            this.ANGLE = radians(angle);
            SPRITE = staticSprites.get("glueSpikePj");
            SIZE = new PVector(7,7);
        }

        private void display(PVector absPosition) {
            p.pushMatrix();
            p.translate(absPosition.x + POSITION.x, absPosition.y + POSITION.y);
            p.rotate(ANGLE);
            p.image(SPRITE, -SIZE.x / 2, -SIZE.y / 2);
            p.popMatrix();
        }
    }
}