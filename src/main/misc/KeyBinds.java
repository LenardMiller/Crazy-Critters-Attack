package main.misc;

import main.buffs.Burning;
import main.buffs.Poisoned;
import main.buffs.Wet;
import main.enemies.*;
import main.levelStructure.Wave;
import main.particles.*;
import main.projectiles.*;
import main.towers.Tower;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

import static main.Main.*;
import static main.misc.MiscMethods.updateNodes;

public class KeyBinds {

    private static PApplet p;

    public KeyBinds(PApplet p) {
        KeyBinds.p = p;
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
        //enemies
        boolean activateLevels = keysPressed.getPressedPulse('0') && alive;
        boolean littleBug = keysPressed.getPressedPulse('1') && alive && p.mouseX < BOARD_WIDTH;
        boolean mediumBug = keysPressed.getPressedPulse('2') && alive && p.mouseX < BOARD_WIDTH;
        boolean bigBug = keysPressed.getPressedPulse('3') && alive && p.mouseX < BOARD_WIDTH;
        boolean treeSprite = keysPressed.getPressedPulse('4') && alive && p.mouseX < BOARD_WIDTH;
        boolean treeSpirit = keysPressed.getPressedPulse('5') && alive && p.mouseX < BOARD_WIDTH;
        boolean treeGiant = keysPressed.getPressedPulse('6') && alive && p.mouseX < BOARD_WIDTH;
        boolean snake = keysPressed.getPressedPulse('7') && alive && p.mouseX < BOARD_WIDTH;
        boolean worm = keysPressed.getPressedPulse('8') && alive && p.mouseX < BOARD_WIDTH;
        boolean butterfly = keysPressed.getPressedPulse('9') && alive && p.mouseX < BOARD_WIDTH;
        //buffs
        boolean poisoned = keysPressed.getPressedPulse(',') && alive;
        boolean wet = keysPressed.getPressedPulse('.') && alive;
        boolean burning = keysPressed.getPressedPulse('/') && alive;
        //particles
        boolean hurt = keysPressed.getPressed('z') && alive;
        boolean die = keysPressed.getPressed('x') && alive;
        boolean debris = keysPressed.getPressed('c') && alive;
        boolean buff = keysPressed.getPressed('v') && alive;
        boolean mediumExplosion = keysPressed.getPressed('b') && alive;
        boolean largeExplosion = keysPressed.getPressed('n') && alive;
        //projectiles
        if (pebble) projectiles.add(new Pebble(p, p.mouseX, p.mouseY, 0, null, 10));
        if (bolt) projectiles.add(new Bolt(p, p.mouseX, p.mouseY, 0, null, 20, 2));
        if (miscProjectile) projectiles.add(new MiscProjectile(p, p.mouseX, p.mouseY, 0, null, round(p.random(0, 5)), 6));
        if (smallEnergyBlast) projectiles.add(new EnergyBlast(p, p.mouseX, p.mouseY, 0, null, 20, 20, false));
        if (largeEnergyBlast) projectiles.add(new EnergyBlast(p, p.mouseX, p.mouseY, 0, null, 20, 30, true));
        if (magicMissle) projectiles.add(new MagicMissile(p, p.mouseX, p.mouseY, 0, null, 5, 0, new PVector(p.mouseX,p.mouseY)));
        if (arc) arcs.add(new Arc(p, p.mouseX, p.mouseY, null, 35, 5, 500, 0));
        if (needle) projectiles.add(new Needle(p, p.mouseX, p.mouseY, 0, null, 5, 1,20));
        //enemies
        if (activateLevels) { //temp
            playingLevel = true;
            forest.currentWave = 0;
            Wave wave = forest.waves[forest.currentWave];
            wave.init();
        }
        if (littleBug) enemies.add(new SmolBug(p, p.mouseX, p.mouseY));
        if (mediumBug) enemies.add(new MidBug(p, p.mouseX, p.mouseY));
        if (bigBug) enemies.add(new BigBug(p, p.mouseX, p.mouseY));
        if (treeSprite) enemies.add(new TreeSprite(p, p.mouseX, p.mouseY));
        if (treeSpirit) enemies.add(new TreeSpirit(p, p.mouseX, p.mouseY));
        if (treeGiant) enemies.add(new TreeGiant(p, p.mouseX, p.mouseY));
        if (snake) enemies.add(new Snake(p, p.mouseX, p.mouseY));
        if (worm) enemies.add(new LittleWorm(p, p.mouseX, p.mouseY));
        if (butterfly) enemies.add(new Butterfly(p, p.mouseX, p.mouseY));
        if (littleBug || mediumBug || bigBug || treeSprite || treeSpirit || treeGiant || snake || worm || butterfly) enemies.get(enemies.size() - 1).requestPath(enemies.size() - 1);
        //buffs
        if (poisoned) buffs.add(new Poisoned(p, (int) (p.random(0, enemies.size())),null));
        if (wet) buffs.add(new Wet(p, (int) (p.random(0, enemies.size())),null));
        if (burning) buffs.add(new Burning(p, (int) (p.random(0, enemies.size())),null));
        //particles
        if (hurt) {
            int num = round(p.random(0, 2));
            String type = "redOuch";
            if (num == 0) type = "redOuch";
            else if (num == 1) type = "greenOuch";
            else if (num == 2) type = "pinkOuch";
            particles.add(new Ouch(p, p.mouseX, p.mouseY, p.random(0, 360), type));
        }
        if (die) particles.add(new Ouch(p, p.mouseX, p.mouseY, p.random(0, 360), "greyPuff"));
        if (debris) {
            int num = round(p.random(0, 4));
            String type = "null";
            if (num == 0) type = "wood";
            else if (num == 1) type = "stone";
            else if (num == 2) type = "metal";
            else if (num == 3) type = "crystal";
            else if (num == 4) type = "devWood";
            particles.add(new Debris(p, p.mouseX, p.mouseY, p.random(0, 360), type));
        }
        if (buff) {
            int num = floor(p.random(0, 4.9f));
            String type = "poison";
            if (num == 0) type = "poison";
            else if (num == 1) type = "water";
            else if (num == 2) type = "fire";
            else if (num == 3) type = "energy";
            else if (num == 4) type = "greenMagic";
            particles.add(new BuffParticle(p, p.mouseX, p.mouseY, p.random(0, 360), type));
        }
        if (mediumExplosion) particles.add(new MediumExplosion(p, p.mouseX, p.mouseY, p.random(0, 360)));
        if (largeExplosion) particles.add(new LargeExplosion(p, p.mouseX, p.mouseY, p.random(0, 360)));
    }

