package main.misc;

import processing.core.PApplet;

import java.util.ArrayList;

import static main.Main.FRAMERATE;
import static main.Main.tiles;
import static main.misc.LayoutLoader.loadTile;

public class Polluter {

    public final ArrayList<Tile> CLEAN_TILES;
    public final String NAME;

    public int betweenPollutes;

    private final PApplet P;

    private int polluteTimer;

    /**
     * Swaps out tiles with ones from a given layout file.
     * @param p the PApplet
     * @param secondsBetweenPollutes how many seconds between polluting a tile, usually 5
     * @param name layout name, format: levelName/fileName with no extension
     */
    public Polluter(PApplet p, float secondsBetweenPollutes, String name) {
        P = p;
        betweenPollutes = (int) secondsBetweenPollutes * FRAMERATE;
        NAME = name;

        CLEAN_TILES = new ArrayList<>();
        for (int i = 0; i < tiles.size(); i++) {
            CLEAN_TILES.add(tiles.get(i));
        }
    }

    /**
     * Jumps the pollution to a certain level, immediately polluting the level that far
     * @param cleanTilesSize how many clean tiles should be left.
     */
    public void setCleanTilesSize(int cleanTilesSize) {
        int amountToRemove = CLEAN_TILES.size() - cleanTilesSize;
        for (int i = 0; i < amountToRemove; i++) {
            pollute();
        }
    }

    public void setBetweenPollutes(int betweenPollutes) {
        this.betweenPollutes = betweenPollutes;
    }

    public void update() {
        polluteTimer++;
        if (polluteTimer > betweenPollutes) {
            pollute();
            polluteTimer = 0;
        }
    }

    private void pollute() {
        if (CLEAN_TILES.size() == 0) return;
        Tile tile = CLEAN_TILES.get((int) P.random(CLEAN_TILES.size()));
        String name = tile.obstacleLayer.name;
        if (Tile.containsCorners(tile.obstacleLayer.name, "tree") ||
                Tile.containsCorners(tile.obstacleLayer.name, "yellowTree") ||
                Tile.containsCorners(tile.obstacleLayer.name, "brownTree")) {
            IntVector tlPos = tile.getGridPosition();
            if (name.contains("TR")) tlPos = new IntVector(tlPos.x - 1, tlPos.y);
            else if (name.contains("BR")) tlPos = new IntVector(tlPos.x - 1, tlPos.y - 1);
            else if (name.contains("BL")) tlPos = new IntVector(tlPos.x, tlPos.y - 1);
            pollute(tiles.get(tlPos));
            pollute(tiles.get(new IntVector(tlPos.x + 1, tlPos.y))); //tr
            pollute(tiles.get(new IntVector(tlPos.x + 1, tlPos.y + 1))); //br
            pollute(tiles.get(new IntVector(tlPos.x, tlPos.y + 1))); //bl
        } else pollute(tile);
    }

    private void pollute(Tile tile) {
        loadTile(tile, "levels/" + NAME);
        CLEAN_TILES.remove(tile);
    }
}
