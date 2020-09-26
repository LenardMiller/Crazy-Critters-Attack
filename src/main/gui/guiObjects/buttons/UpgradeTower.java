package main.gui.guiObjects.buttons;

import main.towers.Tower;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;

public class UpgradeTower extends Button {

    private PImage spriteRed;
    private PImage spriteGrey;
    public int id;

    public UpgradeTower(PApplet p, float x, float y, String type, boolean active, int id) {
        super(p,x,y,type,active);
        position = new PVector(x, y);
        size = new PVector(200, 150);
        spriteIdle = spritesAnimH.get("upgradeBT")[0]; //green out
        spritePressed = spritesAnimH.get("upgradeBT")[1]; //green in
        spriteHover = spritesAnimH.get("upgradeBT")[2]; //hover
        spriteRed = spritesAnimH.get("upgradeBT")[3]; //red
        spriteGrey = spritesAnimH.get("upgradeBT")[4]; //grey
        sprite = spriteIdle;
        this.id = id;
    }

    public void main() {
        if (active) {
            if (towers.size() > 0) {
                Tower tower = tiles.get(selection.id).tower;
                int nextLevel;
                if (id == 0) nextLevel = tower.nextLevelA;
                else nextLevel = tower.nextLevelB;
                if (tower.upgradeNames.length == nextLevel && id == 1 || tower.upgradeNames.length / 2 == nextLevel && id == 0)
                    sprite = spriteGrey;
                else if (tower.upgradePrices[nextLevel] > money) sprite = spriteRed;
                else hover();
                display();
            } else active = false;
        }
    }

    public void action() {
        Tower tower = tiles.get(selection.id).tower;
        int nextLevel;
        if (id == 0) nextLevel = tower.nextLevelA;
        else nextLevel = tower.nextLevelB;
        money -= tower.upgradePrices[nextLevel];
        tower.upgrade(id);
    }
}