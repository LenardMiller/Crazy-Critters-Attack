package main.misc;

import main.particles.Debris;
import main.particles.LargeExplosion;
import main.particles.MediumExplosion;
import main.projectiles.Flame;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;

public class Machine {

    public PApplet p;

    public int hp;
    public PVector position;
    public String name;
    public String debris;
    public int betweenFrames;
    private boolean hit;
    private int tintColor;
    private int currentFrame;
    private int frameTimer;
    private int deathFrame;
    private boolean dead;
    private PImage[] sprites;
    public Tile[] machTiles;

    public Machine(PApplet p, PVector position, String name, String debris, int betweenFrames) {
        this.p = p;

        hp = 500; //todo: Balance
        this.position = position;
        this.name = name;
        this.debris = debris;
        this.betweenFrames = betweenFrames;
        sprites = spritesAnimH.get(name);
        tintColor = 255;
        updateNodes();
    }

    public void updateNodes() {
        int l = 0;
        for (int i = 0; i < tiles.size(); i++) {
            Tile tile = tiles.get(i);
            if (tile.machine) l++;
        }
        machTiles = new Tile[l];
        l = 0;
        for (int i = 0; i < tiles.size(); i++) {
            Tile tile = tiles.get(i);
            if (tile.machine) {
                machTiles[l] = tile;
                l++;
            }
        }
    }

    public void main() {
        if (hp <= 0 && !dead) die();
        display();
    }

    public void display() { //todo: some sort of health indicator
        if (hit) {
            tintColor = 0;
            hit = false;
        }
        p.tint(255, tintColor, tintColor);
        p.imageMode(CENTER);
        if (deathFrame < 200) p.image(sprites[currentFrame], position.x, position.y);
        p.imageMode(CORNER);
        p.tint(255);
        if (!dead) drillParticles();
        else if (deathFrame < 200) deathAnim();
        if (p.frameCount > frameTimer && !dead) {
            if (currentFrame < sprites.length - 1) currentFrame++;
            else currentFrame = 0;
            frameTimer = p.frameCount + betweenFrames;
        }
        if (tintColor < 255) tintColor += 20;
    }

    private void drillParticles() {
        if (name.equals("stoneDrillMA")) {
            int r = (int) p.random(0, 10);
            if (r == 0) {
                underParticles.add(new Debris(p, position.x, position.y, p.random(0, 360), "stone"));
            } else {
                underParticles.add(new Debris(p, position.x, position.y, p.random(0, 360), "dirt"));
            }
        }
    }

    private float shuffle(int i) {
        return i + p.random(0,50);
    }

    private void deathAnim() {
        deathFrame++;
        if (deathFrame < 160) {
            for (Tile tile : machTiles) {
                int x = (int)tile.position.x;
                int y = (int)tile.position.y;
                if ((int)p.random(0,3) == 0) particles.add(new Debris(p, shuffle(x), shuffle(y), p.random(0,360), debris));
                if ((int)p.random(0,6) == 0) particles.add(new MediumExplosion(p, shuffle(x), shuffle(y), p.random(0,360)));
            }
        } else {
            for (Tile tile : machTiles) {
                int x = (int)tile.position.x;
                int y = (int)tile.position.y;
                if ((int)p.random(0,4) == 0) particles.add(new LargeExplosion(p, shuffle(x), shuffle(y), p.random(0,360)));
                if ((int)p.random(0,2) == 0) particles.add(new MediumExplosion(p, shuffle(x), shuffle(y), p.random(0,360)));
                for (int i = 0; i < 3; i++) {
                    particles.add(new Debris(p, shuffle(x), shuffle(y), p.random(0,360), debris));
                } if ((int)p.random(0,8) == 0) {
                    projectiles.add(new Flame(p, shuffle(x), shuffle(y), p.random(0,360), null, 100, 10, 100, (int)p.random(1,4)));
                }
            }
        }
        if (deathFrame == 180) for (Tile tile : machTiles) tile.setBgC(debris + "DebrisBGC_TL");
    }

    public void damage(int dmg) {
        hp -= dmg;
        hit = true;
        for (Tile tile : machTiles) {
            int x = (int)tile.position.x;
            int y = (int)tile.position.y;
            for (int i = 0; i < 5; i++) {
                particles.add(new Debris(p, shuffle(x), shuffle(y), p.random(0,360), debris));
            } if ((int)p.random(0,2) == 0) particles.add(new MediumExplosion(p, shuffle(x), shuffle(y), p.random(0,360)));
        }
    }

    public void die() {
        dead = true;
        alive = false;
        //todo: enemies party
    }
}
