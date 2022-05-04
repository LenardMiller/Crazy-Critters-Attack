package main.misc;

import main.Main;
import processing.data.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
    }

    /** Clears all the saves */
    public static void wipe() {
        deleteFile("level");
    }

    private static void level() {
        JSONObject object = new JSONObject();

        object.setInt("level", Main.currentLevel + 1);
        object.setInt("wave", Main.levels[Main.currentLevel].currentWave);
        object.setInt("money", Main.money);
        object.setInt("hp", Main.machine.hp);

        saveObject(object, "level");
    }

    private static void saveObject(JSONObject object, String name) {
        String path = filePath() + "/data/save/" + name + ".json";
        new File(path);
        try {
            FileWriter saveWriter = new FileWriter(path);
            saveWriter.write(object.toString());
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
