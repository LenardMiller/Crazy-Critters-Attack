package main.misc;

import processing.data.JSONArray;
import processing.data.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static main.Main.tiles;
import static processing.core.PApplet.*;

public class DataControl {

    public DataControl() {}

    public static void saveTiles() throws IOException {
        JSONArray saveArray = new JSONArray();
        for (int i = 0; i < tiles.size(); i++) {
            Tile tile = tiles.get(i);
            JSONObject saveObject = new JSONObject();
            saveObject.setInt("id", i);
            saveObject.setString("bgA", tile.bgAName);
            saveObject.setString("bgB", tile.bgBName);
            saveObject.setString("bgC", tile.bgCName);
            saveObject.setString("obstacle", tile.obstacleName);
            saveArray.setJSONObject(i,saveObject);
        }
        String name = "Tiles-"+month()+"-"+day()+"-"+year()+"-"+hour() +"-"+minute()+"-"+second();
        File saveFile = new File("resources/data/saves/" + name + ".json");
        FileWriter saveWriter = new FileWriter("resources/data/saves/" + name + ".json");
        saveWriter.write(saveArray.toString());
        saveWriter.close();
    }

    public static void loadTiles(String name) {
        File loadFile = new File("resources/data/"+name+".json");
        JSONArray loadArray = loadJSONArray(loadFile);
        for (int i = 0; i < tiles.size(); i++) {
            Tile tile = tiles.get(i);
            JSONObject loadObject = loadArray.getJSONObject(i);
            String bgA = loadObject.getString("bgA");
            String bgB = loadObject.getString("bgB");
            String bgC = loadObject.getString("bgC");
            String obstacle = loadObject.getString("obstacle");
            if (bgA != null) tile.setBgA(bgA);
            if (bgB != null) tile.setBgB(bgB);
            if (bgC != null) tile.setBgC(bgC);
            if (obstacle != null) tile.setObstacle(obstacle);
        }
    }
}
