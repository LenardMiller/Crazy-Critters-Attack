package main.levelStructure;

import main.enemies.*;
import main.enemies.burrowingEnemies.*;
import main.enemies.flyingEnemies.*;
import main.enemies.shootingEnemies.*;
import main.gui.guiObjects.PopupText;
import main.misc.Polluter;
import main.towers.IceWall;
import main.towers.Tower;
import main.towers.turrets.Booster;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

import static main.Main.*;
import static main.misc.Utilities.randomSpawnPosition;
import static main.misc.Utilities.secondsToFrames;
import static main.sound.SoundUtilities.playSound;

public class Wave {

    private final PApplet P;
    private final Color FILL_COLOR;
    private final Color ACCENT_COLOR;
    private final Color TEXT_COLOR;
    private final String TITLE;
    final int SPAWN_LENGTH;
    final int LENGTH;

    public Polluter polluter;
    public String groundType;

    boolean unskippable;
    private int betweenSpawns;
    /**Time until wave end*/
    int lengthTimer;
    /**Time until next spawn*/
    private int betweenSpawnTimer;
    /**Time until stop spawning*/
    int spawnLengthTimer;

    ArrayList<String> spawns;

    boolean hasFlying;
    boolean hasBurrowing;
    boolean hasShooting;

    /**
     * A wave of enemies
     * @param p the PApplet
     * @param length how long it lasts in seconds
     * @param spawnLength how long it spawns enemies in seconds, must be less than length
     * @param fillColor main icon color
     * @param accentColor border color
     * @param textColor color of title and number
     * @param title main enemy of wave
     */
    public Wave(PApplet p, int length, int spawnLength, Color fillColor, Color accentColor, Color textColor, String title) {
        P = p;
        LENGTH = secondsToFrames(length);
        SPAWN_LENGTH = secondsToFrames(spawnLength);
        if (SPAWN_LENGTH >= LENGTH) {
            throw new RuntimeException("Wave spawn length should always be shorter than its total length!");
        }
        FILL_COLOR = fillColor;
        ACCENT_COLOR = accentColor;
        TEXT_COLOR = textColor;
        TITLE = title;
        spawns = new ArrayList<>();
    }

    public void end() {
        for (Tower tower : towers) {
            if (tower instanceof Turret) tower.heal(1);
            else if (!(tower instanceof IceWall)) tower.heal(0.35f);
        }
        machine.heal(0.05f);
        for (Tower tower : towers) {
            if (tower.name.equals("moneyBooster")) ((Booster) tower).giveMoney();
        }
        playSound(sounds.get("waveEnd"), 1, 1);
        int reward = (int) (levels[currentLevel].reward + (levels[currentLevel].reward / 10f) * enemies.size());
        money += reward;
        popupTexts.add(new PopupText(P, new PVector(BOARD_WIDTH / 2f, BOARD_HEIGHT / 2f), reward));
    }

    /** Calculates the time between spawns. */
    void load() {
        if (spawns.size() > 0) betweenSpawns = SPAWN_LENGTH / spawns.size();
    }

    void addSpawns(String enemy, int count) {
        if (getEnemy(enemy) instanceof FlyingEnemy) hasFlying = true;
        if (getEnemy(enemy) instanceof BurrowingEnemy) hasBurrowing = true;
        if (getEnemy(enemy) instanceof ShootingEnemy) hasShooting = true;
        for (int i = 0; i < count; i++) spawns.add(enemy);
        Collections.shuffle(spawns);
    }

    public void spawnEnemies() {
        betweenSpawnTimer++;
        spawnLengthTimer++;
        lengthTimer++;
//        System.out.println("spawnSize: " + spawns.size() + ", spawnLengthTimer: " + spawnLengthTimer +
//                ", betweenSpawnTimer: " + betweenSpawnTimer + ", betweenSpawns: " + betweenSpawns);
        if (spawns.size() > 0 && betweenSpawnTimer >= betweenSpawns) {
            betweenSpawnTimer = 0;
            String s = spawns.get(spawns.size() - 1);
            spawns.remove(spawns.size() - 1);
            PVector pos;
            pos = randomSpawnPosition(P);
            enemies.add(getEnemy(s, pos));
            enemies.get(enemies.size() - 1).requestPath(enemies.size() - 1);
        }
    }

