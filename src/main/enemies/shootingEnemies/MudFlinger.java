package main.enemies.shootingEnemies;

import main.projectiles.enemyProjeciles.MudBall;
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
        speed = 30;
        moneyDrop = 500;
        damage = 12;
        shootDamage = 4;
        maxHp = 15000;
        range = 140;
        barrelLength = 7;
        hp = maxHp;
        hitParticle = "mudOuch";
        name = "mudFlinger";
        shootSound = sounds.get("spit");
        attackSound = sounds.get("slime");
        betweenWalkFrames = down60ToFramerate(10);
        betweenAttackFrames = down60ToFramerate(8);
        betweenShootFrames = down60ToFramerate(8);
        attackDmgFrames = new int[]{9};
        shootFireFrame = 9;
        System.arraycopy(attackDmgFrames, 0, tempAttackDmgFrames, 0, tempAttackDmgFrames.length);
        corpseSize = size;
        partSize = new PVector(25,25);
        overkillSound = sounds.get("mudSquish");
        dieSound = sounds.get("mudDie");
        moveSoundLoop = moveSoundLoops.get("slimeMovement");
        loadStuff();
    }

    @Override
    protected void fire(float projectileAngle, PVector projectilePosition) {
        projectiles.add(new MudBall(p, shootDamage, projectilePosition.x, projectilePosition.y, projectileAngle));
        for (int i = 0; i < 5; i++) {
            midParticles.add(new Debris(p, projectilePosition.x, projectilePosition.y,
              projectileAngle + radians(p.random(-15, 15)), "mud"));
        }
    }
}
