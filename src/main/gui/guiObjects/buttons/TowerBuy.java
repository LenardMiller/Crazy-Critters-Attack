package main.gui.guiObjects.buttons;

import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;

import static main.Main.*;
import static main.gui.TowerInfo.*;
import static main.misc.Utilities.playSound;
import static main.misc.Utilities.strikethroughText;
import static processing.core.PConstants.CENTER;

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
                price = 400;
                break;
            case "waveMotion":
                price = 500;
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
        return  ((matchPosition && !paused) || depressed) && alive;
    }

    @Override
    public void hover() {
        if (hovered()) {
            if (depressed) sprite = spritePressed;
            else sprite = spriteHover;
            if (inputHandler.leftMousePressedPulse && !depressed) playSound(clickIn, 1, 1);
            if (p.mousePressed && p.mouseButton == LEFT) sprite = spritePressed;
            p.fill(235);
            p.noStroke();
            p.rect(900,212,200,707);
            p.textAlign(CENTER);
            p.fill(0, 254);
            p.textFont(largeFont); //displays info about tower
            int x = 1000;
            int offset = 0;
            switch (TOWER_TYPE) {
                case "slingshot":
                    p.text("Slingshot", 1000, 241);
                    slingshotInfo(p);
                    break;
                case "miscCannon":
                    p.text("Luggage", x, 241);
                    p.text("Blaster", x, 266);
                    offset = 25;
                    randomCannonInfo(p);
                    break;
                case "crossbow":
                    p.text("Crossbow", x, 241);
                    crossbowInfo(p);
                    break;
                case "cannon":
                    p.text("Cannon", x, 241);
                    cannonInfo(p);
                    break;
                case "gluer":
                    p.text("Gluer", x, 241);
                    gluerInfo(p);
                    break;
                case "seismic":
                    p.text("Seismic Tower", x, 241);
                    seismicInfo(p);
                    break;
                case "energyBlaster":
                    p.text("Energy Blaster", x, 241);
                    energyBlasterInfo(p);
                    break;
                case "magicMissleer":
                    p.text("Magic Missileer", x, 241);
                    magicMissileerInfo(p);
                    break;
                case "tesla":
                    p.text("Tesla Tower", x, 241);
                    teslaTowerInfo(p);
                    break;
                case "nightmare":
                    p.text("Nightmare", x, 241);
                    p.text("Blaster", x, 266);
                    offset = 25;
                    break;
                case "flamethrower":
                    p.text("Flamethrower", x, 241);
                    flamethrowerInfo(p);
                    break;
                case "iceTower":
                    p.text("Freeze Ray", x, 241);
                    iceTowerInfo(p);
                    break;
                case "booster":
                    p.text("Booster", x, 241);
                    boosterInfo(p);
                    break;
                case "railgun":
                    p.text("Railgun", x, 241);
                    break;
                case "waveMotion":
                    p.text("Death Beam", x, 241);
                    break;
            }
            displayPrice(offset, x);
            if (inputHandler.leftMousePressedPulse && alive && !paused) {
                action();
                if (money >= price) sprite = spritePressed;
            }
        }
        else sprite = spriteIdle;
        if (money < price) sprite = spritePressed;
    }

    private void displayPrice(int offset, int x) {
        p.textAlign(CENTER);
        p.textFont(mediumFont);
        if (money < price) {
            strikethroughText(p, "$" + price, new PVector(x, 271 + offset), new Color(150, 0, 0, 254),
                    mediumFont.getSize(), CENTER);
        }
        else p.text("$" + price, x, 271 + offset);
    }

    @Override
    public void action() {
        if (money < price) depressed = false;
        else depressed = !depressed;
        //if already holding, stop
        if (hand.held.equals(TOWER_TYPE)) hand.setHeld("null");
        //if not, do
        else if (depressed) hand.setHeld(TOWER_TYPE);
    }
}