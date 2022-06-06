package main.towers.turrets;

import main.enemies.Enemy;
import main.enemies.burrowingEnemies.BurrowingEnemy;
import main.gui.guiObjects.PopupText;
import main.misc.CompressArray;
import main.misc.Tile;
import main.particles.MiscParticle;
import main.particles.Ouch;
import main.towers.Tower;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import processing.sound.SoundFile;

import java.util.ArrayList;
import java.util.function.Consumer;

import static main.Main.*;
import static main.misc.Utilities.*;
import static main.misc.WallSpecialVisuals.updateFlooring;
import static main.misc.WallSpecialVisuals.updateTowerArray;
import static main.pathfinding.PathfindingUtilities.updateCombatPoints;
import static main.sound.SoundUtilities.playSoundRandomSpeed;

public abstract class Turret extends Tower {

    public enum Priority {
        Close("closest"),
        Far("farthest"),
        Strong("most HP"),
        Weak("least hp"),
        None("");

        public final String text;

        Priority(String text) {
            this.text = text;
        }
    }

    public enum State {
        Idle,
        Fire,
        Load
    }

    public boolean hasPriority;
    public int pjSpeed;
    public int range;
    public int pierce;
    public int killsTotal;
    public int damageTotal;
    public int damage;
    public float effectDuration;
    public float effectLevel;
    /** Seconds */
    public float delay;
    /** Radians */
    public float angle;
    public String[] upgradeDescA;
    public String[] upgradeDescB;
    public String[] upgradeDescC;
    public String[] titleLines;
    public Consumer<Integer> infoDisplay;
    public Consumer<Integer> statsDisplay;
    public Priority priority = Priority.Close;

    protected State state = State.Idle;
    protected int offset;
    protected int frame;
    protected int frameTimer;
    protected int betweenIdleFrames;
    protected int betweenFireFrames;
    protected float targetAngle;
    protected float barrelLength;
    protected String fireParticle;
    protected PImage idleSprite;
    protected PImage sBase;
    protected PImage[] fireFrames;
    protected PImage[] loadFrames;
    protected PImage[] idleFrames;
    protected Enemy targetEnemy;
    protected SoundFile fireSound;

    protected ArrayList<Integer> spriteArray;

    protected Turret(PApplet p, Tile tile) {
        super(p, tile);
        this.p = p;
        hasPriority = true;
        size = new PVector(50, 50);
        maxHp = 20;
        hp = maxHp;
        delay = 4;
        pjSpeed = 500;
        spriteArray = new ArrayList<>();
        upgradePrices = new int[6];
        upgradeTitles = new String[6];
        upgradeDescA = new String[6];
        upgradeDescB = new String[6];
        upgradeDescC = new String[6];
        upgradeIcons = new PImage[6];
        nextLevelB = upgradeTitles.length / 2;
        infoDisplay = (ignored) -> {};
        statsDisplay = (o) -> {
            if (killsTotal != 1) p.text(nfc(killsTotal) + " kills", 910, 475 + o);
            else p.text("1 kill", 910, 475 + o);
            p.text(nfc(damageTotal) + " total dmg", 910, 500 + o);
        };

        updateTowerArray();
    }

    @Override
    public int getValue() {
        int value = basePrice;
        for (int i = 3; i < nextLevelB; i++) {
            value += upgradePrices[i];
        } for (int i = 0; i < nextLevelA; i++) {
            value += upgradePrices[i];
        }
        return value;
    }

    @Override
    public void placeEffect(boolean quiet) {
        loadSprites();
        setUpgrades();
        if (!quiet) {
            spawnParticles();
            playSoundRandomSpeed(p, placeSound, 1);
        }
    }

    protected void checkTarget() {
        getTargetEnemy();
        if (targetEnemy != null && state != State.Fire) aim(targetEnemy);
        if (state == State.Idle && targetEnemy != null && abs(targetAngle - angle) < 0.02) { //if done animating and aimed
            state = State.Fire;
            frame = 0;
            fire(barrelLength, fireParticle);
        }
    }

