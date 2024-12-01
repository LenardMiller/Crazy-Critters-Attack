package main.projectiles.arcs;

import main.enemies.Enemy;
import main.enemies.BurrowingEnemy;
import main.particles.MiscParticle;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;
import java.util.ArrayList;

import static main.Main.*;
import static main.misc.Utilities.up60ToFramerate;

public class Arc {

    protected final int damage;
    protected final int maxLength;
    protected final int maxDistance;
    protected final Turret.Priority priority;
    protected final ArrayList<StartEndPoints> bigPoints;
    protected final PApplet p;
    protected final PVector startPosition;
    protected final Turret turret;

    protected final Enemy blacklistedEnemy;

    protected Color lineColor;
    protected Enemy.DamageType particleType;
    protected int maxPoints;
    protected int variation;
    protected int weight;
    protected int particleChance;

    public int alpha;

    /** Used by Tesla Tower. */
    public Arc(PApplet p, float startX, float startY, Turret turret, int damage, int maxLength, int maxDistance, Turret.Priority priority) {
        this(p, startX, startY, turret, damage, maxLength, maxDistance, priority, null);
    }

    /** Used by lightning caller. */
    public Arc(PApplet p, float startX, float startY, Turret turret, int damage, int maxLength, int maxDistance,
               Turret.Priority priority, Enemy blacklistedEnemy) {
        this.p = p;
        startPosition = new PVector(startX, startY);
        this.turret = turret;
        this.damage = damage;
        this.maxLength = maxLength;
        this.maxDistance = maxDistance;
        this.priority = priority;
        bigPoints = new ArrayList<>();
        alpha = 255;
        variation = 25;
        maxPoints = 10;
        weight = 1;
        particleChance = 1;
        lineColor = new Color(215, 242, 248);
        particleType = Enemy.DamageType.electricity;
        this.blacklistedEnemy = blacklistedEnemy;
    }

    public void display() {
        p.stroke(lineColor.getRGB(), alpha);
        p.fill(255);
        p.strokeWeight(weight);
        for (int k = 0; k < bigPoints.size() - 1; k++) {
            PVector pointB = bigPoints.get(k).position;
            PVector pointA = bigPoints.get(k + 1).position;
            PVector[] points = bigPoints.get(k).points;
            if (isDebug) p.ellipse(points[1].x, points[1].y, 5, 5);
            p.line(pointB.x, pointB.y, points[points.length - 1].x, points[points.length - 1].y);
            for (int i = points.length - 1; i > 1; i--) {
                p.line(points[i].x, points[i].y, points[i - 1].x, points[i - 1].y);
                if (isDebug) p.ellipse(points[i].x, points[i].y, 5, 5);
            }
            p.line(points[1].x, points[1].y, pointA.x, pointA.y);
        }
        p.strokeWeight(1);
    }

    public void update(int j) {
        if (isPaused) return;
        if (alpha == 255) zap(blacklistedEnemy);
        if (!isPaused) alpha -= up60ToFramerate(8);
        if (alpha <= 0) arcs.remove(j);
    }

    /** Creates points that define effect, damages enemies **/
    protected void zap(Enemy blacklistedEnemy) {
        bigPoints.add(new StartEndPoints(p, startPosition));
        ArrayList<Enemy> hitEnemies = new ArrayList<>();
        if (blacklistedEnemy != null) hitEnemies.add(blacklistedEnemy);
        Enemy mainEnemy = getTargetEnemy(startPosition, priority, hitEnemies);
        if (mainEnemy != null) {
            damageEnemy(mainEnemy, damage, turret);
            hitEnemies.add(mainEnemy);
            bigPoints.add(new StartEndPoints(p, new PVector(mainEnemy.position.x, mainEnemy.position.y)));
            int x = 2; //no clue
            for (int i = 1; i < maxLength; i++) {
                Enemy jumpEnemy = getTargetEnemy(bigPoints.get(x - 1).position, Turret.Priority.Close, hitEnemies);
                if (jumpEnemy != null) {
                    bigPoints.add(new StartEndPoints(p, jumpEnemy.position));
                    damageEnemy(jumpEnemy, damage, turret);
                    hitEnemies.add(jumpEnemy);
                    x++;
                }
            }
            for (int i = 0; i < bigPoints.size() - 1; i++)
                bigPoints.get(i).getPoints(
                        bigPoints.get(i + 1).position,
                        particleChance, maxPoints,
                        turret != null && turret.boostedDamage() > 0);
        } else {
            float angle = p.random(TWO_PI);
            float mag = p.random(maxDistance / 4f, maxDistance);
            PVector position = PVector.fromAngle(angle);
            position = position.setMag(mag);
            position.add(startPosition);
            bigPoints.add(new StartEndPoints(p, new PVector(position.x, position.y)));
            for (int i = 0; i < bigPoints.size() - 1; i++)
                bigPoints.get(i).getPoints(
                        bigPoints.get(i + 1).position,
                        particleChance, maxPoints,
                        turret != null && turret.boostedDamage() > 0);
        }
    }

