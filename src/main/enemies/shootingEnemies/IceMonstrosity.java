package main.enemies.shootingEnemies;

import main.damagingThings.projectiles.enemyProjeciles.IceCrystal;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.projectiles;
import static main.Main.staticSprites;
import static processing.core.PApplet.radians;

public class IceMonstrosity extends IceEntity {

    public IceMonstrosity(PApplet p, float x, float y) {
        super(p, x, y);
        size = new PVector(50, 50);
        pfSize = 2;
        radius = 25;
        maxSpeed = 30;
        speed = maxSpeed;
        moneyDrop = 1000;
        damage = 10;
        shootDamage = 2;
        maxHp = 5000;
        hp = maxHp;
        range = 150;
        name = "iceMonstrosity";
        orbitSprite = staticSprites.get("iceMonstrosityOrbitEn");
        corpseSize = size;
        loadStuff();
    }

    @Override
    protected void fire(float projectileAngle, PVector projectilePosition) {
        orbitAngleSpeed = 1;
        for (int i = 0; i < 5; i++) {
            float deflection = radians(p.random(-45, 45));
            projectiles.add(new IceCrystal(p, shootDamage, projectilePosition.x, projectilePosition.y,
              projectileAngle + deflection, target));
        }
    }
}