    protected void getTargetEnemy() {
        //0: close
        //1: far
        //2: strong
        float finalDist;
        if (priority == Priority.Close) finalDist = 1000000;
        else finalDist = 0;
        float maxHp = -1;
        Enemy e = null;
        for (Enemy enemy : enemies) {
            if (enemyCanBeAttacked(enemy)) {
                float x = abs(tile.position.x - (size.x / 2) - enemy.position.x);
                float y = abs(tile.position.y - (size.y / 2) - enemy.position.y);
                float dist = sqrt(sq(x) + sq(y));
                if (enemy.onScreen() && dist < getRange()) {
                    switch (priority) {
                        case Close:
                            if (dist >= finalDist) break;
                            e = enemy;
                            finalDist = dist;
                            break;
                        case Far:
                            if (dist <= finalDist) break;
                            e = enemy;
                            finalDist = dist;
                            break;
                        case Strong:
                            if (enemy.maxHp > maxHp || maxHp == -1) { //strong
                                e = enemy;
                                finalDist = dist;
                                maxHp = enemy.maxHp;
                            } else if (enemy.maxHp == maxHp && dist < finalDist) { //strong -> close
                                e = enemy;
                                finalDist = dist;
                            }
                            break;
                        case Weak:
                            if (enemy.maxHp < maxHp || maxHp == -1) { //weak
                                e = enemy;
                                finalDist = dist;
                                maxHp = enemy.maxHp;
                            } else if (enemy.maxHp == maxHp && dist < finalDist) { //weak -> close
                                e = enemy;
                                finalDist = dist;
                            }
                    }
                }
            }
        }
        targetEnemy = e;
    }

    protected boolean enemyCanBeAttacked(Enemy enemy) {
        return !(enemy.state == Enemy.State.Moving && enemy instanceof BurrowingEnemy);
    }

    /**
     * Sets the target angle to match the target.
     * Leads shots if enemy moving.
     * @param enemy enemy to aim at
     */
    protected void aim(Enemy enemy) {
        PVector position = new PVector(tile.position.x - 25, tile.position.y - 25);
        PVector target = enemy.position;

        if (pjSpeed > 0) { //shot leading
            float dist = PVector.sub(target, position).mag();
            float time = dist / pjSpeed;
            PVector enemyHeading = PVector.fromAngle(enemy.rotation);
            if (enemy.state == Enemy.State.Moving) enemyHeading.setMag(enemy.getActualSpeed() * time); //only lead if enemy moving
            else enemyHeading.setMag(0);
            target = new PVector(target.x + enemyHeading.x, target.y + enemyHeading.y);
        }

        targetAngle = normalizeAngle(findAngle(position, target));
        angle = normalizeAngle(angle);
        angle += getAngleDifference(targetAngle, angle) / (FRAMERATE/6f);

        if (abs(targetAngle - angle) < 0.05) angle = targetAngle; //snap to prevent getting stuck

        if (visualize && debug) { //cool lines
            p.stroke(255);
            p.line(position.x, position.y, target.x, target.y);
            p.stroke(255, 0, 0, 150);
            p.line(target.x, p.height, target.x, 0);
            p.stroke(0, 0, 255, 150);
            p.line(p.width, target.y, 0, target.y);
        }
    }

    protected void fire(float barrelLength, String particleType) {
        playSoundRandomSpeed(p, fireSound, 1);
        float displayAngle = angle;
        PVector projectileSpawn = new PVector(tile.position.x-size.x/2,tile.position.y-size.y/2);
        PVector barrel = PVector.fromAngle(displayAngle-HALF_PI);
        float particleCount = p.random(1,5);
        barrel.setMag(barrelLength); //barrel length
        projectileSpawn.add(barrel);
        spawnProjectiles(projectileSpawn, displayAngle);
        if (particleType != null && !particleType.equals("null")) {
            for (int i = 0; i < particleCount; i++) {
                PVector spa2 = PVector.fromAngle(displayAngle - HALF_PI + radians(p.random(-20, 20)));
                spa2.setMag(-5);
                PVector spp2 = new PVector(projectileSpawn.x, projectileSpawn.y);
                spp2.add(spa2);
                topParticles.add(new MiscParticle(p, spp2.x, spp2.y, displayAngle + radians(p.random(-45, 45)), particleType));
            }
        }
    }

    protected abstract void spawnProjectiles(PVector position, float angle);

    protected void loadSprites() {
        sBase = staticSprites.get(name + "BaseTR");
        idleSprite = staticSprites.get(name + "IdleTR");
        fireFrames = animatedSprites.get(name + "FireTR");
        loadFrames = animatedSprites.get(name + "LoadTR");
        if (animatedSprites.get(name + "IdleTR") != null) {
            idleFrames = animatedSprites.get(name + "IdleTR");
            idleSprite = idleFrames[0];
            sprite = idleSprite;
        }
        else {
            idleFrames = new PImage[]{staticSprites.get(name + "IdleTR")};
            sprite = idleFrames[0];
        }
    }

