package main.projectiles;

import main.enemies.Enemy;
import main.particles.BuffParticle;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.util.ArrayList;

import static main.Main.*;
import static processing.core.PConstants.HALF_PI;

public abstract class Projectile {

    public PApplet p;

    public PVector position;
    public PVector size;
    public float radius;
    public float maxSpeed;
    public float speed;
    public int damage;
    int pierce;
    int hitTime;
    public PImage sprite;
    PVector velocity;
    public float angle;
    float angleTwo;
    float angularVelocity;
    boolean dead;
    String trail;
    boolean hasTrail;
    String buff;
    int effectRadius;
    ArrayList<Enemy> hitEnemies;
    Turret turret;
    int effectLevel;
    int effectDuration;
    boolean splashEn;
    String type;

    Projectile(PApplet p, float x, float y, float angle, Turret turret) {
        this.p = p;

        splashEn = true;
        this.turret = turret;
        hitEnemies = new ArrayList<>();
        position = new PVector(x, y);
        size = new PVector(10, 10);
        radius = 10;
        speed = (float) 2;
        damage = 1;
        pierce = 1;
        hitTime = 0;
        angleTwo = angle;
        angularVelocity = 0; //degrees mode
        sprite = spritesH.get("nullPj");
        velocity = PVector.fromAngle(angle - HALF_PI);
        hasTrail = false;
        trail = "null";
        buff = "null";
        effectRadius = 0;
        effectLevel = 0;
        effectDuration = 0;
    }

    public void main(ArrayList<Projectile> projectiles, int i) {
        trail();
        displayPassB();
        move();
        collideEn();
        if (position.y - size.y > BOARD_HEIGHT + 100 || position.x - size.x > BOARD_WIDTH + 100 || position.y + size.y < -100 || position.x + size.x < -100) {
            dead = true;
        }
        if (dead) die(i);
    }

    public void die(int i) {
        projectiles.remove(i);
    }

    void trail() { //leaves a trail of particles
        if (hasTrail) {
            int num = floor(p.random(0, 3));
            if (num == 0) particles.add(new BuffParticle(p, position.x, position.y, p.random(0, 360), trail));
        }
    }

    public void displayPassA() {
        angleTwo += radians(angularVelocity);
        p.pushMatrix();
        p.tint(0,60);
        p.translate(position.x + 2, position.y + 2);
        p.rotate(angleTwo);
        p.image(sprite, -size.x / 2, -size.y / 2);
        p.tint(255);
        p.popMatrix();
    }

    public void displayPassB() {
        angleTwo += radians(angularVelocity);
        p.pushMatrix();
        p.translate(position.x, position.y);
        p.rotate(angleTwo);
        p.image(sprite, -size.x / 2, -size.y / 2);
        p.popMatrix();
    }

    public void move() {
        velocity.setMag(speed);
        position.add(velocity);
    }

    public void collideEn() {
        for (int i = enemies.size() - 1; i >= 0; i--) {
            Enemy enemy = enemies.get(i);
            boolean hitAlready = false;
            for (Enemy hitEnemy : hitEnemies)
                if (hitEnemy == enemy) {
                    hitAlready = true;
                    break;
                }
            if (hitAlready) continue;
            if (abs(enemy.position.x - position.x) <= (radius + enemy.radius) && abs(enemy.position.y - position.y) <= (radius + enemy.radius) && pierce > 0) { //if touching enemy, and has pierce
                enemy.damagePj(damage, buff, effectLevel, effectDuration, turret, splashEn, type, i);
                hitEnemies.add(enemy);
                pierce--;
                for (int j = enemies.size() - 1; j >= 0; j--) {
                    Enemy erEnemy = enemies.get(j);
                    if (abs(erEnemy.position.x - position.x) <= (effectRadius + erEnemy.radius) && abs(erEnemy.position.y - position.y) <= (effectRadius + erEnemy.radius)) { //if near enemy
                        hitAlready = false;
                        for (Enemy hitEnemy : hitEnemies)
                            if (hitEnemy == enemy) {
                                hitAlready = true;
                                break;
                            }
                        if (hitAlready) continue;
                        erEnemy.damagePj(3 * (damage / 4), buff, effectLevel, effectDuration, turret, splashEn, type, i);
                    }
                }
            }
            if (pierce == 0) dead = true;
        }
    }
}
