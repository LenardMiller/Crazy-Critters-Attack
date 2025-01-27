package main.gui.guiObjects.buttons;

import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;

public class UpgradeTower extends Button {

    private final PImage SPRITE_RED;
    private final PImage SPRITE_GREY;

    public int id;
    public boolean greyed;

    public UpgradeTower(PApplet p, float x, float y, String type, boolean active, int id) {
        super(p,x,y,type,active);
        position = new PVector(x, y);
        size = new PVector(200, 150);
        spriteIdle = animatedSprites.get("upgradeBT")[0]; //green out
        spritePressed = animatedSprites.get("upgradeBT")[1]; //green in
        spriteHover = animatedSprites.get("upgradeBT")[2]; //hover
        SPRITE_RED = animatedSprites.get("upgradeBT")[3]; //red
        SPRITE_GREY = animatedSprites.get("upgradeBT")[4]; //grey
        sprite = spriteIdle;
        this.id = id;
        clickIn = sounds.get("bigButtonIn");
        clickOut = sounds.get("bigButtonOut");
    }

    @Override
    public void update() {
        if (!active) return;
        if (towers.size() > 0) {
            updateGrey();
            if (!greyed) {
                if (selection.turret.upgradePrices[
                        id == 0 ?
                                selection.turret.nextLevelA :
                                selection.turret.nextLevelB
                        ] > money) sprite = SPRITE_RED;
                else hover();
            }
        } else active = false;
    }

    public void updateGrey() {
        int thisNextLevel;
        int thisMax;
        int otherNextLevel;
        int otherMax;
        if (id == 0) {
            thisNextLevel = selection.turret.nextLevelA;
            thisMax = selection.turret.upgradeTitles.length / 2;
            otherNextLevel = selection.turret.nextLevelB;
            otherMax = selection.turret.upgradeTitles.length;
        } else {
            thisNextLevel = selection.turret.nextLevelB;
            thisMax = selection.turret.upgradeTitles.length;
            otherNextLevel = selection.turret.nextLevelA;
            otherMax = selection.turret.upgradeTitles.length / 2;
        }
        greyed = false;

        if (thisNextLevel == thisMax || (otherNextLevel == otherMax && thisNextLevel == thisMax - 1)) {
            greyed = true;
            sprite = SPRITE_GREY;
        }
    }

    @Override
    public void pressIn() {
        Turret turret = selection.turret;
        turret.upgrade(id, false);
        updateGrey();
    }
}