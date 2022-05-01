package main.misc;

import main.Main;
import main.damagingThings.arcs.Arc;
import main.damagingThings.projectiles.EnergyBlast;
import main.damagingThings.projectiles.Flame;
import main.damagingThings.projectiles.MiscProjectile;
import main.damagingThings.projectiles.Needle;
import main.damagingThings.projectiles.enemyProjeciles.IceCrystal;
import main.damagingThings.projectiles.enemyProjeciles.Snowball;
import main.damagingThings.projectiles.homing.MagicMissile;
import main.enemies.*;
import main.enemies.burrowingEnemies.BigWorm;
import main.enemies.burrowingEnemies.Shark;
import main.enemies.flyingEnemies.Bat;
import main.enemies.flyingEnemies.Frost;
import main.enemies.flyingEnemies.Mantoid;
import main.enemies.shootingEnemies.IceEntity;
import main.enemies.shootingEnemies.IceMonstrosity;
import main.enemies.shootingEnemies.SnowAntlion;
import main.levelStructure.Level;
import main.towers.Tower;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import java.io.IOException;
import java.util.ArrayList;

import static main.Main.*;
import static main.misc.Utilities.closeSettingsMenu;
import static main.pathfinding.PathfindingUtilities.updateCombatPoints;
import static main.sound.SoundUtilities.playSound;

public class KeyBinds {

    private static PApplet p;

    public KeyBinds(PApplet p) {
        KeyBinds.p = p;
    }

    public void menuKeys() {
        boolean pause = keysPressed.getPressedPulse('|');

        if (pause) {
            switch (screen) {
                case InGame:
                    playSound(sounds.get("clickOut"), 1, 1);
                    if (settings) closeSettingsMenu();
                    else paused = !paused;
                    break;
                case LevelSelect:
                    if (settings) {
                        playSound(sounds.get("clickOut"), 1, 1);
                        settings = false;
                    }
                    break;
            }
        }
    }

    public void selectionKeys() {
        boolean upgradeTop = keysPressed.getPressedPulse('1');
        boolean upgradeBottom = keysPressed.getPressedPulse('2');
        boolean priorityRight = keysPressed.getPressedPulse('3');
        boolean delete = keysPressed.getPressedPulse('4');

        if (upgradeTop) selection.upgradeTop();
        if (upgradeBottom) selection.upgradeBottom();
        if (priorityRight) selection.changePriority();
        if (delete) selection.sell();
    }

    public void inGameKeys() {
        boolean play = keysPressed.getPressedPulse(' ');
        //hotkeys
        boolean wall =           addHotkey(new char[]{'?'}, 0),
                slingshot =      addHotkey(new char[]{'q', 'Q'}, SLINGSHOT_PRICE),
                luggageBlaster = addHotkey(new char[]{'a', 'A'}, RANDOM_CANNON_PRICE),
                crossbow =       addHotkey(new char[]{'z', 'Z'}, CROSSBOW_PRICE),
                cannon =         addHotkey(new char[]{'w', 'W'}, CANNON_PRICE),
                gluer =          addHotkey(new char[]{'s', 'S'}, GLUER_PRICE),
                seismicTower =   addHotkey(new char[]{'x', 'X'}, SEISMIC_PRICE),
                energyBlaster =  addHotkey(new char[]{'e', 'E'}, ENERGY_BLASTER_PRICE),
                flamethrower =   addHotkey(new char[]{'d', 'Q'}, FLAMETHROWER_PRICE),
                teslaTower =     addHotkey(new char[]{'c', 'C'}, TESLA_TOWER_PRICE),
                booster =        addHotkey(new char[]{'r', 'R'}, BOOSTER_PRICE),
                iceTower =       addHotkey(new char[]{'f', 'F'}, ICE_TOWER_PRICE),
                magicMissileer = addHotkey(new char[]{'v', 'V'}, MAGIC_MISSILEER_PRICE),
                railgun =        addHotkey(new char[]{'t', 'T'}, RAILGUN_PRICE),
                nightmare =      addHotkey(new char[]{'g', 'G'}, NIGHTMARE_PRICE),
                waveMotion =     addHotkey(new char[]{'b', 'B'}, WAVE_MOTION_PRICE);

        if (play) {
            if (!playingLevel) {
                playingLevel = true;
                Level level = levels[currentLevel];
                level.currentWave = 0;
            } else {
                levels[currentLevel].advance();
            }
        }
        //hotkeys
        if (!dev) {
            String name = "";
            if (wall)               name = "wall";
            if (slingshot)          name = "slingshot";
            if (luggageBlaster)     name = "miscCannon";
            if (crossbow)           name = "crossbow";
            if (currentLevel > 0) {
                if (cannon)         name = "cannon";
                if (gluer)          name = "gluer";
                if (seismicTower)   name = "seismic";
            } if (currentLevel > 1) {
                if (energyBlaster)  name = "energyBlaster";
                if (flamethrower)   name = "flamethrower";
                if (teslaTower)     name = "tesla";
            } if (currentLevel > 2) {
                if (booster)        name = "booster";
                if (iceTower)       name = "iceTower";
                if (magicMissileer) name = "magicMissleer";
            } if (currentLevel > 3) {
                if (railgun)        name = "railgun";
                if (nightmare)      name = "nightmare";
                if (waveMotion)     name = "waveMotion";
            }
            if (!name.isEmpty()) {
                if (hand.held.equals(name)) hand.setHeld("null");
                else hand.setHeld(name);
            }
        }
    }

