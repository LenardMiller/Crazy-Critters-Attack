package main.enemies.shootingEnemies;

import main.damagingThings.projectiles.enemyProjeciles.MudBall;
import main.particles.Debris;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.down60ToFramerate;

public class MudFlinger extends ShootingEnemy {

    public MudFlinger(PApplet p, float x, float y) {
        super(p, x, y);
        size = new PVector(50,50);
        pfSize = 2;
        radius = 25;
        maxSpeed = 24;
        speed = maxSpeed;
        moneyDrop = 60;
        damage = 2;
        maxHp = 200;
        range = 110;
        barrelLength = 7;
        hp = maxHp;
        hitParticle = "mudOuch";
        name = "mudFlinger";
        shootSound = sounds.get("spit");
        betweenWalkFrames = down60ToFramerate(10);
        betweenAttackFrames = down60ToFramerate(8);
        betweenShootFrames = down60ToFramerate(8);
        attackStartFrame = 0; //attack start
        attackFrame = attackStartFrame;
        attackDmgFrames = new int[]{9};
        shootFireFrame = 9;
        System.arraycopy(attackDmgFrames, 0, tempAttackDmgFrames, 0, tempAttackDmgFrames.length);
        corpseSize = size;
        partSize = new PVector(11,11);
        overkillSound = sounds.get("squish");
        dieSound = sounds.get("crunch");
        loadStuff();
    }

    @Override
    protected void fire(float projectileAngle, PVector projectilePosition) {
        projectiles.add(new MudBall(p, damage, projectilePosition.x, projectilePosition.y, projectileAngle));
        for (int i = 0; i < 5; i++) {
            midParticles.add(new Debris(p, projectilePosition.x, projectilePosition.y,
              projectileAngle + radians(p.random(-15, 15)), "mud"));
        }
    }
}