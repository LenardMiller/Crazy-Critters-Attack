package main.enemies;

import main.Main;
import main.buffs.Buff;
import main.buffs.Burning;
import main.buffs.Poisoned;
import main.buffs.Wet;
import main.particles.Ouch;
import main.pathfinding.Node;
import main.pathfinding.PathRequest;
import main.towers.Tower;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import static main.Main.*;
import static main.misc.MiscMethods.findAngleBetween;
import static main.misc.MiscMethods.roundTo;

public abstract class Enemy {

    private PApplet p;

    public ArrayList<TurnPoint> points;
    public int pfSize;
    public PVector position;
    public PVector size;
    private float angle;
    public float radius;
    public float maxSpeed;
    public float speed;
    public int dangerLevel;
    int twDamage;
    public int maxHp;
    public int hp;
    private PImage sprite;
    private PImage[] attackFrames;
    private PImage[] moveFrames;
    private float moveFrame;
    int attackFrame;
    int[] attackDmgFrames;
    public boolean attacking;
    private boolean attackCue;
    int numAttackFrames;
    int numMoveFrames;
    int betweenWalkFrames;
    int betweenAttackFrames;
    int idleTime;
    int startFrame;
    public int barTrans;
    public int tintColor;
    String hitParticle;
    public String name;
    private Tower target;

    public Enemy(PApplet p, float x, float y) {
        this.p = p;

        points = new ArrayList<>();
        position = new PVector(roundTo(x, 25) + 12.5f, roundTo(y, 25) + 12.5f);
        size = new PVector(20, 20);
        angle = radians(270);
        radius = 10;
        maxSpeed = 1;
        speed = maxSpeed;
        dangerLevel = 1;
        twDamage = 1;
        maxHp = 20; //Hp <---------------------------
        hp = maxHp;
        barTrans = 0;
        tintColor = 255;
        hitParticle = "redOuch";
        name = "null";
        numAttackFrames = 1;
        numMoveFrames = 1;
        startFrame = 0;
        betweenWalkFrames = 0;
        attackDmgFrames = new int[]{0};
        loadSprites();
        pfSize = 1; //enemies pathfinding size, multiplied by twenty-five
    }

    public void main(int i) {
        boolean dead = false; //if its gotten this far, it must be alive?
        swapPoints(false);
        if (!attacking) move();
        else attack();
        if (points.size() != 0 && intersectTurnPoint()) swapPoints(true);
        display();
        //prevent from going offscreen
        if (position.x >= GRID_WIDTH-100 || position.x <= -100 || position.y >= GRID_HEIGHT-100 || position.y <= -100) dead = true;
        //if health is 0, die
        if (hp <= 0) dead = true;
        if (dead) die(i);
    }

    private void die(int i) {
        Main.money += dangerLevel;
        int num = (int) (p.random(2, 5));
        for (int j = num; j >= 0; j--) { //creates death particles
            particles.add(new Ouch(p, position.x + p.random((size.x / 2) * -1, size.x / 2), position.y + p.random((size.y / 2) * -1, size.y / 2), p.random(0, 360), "greyPuff"));
        }
        for (int j = buffs.size() - 1; j >= 0; j--) { //deals with buffs
            Buff buff = buffs.get(j);
            //if attached, remove
            if (buff.enId == i) buffs.remove(j);
                //shift ids to compensate for enemy removal
            else if (buff.enId > i) buff.enId -= 1;
        }
        enemies.remove(i);
    }

    private void move() { //todo: add super stylish turning system
        PVector m = PVector.fromAngle(angle);
        m.setMag(speed);
        position.add(m);
        speed = maxSpeed;
    }

    private void attack() {
        boolean dmg = false;
        for (int frame : attackDmgFrames) {
            if (attackFrame == frame) {
                dmg = true;
                break;
            }
        }
        if (target != null) {
//            angle = radians(roundTo(degrees(findAngleBetween(target.tile.position, position)), 90));
            angle = findAngleBetween(target.tile.position, position); //todo: angle better
            moveFrame = 0;
            if (dmg) target.damage(twDamage);
        }
        if (!attackCue && attackFrame == startFrame) {
            attacking = false;
            attackFrame = startFrame;
        }
    }

    //todo: dance?