    private boolean addHotkey(char[] keys, int price) {
        boolean pressed = false;
        //this is important, don't remove it
        for (char key : keys) if (keysPressed.getPressedPulse(key)) pressed = true;
        return pressed && alive && !paused && money >= price;
    }

    public void spawnKeys() {
        //projectiles
        boolean pebble = keysPressed.getPressedPulse('q') && alive;
        boolean bolt = keysPressed.getPressedPulse('w') && alive;
        boolean miscProjectile = keysPressed.getPressedPulse('e') && alive;
        boolean smallEnergyBlast = keysPressed.getPressedPulse('r') && alive;
        boolean largeEnergyBlast = keysPressed.getPressedPulse('R') && alive;
        boolean magicMissle = keysPressed.getPressedPulse('t') && alive;
        boolean arc = keysPressed.getPressedPulse('y') && alive && enemies.size() != 0;
        boolean needle = keysPressed.getPressedPulse('u') && alive;
        boolean flame = keysPressed.getPressed('i') && alive;
        //enemies
        boolean en1 =  keysPressed.getPressedPulse('1') && alive && matrixMousePosition.x < BOARD_WIDTH;
        boolean en2 =  keysPressed.getPressedPulse('2') && alive && matrixMousePosition.x < BOARD_WIDTH;
        boolean en3 =  keysPressed.getPressedPulse('3') && alive && matrixMousePosition.x < BOARD_WIDTH;
        boolean en4 =  keysPressed.getPressedPulse('4') && alive && matrixMousePosition.x < BOARD_WIDTH;
        boolean en5 =  keysPressed.getPressedPulse('5') && alive && matrixMousePosition.x < BOARD_WIDTH;
        boolean en6 =  keysPressed.getPressedPulse('6') && alive && matrixMousePosition.x < BOARD_WIDTH;
        boolean en7 =  keysPressed.getPressedPulse('7') && alive && matrixMousePosition.x < BOARD_WIDTH;
        boolean en8 =  keysPressed.getPressedPulse('8') && alive && matrixMousePosition.x < BOARD_WIDTH;
        boolean en9 =  keysPressed.getPressedPulse('9') && alive && matrixMousePosition.x < BOARD_WIDTH;
        boolean en1b = keysPressed.getPressedPulse('!') && alive && matrixMousePosition.x < BOARD_WIDTH;
        //projectiles
        if (pebble) projectiles.add(new Snowball(p, 10, matrixMousePosition.x, matrixMousePosition.y, 0));
        if (bolt) projectiles.add(new IceCrystal(p, 10, matrixMousePosition.x, matrixMousePosition.y, 0));
        if (miscProjectile) projectiles.add(new MiscProjectile(p, matrixMousePosition.x, matrixMousePosition.y, 0, null, round(p.random(0, 5)), 6));
        if (smallEnergyBlast) projectiles.add(new EnergyBlast(p, matrixMousePosition.x, matrixMousePosition.y, 0, null, 20, 20, false));
        if (largeEnergyBlast) projectiles.add(new EnergyBlast(p, matrixMousePosition.x, matrixMousePosition.y, 0, null, 20, 30, true));
        if (magicMissle) projectiles.add(new MagicMissile(p, matrixMousePosition.x, matrixMousePosition.y, 0, null, 5, Turret.Priority.Close, new PVector(matrixMousePosition.x,matrixMousePosition.y)));
        if (arc) arcs.add(new Arc(p, matrixMousePosition.x, matrixMousePosition.y, null, 35, 5, 500, Turret.Priority.Close));
        if (needle) projectiles.add(new Needle(p, matrixMousePosition.x, matrixMousePosition.y, 0, null, 5, 1,150, 500));
        if (flame) projectiles.add(new Flame(p, matrixMousePosition.x, matrixMousePosition.y, 0, null, 5, 1, 300, 5, false));
        //enemies
        if (en1) enemies.add(new GiantGolem( p, matrixMousePosition.x, matrixMousePosition.y));
        if (en2) enemies.add(new Wolf(p, matrixMousePosition.x, matrixMousePosition.y));
        if (en3) enemies.add(new Velociraptor(p, matrixMousePosition.x, matrixMousePosition.y));
        if (en4) enemies.add(new Mammoth(p, matrixMousePosition.x, matrixMousePosition.y));
        if (en5) enemies.add(new MudCreature(p, matrixMousePosition.x, matrixMousePosition.y));
        if (en6) enemies.add(new Frost(p, matrixMousePosition.x, matrixMousePosition.y));
        if (en7) enemies.add(new Bat(p, matrixMousePosition.x, matrixMousePosition.y));
        if (en8) enemies.add(new Shark(p, matrixMousePosition.x, matrixMousePosition.y));
        if (en9) enemies.add(new TreeSpirit(p, matrixMousePosition.x, matrixMousePosition.y));
        if (en1b) enemies.add(new Dummy(p, matrixMousePosition.x, matrixMousePosition.y));
        if (en1 || en2 || en3 || en4 || en5 || en6 || en8 || en7 || en9 || en1b) enemies.get(enemies.size() - 1).requestPath(enemies.size() - 1);
    }

