package main.towers;

import main.particles.Debris;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.spritesH;
import static main.Main.particles;

public class DevWall extends Tower{
    public DevWall(PApplet p, float x, float y) {
        super(p,x,y);
        name = "devWall";
        position = new PVector(x,y);
        size = new PVector(120,37);
        maxHp = 9999;
        twHp = maxHp;
        hit = false;
        sprite = spritesH.get("devWallTW");
        debrisType = "devWood";
    }

    public void collideEN(int dangerLevel){ //if it touches an enemy, animate but don't loose hp
        hit = true;
        barTrans = 255;
        int num = PApplet.round(p.random(1,3));
        for (int i = num; i >= 0; i--){
            particles.add(new Debris(p,(position.x-size.x/2)+p.random((size.x/2)*-1,size.x/2), (position.y-size.y/2)+p.random((size.y/2)*-1,size.y/2), p.random(0,360), debrisType));
        }
    }

    private void HPText(){ //displays infinity
        p.text("infinity", position.x-size.x/2, position.y + size.y/4);
    }

    public void HpBar(){ //same as normal, but pink
        p.fill(255,0,255,barTrans);
        if (barTrans > 0 && twHp > maxHp/2){
            barTrans--;
        }
        p.noStroke();
        p.rect(position.x-size.x, position.y + size.y/4, (size.x)*(((float) twHp)/((float) maxHp)), -6);
    }
}