    @Override
    public void main() {
        if (hp <= 0) {
            die(false);
            tile.tower = null;
        }
        updateBoosts();
        if (enemies.size() > 0 && !machine.dead && !paused) checkTarget();
        if (p.mousePressed && matrixMousePosition.x < tile.position.x && matrixMousePosition.x > tile.position.x - size.x && matrixMousePosition.y < tile.position.y
                && matrixMousePosition.y > tile.position.y - size.y && alive && !paused) {
            selection.swapSelected(tile.id);
        }
    }

    @Override
    public void die(boolean sold) {
        playSoundRandomSpeed(p, breakSound, 1);
        spawnParticles();
        tile.tower = null;
        alive = false;
        updateTowerArray();
        if (selection.turret == this) {
            selection.name = "null";
            inGameGui.flashA = 255;
        }
        else if (!selection.name.equals("null")) selection.swapSelected(selection.turret);
        int moneyGain;
        if (!sold) {
            moneyGain = (int) (getValue() * 0.4);
            tiles.get(((int)tile.position.x/50) - 1, ((int)tile.position.y/50) - 1).setBreakable(material + "DebrisBr_TL");
        } else moneyGain = (int) (getValue() * 0.8);
        popupTexts.add(new PopupText(p, new PVector(tile.position.x - 25, tile.position.y - 25), moneyGain));
        money += moneyGain;
        if (hasBoostedDeathEffect()) boostedDeathEffect();
        updateFlooring();
        connectWallQueues++;
        updateCombatPoints();
    }

    @Override
    public void controlAnimation() {
        if (!paused) {
            if (hp < getMaxHp() && p.random(30) < 1) {
                topParticles.add(new Ouch(p, p.random(tile.position.x - size.x, tile.position.x),
                  p.random(tile.position.y - size.y, tile.position.y), p.random(360), "greyPuff"));
            }
            if (tintColor < 255) tintColor += 20;
            switch (state) {
                case Idle:
                    sprite = idleSprite;
                    if (idleFrames.length > 1) {
                        if (frame < idleFrames.length) {
                            sprite = idleFrames[frame];
                            if (frameTimer >= betweenIdleFrames) {
                                frame++;
                                frameTimer = 0;
                            } else frameTimer++;
                        } else {
                            frame = 0;
                            sprite = idleFrames[frame];
                        }
                    }
                    break;
                case Fire:
                    if (frame < fireFrames.length - 1) { //if not done, keep going
                        if (frameTimer >= betweenFireFrames) {
                            frame++;
                            frameTimer = 0;
                            sprite = fireFrames[frame];
                        } else frameTimer++;
                    } else { //if done, switch to load
                        if (loadFrames.length > 0) {
                            int oldSize = loadFrames.length;
                            int newSize = secondsToFrames(getDelay());
                            spriteArray = new ArrayList<>();
                            if (oldSize > newSize) { //decreasing size
                                //creates the new spriteArray
                                for (int i = 0; i < oldSize; i++) spriteArray.add(i);
                                //compression
                                compress = new CompressArray(oldSize, newSize, spriteArray);
                                compress.main();
                            } else { //increasing size
                                compress = new CompressArray(oldSize - 1, newSize, spriteArray);
                                compress.main();
                                spriteArray = compress.compArray;
                            }
                        }
                        frame = 0;
                        state = State.Load;
                    }
                    break;
                case Load:
                    frame++;
                    if (frame < spriteArray.size() && spriteArray.get(frame) < loadFrames.length) {
                        sprite = loadFrames[spriteArray.get(frame)];
                    } else { //if time runs out, switch to idle
                        frame = 0;
                        sprite = idleSprite;
                        state = State.Idle;
                    }
                    break;
            }
            if (hit) { //change to red if under attack
                tintColor = 0;
                hit = false;
            }
        }
        displayMain();
    }

    protected void displayMain() {
        //shadow
        p.pushMatrix();
        p.translate(tile.position.x - size.x / 2 + 2, tile.position.y - size.y / 2 + 2);
        p.rotate(angle);
        p.tint(0, 60);
        if (sprite != null) p.image(sprite, -size.x / 2 - offset, -size.y / 2 - offset);
        p.popMatrix();
        //main
        p.pushMatrix();
        p.translate(tile.position.x - size.x / 2, tile.position.y - size.y / 2);
        p.rotate(angle);
        p.tint(255, tintColor, tintColor);
        if (sprite != null) p.image(sprite, -size.x / 2 - offset, -size.y / 2 - offset);
        p.popMatrix();
        p.tint(255);
    }

