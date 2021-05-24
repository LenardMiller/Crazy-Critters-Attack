package main.projectiles.enemyProjeciles;

import main.projectiles.Projectile;
import main.towers.turrets.Turret;
import processing.core.PApplet;

public abstract class EnemyProjectile extends Projectile {

    protected EnemyProjectile(PApplet p, int damage, float x, float y, float angle, Turret turret) {
        super(p, x, y, angle, turret);
        this.damage = damage;
    }

    @Override
    public void checkCollision() {
        if (collidingWithTurret()) {
            turret.damage(damage);
            dead = true;
        }
    }

    private boolean collidingWithTurret() {
        if (turret == null) return false;
        float x = turret.tile.position.x - turret.size.x;
        float y = turret.tile.position.y - turret.size.y;
        boolean matchX = position.x > x && position.x < x + turret.size.x;
        boolean matchY = position.y > y && position.y < y + turret.size.y;
        return matchX && matchY;
    }
}
