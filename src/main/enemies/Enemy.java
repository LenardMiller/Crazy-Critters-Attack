package main.enemies;

import main.Main;
import main.buffs.*;
import main.buffs.glued.Glued;
import main.buffs.glued.SpikeyGlued;
import main.buffs.stunned.Frozen;
import main.buffs.stunned.Stunned;
import main.gui.guiObjects.PopupText;
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
import processing.sound.SoundFile;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import static main.Main.*;
import static main.misc.Utilities.*;

public abstract class Enemy {

    /**
     * measured in pixels per second
     */
    public float maxSpeed;
    public float speed;
    public float angle;
    public float radius;
    /**
     * moving, attacking, (shooting/burrowing)
     */
    public int state;
    public int barAlpha;
    public int hp;
    public int maxHp;
    public int attackFrame;
    public int pfSize;
    public int[] attackDmgFrames;
    public int[] tempAttackDmgFrames;
    public boolean immobilized;
    public ArrayList<TurnPoint> points;
    public PImage[] attackFrames;
    public String hitParticle;
    public String lastDamageType;
    public String name;
    public PVector position;
    public PVector size;

    protected int moneyDrop;
    protected int damage;
    protected int betweenWalkFrames;
    protected int betweenAttackFrames;
    protected int attackStartFrame;
    protected int betweenCorpseFrames;
    protected int corpseLifespan;
    protected int pathRequestWaitTimer;
    protected int moveFrame;
    protected int idleTime;
    protected float targetAngle;
    protected boolean overkill;
    protected PApplet p;
    protected PVector partsDirection;
    protected PVector corpseSize;
    protected PVector partSize;
    protected PImage[] moveFrames;
    protected PImage sprite;
    protected Color maxTintColor;
    protected Color currentTintColor;
    protected SoundFile overkillSound;
    protected SoundFile dieSound;

    protected int attackCount;
    protected boolean attackCue;
    protected boolean targetMachine;
    protected Tower targetTower;

    protected Enemy(PApplet p, float x, float y) {
        this.p = p;

        points = new ArrayList<>();
        position = new PVector(roundTo(x, 25) + 12.5f, roundTo(y, 25) + 12.5f);
        size = new PVector(20, 20);
        angle = radians(270);
        radius = 10;
        maxSpeed = 60;
        speed = maxSpeed;
        moneyDrop = 1;
        damage = 1;
        maxHp = 20; //Hp <---------------------------
        hp = maxHp;
        barAlpha = 0;
        hitParticle = "redOuch";
        name = "";
        attackStartFrame = 0;
        betweenWalkFrames = 0;
        attackDmgFrames = new int[]{0};
        tempAttackDmgFrames = new int[attackDmgFrames.length];
        System.arraycopy(attackDmgFrames, 0, tempAttackDmgFrames, 0, tempAttackDmgFrames.length);
        pfSize = 1;
        attackCount = 0;
        corpseSize = size;
        partSize = size;
        betweenCorpseFrames = down60ToFramerate(7);
        corpseLifespan = 8;
        lastDamageType = "normal";
    }

    public void main(int i) {
        boolean dead = false; //if its gotten this far, it must be alive?
        swapPoints(false);

        if (!paused && !immobilized) {
            angle = clampAngle(angle);
            targetAngle = clampAngle(targetAngle);
            angle += angleDifference(targetAngle, angle) / 10;

            if (state == 0) move();
            else if (state == 1) attack();

            //prevent wandering
            if (points.size() == 0 && state != 1) pathRequestWaitTimer++;
            if (pathRequestWaitTimer > FRAMERATE) {
                requestPath(i);
                pathRequestWaitTimer = 0;
            }
        }
        if (points.size() != 0 && intersectTurnPoint()) swapPoints(true);
        displayMain();
        //if health is 0, die
        if (hp <= 0) dead = true;
        if (dead) die(i);
    }

