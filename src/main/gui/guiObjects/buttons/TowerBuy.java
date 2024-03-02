package main.gui.guiObjects.buttons;

import main.misc.IntVector;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static main.Main.*;
import static main.gui.inGame.TowerInfo.*;
import static main.sound.SoundUtilities.playSound;

public class TowerBuy extends Button {

    private final String towerType;
    private final PImage spriteInvalid;

    public boolean depressed;
    public int price;

    public TowerBuy(PApplet p, float x, float y, String type, boolean active) {
        super(p, x, y, type, active);
        price = 0;
        position = new PVector(x, y);
        size = new PVector(35, 35);
        towerType = type;
        spriteLocation = "sprites/gui/buttons/towerBuy/" + towerType + "/"; //still uses old system because it is only created at beginning of game
        spriteIdle = p.loadImage(spriteLocation + "000.png");
        spritePressed = p.loadImage(spriteLocation + "001.png");
        spriteHover = p.loadImage(spriteLocation + "002.png");
        spriteInvalid = p.loadImage(spriteLocation + "003.png");
        sprite = spriteIdle;
        depressed = false;
        switch (type) {
            case "slingshot" -> price = SLINGSHOT_PRICE;
            case "crossbow" -> price = CROSSBOW_PRICE;
            case "miscCannon" -> price = RANDOM_CANNON_PRICE;
            case "cannon" -> price = CANNON_PRICE;
            case "gluer" -> price = GLUER_PRICE;
            case "seismic" -> price = SEISMIC_PRICE;
            case "tesla" -> price = TESLA_TOWER_PRICE;
            case "energyBlaster" -> price = ENERGY_BLASTER_PRICE;
            case "magicMissleer" -> price = MAGIC_MISSILEER_PRICE;
            case "nightmare" -> price = NIGHTMARE_PRICE;
            case "flamethrower" -> price = FLAMETHROWER_PRICE;
            case "iceTower" -> price = ICE_TOWER_PRICE;
            case "booster" -> price = BOOSTER_PRICE;
            case "railgun" -> price = RAILGUN_PRICE;
            case "waveMotion" -> price = WAVE_MOTION_PRICE;
        }
    }

    @Override
    public void display() {
        if (!active) return;
        p.image(sprite,position.x-size.x/2,position.y-size.y/2);
        if (hovered()) displayTurretInfo(p, towerType);
    }

    @Override
    public void update() {
        if (!active) return;
        if (!towerType.equals("null")) hover();
        if (money < price) {
            p.tint(255, 0, 0, 200);
            p.image(sprite, position.x - size.x / 2, position.y - size.y / 2);
            p.tint(255);
        }
    }

    private boolean hovered() {
        if (towerType.equals("null")) return false;
        IntVector d = new IntVector(1, 1);
        boolean matchX = boardMousePosition.x < (position.x+size.x/2)+d.x && boardMousePosition.x > (position.x-size.x/2)-d.x-1;
        boolean matchY = boardMousePosition.y < (position.y+size.y/2)+d.y && boardMousePosition.y > (position.y-size.y/2)-d.y-1;
        boolean matchPosition = matchX && matchY && active;
        return ((matchPosition && !paused) || depressed) && alive;
    }

    @Override
    public void hover() {
        if (hovered()) {
            if (depressed) sprite = spritePressed;
            else sprite = spriteHover;
            if (inputHandler.leftMousePressedPulse && !depressed) playSound(clickIn, 1, 1);
            if (p.mousePressed && p.mouseButton == LEFT) sprite = spritePressed;
            if (inputHandler.leftMousePressedPulse && alive && !paused) {
                pressIn();
                if (money >= price) sprite = spritePressed;
            }
        }
        else sprite = spriteIdle;
        if (money < price) {
            if (spriteInvalid != null) sprite = spriteInvalid;
            else sprite = spritePressed;
        }
    }

    @Override
    public void pressIn() {
        if (money < price) depressed = false;
        else depressed = !depressed;
        //if already holding, stop
        if (hand.held.equals(towerType)) hand.setHeld("null");
        //if not, do
        else if (depressed) hand.setHeld(towerType);
    }
}