package main.guiObjects.buttons;

import main.guiObjects.Icon;
import main.towers.Tower;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.util.ArrayList;

import static main.Main.*;

public class RepairWall extends Button{

    private PImage spriteRed;
    private PImage spriteGrey;

    public RepairWall(PApplet p, float x, float y, String type, boolean active){
        super(p,x,y,type,active);
        position = new PVector(x, y);
        size = new PVector(200, 150);
        spriteOne = spritesAnimH.get("repairBT")[0]; //green out
        spriteTwo = spritesAnimH.get("repairBT")[1]; //green in
        spriteRed = spritesAnimH.get("repairBT")[2]; //red
        spriteGrey = spritesAnimH.get("repairBT")[3]; //grey
        sprite = spriteOne;
        actionTime = p.frameCount + 6;
    }

    public void main(ArrayList<Icon> icons, int i){
        if (active){
            Tower tower = towers.get(selection.id);
            if (tower.twHp == tower.maxHp){ //if full health, grey out
                sprite = spriteGrey;
                actionTime = p.frameCount + 3;
            } else if (ceil((float)(tower.price) - (float)(tower.value)) > money){ //if can't afford, red out
                sprite = spriteRed;
                actionTime = p.frameCount + 3;
            } else if (ceil((float)(tower.price) - (float)(tower.value)) <= money && tower.twHp < tower.maxHp){ //if neither, work fine
                hover();
            }
            display();
        }
    }

    public void action(){
        Tower tower = towers.get(selection.id);
        money -= ceil((float)(tower.price) - (float)(tower.value));
        tower.twHp = tower.maxHp;
        //actionTime = frameCount + 6;
    }
}