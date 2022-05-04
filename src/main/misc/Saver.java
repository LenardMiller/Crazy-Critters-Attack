package main.misc;

import main.Main;
import main.enemies.Enemy;
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
     * Enemy position, hp
     * Turret position, hp, level
     * Wall position, hp, level
     * Projectile position, rotation?
     */

    /** Save at the end of each wave */
    public static void save() {
        level();
        enemies();
    }

    /** Clears all the saves */
    public static void wipe() {
        deleteFile("level");
    }

    private static void level() {
        JSONObject object = new JSONObject();

        object.setInt("level", Main.currentLevel);
        object.setInt("wave", Main.levels[Main.currentLevel].currentWave + 1);
        object.setInt("money", Main.money);
        object.setInt("hp", Main.machine.hp);

        saveObject(object.toString(), "level");
    }

    private static void enemies() {
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

        saveObject(array.toString(), "enemies");
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

        file.delete();
    }
}
