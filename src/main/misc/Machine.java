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
    public PVector size;
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

    public Machine(PApplet p, PVector position, PVector size, String name, String debris, int betweenFrames) {
        this.p = p;

        hp = 200; //todo: Balance
        this.position = position;
        this.size = size;
        this.name = name;
        this.debris = debris;
        this.betweenFrames = betweenFrames;
        sprites = spritesAnimH.get(name);
        tintColor = 255;

        int x2 = (int)position.x/50;
        int y2 = (int)position.y/50;
        for (int x = 0; x < (size.x/50); x++) {
            for (int y = 0; y < (size.y/50); y++) {
                tiles.get(-x+x2-1,-y+y2-1).machine = true;
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
        if (deathFrame < 200) p.image(sprites[currentFrame], position.x - size.x, position.y - size.y);
        p.tint(255);
        if (!dead) {
            if (name.equals("stoneDrillMA")) {
                int r = (int) p.random(0, 10);
                if (r == 0) {
                    underParticles.add(new Debris(p, position.x - size.x / 2, position.y - size.y / 2, p.random(0, 360), "stone"));
                } else {
                    underParticles.add(new Debris(p, position.x - size.x / 2, position.y - size.y / 2, p.random(0, 360), "dirt"));
                }
            }
        } else if (deathFrame < 200) deathAnim();
        if (p.frameCount > frameTimer && !dead) {
            if (currentFrame < sprites.length - 1) currentFrame++;
            else currentFrame = 0;
            frameTimer = p.frameCount + betweenFrames;
        }
        if (tintColor < 255) tintColor += 20;
    }

    private void deathAnim() {
        deathFrame++;
        if (deathFrame < 160) {
            int num = (int) (p.random(0, 2));
            for (int j = 2; j >= 0; j--) {
                particles.add(new Debris(p, (position.x - size.x / 2) + p.random((size.x / 2) * -1, size.x / 2), (position.y - size.y / 2) + p.random((size.y / 2) * -1, size.y / 2), p.random(0, 360), debris));
            } if (num == 0) {
                particles.add(new MediumExplosion(p, (position.x - size.x / 2) + p.random((size.x / 2) * -1, size.x / 2), (position.y - size.y / 2) + p.random((size.y / 2) * -1, size.y / 2), p.random(0, 360)));
            }
        } else {
            particles.add(new LargeExplosion(p, (position.x - size.x / 2) + p.random((size.x / 2) * -1, size.x / 2), (position.y - size.y / 2) + p.random((size.y / 2) * -1, size.y / 2), p.random(0, 360)));
            for (int i = 5; i >= 0; i--) {
                particles.add(new MediumExplosion(p, (position.x - size.x / 2) + p.random((size.x / 2) * -1, size.x / 2), (position.y - size.y / 2) + p.random((size.y / 2) * -1, size.y / 2), p.random(0, 360)));
            } for (int j = 15; j >= 0; j--) {
                particles.add(new Debris(p, (position.x - size.x / 2) + p.random((size.x / 2) * -1, size.x / 2), (position.y - size.y / 2) + p.random((size.y / 2) * -1, size.y / 2), p.random(0, 360), debris));
            } if ((int) (p.random(0, 2f)) == 0) {
                projectiles.add(new Flame(p, (position.x - size.x / 2) + p.random((size.x / 2) * -1, size.x / 2), (position.y - size.y / 2) + p.random((size.y / 2) * -1), p.random(0, 360), null, 50, 5, 100, (int) p.random(1, 4)));
            }
        }
        if (deathFrame == 180) {
            int x2 = (int)position.x/50;
            int y2 = (int)position.y/50;
            for (int x = 0; x < (size.x/50); x++) {
                for (int y = 0; y < (size.y/50); y++) {
                    tiles.get(-x+x2-1,-y+y2-1).setBgC(debris + "DebrisBGC_TL");
                }
            }
        }
    }

    public void damage(int dmg) {
        hp -= dmg;
        hit = true;
        //explosions
        int num = (int) (p.random(1, 3));
        for (int i = num; i >= 0; i--) {
            particles.add(new MediumExplosion(p, (position.x - size.x / 2) + p.random((size.x / 2) * -1, size.x / 2), (position.y - size.y / 2) + p.random((size.y / 2) * -1, size.y / 2), p.random(0, 360)));
        }
        //debris
        num = (int) (p.random(1, 8));
        for (int i = num; i >= 0; i--) {
            particles.add(new Debris(p, (position.x - size.x / 2) + p.random((size.x / 2) * -1, size.x / 2), (position.y - size.y / 2) + p.random((size.y / 2) * -1, size.y / 2), p.random(0, 360), debris));
        }
    }

    public void die() {
        dead = true;
        alive = false;
        //todo: enemies party
    }
}
