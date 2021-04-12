package main.misc;

import processing.core.PApplet;
import processing.core.PVector;
import processing.data.JSONArray;
import processing.data.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static main.Main.*;
import static main.misc.Utilities.down60ToFramerate;

public class DataControl extends ClassLoader {

    public DataControl() {}

    /**
     * Saves level data to a JSON file.
     * @throws IOException catch if file is missing
     */
    public static void saveLayout() throws IOException {
        JSONArray saveArray = new JSONArray();
        //tiles
        for (int i = 0; i < tiles.size(); i++) {
            Tile tile = tiles.get(i);
            JSONObject saveObject = new JSONObject();
            saveObject.setString("type", "tile");
            saveObject.setInt("id", i);
            saveObject.setString("bgA", tile.baseName);
            saveObject.setString("bgB", tile.decorationName);
            saveObject.setString("bgC", tile.breakableName);
            saveObject.setString("obstacle", tile.obstacleName);
            saveObject.setBoolean("machine", tile.machine);
            saveArray.setJSONObject(i, saveObject);
        }
        int i = tiles.size();
        //machine
        JSONObject saveObject = new JSONObject();
        saveObject.setString("type", "machine");
        saveObject.setInt("id", i);
        saveObject.setInt("hp", machine.hp);
        saveObject.setInt("maxHp", machine.maxHp);
        saveObject.setFloat("x", machine.position.x);
        saveObject.setFloat("y", machine.position.y);
        saveObject.setString("name", machine.name);
        saveObject.setString("debris", machine.debris);
        saveObject.setInt("betweenFrames", machine.betweenFrames);
        saveObject.setFloat("barX", machine.barPosition.x);
        saveObject.setFloat("barY", machine.barPosition.y);
        saveObject.setFloat("barSizeX", machine.barSize.x);
        saveObject.setFloat("barSizeY", machine.barSize.y);
        saveObject.setBoolean("barHorizontal", machine.barHorizontal);
        saveArray.setJSONObject(i, saveObject);

        String name = "Save-"+month()+"-"+day()+"-"+year()+"-"+hour() +"-"+minute()+"-"+second();
        //run from terminal
        String filePath = new File("").getAbsolutePath();
        //run from intelliJ
        if (filePath.equals("/Users/blakebabb/Documents/GitHub/Crazy-Critters-Attack")) {
            filePath = "resources";
        }
        new File(filePath + "/data/saves/" + name + ".json");
        FileWriter saveWriter = new FileWriter("resources/data/saves/" + name + ".json");
        saveWriter.write(saveArray.toString());
        saveWriter.close();
    }

    /**
     * Saves settings to a JSON file
     * @param p the PApplet
     * @throws IOException if the file doesn't exist
     */
    public static void saveSettings(PApplet p) throws IOException {
        JSONObject saveObject = new JSONObject();
        saveObject.setFloat("volume", globalVolume);

        String name = "settings";
        //run from terminal
        String filePath = new File("").getAbsolutePath();
        //run from intelliJ
        if (filePath.equals("/Users/blakebabb/Documents/GitHub/Crazy-Critters-Attack")) {
            filePath = "resources";
        }
        new File(filePath + "/data/saves/" + name + ".json");
        FileWriter saveWriter = new FileWriter("resources/data/" + name + ".json");
        saveWriter.write(saveObject.toString());
        saveWriter.close();
    }

    public static void loadSettings(PApplet p) {
        //run from terminal
        String filePath = new File("").getAbsolutePath();
        //run from intelliJ
        if (filePath.equals("/Users/blakebabb/Documents/GitHub/Crazy-Critters-Attack")) {
            filePath = "resources";
        }
        File loadFile = new File(filePath+"/data/settings.json");
        JSONObject loadObject = loadJSONObject(loadFile);
        globalVolume = loadObject.getFloat("volume");
    }

    /**
     * Loads level data from a JSON file.
     * @param p the PApplet
     * @param file the filename, sans extension.
     */
    public static void loadLayout(PApplet p, String file) {
        //run from terminal
        String filePath = new File("").getAbsolutePath();
        //run from intelliJ
        if (filePath.equals("/Users/blakebabb/Documents/GitHub/Crazy-Critters-Attack")) {
            filePath = "resources";
        }
        File loadFile = new File(filePath+"/data/"+file+"/clean.json");
        JSONArray loadArray = loadJSONArray(loadFile);

        alive = true;
        //tiles
        for (int i = 0; i < tiles.size(); i++) {
            Tile tile = tiles.get(i);
            tile.machine = false;
            JSONObject loadedTile = loadArray.getJSONObject(i);
            String bgA = loadedTile.getString("bgA");
            String bgB = loadedTile.getString("bgB");
            String bgC = loadedTile.getString("bgC");
            String obstacle = loadedTile.getString("obstacle");
            boolean machine = loadedTile.getBoolean("machine");
            if (bgA != null) tile.setBase(bgA);
            if (bgB != null) tile.setDecoration(bgB);
            if (bgC != null) tile.setBreakable(bgC);
            if (obstacle != null) tile.setObstacle(obstacle);
            tile.machine = machine;
        }
        int i = tiles.size();
        //machine
        JSONObject loadObject = loadArray.getJSONObject(i);
        int hp = loadObject.getInt("hp");
        int maxHp = loadObject.getInt("maxHp");
        int x = loadObject.getInt("x");
        int y = loadObject.getInt("y");
        String machineName = loadObject.getString("name");
        String debris = loadObject.getString("debris");
        int betweenFrames = down60ToFramerate(loadObject.getInt("betweenFrames"));
        float barX = loadObject.getFloat("barX");
        float barY = loadObject.getFloat("barY");
        float barSizeX = loadObject.getFloat("barSizeX");
        float barSizeY = loadObject.getFloat("barSizeY");
        boolean barHorizontal = loadObject.getBoolean("barHorizontal");
        machine = new Machine(p,new PVector(x,y), machineName, debris, betweenFrames, maxHp);
        machine.hp = hp;
        machine.barPosition = new PVector(barX, barY);
        machine.barSize = new PVector(barSizeX, barSizeY);
        machine.barHorizontal = barHorizontal;
    }

    /**
     * Loads data about a single tile from JSON
     * @param tile Tile to load data about
     * @param file file to load data from, no extension
     */
    public static void loadTile(Tile tile, String file) {
        //run from terminal
        String filePath = new File("").getAbsolutePath();
        //run from intelliJ
        if (filePath.equals("/Users/blakebabb/Documents/GitHub/Crazy-Critters-Attack")) {
            filePath = "resources";
        }
        File loadFile = new File(filePath+"/data/"+file+".json");
        JSONArray loadArray = loadJSONArray(loadFile);

        JSONObject loadedTile = loadArray.getJSONObject(tile.id);
        String bgA = loadedTile.getString("bgA");
        String bgB = loadedTile.getString("bgB");
        String bgC = loadedTile.getString("bgC");
        String obstacle = loadedTile.getString("obstacle");
        if (bgA != null) tile.setBase(bgA);
        tile.setDecoration(bgB);
        tile.setBreakable(bgC);
        tile.setObstacle(obstacle);
    }
}
