package main.projectiles;

import main.particles.BuffParticle;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;

public class Flame extends Projectile {

    private PImage[] sprites;
    private int currentSprite;
    private int delay;
    private float spawnRange;
    private int fireChance;
    private int smokeChance;
    private int timer;

    public Flame(PApplet p, float x, float y, float angle, Turret turret, int damage, int effectLevel, int effectDuration, int timer) {
        super(p, x, y, angle, turret);
        position = new PVector(x, y);
        size = new PVector(25, 25);
        spawnRange = 0;
        radius = 5;
        maxSpeed = 5;
        speed = maxSpeed;
        this.damage = damage;
        pierce = 900;
        this.effectLevel = effectLevel;
        this.effectDuration = effectDuration;
        this.angle = angle;
        this.timer = timer;
        angleTwo = angle;
        angularVelocity = 0; //degrees mode
        sprites = spritesAnimH.get("flamePJ");
        buff = "burning";
        splashEn = false;
        fireChance = 8;
        smokeChance = 100;
        hitSound = soundsH.get("fireImpact");
    }

    public void die(int i) {
        projectiles.remove(i);
    }

    public void displayPassA() {
    } //no shadow

    public void displayPassB() {
        delay++;
        sprite = sprites[currentSprite];
        //particles
        spawnRange += 0.5f;
        if (currentSprite == 9) smokeChance = 20;
        if (currentSprite > 9) {
            fireChance +=5;
            if (smokeChance > 4 && currentSprite < 15) smokeChance -= 0.5f;
            else smokeChance +=5;
        }
        int num = (int) (p.random(0, fireChance));
        if (num == 0) {
            particles.add(new BuffParticle(p, (float) (position.x + 2.5 + p.random((spawnRange / 2f) * -1, (spawnRange / 2f))), (float) (position.y + 2.5 + p.random((spawnRange / 2f) * -1, (spawnRange / 2f))), p.random(0, 360), "fire"));
        }
        num = (int) (p.random(0, smokeChance));
        if (num == 0) {
            particles.add(new BuffParticle(p, (float) (position.x + 2.5 + p.random((spawnRange / 2f) * -1, (spawnRange / 2f))), (float) (position.y + 2.5 + p.random((spawnRange / 2f) * -1, (spawnRange / 2f))), p.random(0, 360), "smoke"));
        }
        //animation
        if (delay > timer && p.random(0, 20) > 1) {
            currentSprite++;
            delay = 0;
        }
        //control
        if (currentSprite > 9 && speed > 0) speed /= 1.1;
        if (pierce < 900) speed = 0;
        if (currentSprite >= sprites.length) dead = true;
        //main sprite
        angleTwo += radians(angularVelocity);
        p.pushMatrix();
        p.translate(position.x, position.y);
        p.rotate(angleTwo);
        p.image(sprite, -size.x / 2, -size.y / 2);
        p.popMatrix();
    }
}