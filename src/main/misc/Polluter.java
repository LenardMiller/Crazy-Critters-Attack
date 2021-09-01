package main.misc;

import processing.core.PApplet;

import java.util.ArrayList;

import static main.Main.FRAMERATE;
import static main.Main.tiles;
import static main.misc.DataControl.loadTile;

public class Polluter {

    private final PApplet P;
    private final ArrayList<Tile> CLEAN_TILES;
    private final int BETWEEN_POLLUTES;
    private final String NAME;

    private int polluteTimer;

    /**
     * Swaps out tiles with ones from a given layout file.
     * @param p the PApplet
     * @param secondsBetweenPollutes how many seconds between polluting a tile, usually 5
     * @param name layout name, format: levelName/fileName with no extension
     */
    public Polluter(PApplet p, float secondsBetweenPollutes, String name) {
        P = p;
        BETWEEN_POLLUTES = (int) secondsBetweenPollutes * FRAMERATE;
        NAME = name;

        CLEAN_TILES = new ArrayList<>();
        for (int i = 0; i < tiles.size(); i++) {
            CLEAN_TILES.add(tiles.get(i));
        }
    }

    public void main() {
        polluteTimer++;
        if (polluteTimer > BETWEEN_POLLUTES) {
            pollute();
            polluteTimer = 0;
        }
    }

    private void pollute() {
        if (CLEAN_TILES.size() == 0) return;
        int id = (int) P.random(CLEAN_TILES.size());
        loadTile(CLEAN_TILES.get(id), "levels/" + NAME);
        CLEAN_TILES.remove(id);
    }
}
