package main.projectiles;

import main.enemies.Enemy;
import main.particles.MiscParticle;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.down60ToFramerate;

public class BlueFlame extends Projectile {

    private final PImage[] SPRITES;
    private final int TIMER;

    private int currentSprite;
    private int delay;
    private float spawnRange;
    private int fireChance;
    private int smokeChance;

    public BlueFlame(PApplet p, float x, float y, float angle, Turret turret, int damage, float effectLevel,
                     float effectDuration, int range, boolean sound) {
        super(p, x, y, angle, turret);
        position = new PVector(x, y);
        size = new PVector(25, 25);
        radius = 5;
        float ratio = range / 200f;
        maxSpeed = 150 * ratio;
        speed = maxSpeed;
        this.damage = damage;
        pierce = 900;
        this.effectLevel = effectLevel;
        this.effectDuration = effectDuration;
        this.angle = angle;
        TIMER = down60ToFramerate(2);
        angleTwo = angle;
        angularVelocity = 0; //degrees mode
        SPRITES = animatedSprites.get("blueFlamePJ");
        buff = "blueBurning";
        type = Enemy.DamageType.blueBurning;
        causeEnemyParticles = false;
        fireChance = 8;
        smokeChance = 100;
        if (sound) hitSound = sounds.get("fireImpact");
        else hitSound = null;
    }

    @Override
    public void die() {
        projectiles.remove(this);
    }

    /**
     * no shadow
     */
    @Override
    public void displayShadow() {}

    @Override
    public void display() {
        if (!isPaused) {
            delay++;
            sprite = SPRITES[currentSprite];
            //particles
            spawnRange += 0.5f;
            if (currentSprite == 9) smokeChance = 20;
            if (currentSprite > 9) {
                fireChance += 5;
                if (smokeChance > 4 && currentSprite < 15) smokeChance -= 0.5f;
                else smokeChance += 5;
            }
            int num = (int) (p.random(0, fireChance));
            if (num == 0) {
                topParticles.add(new MiscParticle(p, (float) (position.x + 2.5 + p.random((spawnRange / 2f) * -1, (spawnRange / 2f))), (float) (position.y + 2.5 + p.random((spawnRange / 2f) * -1, (spawnRange / 2f))), p.random(0, 360), "greenMagic"));
            }
            num = (int) (p.random(0, smokeChance));
            if (num == 0) {
                topParticles.add(new MiscParticle(p, (float) (position.x + 2.5 + p.random((spawnRange / 2f) * -1, (spawnRange / 2f))), (float) (position.y + 2.5 + p.random((spawnRange / 2f) * -1, (spawnRange / 2f))), p.random(0, 360), "blueSmoke"));
            }
            //animation
            if (delay > TIMER && p.random(0, 20) > 1) {
                currentSprite++;
                delay = 0;
            }
            //control
            if (currentSprite >= SPRITES.length) dead = true;
        }
        //main sprite
        angleTwo += radians(angularVelocity);
        p.pushMatrix();
        p.translate(position.x, position.y);
        p.rotate(angleTwo);
        p.image(sprite, -size.x / 2, -size.y / 2);
        p.popMatrix();
    }

    @Override
    protected void boostedDieParticles() {}

    @Override
    protected void boostedTrailParticles() {
        if (turret.boostedDamage() <= 0 || p.random(trainChance * 5) > 1) return;
        topParticles.add(new MiscParticle(p, position.x, position.y,
                p.random(TWO_PI), "orangeMagic"));
    }
}