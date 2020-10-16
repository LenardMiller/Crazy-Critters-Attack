package main.enemies;

import main.Main;
import main.buffs.*;
import main.misc.Corpse;
import main.misc.Tile;
import main.particles.Ouch;
import main.particles.Pile;
import main.pathfinding.Node;
import main.pathfinding.PathRequest;
import main.towers.Tower;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import static main.Main.*;
import static main.misc.MiscMethods.*;

public abstract class Enemy {

    PApplet p;

    public ArrayList<TurnPoint> points;
    public int pfSize;
    public PVector position;
    public PVector size;
    public float angle;
    private float targetAngle;
    public float radius;
    public float maxSpeed;
    public float speed;
    public int moneyDrop;
    int damage;
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
    int attackStartFrame;
    public int barTrans;
    public int tintColor;
    public String hitParticle;
    public String name;
    private Tower targetTower;
    private boolean targetMachine;
    public boolean stealthMode;
    boolean stealthy;
    public boolean flying;
    private int attackCount;
    PVector corpseSize;
    PVector partSize;
    int betweenCorpseFrames;
    int corpseLifespan;
    public String lastDamageType;
    boolean overkill;
    PVector partsDirection;

    public Enemy(PApplet p, float x, float y) {
        this.p = p;

        points = new ArrayList<>();
        position = new PVector(roundTo(x, 25) + 12.5f, roundTo(y, 25) + 12.5f);
        size = new PVector(20, 20);
        angle = radians(270);
        radius = 10;
        maxSpeed = 1;
        speed = maxSpeed;
        moneyDrop = 1;
        damage = 1;
        maxHp = 20; //Hp <---------------------------
        hp = maxHp;
        barTrans = 0;
        tintColor = 255;
        hitParticle = "redOuch";
        name = "";
        numAttackFrames = 1;
        numMoveFrames = 1;
        attackStartFrame = 0;
        betweenWalkFrames = 0;
        attackDmgFrames = new int[]{0};
        pfSize = 1; //enemies pathfinding size, measured in nodes
        stealthy = false;
        stealthMode = false;
        flying = false;
        attackCount = 0;
        corpseSize = size;
        partSize = size;
        betweenCorpseFrames = 7;
        corpseLifespan = 500;
        lastDamageType = "normal";
    }

    public void main(int i) {
        boolean dead = false; //if its gotten this far, it must be alive?
        swapPoints(false);

        angle = clampAngle(angle);
        targetAngle = clampAngle(targetAngle);
        angle += angleDifference(targetAngle, angle) / 10;

        if (!attacking) {
            stealthMode = stealthy;
            //todo: make work with other backgrounds
            move();
        }
        else {
            attack();
            stealthMode = false;
        }
        if (points.size() != 0 && intersectTurnPoint()) swapPoints(true);
        displayPassB();
        //prevent from going offscreen
        if (position.x >= GRID_WIDTH - 100 || position.x <= -100 || position.y >= GRID_HEIGHT - 100 || position.y <= -100)
            dead = true;
        //if health is 0, die
        if (hp <= 0) dead = true;
        if (dead) die(i);
    }

