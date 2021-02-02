package main.projectiles;

import main.enemies.Enemy;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

import static main.Main.debug;
import static main.Main.enemies;
import static processing.core.PApplet.*;

public class Arc {

    private final PApplet P;
    private final PVector START_POSITION;
    private final Turret TURRET;
    private final int DAMAGE;
    private final int MAX_LENGTH;
    private final int MAX_DISTANCE;
    private final int PRIORITY;

    ArrayList<BigPoint> bigPoints;
    public int alpha;
    private static int variation;
    private static int maxPoints;

    public Arc(PApplet p, float startX, float startY, Turret turret, int damage, int maxLength, int maxDistance, int priority) {
        this.P = p;
        START_POSITION = new PVector(startX,startY);
        this.TURRET = turret;
        this.DAMAGE = damage;
        this.MAX_LENGTH = maxLength;
        this.MAX_DISTANCE = maxDistance;
        this.PRIORITY = priority;
        bigPoints = new ArrayList<>();
        alpha = 255;
        variation = 25; //25
        maxPoints = 10;
        zap();
    }

    public void main() {
        for (int k = 0; k < bigPoints.size()-1; k++) {
//            bigPoints.get(k).getPoints(bigPoints.get(k+1).position);
            P.stroke(215,242,248, alpha);
            P.fill(255);
            PVector pointB = bigPoints.get(k).position;
            PVector pointA = bigPoints.get(k+1).position;
            PVector[] points = bigPoints.get(k).points;
            if (debug) P.ellipse(points[1].x,points[1].y,5,5);
            P.line(pointB.x,pointB.y,points[points.length-1].x,points[points.length-1].y);
            for (int i = points.length-1; i > 1; i--) {
                P.line(points[i].x,points[i].y,points[i-1].x,points[i-1].y);
                if (debug) P.ellipse(points[i].x,points[i].y,5,5);
            }
            P.line(points[1].x,points[1].y,pointA.x,pointA.y);
        }
        alpha -= 5;
    }

    private void zap() {
        bigPoints.add(new BigPoint(P, START_POSITION));
        ArrayList<Enemy> hitEnemies = new ArrayList<>();
        Enemy enemy = getTargetEnemy(START_POSITION, PRIORITY,false, hitEnemies);
        if (enemy != null) {
            int enId = 0;
            for (int j = enemies.size() - 1; j >= 0; j--) if (enemies.get(j) == enemy) enId = j;
            enemy.damagePj(DAMAGE, "null", 0, 0, TURRET, true, "normal", new PVector(0,0), enId);
            hitEnemies.add(enemy);
            bigPoints.add(new BigPoint(P, enemy.position));
            int x = 2;
            for (int i = 1; i < MAX_LENGTH; i++) {
                Enemy enemyJ = getTargetEnemy(bigPoints.get(x - 1).position, 0, true, hitEnemies);
                if (enemyJ != null) {
                    bigPoints.add(new BigPoint(P, enemyJ.position));
                    enId = 0;
                    for (int j = enemies.size() - 1; j >= 0; j--) if (enemies.get(j) == enemyJ) enId = j;
                    enemyJ.damagePj(DAMAGE, "null", 0, 0, TURRET, true, "normal", new PVector(0,0), enId);
                    hitEnemies.add(enemyJ);
                    x++;
                }
            }
            for (int i = 0; i < bigPoints.size() - 1; i++) bigPoints.get(i).getPoints(bigPoints.get(i + 1).position);
        }
    }

    private Enemy getTargetEnemy(PVector position, int targetting, boolean jumping, ArrayList<Enemy> enemiesRepeat) {
        //0: close
        //1: far
        //2: strong
        float dist;
        if (targetting == 0) dist = 1000000;
        else dist = 0;
        float maxHp = 0;
        Enemy e = null;
        for (Enemy enemy : enemies) {
            if (!enemy.stealthMode) {
                boolean repeat = false;
                for (Enemy enemyRepeat : enemiesRepeat)
                    if (enemy == enemyRepeat) {
                        repeat = true;
                        break;
                    }
                if (repeat) continue;
                float x = abs(position.x - enemy.position.x);
                float y = abs(position.y - enemy.position.y);
                float t = sqrt(sq(x) + sq(y));
                if (jumping && t > MAX_DISTANCE) continue;
                if (targetting == 0 && t < dist) { //close
                    e = enemy;
                    dist = t;
                }
                if (targetting == 1 && t > dist) { //far
                    e = enemy;
                    dist = t;
                }
                if (targetting == 2) if (enemy.maxHp > maxHp) { //strong
                    e = enemy;
                    maxHp = enemy.maxHp;
                } else if (enemy.maxHp == maxHp && t < dist) { //strong -> close
                    e = enemy;
                    dist = t;
                }
            }
        }
        return e;
    }

    private static class BigPoint {

        private final PApplet P;

        PVector position;
        PVector[] points;

        private BigPoint(PApplet p, PVector position) {
            this.position = position;
            this.P = p;
        }

        void getPoints(PVector pointA) {
            PVector pointB = position;
            points = new PVector[(int) P.random(3,maxPoints)];
            float lineLength = sqrt(sq(0.0f)+sq(pointB.y-pointA.y));
            float d = lineLength / points.length+1;
            for (int i = 1; i < points.length; i++) {
                float di = d*(i);
                PVector e = new PVector(pointA.x-pointB.x,pointA.y-pointB.y);
                e.setMag(di);
                e.x *= -1;
                e.y *= -1;
                points[i] = new PVector(e.x+pointA.x+ P.random(-variation,variation),e.y+pointA.y+ P.random(-variation,variation));
            }
        }
    }
}
