package main.misc;

import main.Game;
import main.Main;
import processing.core.PApplet;
import processing.data.JSONObject;

import java.io.File;

import static processing.core.PApplet.loadJSONObject;

public class Loader {

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

    /**
     * @throws RuntimeException if any of the save files are missing
     * @param p the PApplet
     */
    public static void load(PApplet p) {
        level(p);

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

    private static JSONObject loadObject(String name) {
        return loadJSONObject(new File(filePath() + "/data/save/" + name + ".json"));
    }
}
