package main.gui.guiObjects.buttons;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.animatedSprites;
import static main.Main.selection;

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

    @Override
    public void main(){
        if (active){
            hover();
            display();
        }
    }

    @Override
    public void action(){
        //roll over
        if (selection.turret.priority < 2) selection.turret.priority += 1;
        else selection.turret.priority = 0;
    }
}