package main.projectiles.enemyProjeciles;

import main.particles.Ouch;
import main.towers.turrets.Turret;
import processing.core.PApplet;

import static main.Main.projectiles;
import static main.Main.topParticles;

public class Snowball extends EnemyProjectile {

    public Snowball(PApplet p, int damage, float x, float y, float angle, Turret turret) {
        super(p, damage, x, y, angle, turret);
    }

    @Override
    public void die() {
        topParticles.add(new Ouch(p,position.x,position.y,p.random(0,360),"snowPuff"));
        projectiles.remove(this);
    }
}
