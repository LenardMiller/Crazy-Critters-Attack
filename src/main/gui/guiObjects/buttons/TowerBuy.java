package main.gui.guiObjects.buttons;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.gui.inGame.TowerInfo.*;
import static main.sound.SoundUtilities.playSound;

public class TowerBuy extends Button {

    private final String TOWER_TYPE;

    public boolean depressed;
    public int price;

    public TowerBuy(PApplet p, float x, float y, String type, boolean active){ //todo: redo old art
        super(p,x,y,type,active);
        price = 0;
        position = new PVector(x, y);
        size = new PVector(35, 35);
        TOWER_TYPE = type;
        spriteLocation = "sprites/gui/buttons/towerBuy/" + TOWER_TYPE + "/"; //still uses old system because it is only created at beginning of game
        spriteIdle = p.loadImage(spriteLocation + "000.png");
        spritePressed = p.loadImage(spriteLocation + "001.png");
        spriteHover = p.loadImage(spriteLocation + "002.png");
        sprite = spriteIdle;
        depressed = false;
        //I am way too lazy to fix this goddamn monstrosity
        switch (type) {
            case "slingshot":
                price = SLINGSHOT_PRICE;
                break;
            case "crossbow":
                price = CROSSBOW_PRICE;
                break;
            case "miscCannon":
                price = RANDOM_CANNON_PRICE;
                break;
            case "cannon":
                price = CANNON_PRICE;
                break;
            case "gluer":
                price = GLUER_PRICE;
                break;
            case "seismic":
                price = SEISMIC_PRICE;
                break;
            case "tesla":
                price = TESLA_TOWER_PRICE;
                break;
            case "energyBlaster":
                price = ENERGY_BLASTER_PRICE;
                break;
            case "magicMissleer":
                price = MAGIC_MISSILEER_PRICE;
                break;
            case "nightmare":
                price = NIGHTMARE_PRICE;
                break;
            case "flamethrower":
                price = FLAMETHROWER_PRICE;
                break;
            case "iceTower":
                price = ICE_TOWER_PRICE;
                break;
            case "booster":
                price = BOOSTER_PRICE;
                break;
            case "railgun":
                price = RAILGUN_PRICE;
                break;
            case "waveMotion":
                price = WAVE_MOTION_PRICE;
                break;
        }
    }

    @Override
    public void main() {
        if (active) {
            if (!TOWER_TYPE.equals("null")) hover();
            display();
            if (money < price) {
                p.tint(255, 0, 0, 200);
                p.image(sprite, position.x - size.x / 2, position.y - size.y / 2);
                p.tint(255);
            }
        }
    }

    private boolean hovered() {
        int d = 2;
        boolean matchX = matrixMousePosition.x < (position.x+size.x/2)+d && matrixMousePosition.x > (position.x-size.x/2)-d-1;
        boolean matchY = matrixMousePosition.y < (position.y+size.y/2)+d && matrixMousePosition.y > (position.y-size.y/2)-d-1;
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
            displayTurretInfo(p, TOWER_TYPE);
            if (inputHandler.leftMousePressedPulse && alive && !paused) {
                pressIn();
                if (money >= price) sprite = spritePressed;
            }
        }
        else sprite = spriteIdle;
        if (money < price) sprite = spritePressed;
    }

    @Override
    public void pressIn() {
        if (money < price) depressed = false;
        else depressed = !depressed;
        //if already holding, stop
        if (hand.held.equals(TOWER_TYPE)) hand.setHeld("null");
        //if not, do
        else if (depressed) hand.setHeld(TOWER_TYPE);
    }
}