package main.enemies;

import main.pathfinding.Node;
import main.towers.Tower;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import static main.Main.nodeGrid;

public class FlyingEnemy extends Enemy {

    FlyingEnemy(PApplet p, float x, float y,
            String name,
            PVector size,
            int pfSize,
            int radius,
            int speed,
            int moneyDrop,
            int damage,
            int hp,
            int[] attackDmgFrames,
            int walkDelay,
            int attackDelay,
            PVector corpseSize,
            PVector partSize,
            int corpseLifespan,
            int corpseDelay,
            HitParticle hitParticle,
            String dieSound,
            String overkillSound,
            String attackSound,
            String moveSoundLoop
    ) {
        super(p, x, y, name, size, pfSize, radius, speed, moneyDrop, damage, hp, attackDmgFrames, walkDelay,
                attackDelay, corpseSize, partSize, corpseLifespan, corpseDelay, hitParticle, dieSound, overkillSound, attackSound,
                moveSoundLoop);
    }

    protected FlyingEnemy(PApplet p, float x, float y) {
        super(p, x, y);
    }

    /** returns all towers in enemy "bubble"? */
    @Override
    protected ArrayList<Tower> clearanceTowers(TurnPoint point) {
        ArrayList<Tower> towers = new ArrayList<>();
        boolean clear = true;
        int kSize = 1;
        int x = (int) (point.position.x + 100) / 25;
        int y = (int) (point.position.y + 100) / 25;
        while (true) {
            for (int xn = 0; xn < kSize; xn++) {
                for (int yn = 0; yn < kSize; yn++) {
                    if (!(x + xn >= nodeGrid.length || y + yn >= nodeGrid[x].length)) {
                        Node nodeB = nodeGrid[x + xn][y + yn];
                        if (nodeB.tower instanceof Turret) towers.add(nodeB.tower);
                    } else {
                        clear = false;
                        break;
                    }
                }
                if (!clear) break;
            }
            if (clear && kSize < pfSize) kSize++;
            else break;
        }
        //deletes duplicates
        CopyOnWriteArrayList<Tower> towersB = new CopyOnWriteArrayList<>(towers);
        for (int i = 0; i < towersB.size() - 1; i++) if (towersB.get(i) == towersB.get(i++)) towersB.remove(i);
        towers = new ArrayList<>(towersB);
        return towers;
    }
}
