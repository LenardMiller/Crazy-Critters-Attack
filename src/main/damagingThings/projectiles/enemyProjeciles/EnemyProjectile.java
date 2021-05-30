package main.damagingThings.projectiles.enemyProjeciles;

import main.damagingThings.projectiles.Projectile;
import main.towers.Tower;
import main.towers.turrets.Turret;
import processing.core.PApplet;

import static main.Main.towers;

public abstract class EnemyProjectile extends Projectile {

    protected EnemyProjectile(PApplet p, int damage, float x, float y, float angle) {
        super(p, x, y, angle, null);
        this.damage = damage;
    }

    @Override
    public void checkCollision() {
        Turret turret = collidingWithTurret();
        if (turret != null) turret.damage(damage);
    }

    private Turret collidingWithTurret() {
        for (Tower tower : towers) {
            if (!(tower instanceof Turret)) continue;
            Turret turret = (Turret) tower;
            if (!turret.alive) continue;
            float x = turret.tile.position.x - turret.size.x;
            float y = turret.tile.position.y - turret.size.y;
            boolean matchX = position.x > x && position.x < x + turret.size.x;
            boolean matchY = position.y > y && position.y < y + turret.size.y;
            if (matchX && matchY) return turret;
        }
        return null;
    }
}
