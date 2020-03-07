package main.misc;

import main.particles.Debris;
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
        //todo: tiles
    }

    public void main() {
        if (hp <= 0) {
            if (alive) die();
        } else display();
    }

    public void display() {
        if (hit) {
            tintColor = 0;
            hit = false;
        }
        p.tint(255,tintColor,tintColor);
        p.image(sprites[currentFrame],position.x-size.x,position.y-size.y);
        p.tint(255);
        if (name.equals("stoneDrillMA")) {
            int r = (int) p.random(0,10);
            if (r == 0) underParticles.add(new Debris(p,position.x - size.x/2, position.y - size.y/2, p.random(0,360), "stone"));
            else underParticles.add(new Debris(p,position.x - size.x/2, position.y - size.y/2, p.random(0,360), "dirt"));
        }
        if (p.frameCount > frameTimer) {
            if (currentFrame < sprites.length-1) {
                currentFrame++;

            } else currentFrame = 0;
            frameTimer = p.frameCount + betweenFrames;
        }
        if (tintColor < 255) tintColor += 20;
    }

    public void damage(int dmg) {
        hp -= dmg;
        hit = true;
        int num = (int)(p.random(1,4));
        for (int i = num; i >= 0; i--){ //spray debris
            particles.add(new Debris(p,(position.x-size.x/2)+p.random((size.x/2)*-1,size.x/2), (position.y-size.y/2)+p.random((size.y/2)*-1,size.y/2), p.random(0,360), debris));
        }
    }

    public void die() { //todo: fancy explosion
        int num = (int)(p.random(30,50)); //shower debris
        for (int j = num; j >= 0; j--){
            particles.add(new Debris(p,(position.x-size.x/2)+p.random((size.x/2)*-1,size.x/2), (position.y-size.y/2)+p.random((size.y/2)*-1,size.y/2), p.random(0,360), debris));
        }
        alive = false;
        //todo: enemies party
    }
}
