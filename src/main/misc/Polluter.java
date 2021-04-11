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

    private int polluteTimer;

    public Polluter(PApplet p, float secondsBetweenPollutes) {
        P = p;
        BETWEEN_POLLUTES = (int) secondsBetweenPollutes * FRAMERATE;

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
        loadTile(CLEAN_TILES.get(id));
        CLEAN_TILES.remove(id);
    }
}
