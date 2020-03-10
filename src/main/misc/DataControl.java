package main.misc;

import processing.core.PApplet;
import processing.core.PVector;
import processing.data.JSONArray;
import processing.data.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static main.Main.machine;
import static main.Main.tiles;
import static main.Main.alive;
import static processing.core.PApplet.*;

public class DataControl {

    public DataControl() {}

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
        saveObject.setFloat("x", machine.position.x);
        saveObject.setFloat("y", machine.position.y);
        saveObject.setString("name", machine.name);
        saveObject.setString("debris", machine.debris);
        saveObject.setInt("betweenFrames", machine.betweenFrames);
        saveArray.setJSONObject(i, saveObject);

        String name = "Save-"+month()+"-"+day()+"-"+year()+"-"+hour() +"-"+minute()+"-"+second();
        File saveFile = new File("resources/data/saves/" + name + ".json");
        FileWriter saveWriter = new FileWriter("resources/data/saves/" + name + ".json");
        saveWriter.write(saveArray.toString());
        saveWriter.close();
    }

    public static void load(PApplet p, String name) {
        File loadFile = new File("resources/data/"+name+".json");
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
        int x = loadObject.getInt("x");
        int y = loadObject.getInt("y");
        String machineName = loadObject.getString("name");
        String debris = loadObject.getString("debris");
        int betweenFrames = loadObject.getInt("betweenFrames");
        machine = new Machine(p,new PVector(x,y), machineName, debris, betweenFrames);
        machine.hp = hp;
    }
}
