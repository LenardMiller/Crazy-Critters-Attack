package main.util;

import main.buffs.Burning;
import main.buffs.Poisoned;
import main.buffs.Wet;
import main.enemies.*;
import main.projectiles.*;
import main.towers.Tower;
import processing.core.PApplet;

import java.util.ArrayList;

import static main.Main.*;

public class KeyBinds {

    private static PApplet p;

    public KeyBinds(PApplet p) {
        KeyBinds.p = p;
    }

    public void spawnKeys() {
        //projectiles
        boolean pebble = keysPressed.get('q') && alive;
        boolean bolt = keysPressed.get('w') && alive;
        boolean devProjectile = keysPressed.get('e') && alive;
        boolean miscProjectile = keysPressed.get('r') && alive;
        boolean smallEnergyBlast = keysPressed.get('t') && alive;
        boolean largeEnergyBlast = keysPressed.get('T') && alive;
        boolean magicMissle = keysPressed.get('y') && alive;
        //enemies
        boolean littleBug = keysPressed.get('1') && alive && p.mouseX < BOARD_WIDTH;
        boolean mediumBug = keysPressed.get('2') && alive && p.mouseX < BOARD_WIDTH;
        boolean bigBug = keysPressed.get('3') && alive && p.mouseX < BOARD_WIDTH;
        boolean treeSprite = keysPressed.get('4') && alive && p.mouseX < BOARD_WIDTH;
        boolean treeSpirit = keysPressed.get('5') && alive && p.mouseX < BOARD_WIDTH;
        //buffs
        boolean poisoned = keysPressed.get(',') && alive;
        boolean wet = keysPressed.get('.') && alive;
        boolean burning = keysPressed.get('/') && alive;
        //projectile form: spawn x, spawn y, angle
        if (pebble) projectiles.add(new Pebble(p, p.mouseX, p.mouseY, 0, 10));
        if (bolt) projectiles.add(new Bolt(p, p.mouseX, p.mouseY, 0, 20, 2));
        if (devProjectile) projectiles.add(new DevProjectile(p, p.mouseX, p.mouseY, 0));
        if (miscProjectile) projectiles.add(new MiscProjectile(p, p.mouseX, p.mouseY, 0, round(p.random(0, 5)), 6));
        if (smallEnergyBlast) projectiles.add(new EnergyBlast(p, p.mouseX, p.mouseY, 0, 20, 20, false));
        if (largeEnergyBlast) projectiles.add(new EnergyBlast(p, p.mouseX, p.mouseY, 0, 20, 30, true));
        if (magicMissle) projectiles.add(new MagicMissile(p, p.mouseX, p.mouseY, 0, 5, 0));
        //enemy form: spawn x, spawn y
        if (littleBug) {
            enemies.add(new SmolBug(p, p.mouseX, p.mouseY));
            enemies.get(enemies.size() - 1).requestPath(enemies.size() - 1);
        }
        if (mediumBug) {
            enemies.add(new MidBug(p, p.mouseX, p.mouseY));
            enemies.get(enemies.size() - 1).requestPath(enemies.size() - 1);
        }
        if (bigBug) {
            enemies.add(new BigBug(p, p.mouseX, p.mouseY));
            enemies.get(enemies.size() - 1).requestPath(enemies.size() - 1);
        }
        if (treeSprite) {
            enemies.add(new TreeSprite(p, p.mouseX, p.mouseY));
            enemies.get(enemies.size() - 1).requestPath(enemies.size() - 1);
        }
        if (treeSpirit) {
            enemies.add(new TreeSpirit(p, p.mouseX, p.mouseY));
            enemies.get(enemies.size() - 1).requestPath(enemies.size() - 1);
        }
        //buff form: enemy id
        if (poisoned) buffs.add(new Poisoned(p, (int) (p.random(0, enemies.size()))));
        if (wet) buffs.add(new Wet(p, (int) (p.random(0, enemies.size()))));
        if (burning) buffs.add(new Burning(p, (int) (p.random(0, enemies.size()))));
    }

    public void debugKeys() {
        //entity stuff
        boolean killEnemies = keysPressed.get('s') && alive;
        boolean killTowers = keysPressed.get('d') && alive;
        boolean hurtTowers = keysPressed.get('D') && alive;
        boolean killProjectiles = keysPressed.get('f') && alive;
        //other stuff
        boolean displayPathLines = keysPressed.get('g');
        //entity stuff
        if (killEnemies) {
            enemies = new ArrayList<Enemy>();
            buffs = new ArrayList<>();
            path.updatePath();
        }
        if (killTowers) {
            for (int i = 0; i < tiles.size(); i++) {
                Tower tower = tiles.get(i).tower;
                if (tower != null) tower.die();
            }
//            path.nodeCheckObs();
        }
        if (hurtTowers) {
            for (int i = 0; i < tiles.size(); i++) {
                Tower tower = tiles.get(i).tower;
                if (tower != null) tower.hp = tower.maxHp / 2;
            }
//            path.nodeCheckObs();
        }
        if (killProjectiles) projectiles = new ArrayList<>();
        //other stuff;
        if (displayPathLines) pathLines = !pathLines;
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
    }
}
