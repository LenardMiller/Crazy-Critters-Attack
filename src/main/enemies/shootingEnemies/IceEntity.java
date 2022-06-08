package main.enemies.shootingEnemies;

import main.projectiles.enemyProjeciles.IceCrystal;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.Utilities.down60ToFramerate;

public class IceEntity extends ShootingEnemy {

    protected PImage orbitSprite;
    protected float orbitAngle;
    protected float orbitAngleSpeed;
    protected float orbitAngleMinSpeed;
    protected float orbitAngleTopSpeed;
    protected float orbitAngleSpeedChange;

    public IceEntity(PApplet p, float x, float y) {
        super(p, x, y);
        size = new PVector(25,25);
        pfSize = 1;
        radius = 13;
        speed = 30;
        moneyDrop = 150;
        damage = 9;
        shootDamage = 3;
        maxHp = 1200;
        range = 120;
        barrelLength = 0;
        hp = maxHp;
        hitParticle = "iceOuch";
        name = "iceEntity";
        orbitSprite = staticSprites.get("iceEntityOrbitEn");
        orbitAngleMinSpeed = 0.2f;
        orbitAngleSpeed = orbitAngleMinSpeed;
        orbitAngleTopSpeed = 1;
        orbitAngleSpeedChange = 0.02f;
        betweenWalkFrames = down60ToFramerate(10);
        betweenAttackFrames = down60ToFramerate(6);
        betweenShootFrames = down60ToFramerate(6);
        attackDmgFrames = new int[]{1};
        shootFireFrame = 1;
        System.arraycopy(attackDmgFrames, 0, tempAttackDmgFrames, 0, tempAttackDmgFrames.length);
        corpseSize = size;
        partSize = new PVector(10,10);
        shootSound = sounds.get("badMagic");
        attackSound = sounds.get("swooshPunch");
        overkillSound = sounds.get("smallCrystalBreak");
        dieSound = sounds.get("smallCrystalBreak");
        loadStuff();
    }

    @Override
    protected void fire(float projectileAngle, PVector projectilePosition) {
        orbitAngleSpeed = orbitAngleTopSpeed;
        projectiles.add(new IceCrystal(p, shootDamage, projectilePosition.x, projectilePosition.y, projectileAngle));
    }

    @Override
    public void displayShadow() {
        if (!paused) {
            animate();
            if (!immobilized) {
                if (attackFrame == attackDmgFrames[0]) orbitAngleSpeed = orbitAngleTopSpeed;
                orbitAngle += orbitAngleSpeed;
                if (orbitAngleSpeed > orbitAngleMinSpeed) orbitAngleSpeed -= orbitAngleSpeedChange;
            }
        }
        p.pushMatrix();
        p.tint(0, 60);
        int x = 1;
        if (pfSize > 1) x++;
        p.translate(position.x + x, position.y + x);
        p.rotate(rotation);
        if (sprite != null) p.image(sprite, -size.x / 2, -size.y / 2);
        p.rotate(orbitAngle);
        p.image(orbitSprite, -size.x / 2, -size.y / 2);
        p.tint(255);
        p.popMatrix();
    }

    @Override
    public void displayMain() {
        if (debug) for (int i = trail.size() - 1; i > 0; i--) {
            trail.get(i).display();
        }
        p.pushMatrix();
        p.translate(position.x, position.y);
        p.rotate(rotation);
        p.tint(currentTintColor.getRGB());
        if (sprite != null) p.image(sprite, -size.x / 2, -size.y / 2);
        p.rotate(orbitAngle);
        p.image(orbitSprite, -size.x / 2, -size.y / 2);
        p.popMatrix();
        if (debug) {
            PVector pfPosition = new PVector(position.x - ((pfSize - 1) * 12.5f), position.y - ((pfSize - 1) * 12.5f));
            p.stroke(0, 0, 255);
            p.line(pfPosition.x - 10, pfPosition.y, pfPosition.x + 10, pfPosition.y);
            p.stroke(255, 0, 0);
            p.line(pfPosition.x, pfPosition.y - 10, pfPosition.x, pfPosition.y + 10);
            p.noFill();
            p.stroke(255, 0, 255);
            p.rect(pfPosition.x - 12.5f, pfPosition.y - 12.5f, pfSize * 25, pfSize * 25);
        }
    }
}