    public void debugKeys() {
        //entity stuff
        boolean killEnemies = keysPressed.getReleasedPulse('s') && alive;
        boolean killTowers = keysPressed.getReleasedPulse('d') && alive;
        boolean hurtTowers = keysPressed.getReleasedPulse('D') && alive;
        boolean killProjectiles = keysPressed.getReleasedPulse('f') && alive;
        //other stuff
        boolean displayPathLines = keysPressed.getReleasedPulse('g');
        boolean updatePaths = keysPressed.getPressedPulse(' ');
        boolean loseMoney = keysPressed.getPressedPulse('-');
        //entity stuff
        if (killEnemies) {
            enemies = new ArrayList<>();
            buffs = new ArrayList<>();
            updateNodes();
        }
        if (killTowers) {
            for (int i = 0; i < tiles.size(); i++) {
                Tower tower = tiles.get(i).tower;
                if (tower != null) tower.die();
            }
            updateNodes();
        }
        if (hurtTowers) {
            for (int i = 0; i < tiles.size(); i++) {
                Tower tower = tiles.get(i).tower;
                if (tower != null) {
                    tower.hp = tower.maxHp / 2;
                    tower.barTrans = 255;
                }
            }
            updateNodes();
        }
        if (killProjectiles) projectiles = new ArrayList<>();
        //other stuff
        if (displayPathLines) debug = !debug;
        if (updatePaths) updateNodes();
        if (loseMoney) money = 0;
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
