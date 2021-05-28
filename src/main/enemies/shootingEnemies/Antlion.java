package main.enemies.shootingEnemies;

import main.damagingThings.projectiles.enemyProjeciles.Snowball;
import main.particles.Debris;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.down60ToFramerate;

public class Antlion extends ShootingEnemy {

    public Antlion(PApplet p, float x, float y) {
        super(p, x, y);
        size = new PVector(25,25);
        pfSize = 1;
        radius = 13;
        maxSpeed = 24;
        speed = maxSpeed;
        moneyDrop = 10;
        damage = 1;
        maxHp = 20; //Hp <---------------------------
        range = 100;
        barrelLength = 7;
        hp = maxHp;
        hitParticle = "greenOuch";
        name = "antlion";
        betweenWalkFrames = down60ToFramerate(10);
        betweenAttackFrames = down60ToFramerate(12);
        betweenShootFrames = down60ToFramerate(12);
        attackStartFrame = 0; //attack start
        attackFrame = attackStartFrame;
        attackDmgFrames = new int[]{6};
        shootFireFrame = 6;
        System.arraycopy(attackDmgFrames, 0, tempAttackDmgFrames, 0, tempAttackDmgFrames.length);
        corpseSize = size;
        partSize = new PVector(11,11);
        overkillSound = sounds.get("squish");
        dieSound = sounds.get("crunch");
        loadStuff();
    }

    @Override
    protected void fire(float projectileAngle, PVector projectilePosition) {
        projectiles.add(new Snowball(p, damage, projectilePosition.x, projectilePosition.y, projectileAngle, target));
        for (int i = 0; i < 5; i++) {
            midParticles.add(new Debris(p, projectilePosition.x, projectilePosition.y,
              projectileAngle + radians(p.random(-15, 15)), "snow"));
        }
    }
}