    /**
     * Adds money with a popup.
     * Plays death sound.
     * If overkill, fling bits everywhere, else create a corpse.
     * Clear buffs.
     * Remove from array.
     * @param i id for buff stuff
     */
    protected void die(int i) {
        Main.money += moneyDrop;
        popupTexts.add(new PopupText(p, new PVector(position.x, position.y), moneyDrop));

        String type = lastDamageType;
        for (Buff buff : buffs) {
            if (buff.enId == i) type = buff.name;
        }
        if (overkill) playSoundRandomSpeed(p, overkillSound, 1);
        else playSoundRandomSpeed(p, dieSound, 1);
        if (overkill) {
            for (int j = 0; j < animatedSprites.get(name + "PartsEN").length; j++) {
                float maxRotationSpeed = up60ToFramerate(200f / partSize.x);
                corpses.add(new Corpse(p, position, partSize, angle, adjustPartVelocityToFramerate(partsDirection),
                        currentTintColor ,p.random(radians(-maxRotationSpeed), radians(maxRotationSpeed)),
                        0, corpseLifespan, type, name + "Parts", hitParticle, j, false));
            }
            for (int k = 0; k < sq(pfSize); k++) {
                bottomParticles.add(new Pile(p, (float) (position.x + 2.5 + p.random((size.x / 2) * -1,
                        (size.x / 2))), (float) (position.y + 2.5 + p.random((size.x / 2) * -1, (size.x / 2))),
                        0, hitParticle));
            }
        } else
            corpses.add(new Corpse(p, position, corpseSize,
                    angle + p.random(radians(-5), radians(5)), new PVector(0, 0),
                    currentTintColor, 0, betweenCorpseFrames, corpseLifespan, type, name + "Die",
                    "none", 0, true));

        for (int j = buffs.size() - 1; j >= 0; j--) { //deals with buffs
            Buff buff = buffs.get(j);
            //if attached, remove
            if (buff.enId == i) {
                buffs.get(j).dieEffect();
                buffs.remove(j);
            } //shift ids to compensate for enemy removal
            else if (buff.enId > i) buff.enId -= 1;
        }

        enemies.remove(i);
    }

    protected PVector adjustPartVelocityToFramerate(PVector partVelocity) {
        return partVelocity.setMag(partVelocity.mag() * up60ToFramerate(1));
    }

    protected void move() {
        PVector m = PVector.fromAngle(angle);
        float pixelsMoved = speed / FRAMERATE;
        if (intersectCombatPoint()) pixelsMoved /= 3;
        m.setMag(pixelsMoved);
        //don't move if no path
        if (points.size() > 0) position.add(m);
    }

    /**
     * handle animation states
     */
    protected void animate() {
        if (!immobilized) {
            if (state == 1) {
                if (attackFrame >= attackFrames.length) attackFrame = 0;
                sprite = attackFrames[attackFrame];
                idleTime++;
                if (attackFrame < attackFrames.length - 1) {
                    if (idleTime >= betweenAttackFrames) {
                        attackFrame += 1;
                        idleTime = 0;
                    }
                } else attackFrame = 0;
            } else if (state == 0) {
                idleTime++;
                if (moveFrame < moveFrames.length - 1) {
                    if (idleTime >= betweenWalkFrames) {
                        moveFrame++;
                        idleTime = 0;
                    }
                } else moveFrame = 0;
                sprite = moveFrames[moveFrame];
            }
        }
        //shift back to normal
        currentTintColor = incrementColorTo(currentTintColor, up60ToFramerate(20), new Color(255, 255, 255));
    }

    /**
     * Displays but tinted black and semi-transparent.
     * Calls to animate sprite.
     */
    public void displayShadow() {
        if (!paused) animate();
        p.pushMatrix();
        p.tint(0, 60);
        int x = 1;
        if (pfSize > 1) x++;
        p.translate(position.x + x, position.y + x);
        p.rotate(angle);
        if (sprite != null) p.image(sprite, -size.x / 2, -size.y / 2);
        p.tint(255);
        p.popMatrix();
    }