    private void preDisplay() {
        if (attacking) {
            sprite = attackFrames[attackFrame];
            idleTime++;
            if (attackFrame < numAttackFrames - 1) {
                if (idleTime >= betweenAttackFrames) {
                    attackFrame += 1;
                    idleTime = 0;
                }
            } else attackFrame = 0;
        } else {
            sprite = moveFrames[(int) (moveFrame)];
            idleTime++;
            if (moveFrame < numMoveFrames - 1) {
                if (idleTime >= betweenWalkFrames) {
                    moveFrame += speed;
                    idleTime = 0;
                }
            } else moveFrame = 0;
        }
        //shift back to normal
        if (tintColor < 255) tintColor += 20;
    }

    private void display() {
        preDisplay();
        if (debug) for (int i = points.size() - 1; i > 0; i--) {
            points.get(i).display();
        }
        p.pushMatrix();
        p.translate(position.x, position.y);
        p.rotate(angle);
        p.image(sprite, -size.x / 2, -size.y / 2);
        p.popMatrix();
        if (hp > 0) HpBar();
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

    public void collidePJ(int damage, String pjBuff, Tower tower, int i) { //when the enemy hits a projectile
        hp -= damage;
        if (tower != null) {
            if (hp <= 0) {
                tower.killsTotal++;
                tower.damageTotal += damage + hp;
            } else tower.damageTotal += damage;
        }
        if (pjBuff.equals("poison")) { //applies buffs
            if (buffs.size() > 0) {
                for (int j = buffs.size() - 1; j >= 0; j--) {
                    Buff buff = buffs.get(j);
                    if (buff.particle.equals("poison") && buff.enId == i) {
                        buffs.remove(j);
                    }
                }
            }
            buffs.add(new Poisoned(p, i));
        }
        if (pjBuff.equals("wet")) {
            if (buffs.size() > 0) {
                for (int j = buffs.size() - 1; j >= 0; j--) {
                    Buff buff = buffs.get(j);
                    if (buff.particle.equals("water") && buff.enId == i) {
                        buffs.remove(j);
                    }
                }
            }
            buffs.add(new Wet(p, i));
        }
        if (pjBuff.equals("burning")) {
            if (buffs.size() > 0) {
                for (int j = buffs.size() - 1; j >= 0; j--) {
                    Buff buff = buffs.get(j);
                    if (buff.particle.equals("fire") && buff.enId == i) {
                        buffs.remove(j);

                    }
                }
            }
            buffs.add(new Burning(p, i));
        }
        barTrans = 255;
        tintColor = 0;
        int num = (int) (p.random(1, 3));
        for (int j = num; j >= 0; j--) { //sprays ouch
            particles.add(new Ouch(p, position.x + p.random((size.x / 2) * -1, size.x / 2), position.y + p.random((size.y / 2) * -1, size.y / 2), p.random(0, 360), hitParticle));
        }
    }

    private void HpBar() {
        p.fill(255, 0, 0, barTrans);
        p.noStroke();
        p.rect(position.x - size.x/2 - 10, position.y + size.y / 2 + 6, (size.x + 20) * (((float) hp) / ((float) maxHp)), 6);
    }

    public void loadSprites() {
        attackFrames = spritesAnimH.get(name + "AttackEN");
        moveFrames = spritesAnimH.get(name + "MoveEN");
    }

    //pathfinding

    private boolean intersectTurnPoint() {
        TurnPoint point = points.get(points.size() - 1);
        PVector p = point.position;
        boolean intersecting;
        float tpSize;
        if (point.combat) tpSize = speed;
        else tpSize = 5;
        PVector pfPosition = new PVector(position.x - ((pfSize - 1) * 12.5f), position.y - ((pfSize - 1) * 12.5f));
        intersecting = (pfPosition.x > p.x - tpSize + (nSize / 2f) && pfPosition.x < p.x + tpSize + (nSize / 2f)) && (pfPosition.y > p.y - tpSize + (nSize / 2f) && pfPosition.y < p.y + tpSize + (nSize / 2f));
        return intersecting;
    }

    public void requestPath(int i) {
        path.reqQ.add(new PathRequest(i, enemies.get(i)));
    }

    public void swapPoints(boolean remove) {
        if (points.size() != 0) {
            TurnPoint intersectingPoint = points.get(points.size() - 1);
            if (remove) {
                if (intersectingPoint.combat) {
                    attacking = true;
                    attackCue = true;
                    target = intersectingPoint.tower;
                } else attackCue = false;
                points.remove(intersectingPoint);
            }
            if (points.size() != 0) {
                PVector pointPosition = points.get(points.size() - 1).position;
                pointPosition = new PVector(pointPosition.x + 12.5f, pointPosition.y + 12.5f);
                pointPosition = new PVector(pointPosition.x + ((pfSize - 1) * 12.5f), pointPosition.y + ((pfSize - 1) * 12.5f));
                angle = findAngleBetween(pointPosition, position);
            }
        }
    }

    public void cleanTurnPoints() {
        ArrayList<TurnPoint> pointsD = new ArrayList<>(points);
        //handle combat points
        boolean combatPoints = false;
        for (TurnPoint point : pointsD) {
            point.towers = clearanceTowers(point);
            if (point.towers.size() > 0) combatPoints = true;
        }
        if (combatPoints) {
            TurnPoint backPoint;
            backPoint = backPoint();
            backPoint.combat = true;
            backPoint.tower = backPoint.towers.get(floor(backPoint.towers.size() / 2f));
        }
        //remove extra white points
        TurnPoint startpoint = pointsD.get(pointsD.size()-1);
        if (!startpoint.combat && !attacking) pointsD.remove(startpoint);
        for (int i = 0; i < pointsD.size()-2; i++) {
            TurnPoint pointA = pointsD.get(i);
            TurnPoint pointB = pointsD.get(i+1);
            TurnPoint pointC = pointsD.get(i+2);
            float angleAB = findAngleBetween(pointA.position, pointB.position);
            float angleBC = findAngleBetween(pointB.position, pointC.position);
            if (angleAB == angleBC && !pointB.combat) {
                pointsD.remove(pointB);
                i--;
            }
            if (i+1 == pointsD.size()+2) break;
        }
        points = new ArrayList<>();
        points.addAll(pointsD);
    }

    private ArrayList<Tower> clearanceTowers(TurnPoint point) { //todo: fix weird clearance around end points
        ArrayList<Tower> towers = new ArrayList<>();
        boolean clear = true;
        int kSize = 1;
        int x = (int) (point.position.x+100) / 25;
        int y = (int) (point.position.y+100) / 25;
        while (true) {
            for (int xn = 0; xn < kSize; xn++) {
                for (int yn = 0; yn < kSize; yn++) {
                    if (!(x + xn >= nodeGrid.length || y + yn >= nodeGrid[x].length)) {
                        Node nodeB = nodeGrid[x + xn][y + yn];
                        if (nodeB.tower != null) towers.add(nodeB.tower);
                    } else {
                        clear = false;
                        break;
                    }
                }
                if (!clear) break;
            }
            if (clear && kSize < pfSize) kSize++;
            else break;
        }
        //deletes duplicates
        CopyOnWriteArrayList<Tower> towersB = new CopyOnWriteArrayList<>(towers);
        for (int i = 0; i < towersB.size() - 1; i++) if (towersB.get(i) == towersB.get(i++)) towersB.remove(i);
        towers = new ArrayList<>(towersB);
        return towers;
    }

    private TurnPoint backPoint() {
        TurnPoint bp = null;
        for (int i = points.size()-1; i >= 0; i--) {
            if (points.get(i).towers != null && points.get(i).towers.size() > 0) {
                points.get(i).combat = true;
                if (i < points.size()-1) bp = points.get(i + 1);
                else bp = points.get(i);
                bp.towers = points.get(i).towers;
                bp.combat = true;
                bp.back = true;
                break;
            }
        }
        return bp;
    }

    public static class TurnPoint {

        private PApplet p;
        public Tower tower;
        public ArrayList<Tower> towers;
        public PVector position;
        boolean combat;
        boolean back;

        public TurnPoint(PApplet p, PVector position, Tower tower) {
            this.p = p;
            this.position = new PVector(position.x, position.y);
            this.tower = tower;

            combat = false;
            back = false;
        }

        public void display() {
            p.noStroke();
            if (back) p.stroke(0, 255, 0);
            else p.noStroke();
            if (combat) p.fill(255, 0, 0);
            else p.fill(255);
            p.ellipse(position.x + nSize / 2f, position.y + nSize / 2f, nSize, nSize);
            hover();
        }

        private void hover() {
            boolean intersecting;
            float tpSize = 10;
            PVector pfPosition = new PVector(p.mouseX, p.mouseY);
            intersecting = (pfPosition.x > position.x - tpSize + (nSize / 2f) && pfPosition.x < position.x + tpSize + (nSize / 2f)) && (pfPosition.y > position.y - tpSize + (nSize / 2f) && pfPosition.y < position.y + tpSize + (nSize / 2f));
            if (intersecting && tower != null) {
                p.stroke(255, 255, 0);
                p.noFill();
                p.rect(tower.tile.position.x - 50, tower.tile.position.y - 50, 50, 50);
            }
        }
    }
}
