package main.gui.guiObjects.buttons;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
import static main.misc.TowerInfo.*;
import static processing.core.PConstants.CENTER;

public class TowerBuy extends Button {

    private String towerType;
    public boolean depressed;
    public int price;

    public TowerBuy(PApplet p, float x, float y, String type, boolean active){
        super(p,x,y,type,active);
        price = 0;
        position = new PVector(x, y);
        size = new PVector(35, 35);
        towerType = type;
        spriteLocation = "sprites/guiObjects/buttons/towerBuy/" + towerType + "/"; //still uses old system because it is only created at beginning of game
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
                price = RANDOMCANNON_PRICE;
                break;
            case "cannon":
                price = CANNON_PRICE;
                break;
            case "gluer":
                price = GLUER_PRICE;
                break;
            case "siesmic":
                price = SEISMIC_PRICE;
                break;
            case "tesla":
            case "energyBlaster":
            case "magicMissleer":
                price = 300;
                break;
            case "nightmare":
            case "flamethrower":
            case "railgun":
                price = 400;
                break;
            case "waveMotion":
                price = 500;
                break;
        }
    }

    public void main() {
        if (active) {
            if (!towerType.equals("null")) hover();
            display();
            if (money < price) {
                p.tint(255, 0, 0, 100);
                p.image(sprite, position.x - size.x / 2, position.y - size.y / 2);
                p.tint(255);
            }
        }
    }

    public void hover() { //if hovered or depressed
        int d = 2;
        if (p.mouseX < (position.x+size.x/2)+d && p.mouseX > (position.x-size.x/2)-d-1 && p.mouseY < (position.y+size.y/2)+d && p.mouseY > (position.y-size.y/2)-d-1 && alive && active || depressed && alive){
            if (depressed) sprite = spritePressed;
            else sprite = spriteHover;
            if (inputHandler.leftMousePressedPulse && !depressed) {
                clickIn.stop();
                clickIn.play(1, volume);
            }
            if (p.mousePressed && p.mouseButton == LEFT) sprite = spritePressed;
            p.fill(235);
            p.noStroke();
            p.rect(900,212,200,707);
            p.textAlign(CENTER);
            p.fill(0);
            p.textFont(largeFont); //displays info about tower
            int x = 1000;
            int offset = 0;
            switch (towerType) {
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
                    break;
                case "magicMissleer":
                    p.text("Magic Missileer", x, 241);
                    break;
                case "tesla":
                    p.text("Tesla Tower", x, 241);
                    break;
                case "nightmare":
                    p.text("Nightmare", x, 241);
                    p.text("Blaster", x, 266);
                    offset = 25;
                    break;
                case "flamethrower":
                    p.text("Flamethrower", x, 241);
                    break;
                case "railgun":
                    p.text("Railgun", x, 241);
                    break;
                case "waveMotion":
                    p.text("Death Beam", x, 241);
                    break;
            }
            displayPrice(offset, x);
            if (inputHandler.leftMousePressedPulse && alive) {
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
        if (money < price) p.fill(255,0,0);
        p.text("$" + price, x, 271 + offset);
    }

    public void action() {
        if (money < price) depressed = false;
        else depressed = !depressed;
        //if already holding, stop
        if (hand.held.equals(towerType)) hand.setHeld("null");
        //if not, do
        else if (depressed && money >= price) hand.setHeld(towerType);
    }
}