    private void die(int i) {
        Main.money += moneyDrop;

        String type = lastDamageType;
        for (Buff buff : buffs) {
            if (buff.enId == i) {
                type = buff.name;
            }
        }
        if (!stealthMode) {
            if (overkill) {
                for (int j = 0; j < spritesAnimH.get(name + "PartsEN").length; j++) {
                    float maxRv = 200f / partSize.x;
                    corpses.add(new Corpse(p, position, partSize, angle, partsDirection, p.random(radians(-maxRv), radians(maxRv)), 0, corpseLifespan, type, name + "Parts", hitParticle, j, false));
                }
                for (int k = 0; k < sq(pfSize); k++) {
                    underParticles.add(new Pile(p, (float) (position.x + 2.5 + p.random((size.x / 2) * -1, (size.x / 2))), (float) (position.y + 2.5 + p.random((size.x / 2) * -1, (size.x / 2))), 0, hitParticle));
                }
            } else
                corpses.add(new Corpse(p, position, corpseSize, angle + p.random(radians(-5), radians(5)), new PVector(0, 0), 0, betweenCorpseFrames, corpseLifespan, type, name + "Die", "none", 0, true));
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

    void move() {
        PVector m = PVector.fromAngle(angle);
        m.setMag(speed);
        position.add(m);
        speed = maxSpeed;
    }

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

    public void displayPassA() {
        preDisplay();
        p.pushMatrix();
        p.tint(0, 60);
        int x = 1;
        if (pfSize > 1) x++;
        p.translate(position.x + x, position.y + x);
        p.rotate(angle);
        p.image(sprite, -size.x / 2, -size.y / 2);
        p.tint(255);
        p.popMatrix();
    }

    public void displayPassB() {
        if (debug) for (int i = points.size() - 1; i > 0; i--) {
            points.get(i).display();
        }
        p.pushMatrix();
        p.translate(position.x, position.y);
        p.rotate(angle);
        p.image(sprite, -size.x / 2, -size.y / 2);
        p.popMatrix();
        if (hp > 0 && !stealthMode) HpBar();
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

    public void damagePj(int damage, String pjBuff, int effectLevel, int effectDuration, Turret turret, boolean splash, String type, PVector direction, int i) { //when the enemy hits a projectile
        lastDamageType = type;
        overkill = damage >= maxHp;
        partsDirection = direction;
        hp -= damage;
        if (turret != null) {
            if (hp <= 0) {
                turret.killsTotal++;
                turret.damageTotal += damage + hp;
            } else turret.damageTotal += damage;
        }
        if (buffs.size() > 0) {
            for (int j = 0; j < buffs.size(); j++) {
                Buff buff = buffs.get(j);
                if (buff.enId == i && buff.name.equals(pjBuff)) {
                    buffs.remove(j);
                    break;
                }
            }
        }
        if (pjBuff != null) {
            switch (pjBuff) {
                case "wet":
                    buffs.add(new Wet(p, i, turret));
                    break;
                case "burning":
                    buffs.add(new Burning(p, i, effectLevel, effectDuration, turret));
                    break;
                case "bleeding":
                    buffs.add(new Bleeding(p, i, turret));
                    break;
                case "poisoned":
                    buffs.add(new Poisoned(p, i, turret));
                    break;
                case "decay":
                    if (turret != null) buffs.add(new Decay(p, i, effectLevel, effectDuration, turret));
                    else buffs.add(new Decay(p, i, 1, 120, null));
                    break;
            }
        }
        damageEffect(splash);
    }

    private void damageEffect(boolean parts) {
        barTrans = 255;
        tintColor = 0;
        if (parts) {
            int num = (int) (p.random(1, 3)) * pfSize * pfSize;
            for (int j = num; j >= 0; j--) { //sprays ouch
                particles.add(new Ouch(p, position.x + p.random((size.x / 2) * -1, size.x / 2), position.y + p.random((size.y / 2) * -1, size.y / 2), p.random(0, 360), hitParticle));
            }
        }
    }

    public void damageSimple(int damage, Turret turret, String type, PVector direction) {
        lastDamageType = type;
        overkill = damage >= maxHp;
        partsDirection = direction;
        hp -= damage;
        if (turret != null) {
            if (hp <= 0) {
                turret.killsTotal++;
                turret.damageTotal += damage + hp;
            } else turret.damageTotal += damage;
        }
        damageEffect(false);
    }

    private void HpBar() {
        p.fill(255, 0, 0, barTrans);
        p.noStroke();
        p.rect(position.x - size.x / 2 - 10, position.y + size.y / 2 + 6, (size.x + 20) * (((float) hp) / ((float) maxHp)), 6);
    }

    public void loadSprites() {
        attackFrames = spritesAnimH.get(name + "AttackEN");
        moveFrames = spritesAnimH.get(name + "MoveEN");
    }

    void attack() {
        boolean dmg = false;
        for (int frame : attackDmgFrames) {
            if (attackFrame == frame) {
                if (betweenAttackFrames > 1) attackCount++;
                dmg = true;
                break;
            }
        }
        //all attackCount stuff prevents attacking multiple times
        if (!dmg) attackCount = 0;
        if (attackCount > 1) dmg = false;
        if (targetTower != null) {
            if (pfSize > 2) { //angle towards tower correctly
                PVector t = new PVector(targetTower.tile.position.x - 25, targetTower.tile.position.y - 25);
                targetAngle = findAngleBetween(t, position);
            }
            moveFrame = 0;
            if (dmg) targetTower.damage(damage);
        }
        if (targetMachine) {
            moveFrame = 0;
            if (dmg) machine.damage(damage);
        }
        if (!attackCue && attackFrame == attackStartFrame) {
            attacking = false;
            attackFrame = attackStartFrame;
        }
    }

    //pathfinding -----------------------------------------------------------------
    //todo: fix big enemy clearance
    //todo: big enemies randomly attack stuff
    //todo: won't target turrets?
    //todo: enemies sometimes wander off if there are a lot of them

    boolean intersectTurnPoint() {
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
                    targetTower = intersectingPoint.tower;
                    targetMachine = intersectingPoint.machine;
                } else attackCue = false;
                points.remove(intersectingPoint);
            }
            if (points.size() != 0) {
                PVector pointPosition = points.get(points.size() - 1).position;
                pointPosition = new PVector(pointPosition.x + 12.5f, pointPosition.y + 12.5f);
                pointPosition = new PVector(pointPosition.x + ((pfSize - 1) * 12.5f), pointPosition.y + ((pfSize - 1) * 12.5f));
                targetAngle = findAngleBetween(pointPosition, position);
            }
        }
    }

