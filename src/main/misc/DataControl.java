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
    public static void save() throws IOException {
        JSONArray saveArray = new JSONArray();
        //tiles
        for (int i = 0; i < tiles.size(); i++) {
            Tile tile = tiles.get(i);
            JSONObject saveObject = new JSONObject();
            saveObject.setString("type", "tile");
            saveObject.setInt("id", i);
            saveObject.setString("bgA", tile.bgAName);
            saveObject.setString("bgB", tile.bgBName);
            saveObject.setString("bgC", tile.bgCName);
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
     * Loads level data from a JSON file.
     * @param p the PApplet
     * @param name the filename, sans extension.
     */
    public static void load(PApplet p, String name) {
        //run from terminal
        String filePath = new File("").getAbsolutePath();
        //run from intelliJ
        if (filePath.equals("/Users/blakebabb/Documents/GitHub/Crazy-Critters-Attack")) {
            filePath = "resources";
        }
        File loadFile = new File(filePath+"/data/"+name+".json");
        JSONArray loadArray = loadJSONArray(loadFile);
        alive = true;
        //tiles
        for (int i = 0; i < tiles.size(); i++) {
            Tile tile = tiles.get(i);
            tile.machine = false;
            JSONObject loadObject = loadArray.getJSONObject(i);
            String bgA = loadObject.getString("bgA");
            String bgB = loadObject.getString("bgB");
            String bgC = loadObject.getString("bgC");
            String obstacle = loadObject.getString("obstacle");
            boolean machine = loadObject.getBoolean("machine");
            if (bgA != null) tile.setBgA(bgA);
            if (bgB != null) tile.setBgB(bgB);
            if (bgC != null) tile.setBgC(bgC);
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
}
