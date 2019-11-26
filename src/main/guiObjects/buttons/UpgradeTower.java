package main.guiObjects.buttons;

import main.towers.Tower;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;

public class UpgradeTower extends Button{

    private PImage spriteRed;
    private PImage spriteGrey;
    public int id;

    public UpgradeTower(PApplet p, float x, float y, String type, boolean active, int id){
        super(p,x,y,type,active);
        position = new PVector(x, y);
        size = new PVector(200, 150);
        spriteOne = spritesAnimH.get("upgradeBT")[0]; //green out
        spriteTwo = spritesAnimH.get("upgradeBT")[1]; //green in
        spriteRed = spritesAnimH.get("upgradeBT")[2]; //red
        spriteGrey = spritesAnimH.get("upgradeBT")[3]; //grey
        sprite = spriteOne;
        this.id = id;
    }

    public void main(){
        if (active){
            Tower tower = tiles.get(selection.id).tower;
            int nextLevel;
            if (id == 0){
                nextLevel = tower.nextLevelZero;
            } else{
                nextLevel = tower.nextLevelOne;
            }
            //println(id + ": " + nextLevel);
            if (tower.upgradeNames.length == nextLevel && id == 1){ //if maxLevel, grey out
                sprite = spriteGrey;
            } else if (tower.upgradeNames.length/2 == nextLevel && id == 0){ //if halfLevel, grey out
                sprite = spriteGrey;
            } else if (tower.upgradePrices[nextLevel] > money){ //if can't afford, red out
                sprite = spriteRed;
            } else{ //if neither, work fine
                hover();
            }
            display();
        }
    }

    public void action(){
        Tower tower = tiles.get(selection.id).tower;
        int nextLevel;
        if (id == 0){
            nextLevel = tower.nextLevelZero;
        } else{
            nextLevel = tower.nextLevelOne;
        }
        money -= tower.upgradePrices[nextLevel];
        tower.upgrade(id);
    }
}