    public void cleanTurnPoints() {
        ArrayList<TurnPoint> pointsD = new ArrayList<>(points);
        //handle combat points
        boolean combatPoints = false;
        for (TurnPoint point : pointsD) {
            point.towers = clearanceTowers(point);
            point.machine = clearanceMachine(point);
            if (point.towers.size() > 0 || point.machine) combatPoints = true;
        }
        if (combatPoints) {
            TurnPoint backPoint = backPoint();
            backPoint.combat = true;
            if (backPoint.towers != null && backPoint.towers.size() > 0) { //what the hell is this for??
                backPoint.tower = backPoint.towers.get(floor(backPoint.towers.size() / 2f));
            } else backPoint.tower = null;
        }
        //remove extra white points
        TurnPoint startpoint = pointsD.get(pointsD.size() - 1);
        if (!startpoint.combat && !attacking) pointsD.remove(startpoint);
        for (int i = 0; i < pointsD.size() - 2; i++) {
            TurnPoint pointA = pointsD.get(i);
            TurnPoint pointB = pointsD.get(i + 1);
            TurnPoint pointC = pointsD.get(i + 2);
            float angleAB = findAngleBetween(pointA.position, pointB.position);
            float angleBC = findAngleBetween(pointB.position, pointC.position);
            if (angleAB == angleBC && !pointB.combat) {
                pointsD.remove(pointB);
                i--;
            }
            if (i + 1 == pointsD.size() + 2) break;
        }
        points = new ArrayList<>();
        points.addAll(pointsD);
    }

    private ArrayList<Tower> clearanceTowers(TurnPoint point) {
        //returns all towers in enemy "bubble"?
        ArrayList<Tower> towers = new ArrayList<>();
        boolean clear = true;
        int kSize = 1;
        int x = (int) (point.position.x + 100) / 25;
        int y = (int) (point.position.y + 100) / 25;
        while (true) {
            for (int xn = 0; xn < kSize; xn++) {
                for (int yn = 0; yn < kSize; yn++) {
                    if (!(x + xn >= nodeGrid.length || y + yn >= nodeGrid[x].length)) {
                        Node nodeB = nodeGrid[x + xn][y + yn];
                        if (nodeB.tower != null && !(flying && !nodeB.tower.turret)) towers.add(nodeB.tower);
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

    private boolean clearanceMachine(TurnPoint point) {
        boolean clear = true;
        int kSize = 1;
        int x = (int) (point.position.x + 100) / 25;
        int y = (int) (point.position.y + 100) / 25;
        while (true) {
            for (int xn = 0; xn < kSize; xn++) {
                for (int yn = 0; yn < kSize; yn++) {
                    if (!(x + xn >= nodeGrid.length || y + yn >= nodeGrid[x].length)) {
                        Node nodeB = nodeGrid[x + xn][y + yn];
                        Tile tileB;
                        if (nodeB != null) {
                            tileB = nodeB.getTile();
                            if (tileB != null && tileB.machine) return true;
                        }
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
        return false;
    }

    private TurnPoint backPoint() {
        TurnPoint bp = null;
        for (int i = points.size() - 1; i >= 0; i--) {
            if (points.get(i).towers != null && points.get(i).towers.size() > 0 || points.get(i).machine) {
//                if (points.get(i).machine) System.out.println("test");
                points.get(i).combat = true;
                if (i < points.size() - 1) bp = points.get(i + 1);
                else bp = points.get(i);
                bp.towers = points.get(i).towers;
                bp.machine = points.get(i).machine;
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
        public boolean machine;
        public PVector position;
        boolean combat;
        boolean back;

        public TurnPoint(PApplet p, PVector position, Tower tower) {
            this.p = p;
            this.position = new PVector(position.x, position.y);
            this.tower = tower;

//            machine = tiles.get((int)(position.x/50)+2,(int)(position.y/50)+2).machine;
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
