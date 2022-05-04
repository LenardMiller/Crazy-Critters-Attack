package main.misc;

import main.Game;
import main.Main;
import main.enemies.Enemy;
import main.towers.Tower;
import main.towers.Wall;
import main.towers.turrets.Booster;
import main.towers.turrets.Gluer;
import main.towers.turrets.IceTower;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;
import processing.data.JSONArray;
import processing.data.JSONObject;

import java.io.File;
import java.util.ArrayList;

import static main.misc.WallSpecialVisuals.updateTowerArray;
import static main.pathfinding.PathfindingUtilities.updateCombatPoints;
import static processing.core.PApplet.loadJSONArray;
import static processing.core.PApplet.loadJSONObject;

public class Loader {

    public static String filePath() {
        return new File("").getAbsolutePath();
    }

    /**
     * @throws RuntimeException if any of the save files are missing
     * @param p the PApplet
     */
    public static void load(PApplet p) {
        level(p);
        enemies(p);
        walls(p);
        turrets(p);

        Main.screen = Main.Screen.InGame;
    }

    private static void level(PApplet p) {
        JSONObject object = loadObject("level");

        Main.currentLevel = object.getInt("level");
        Game.reset(p);
        Main.levels[Main.currentLevel].currentWave = object.getInt("wave");
        Main.money = object.getInt("money");
        Main.machine.hp = object.getInt("hp");
        Main.playingLevel = true;
        Main.paused = true;
    }

    private static void enemies(PApplet p) {
        JSONArray array = loadArray("enemies");

        for (int i = 0; i < array.size(); i++) {
            JSONObject object = array.getJSONObject(i);
            Enemy enemy = Enemy.get(p,
                    object.getString("type"),
                    new PVector(
                            object.getFloat("x"),
                            object.getFloat("y")
                    ));
            assert enemy != null;
            enemy.rotation = object.getFloat("rotation");
            enemy.hp = object.getInt("hp");
            if (enemy.hp < enemy.maxHp) enemy.showBar = true;

            Main.enemies.add(enemy);
        }
    }

    private static void walls(PApplet p) {
        JSONArray array = loadArray("walls");

        for (int i = 0; i < array.size(); i++) {
            JSONObject object = array.getJSONObject(i);
            Tile tile = Main.tiles.get(
                    Utilities.worldPositionToGridPosition(
                            new PVector(
                                    object.getFloat("x"),
                                    object.getFloat("y"))));
            Wall wall = new Wall(p, tile);
            tile.tower = wall;
            for (int j = 0; j < object.getInt("level"); j++) {
                wall.upgrade(0, true);
            }
            wall.hp = object.getInt("hp");
            wall.placeEffect(true);
            wall.updateSprite();

            Main.towers.add(wall);
        }

        ArrayList<Tower> towers = Main.towers;
        for (Tower tower : towers) {
            if (tower instanceof Wall) ((Wall) tower).updateSprite();
        }
    }

    private static void turrets(PApplet p) {
        JSONArray array = loadArray("turrets");

        for (int i = 0; i < array.size(); i++) {
            JSONObject object = array.getJSONObject(i);
            Tile tile = Main.tiles.get(
                    Utilities.worldPositionToGridPosition(
                            new PVector(
                                    object.getFloat("x"),
                                    object.getFloat("y"))));
            Turret turret = Turret.get(p, object.getString("type"), tile);
            assert turret != null;
            for (int j = 0; j < object.getInt("levelA"); j++) {
                turret.upgrade(0, true);
            } for (int j = 0; j < object.getInt("levelB"); j++) {
                turret.upgrade(1, true);
            }
            turret.priority = Turret.Priority.values()[object.getInt("priority")];
            if (turret instanceof IceTower) {
                ((IceTower) turret).frozenTotal = object.getInt("frozenTotal");
            } else if (turret instanceof Booster) {
                ((Booster) turret).moneyTotal = object.getInt("moneyTotal");
            } else {
                if (turret instanceof Gluer) {
                    ((Gluer) turret).gluedTotal = object.getInt("gluedTotal");
                }
                turret.killsTotal = object.getInt("killsTotal");
                turret.damageTotal = object.getInt("damageTotal");
            }

            turret.placeEffect(true);
            tile.tower = turret;
            updateTowerArray();
        }

        updateCombatPoints();
    }

    private static JSONObject loadObject(String name) {
        return loadJSONObject(new File(filePath() + "/data/save/" + name + ".json"));
    }

    private static JSONArray loadArray(String name) {
        return loadJSONArray(new File(filePath() + "/data/save/" + name + ".json"));
    }
}
