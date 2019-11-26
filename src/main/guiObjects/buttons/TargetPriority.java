package main.guiObjects.buttons;

import main.guiObjects.GuiObject;
import main.towers.Tower;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

import static main.Main.selection;
import static main.Main.tiles;

public class TargetPriority extends Button {

    public TargetPriority(PApplet p, float x, float y, String type, boolean active){
        super(p,x,y,type,active);
        position = new PVector(x, y);
        size = new PVector(200, 45);
        spriteLocation = "sprites/guiObjects/buttons/targetPriority/";
        spriteOne = p.loadImage(spriteLocation + "000.png");
        spriteTwo = p.loadImage(spriteLocation + "001.png");
        sprite = spriteOne;
    }

    public void main(ArrayList<GuiObject> guiObjects, int i){
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