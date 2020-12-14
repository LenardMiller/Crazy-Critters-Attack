package main.misc;

import main.particles.Debris;
import main.particles.LargeExplosion;
import main.particles.MediumExplosion;
import main.particles.Ouch;
import main.projectiles.Flame;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import processing.sound.SoundFile;

import static main.Main.*;

public class Machine {

    public PApplet p;

    public int hp;
    public int maxHp;
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
    private int damageState;
    private SoundFile damageSound;
    private SoundFile breakSound;
    private SoundFile explodeSound;
    private SoundLoop explodeLoop;

    public Machine(PApplet p, PVector position, String name, String debris, int betweenFrames, int maxHp) {
        this.p = p;

        damageState = 0;
        hp = maxHp;
        this.maxHp = maxHp;
        this.position = position;
        this.name = name;
        this.debris = debris;
        this.betweenFrames = betweenFrames;
        damageSound = soundsH.get(debris + "Damage");
        breakSound = soundsH.get(debris + "Break");
        explodeSound = soundsH.get("smallExplosion");
        explodeLoop = soundLoopsH.get("smallExplosion");
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

    public void display() {
        if (hit) {
            tintColor = 0;
            hit = false;
        }
        p.tint(255, tintColor, tintColor);
        p.imageMode(CENTER);
        if (deathFrame < 200) p.image(sprites[currentFrame], position.x, position.y);
        p.imageMode(CORNER);
        p.tint(255);
        if (!dead) hurtParticles();
        else if (deathFrame < 300) deathAnim();
        else explodeLoop.stopLoop();
        if (p.frameCount > frameTimer && !dead) {
            if (currentFrame < sprites.length - 1) currentFrame++;
            else currentFrame = 0;
            frameTimer = p.frameCount + betweenFrames;
        }
        if (tintColor < 255) tintColor += 20;
    }

    private void hurtParticles() {
        if (damageState > 0) {
            int r = 0;
            if (damageState == 1) r = 150;
            if (damageState == 2) r = 100;
            if (damageState == 3) r = 20;
            for (Tile tile : machTiles) {
                int x = (int) tile.position.x;
                int y = (int) tile.position.y;
                if ((int) p.random(0, r) == 0)
                    particles.add(new Ouch(p, shuffle(x), shuffle(y), p.random(0, 360), "greyPuff"));
            }
        }
    }

    private float shuffle(int i) {
        return i + p.random(0, 50);
    }

    private void deathAnim() {
        if (deathFrame == 0) {
            breakSound.stop();
            breakSound.play(1, volume);
        } deathFrame++;
        if (deathFrame < 160) {
            for (Tile tile : machTiles) {
                int x = (int) tile.position.x;
                int y = (int) tile.position.y;
                if ((int) p.random(0, 3) == 0)
                    particles.add(new Debris(p, shuffle(x), shuffle(y), p.random(0, 360), debris));
                if ((int) p.random(0, 6) == 0) {
                    if ((int) p.random(0, 5) == 0) {
                        explodeSound.stop();
                        explodeSound.play(p.random(0.8f, 1.2f), volume);
                    } particles.add(new MediumExplosion(p, shuffle(x), shuffle(y), p.random(0, 360), "fire"));
                }
            }
        } else {
            explodeLoop.startLoop(1, volume);
            for (Tile tile : machTiles) {
                int x = (int) tile.position.x;
                int y = (int) tile.position.y;
                if ((int) p.random(0, 4) == 0)
                    particles.add(new LargeExplosion(p, shuffle(x), shuffle(y), p.random(0, 360), "fire"));
                if ((int) p.random(0, 2) == 0)
                    particles.add(new MediumExplosion(p, shuffle(x), shuffle(y), p.random(0, 360), "fire"));
                for (int i = 0; i < 3; i++) {
                    particles.add(new Debris(p, shuffle(x), shuffle(y), p.random(0, 360), debris));
                } if ((int) p.random(0, 8) == 0) {
                    projectiles.add(new Flame(p, shuffle(x), shuffle(y), p.random(0, 360), null, maxHp * 10, maxHp, 1000, (int) p.random(1, 4)));
                }
            }
        }
        if (deathFrame == 280) for (Tile tile : machTiles) tile.setBgC(debris + "DebrisBGC_TL");
    }

    public void damage(int dmg) {
        hp -= dmg;
        hit = true;
        int hpSegment = maxHp / 4;
        if (hp <= hpSegment * 3 && hp > hpSegment * 2) damageState = 1;
        if (hp <= hpSegment * 2 && hp > hpSegment) damageState = 2;
        if (hp <= hpSegment) damageState = 3;
        if (damageState > 0) {
            sprites = spritesAnimH.get(name + "d" + damageState);
//            currentFrame = 0;
        }
        damageSound.stop();
        damageSound.play(p.random(0.8f, 1.2f), volume);
        for (Tile tile : machTiles) {
            int x = (int) tile.position.x;
            int y = (int) tile.position.y;
            for (int i = 0; i < 5; i++) {
                particles.add(new Debris(p, shuffle(x), shuffle(y), p.random(0, 360), debris));
            }
            if ((int) p.random(0, 2 * ((float) hp / (float) hpSegment)) == 0) {
                explodeSound.stop();
                explodeSound.play(p.random(0.8f, 1.2f), volume);
                particles.add(new MediumExplosion(p, shuffle(x), shuffle(y), p.random(0, 360), "fire"));
            }
        }
    }

    public void die() {
        dead = true;
        alive = false;
    }
}
