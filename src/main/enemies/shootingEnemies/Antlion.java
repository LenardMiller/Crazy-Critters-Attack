package main.enemies.shootingEnemies;

import main.projectiles.enemyProjeciles.Sandball;
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
        speed = 24;
        moneyDrop = 40;
        damage = 2;
        shootDamage = 2;
        maxHp = 50;
        range = 100;
        barrelLength = 7;
        hp = maxHp;
        hitParticle = "greenOuch";
        name = "antlion";
        attackSound = sounds.get("bugGrowlVeryQuick");
        shootSound = sounds.get("spit");
        betweenWalkFrames = down60ToFramerate(10);
        betweenAttackFrames = down60ToFramerate(12);
        betweenShootFrames = down60ToFramerate(12);
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
        projectiles.add(new Sandball(p, shootDamage, projectilePosition.x, projectilePosition.y, projectileAngle));
        for (int i = 0; i < 5; i++) {
            midParticles.add(new Debris(p, projectilePosition.x, projectilePosition.y,
              projectileAngle + radians(p.random(-15, 15)), "sand"));
        }
    }
}