    /**
     * Draws wave icons.
     * @param y displacement of icons
     * @param id current wave id
     */
    public void display(int y, int id) {
        P.tint(FILL_COLOR.getRGB());
        P.image(staticSprites.get("wavePrimaryIc"), 890, y);
        P.tint(ACCENT_COLOR.getRGB());
        P.image(staticSprites.get("waveSecondaryIc"), 890, y);
        P.tint(255);

        //title
        P.fill(TEXT_COLOR.getRGB(), 254);
        P.textAlign(CENTER);
        P.textFont(largeFont);
        P.text(TITLE, 1000, y + 110);
        P.textFont(veryLargeFont);
        //number
        P.text(id, 1000, y + 75);
        //enemy types
        int letterCount = 0;
        if (hasBurrowing) letterCount++;
        if (hasFlying) letterCount++;
        if (hasShooting) letterCount++;

        P.textFont(mediumLargeFont);
        StringBuilder enemyTypes = new StringBuilder();
        if (hasBurrowing) {
            if (letterCount > 1) enemyTypes.append(" B");
            else enemyTypes.append(" Burrowing");
        } if (hasFlying) {
            if (letterCount > 1) {
                if (hasBurrowing) enemyTypes.append(" &");
                enemyTypes.append(" F");
            }
            else enemyTypes.append(" Flying");
        } if (hasShooting) {
            if (letterCount > 1) enemyTypes.append(" & S");
            else enemyTypes.append(" Shooting");
        }
        P.text(enemyTypes.toString(), 1000, y + 25);
    }

    private Enemy getEnemy(String name) {
        return getEnemy(name, new PVector(0,0));
    }

    private Enemy getEnemy(String name, PVector pos) {
        Enemy e = null;

        switch (name) {
            case "smolBug":
                e = new SmolBug(P, pos.x, pos.y);
                break;
            case "midBug":
                e = new MidBug(P, pos.x, pos.y);
                break;
            case "Big Bugs":
            case "bigBug":
                e = new BigBug(P, pos.x, pos.y);
                break;
            case "treeSprite":
                e = new TreeSprite(P, pos.x, pos.y);
                break;
            case "Tree Spirits":
            case "treeSpirit":
                e = new TreeSpirit(P, pos.x, pos.y);
                break;
            case "Tree Giants":
            case "treeGiant":
                e = new TreeGiant(P, pos.x, pos.y);
                break;
            case "snake":
                e = new Snake(P, pos.x, pos.y);
                break;
            case "littleWorm":
                e = new Worm(P, pos.x, pos.y);
                break;
            case "butterfly":
                e = new Butterfly(P, pos.x, pos.y);
                break;
            case "scorpion":
                e = new Scorpion(P, pos.x, pos.y);
                break;
            case "sidewinder":
                e = new Sidewinder(P, pos.x, pos.y);
                break;
            case "emperor":
                e = new Emperor(P, pos.x, pos.y);
                break;
            case "midWorm":
                e = new MidWorm(P, pos.x, pos.y);
                break;
            case "Worms":
            case "Megaworms":
            case "bigWorm":
                e = new BigWorm(P, pos.x, pos.y);
                break;
            case "albinoBug":
                e = new AlbinoBug(P, pos.x, pos.y);
                break;
            case "bigAlbinoBug":
                e = new BigAlbinoBug(P, pos.x, pos.y);
                break;
            case "albinoButterfly":
                e = new AlbinoButterfly(P, pos.x, pos.y);
                break;
            case "smallGolem":
                e = new SmallGolem(P, pos.x, pos.y);
                break;
            case "midGolem":
                e = new Golem(P, pos.x, pos.y);
                break;
            case "bigGolem":
                e = new GiantGolem(P, pos.x, pos.y);
                break;
            case "bat":
                e = new Bat(P, pos.x, pos.y);
                break;
            case "bigBat":
                e = new GiantBat(P, pos.x, pos.y);
                break;
            case "wtf":
                e = new Wtf(P, pos.x, pos.y);
                break;
            case "antlion":
                e = new Antlion(P, pos.x, pos.y);
                break;
            case "Antlions":
            case "snowAntlion":
                e = new SnowAntlion(P, pos.x, pos.y);
                break;
            case "Wolves":
            case "wolf":
                e = new Wolf(P, pos.x, pos.y);
                break;
            case "Snow Sharks":
            case "shark":
                e = new Shark(P, pos.x, pos.y);
                break;
            case "Velociraptors":
            case "velociraptor":
                e = new Velociraptor(P, pos.x, pos.y);
                break;
            case "Ice Entities":
                e = new IceEntity(P, pos.x, pos.y);
                break;
            case "Ice Monstrosity":
            case "Ice Monstrosities":
                e = new IceMonstrosity(P, pos.x, pos.y);
                break;
            case "Frost":
                e = new Frost(P, pos.x, pos.y);
                break;
            case "Mammoth":
            case "Mammoths":
                e = new Mammoth(P, pos.x, pos.y);
                break;
            case "Mud Creatures":
                e = new MudCreature(P, pos.x, pos.y);
                break;
            case "Mud Flingers":
                e = new MudFlinger(P, pos.x, pos.y);
                break;
            case "Enraged Giants":
            case "Enraged Giant":
                e = new EnragedGiant(P, pos.x, pos.y);
                break;
            case "Mantises":
            case "Mantis":
                e = new Mantis(P, pos.x, pos.y);
                break;
            case "Roaches":
                e = new Roach(P, pos.x, pos.y);
                break;
        }
        return e;
    }
}
