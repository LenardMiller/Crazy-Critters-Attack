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
        maxSpeed = 20;
        speed = maxSpeed;
        moneyDrop = 1000;
        damage = 10;
        shootDamage = 1;
        maxHp = 5000;
        hp = maxHp;
        range = 130;
        orbitAngleMinSpeed = 0.1f;
        orbitAngleSpeed = orbitAngleMinSpeed;
        orbitAngleTopSpeed = 0.5f;
        orbitAngleSpeedChange = 0.01f;
        name = "iceMonstrosity";
        orbitSprite = staticSprites.get("iceMonstrosityOrbitEn");
        corpseSize = size;
        partSize = new PVector(14, 14);
        loadStuff();
    }

    @Override
    protected void fire(float projectileAngle, PVector projectilePosition) {
        orbitAngleSpeed = orbitAngleTopSpeed;
        for (int i = 0; i < 5; i++) {
            int deflectionAmount = i * 10;
            float deflection = radians(p.random(-deflectionAmount, deflectionAmount));
            projectiles.add(new IceCrystal(p, shootDamage, projectilePosition.x, projectilePosition.y,
              projectileAngle + deflection));
        }
    }
}