    public void debugKeys() {
        //entity stuff
        boolean deleteEnemies = keysPressed.getReleasedPulse('s') && alive;
        boolean killTowers = keysPressed.getReleasedPulse('d') && alive;
        boolean hurtTowers = keysPressed.getReleasedPulse('D') && alive;
        boolean killProjectiles = keysPressed.getReleasedPulse('f') && alive;
        boolean killEnemies = keysPressed.getPressedPulse('c') && alive;
        boolean overkillEnemies = keysPressed.getPressedPulse('C') && alive;
        //other stuff
        boolean displayDebug = keysPressed.getReleasedPulse('g');
        boolean displaySpawnAreas = keysPressed.getReleasedPulse('G');
        boolean loseMoney = keysPressed.getPressedPulse('-');
        boolean addMoney = keysPressed.getPressed('=');
        boolean levelBuilder = keysPressed.getPressedPulse('b');
        boolean saveTiles = keysPressed.getPressedPulse('z');
        boolean increaseWave = keysPressed.getPressedPulse(']'); //only works when game playing
        boolean decreaseWave = keysPressed.getPressedPulse('[');
        boolean increaseWave5 = keysPressed.getPressedPulse('}');
        boolean decreaseWave5 = keysPressed.getPressedPulse('{');
        //entity stuff
        if (deleteEnemies) {
            enemies = new ArrayList<>();
            buffs = new ArrayList<>();
        } if (killTowers) {
            for (int i = 0; i < tiles.size(); i++) {
                Tower tower = tiles.get(i).tower;
                if (tower != null) tower.die(false);
            }
            updateCombatPoints();
            machine.die();
        } if (hurtTowers) {
            for (int i = 0; i < tiles.size(); i++) {
                Tower tower = tiles.get(i).tower;
                if (tower != null) tower.hp -= tower.maxHp/5;
            }
            updateCombatPoints();
            machine.damage(20);
        } if (killProjectiles) projectiles = new ArrayList<>();
        if (killEnemies) {
            for (Enemy enemy : enemies) {
                enemy.damageWithoutBuff(enemy.maxHp - 1, null, "none", new PVector(0,0), true);
                enemy.damageWithoutBuff(1, null, "none", new PVector(0,0), true);

            }
        } if (overkillEnemies) {
            for (Enemy enemy : enemies) {
                enemy.damageWithoutBuff(enemy.maxHp + 1, null, "none", new PVector(0,0), true);
            }
        }
        //other stuff
        if (displayDebug) debug = !debug;
        if (displaySpawnAreas) showSpawn = !showSpawn;
        if (addMoney) money += 1000;
        if (loseMoney) money = 0;
        if (levelBuilder) {
            Main.levelBuilder = !Main.levelBuilder;
            hand.setHeld("null");
        } if (saveTiles) {
            try {
                DataControl.saveLayout();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        if (increaseWave && canWave(1)) levels[currentLevel].setWave(levels[currentLevel].currentWave + 1);
        if (decreaseWave && canWave(-1)) levels[currentLevel].setWave(levels[currentLevel].currentWave - 1);
        if (increaseWave5 && canWave(5)) levels[currentLevel].setWave(levels[currentLevel].currentWave + 5);
        if (decreaseWave5 && canWave(-5)) levels[currentLevel].setWave(levels[currentLevel].currentWave - 5);
    }

    private boolean canWave(int count) {
        if (levels[currentLevel].currentWave + count < 0) return false;
        else return levels[currentLevel].currentWave + count <= levels[currentLevel].waves.length-1;
    }

    public void loadKeyBinds() {
        keysPressed.add('`');
        keysPressed.add('1');
        keysPressed.add('2');
        keysPressed.add('3');
        keysPressed.add('4');
        keysPressed.add('5');
        keysPressed.add('6');
        keysPressed.add('7');
        keysPressed.add('8');
        keysPressed.add('9');
        keysPressed.add('0');
        keysPressed.add('-');
        keysPressed.add('=');
        keysPressed.add('~');
        keysPressed.add('!');
        keysPressed.add('@');
        keysPressed.add('#');
        keysPressed.add('$');
        keysPressed.add('%');
        keysPressed.add('^');
        keysPressed.add('&');
        keysPressed.add('*');
        keysPressed.add('(');
        keysPressed.add(')');
        keysPressed.add('_');
        keysPressed.add('+');
        keysPressed.add('q');
        keysPressed.add('w');
        keysPressed.add('e');
        keysPressed.add('r');
        keysPressed.add('t');
        keysPressed.add('y');
        keysPressed.add('u');
        keysPressed.add('i');
        keysPressed.add('o');
        keysPressed.add('p');
        keysPressed.add('[');
        keysPressed.add(']');
        keysPressed.add('Q');
        keysPressed.add('W');
        keysPressed.add('E');
        keysPressed.add('R');
        keysPressed.add('T');
        keysPressed.add('Y');
        keysPressed.add('U');
        keysPressed.add('I');
        keysPressed.add('O');
        keysPressed.add('P');
        keysPressed.add('{');
        keysPressed.add('}');
        keysPressed.add('|');
        keysPressed.add('a');
        keysPressed.add('s');
        keysPressed.add('d');
        keysPressed.add('f');
        keysPressed.add('g');
        keysPressed.add('h');
        keysPressed.add('j');
        keysPressed.add('k');
        keysPressed.add('l');
        keysPressed.add(';');
        keysPressed.add('A');
        keysPressed.add('S');
        keysPressed.add('D');
        keysPressed.add('F');
        keysPressed.add('G');
        keysPressed.add('H');
        keysPressed.add('J');
        keysPressed.add('K');
        keysPressed.add('L');
        keysPressed.add(':');
        keysPressed.add('"');
        keysPressed.add('z');
        keysPressed.add('x');
        keysPressed.add('c');
        keysPressed.add('v');
        keysPressed.add('b');
        keysPressed.add('n');
        keysPressed.add('m');
        keysPressed.add(',');
        keysPressed.add('.');
        keysPressed.add('/');
        keysPressed.add('Z');
        keysPressed.add('X');
        keysPressed.add('C');
        keysPressed.add('V');
        keysPressed.add('B');
        keysPressed.add('N');
        keysPressed.add('M');
        keysPressed.add('<');
        keysPressed.add('>');
        keysPressed.add('?');
        keysPressed.add(' ');
    }
}