    @Override
    public void displayBase() {
        p.tint(255, tintColor, tintColor);
        p.image(sBase, tile.position.x - size.x, tile.position.y - size.y);
        p.tint(255, 255, 255);
    }

    /**
     * Upgrades the turret once.
     * @param id which track to use, 0 for A, 1 for B
     * @param quiet no particles and sound
     */
    @Override
    public void upgrade(int id, boolean quiet) {
        int price = 0;
        if (id == 0) {
            if (nextLevelA >= upgradePrices.length) return;
            price = upgradePrices[nextLevelA];
            if (price > money) return;
            if (nextLevelA > 2) return;
            if (nextLevelB == 6 && nextLevelA == 2) return;
        } else if (id == 1) {
            if (nextLevelB >= upgradePrices.length) return;
            price = upgradePrices[nextLevelB];
            if (price > money) return;
            if (nextLevelB > 5) return;
            if (nextLevelB == 5 && nextLevelA == 3) return;
        }
        inGameGui.flashA = 255;
        money -= price;
        upgradeEffect(id);
        if (id == 0) nextLevelA++;
        else if (id == 1) nextLevelB++;
        //icons
        if (nextLevelA < upgradeTitles.length / 2) inGameGui.upgradeIconA.sprite = upgradeIcons[nextLevelA];
        else inGameGui.upgradeIconA.sprite = animatedSprites.get("upgradeIC")[0];
        if (nextLevelB < upgradeTitles.length) inGameGui.upgradeIconB.sprite = upgradeIcons[nextLevelB];
        else inGameGui.upgradeIconB.sprite = animatedSprites.get("upgradeIC")[0];

        if (!quiet) {
            playSoundRandomSpeed(p, placeSound, 1);
            spawnParticles();
        }
        //prevent having fire animations longer than delays
//        while (getDelay() <= fireFrames.length * betweenFireFrames + idleFrames.length && betweenFireFrames > 0) betweenFireFrames--;
    }

    protected abstract void setUpgrades();

    protected abstract void upgradeEffect(int id);

    //boosts

    public int boostedDamage() {
        int d = 0;
        for (Booster.Boost boost : boosts) {
            int d2 = (int) (damage * boost.damage);
            if (d2 > d) d = d2;
        }
        return d;
    }

    public int getDamage() {
        return damage + boostedDamage();
    }

    public int boostedRange() {
        int r = 0;
        for (Booster.Boost boost : boosts) {
            int r2 = (int) (range * boost.range);
            if (r2 > r) r = r2;
        }
        return r;
    }

    public int getRange() {
        return range + boostedRange();
    }

    public float boostedFirerate() {
        float f = 0;
        for (Booster.Boost boost : boosts) {
            float f2 = delay * boost.firerate;
            if (f2 > f) f = f2;
        }
        return f;
    }

    public float getDelay() {
        return delay - boostedFirerate();
    }

    public static Turret get(PApplet p, String type, Tile tile) {
        switch (type) {
            case "Booster":
                return new Booster(p, tile);
            case "Cannon":
                return new Cannon(p, tile);
            case "Crossbow":
                return new Crossbow(p, tile);
            case "EnergyBlaster":
                return new EnergyBlaster(p, tile);
            case "Flamethrower":
                return new Flamethrower(p, tile);
            case "Gluer":
                return new Gluer(p, tile);
            case "IceTower":
                return new IceTower(p, tile);
            case "MagicMissileer":
            case "MagicMissleer":
                return new MagicMissileer(p, tile);
            case "Nightmare":
                return new Nightmare(p, tile);
            case "Railgun":
                return new Railgun(p, tile);
            case "RandomCannon":
            case "MiscCannon":
                return new RandomCannon(p, tile);
            case "SeismicTower":
            case "Seismic":
                return new SeismicTower(p, tile);
            case "Slingshot":
                return new Slingshot(p, tile);
            case "TeslaTower":
            case "Tesla":
                return new TeslaTower(p, tile);
            case "WaveMotion":
                return new WaveMotion(p, tile);
            default:
                System.out.println("Could not get Turret of name:\n    " + type);
                return null;
        }
    }
}