    /**
     * Display main sprite
     */
    public void displayMain() {
        if (debug) for (int i = points.size() - 1; i > 0; i--) {
            points.get(i).display();
        }
        p.pushMatrix();
        p.translate(position.x, position.y);
        p.rotate(angle);
        p.tint(currentTintColor.getRGB());
        if (sprite != null) p.image(sprite, -size.x / 2, -size.y / 2);
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

    /**
     * Applies damage to the enemy, can also apply a buff
     * @param damage the amount of damage to be taken
     * @param buffName the name of the buff to be applied, nullable
     * @param effectLevel level of the buff to be applied
     * @param effectDuration duration of the buff to be applied
     * @param turret turret that caused the damage, nullable
     * @param displayParticles if should create particles
     * @param damageType determines what effect to apply to corpse
     * @param direction determines where parts will be flung, (0, 0) for everywhere
     * @param id id of this enemy, set to -1 if unknown
     */
    public void damageWithBuff(int damage, String buffName, float effectLevel, float effectDuration, Turret turret,
                               boolean displayParticles, String damageType, PVector direction, int id) {
        if (id == -1 && buffName != null) id = getId();
        lastDamageType = damageType;
        overkill = damage >= maxHp;
        partsDirection = direction;
        hp -= damage;
        if (turret != null) {
            int statDamage = damage;
            if (hp <= 0) {
                turret.killsTotal++;
                statDamage = damage + hp;
            }
            if (statDamage > 0) turret.damageTotal += statDamage;
        }
        int effectTimer = p.frameCount + 10;
        if (buffs.size() > 0) {
            for (int j = 0; j < buffs.size(); j++) {
                Buff buff = buffs.get(j);
                if (buff.enId == id && buff.name.equals(buffName)) {
                    effectTimer = buff.effectTimer;
                    buffs.remove(j);
                    break;
                }
            }
        }
        if (buffName != null) {
            Buff buff;
            switch (buffName) {
                case "burning":
                    buff = new Burning(p, id, effectLevel, effectDuration, turret);
                    break;
                case "blueBurning":
                    buff = new BlueBurning(p, id, effectLevel, effectDuration, turret);
                    break;
                case "bleeding":
                    buff = new Bleeding(p, id, turret);
                    break;
                case "poisoned":
                    buff = new Poisoned(p, id, turret);
                    break;
                case "decay":
                    if (turret != null) buff = new Decay(p, id, effectLevel, effectDuration, turret);
                    else buff = new Decay(p, id, 1, 120, null);
                    break;
                case "glued":
                    buff = new Glued(p, id, effectLevel, effectDuration, turret);
                    break;
                case "spikeyGlued":
                    buff = new SpikeyGlued(p, id, effectLevel, effectDuration, turret);
                    break;
                case "stunned":
                    buff = new Stunned(p, id, turret);
                    break;
                case "frozen":
                    buff = new Frozen(p, id, turret);
                    break;
                default:
                    buff = null;
                    break;
            }
            if (buff != null) {
                //in order to prevent resetting timer after buff is reapplied
                buff.effectTimer = effectTimer;
                buffs.add(buff);
            }
        }
        damageEffect(displayParticles);
    }

    /**
     * Applies damage to the enemy
     * @param damage amount of damage to be applied
     * @param turret the turret that caused the damage, nullable
     * @param damageType determines what effect to apply to corpse
     * @param direction where parts will be flung, (0, 0) for everywhere
     * @param displayParticles whether it should spawn particles
     */
    public void damageWithoutBuff(int damage, Turret turret, String damageType, PVector direction, boolean displayParticles) {
        lastDamageType = damageType;
        overkill = damage >= maxHp;
        partsDirection = direction;
        hp -= damage;
        if (turret != null) {
            if (hp <= 0) {
                turret.killsTotal++;
                turret.damageTotal += damage + hp;
            } else turret.damageTotal += damage;
        }
        damageEffect(displayParticles);
    }

    private int getId() {
        for (int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i) == this) return i;
        }
        return -1;
    }

    /**
     * Display hp bar.
     * Tint.
     * @param particles whether or not to display hurt particles
     */
    protected void damageEffect(boolean particles) {
        if (hp == maxHp) return;
        barAlpha = 255;
        if (particles) {
            int num = pfSize;
            int chance = 5;
            if (notRecentlyHit()) {
                num = (int) p.random(pfSize, pfSize * pfSize);
                chance = 0;
            }
            if (p.random(0, 6) > chance) {
                for (int j = num; j >= 0; j--) { //sprays ouch
                    topParticles.add(new Ouch(p, position.x + p.random((size.x / 2) * -1, size.x / 2), position.y + p.random((size.y / 2) * -1, size.y / 2), p.random(0, 360), hitParticle));
                }
            }
            currentTintColor = new Color(maxTintColor.getRGB());
        }
    }

    private boolean notRecentlyHit() {
        int totalTintColor = currentTintColor.getRed() + currentTintColor.getGreen() + currentTintColor.getBlue();
        return totalTintColor >= 700;
    }

    public void hpBar() {
        Color barColor = new Color(255, 0, 0);
        float barWidth = size.x * (hp / (float) maxHp);
        p.stroke(barColor.getRGB(), barAlpha);
        p.noFill();
        p.rect(position.x - size.x / 2, position.y + size.y / 2 + 6, size.x, 6);
        p.fill(barColor.getRGB(), barAlpha);
        p.rect(position.x - size.x / 2, position.y + size.y / 2 + 6, barWidth, 6);
    }

    protected void loadStuff() {
        hp = maxHp;
        speed = maxSpeed;
        System.arraycopy(attackDmgFrames, 0, tempAttackDmgFrames, 0, tempAttackDmgFrames.length);
        attackFrames = animatedSprites.get(name + "AttackEN");
        moveFrames = animatedSprites.get(name + "MoveEN");
        maxTintColor = getTintColor();
        currentTintColor = new Color(255, 255, 255);
    }

    protected Color getTintColor() {
        switch (hitParticle) {
            case "glowOuch":
                return new Color(0, 255, 195);
            case "greenOuch":
                return new Color(100, 166, 0);
            case "leafOuch":
                return new Color(19, 183, 25);
            case "lichenOuch":
                return new Color(144, 146, 133);
            case "pinkOuch":
                return new Color(255, 0, 255);
            case "redOuch":
                return new Color(216, 0, 0);
            case "iceOuch":
                return new Color(49, 135, 223);
            default:
                return new Color(255, 0, 0);
        }
    }

    /**
     * Angles towards target.
     * Damages target turret or machine.
     * Messes with state a bit.
     * Prevents attacking multiple times at once.
     */
    protected void attack() {
        boolean dmg = false;
        for (int frame : tempAttackDmgFrames) {
            if (attackFrame == frame) {
                if (betweenAttackFrames > 1) attackCount++;
                dmg = true;
                break;
            }
        }
        //all attackCount stuff prevents attacking multiple times
        if (!dmg) attackCount = 0;
        if (attackCount > 1) dmg = false;
        if (targetTower != null && targetTower.alive) {
            if (pfSize > 2) { //angle towards tower correctly
                PVector t = new PVector(targetTower.tile.position.x - 25, targetTower.tile.position.y - 25);
                targetAngle = findAngleBetween(t, position);
            }
            moveFrame = 0;
            if (dmg) targetTower.damage(damage);
        } else if (!targetMachine) state = 0;
        if (targetMachine) {
            moveFrame = 0;
            if (dmg) machine.damage(damage);
        }
        if (!attackCue && attackFrame == attackStartFrame) {
            state = 0;
            attackFrame = attackStartFrame;
        }
    }

    public boolean onScreen() {
        return position.x > 0 && position.x < 900 && position.y > 0 && position.y < 900;
    }

    //pathfinding -----------------------------------------------------------------
    //todo: fix attacking multiple things at once

    protected boolean intersectTurnPoint() {
        TurnPoint point = points.get(points.size() - 1);
        PVector p = point.position;
        float tpSize;
        if (point.combat) tpSize = 3;
        else tpSize = 15;
        PVector pfPosition = new PVector(position.x - ((pfSize - 1) * 12.5f), position.y - ((pfSize - 1) * 12.5f));
        return (pfPosition.x > p.x - tpSize + (NODE_SIZE / 2f) && pfPosition.x < p.x + tpSize + (NODE_SIZE / 2f)) &&
          (pfPosition.y > p.y - tpSize + (NODE_SIZE / 2f) && pfPosition.y < p.y + tpSize + (NODE_SIZE / 2f));
    }

    protected boolean intersectCombatPoint() {
        TurnPoint point = points.get(points.size() - 1);
        PVector p = point.position;
        float tpSize;
        if (point.combat) tpSize = 15;
        else return false;
        PVector pfPosition = new PVector(position.x - ((pfSize - 1) * 12.5f), position.y - ((pfSize - 1) * 12.5f));
        return (pfPosition.x > p.x - tpSize + (NODE_SIZE / 2f) && pfPosition.x < p.x + tpSize + (NODE_SIZE / 2f)) &&
          (pfPosition.y > p.y - tpSize + (NODE_SIZE / 2f) && pfPosition.y < p.y + tpSize + (NODE_SIZE / 2f));
    }

    public void requestPath(int i) {
        path.reqQ.add(new PathRequest(i, enemies.get(i)));
    }

    public void swapPoints(boolean remove) {
        if (points.size() != 0) {
            TurnPoint intersectingPoint = points.get(points.size() - 1);
            if (remove) {
                if (intersectingPoint.combat) {
                    state = 1;
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

    public void setCombatPoints() {
        //remove
        for (TurnPoint point : points) {
            point.combat = false;
            point.towers = null;
            point.machine = false;
        }
        //add
        ArrayList<TurnPoint> pointsD = new ArrayList<>(points);
        for (TurnPoint point : pointsD) {
            point.towers = clearanceTowers(point);
            point.machine = clearanceMachine(point);
        }
        //iterate backwards based on enemy size
        TurnPoint backPoint = backPoint();
        if (backPoint != null) {
            backPoint.combat = true;
            if (backPoint.towers != null && backPoint.towers.size() > 0) { //what the hell is this for??
                backPoint.tower = backPoint.towers.get(floor(backPoint.towers.size() / 2f));
            } else backPoint.tower = null;
        }

        points = new ArrayList<>();
        points.addAll(pointsD);
    }

    /**
     * returns all towers in enemy "bubble"?
     */
    protected ArrayList<Tower> clearanceTowers(TurnPoint point) {
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

        private final PApplet P;
        public Tower tower;
        public ArrayList<Tower> towers;
        public boolean machine;
        public PVector position;
        boolean combat;
        boolean back;

        public TurnPoint(PApplet p, PVector position, Tower tower) {
            this.P = p;
            this.position = new PVector(position.x, position.y);
            this.tower = tower;

//            machine = tiles.get((int)(position.x/50)+2,(int)(position.y/50)+2).machine;
            combat = false;
            back = false;
        }

        public void display() {
            P.noStroke();
            if (back) P.stroke(0, 255, 0);
            else P.noStroke();
            if (combat) P.fill(255, 0, 0);
            else P.fill(255);
            P.ellipse(position.x + NODE_SIZE / 2f, position.y + NODE_SIZE / 2f, NODE_SIZE, NODE_SIZE);
            hover();
        }

        private void hover() {
            boolean intersecting;
            float tpSize = 10;
            PVector pfPosition = new PVector(P.mouseX, P.mouseY);
            intersecting = (pfPosition.x > position.x - tpSize + (NODE_SIZE / 2f) &&
                    pfPosition.x < position.x + tpSize + (NODE_SIZE / 2f)) &&
                    (pfPosition.y > position.y - tpSize + (NODE_SIZE / 2f) &&
                            pfPosition.y < position.y + tpSize + (NODE_SIZE / 2f));
            if (intersecting && tower != null) {
                P.stroke(255, 255, 0);
                P.noFill();
                P.rect(tower.tile.position.x - 50, tower.tile.position.y - 50, 50, 50);
            }
        }
    }
}
