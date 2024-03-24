package main.misc;

import main.Main;
import main.enemies.Enemy;
import main.levelStructure.Level;
import main.towers.IceWall;
import main.towers.Tower;
import main.towers.Wall;
import main.towers.turrets.Booster;
import main.towers.turrets.Gluer;
import main.towers.turrets.IceTower;
import main.towers.turrets.Turret;
import processing.data.JSONArray;
import processing.data.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Saver {

    public static String filePath() {
        return new File("").getAbsolutePath();
    }

    /* Everything I need to save:
     * How polluted a level is
     * IceWall position, hp, lifespan
     * Projectile position, rotation?
     */

    /** Save at the end of each wave */
    public static void save() {
        JSONObject object = new JSONObject();

        object.setJSONObject("level", level());
        object.setJSONArray("enemies", enemies());
        object.setJSONArray("walls", walls());
        object.setJSONArray("turrets", turrets());
        object.setJSONArray("iceWalls", iceWalls());
        object.setJSONObject("pollution", pollution());

        saveObject(object.toString(), "save");
    }

    /** Clears all the saves */
    public static void wipe() {
        deleteFile("level");
        deleteFile("enemies");
        deleteFile("walls");
        deleteFile("turrets");
        deleteFile("iceWalls");
        deleteFile("pollution");
    }

    private static JSONObject level() {
        JSONObject object = new JSONObject();

        object.setInt("level", Main.currentLevel);
        object.setInt("wave", Main.levels[Main.currentLevel].currentWave + 1);
        object.setInt("money", Main.money);
        object.setInt("hp", Main.machine.hp);

        return object;
    }

    private static JSONArray enemies() {
        JSONArray array = new JSONArray();

        ArrayList<Enemy> enemies = Main.enemies;
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            JSONObject object = new JSONObject();

            object.setString("type", enemy.name);
            object.setFloat("x", enemy.position.x);
            object.setFloat("y", enemy.position.y);
            object.setFloat("rotation", enemy.rotation);
            object.setInt("hp", enemy.hp);

            array.setJSONObject(i, object);
        }

        return array;
    }

    private static JSONArray walls() {
        JSONArray array = new JSONArray();

        ArrayList<Tower> walls = new ArrayList<>(Main.towers);
        walls.removeIf(tower -> !(tower instanceof Wall) || tower instanceof IceWall);
        for (int i = 0; i < walls.size(); i++) {
            Wall wall = (Wall) walls.get(i);
            JSONObject object = new JSONObject();

            object.setInt("level", wall.nextLevelB);
            object.setInt("hp", wall.hp);
            object.setFloat("x", wall.tile.position.x);
            object.setFloat("y", wall.tile.position.y);

            array.setJSONObject(i, object);
        }

        return array;
    }

    private static JSONArray iceWalls() {
        JSONArray array = new JSONArray();

        ArrayList<Tower> iceWalls = new ArrayList<>(Main.towers);
        iceWalls.removeIf(tower -> (!(tower instanceof IceWall)));
        for (int i = 0; i < iceWalls.size(); i++) {
            IceWall iceWall = (IceWall) iceWalls.get(i);
            JSONObject object = new JSONObject();

            object.setInt("maxHp", iceWall.maxHp);
            object.setInt("hp", iceWall.hp);
            object.setFloat("x", iceWall.tile.position.x);
            object.setFloat("y", iceWall.tile.position.y);
            object.setInt("timeUntilDamage", iceWall.timeUntilDamage);

            array.setJSONObject(i, object);
        }

        return array;
    }

    private static JSONArray turrets() {
        JSONArray array = new JSONArray();

        ArrayList<Tower> turrets = new ArrayList<>(Main.towers);
        turrets.removeIf(tower -> !(tower instanceof Turret));
        for (int i = 0; i < turrets.size(); i++) {
            Turret turret = (Turret) turrets.get(i);
            JSONObject object = new JSONObject();

            object.setString("type", turret.getClass().getSimpleName());
            object.setFloat("x", turret.tile.position.x);
            object.setFloat("y", turret.tile.position.y);
            object.setInt("levelA", turret.nextLevelA);
            object.setInt("levelB", turret.nextLevelB - 3);
            object.setInt("priority", turret.priority.ordinal());
            object.setInt("birthday", turret.birthday);
            if (turret instanceof IceTower) {
                object.setInt("frozenTotal", ((IceTower) turret).frozenTotal);
            } else if (turret instanceof Booster) {
                object.setInt("moneyTotal", ((Booster) turret).moneyTotal);
            } else {
                object.setInt("damageTotal", turret.damageTotal);
                object.setInt("killsTotal", turret.killsTotal);
                if (turret instanceof Gluer) {
                    object.setInt("gluedTotal", ((Gluer) turret).gluedTotal);
                }
            }

            array.setJSONObject(i, object);
        }

        return array;
    }

    private static JSONObject pollution() {
        JSONObject object = new JSONObject();

        Level level = Main.levels[Main.currentLevel];
        Polluter polluter = level.polluter;
        if (polluter != null) {
            object.setBoolean("exists", true);
            object.setInt("cleanTilesSize", polluter.CLEAN_TILES.size());
            object.setFloat("secondsBetweenPollutes", polluter.betweenPollutes / (float) Main.FRAMERATE);
            object.setString("name", polluter.NAME);
            if (level.lastPolluterName != null) {
                object.setString("lastPolluterName", level.lastPolluterName);
            }
        }

        return object;
    }

    private static void saveObject(String object, String name) {
        String path = filePath() + "/data/save/" + name + ".json";
        new File(path);
        try {
            FileWriter saveWriter = new FileWriter(path);
            saveWriter.write(object);
            saveWriter.close();
        } catch (IOException exception) {
            System.out.println("failed to save " + path);
        }
    }

    private static void deleteFile(String name) {
        String path = filePath() + "/data/save/" + name + ".json";
        File file = new File(path);

        //do nothing if file doesn't exist
        //noinspection ResultOfMethodCallIgnored
        file.delete();
    }
}