    protected void damageEnemy(Enemy enemy, int damage, Turret turret) {
        enemy.damageWithoutBuff(damage, turret, particleType, new PVector(0, 0), true);
    }

    protected Enemy getTargetEnemy(PVector position, Turret.Priority targeting, ArrayList<Enemy> enemiesRepeat) {
        //-1: none
        //0: close
        //1: far
        //2: strong
        if (targeting == Turret.Priority.None) return null;
        float dist;
        if (targeting == Turret.Priority.Close) dist = 1000000;
        else dist = 0;
        float maxHp = 0;
        Enemy e = null;
        for (Enemy enemy : enemies) {
            if (!(enemy.state == Enemy.State.Moving && enemy instanceof BurrowingEnemy)) {
                boolean repeat = false;
                //prevent hitting enemy twice
                for (Enemy enemyRepeat : enemiesRepeat)
                    if (enemy == enemyRepeat) {
                        repeat = true;
                        break;
                    }
                if (repeat) continue;
                if (!enemy.onScreen()) continue;
                float x = abs(position.x - enemy.position.x);
                float y = abs(position.y - enemy.position.y);
                float t = sqrt(sq(x) + sq(y));
                if (t > maxDistance) continue;
                switch (priority) {
                    case Close -> {
                        if (t >= dist) break;
                        e = enemy;
                        dist = t;
                    } case Far -> {
                        if (t <= dist) break;
                        e = enemy;
                        dist = t;
                    } case Strong -> {
                        if (enemy.maxHp > maxHp) { //strong
                            e = enemy;
                            maxHp = enemy.maxHp;
                        } else if (enemy.maxHp == maxHp && t < dist) { //strong -> close
                            e = enemy;
                            dist = t;
                        }
                    }
                }
            }
        }
        return e;
    }

    protected class StartEndPoints {

        private final PApplet p;

        PVector position;
        PVector[] points;

        protected StartEndPoints(PApplet p, PVector position) {
            this.position = position;
            this.p = p;
        }

        protected void getPoints(PVector pointA, int particleChance, int maxPoints, boolean boosted) {
            PVector pointB = position;
            points = new PVector[(int) p.random(3, maxPoints)];
            float lineLength = sqrt(sq(pointB.x - pointA.x) + sq(pointB.y - pointA.y));
            float d = lineLength / points.length + 1;
            for (int i = 1; i < points.length; i++) {
                float di = d * (i);
                PVector e = new PVector(pointA.x - pointB.x, pointA.y - pointB.y);
                e.setMag(di);
                e.x *= -1;
                e.y *= -1;
                points[i] = new PVector(e.x + pointA.x + p.random(-variation, variation), e.y + pointA.y + p.random(-variation, variation));
                if (p.random(particleChance) < 1)
                    topParticles.add(new MiscParticle(p, points[i].x, points[i].y, p.random(360), particleType.name()));
                if (boosted && p.random(particleChance) < 1)
                    topParticles.add(new MiscParticle(p, points[i].x, points[i].y, p.random(360), "orangeMagic"));
            }
        }
    }
}
