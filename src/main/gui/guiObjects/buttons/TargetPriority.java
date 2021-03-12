package main.gui.guiObjects.buttons;

import main.towers.Tower;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;

public class TargetPriority extends Button {

    public TargetPriority(PApplet p, float x, float y, String type, boolean active){
        super(p,x,y,type,active);
        position = new PVector(x, y);
        size = new PVector(200, 45);
        spriteIdle = animatedSprites.get("targetPriorityBT")[0];
        spritePressed = animatedSprites.get("targetPriorityBT")[1];
        spriteHover = animatedSprites.get("targetPriorityBT")[2];
        sprite = spriteIdle;
    }

    public void main(){
        if (active){
            hover();
            display();
        }
    }

    public void action(){
        Tower tower = tiles.get(selection.id).tower; //switch selected tower's priority
        if (tower.priority < 2){
            tower.priority += 1;
        } else{ //roll over
            tower.priority = 0;
        }
    }
}