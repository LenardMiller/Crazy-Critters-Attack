package main.gui.guiObjects.buttons;

import processing.core.PApplet;
import processing.core.PVector;

import static main.Main.*;
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
        }
    }

    public void hover() { //below is if hovered or depressed
        int d = 2;
        if (p.mouseX < (position.x+size.x/2)+d && p.mouseX > (position.x-size.x/2)-d && p.mouseY < (position.y+size.y/2)+d && p.mouseY > (position.y-size.y/2)-d && alive && active || depressed && alive){
            if (depressed) sprite = spritePressed;
            else sprite = spriteHover;
            p.fill(235);
            p.noStroke();
            p.rect(900,212,200,707);
            p.textAlign(CENTER);
            p.fill(0);
            p.textFont(largeFont); //displays info about tower todo: put more info
            int x = 1000;
            switch (towerType) {
                case "slingshot":
                    p.text("Slingshot", 1000, 241);
                    p.textFont(mediumFont);
                    p.text("$" + price, x, 271);
                    break;
                case "crossbow":
                    p.text("Crossbow", x, 241);
                    p.textFont(mediumFont);
                    p.text("$" + price, x, 271);
                    break;
                case "miscCannon":
                    p.text("Luggage", x, 241);
                    p.text("Blaster", x, 266);
                    p.textFont(mediumFont);
                    p.text("$" + price, x, 296);
                    break;
                case "energyBlaster":
                    p.text("Energy Blaster", x, 241);
                    p.textFont(mediumFont);
                    p.text("$" + price, x, 271);
                    break;
                case "magicMissleer":
                    p.text("Magic Missileer", x, 241);
                    p.textFont(mediumFont);
                    p.text("$" + price, x, 271);
                    break;
                case "tesla":
                    p.text("Tesla Tower", x, 241);
                    p.textFont(mediumFont);
                    p.text("$" + price, x, 271);
                    break;
                case "nightmare":
                    p.text("Nightmare", x, 241);
                    p.text("Blaster", x, 266);
                    p.textFont(mediumFont);
                    p.text("$" + price, x, 296);
                    break;
                case "flamethrower":
                    p.text("Flamethrower", x, 241);
                    p.textFont(mediumFont);
                    p.text("$" + price, x, 271);
                    break;
                case "railgun":
                    p.text("Railgun", x, 241);
                    p.textFont(mediumFont);
                    p.text("$" + price, x, 271);
                    break;
                case "waveMotion":
                    p.text("Death Beam", x, 241);
                    p.textFont(mediumFont);
                    p.text("$" + price, x, 271);
                    break;
            }
            if (p.mousePressed && p.mouseButton == LEFT) sprite = spritePressed;
            if (inputHandler.leftMouseReleasedPulse && alive) {
                action();
                sprite = spritePressed;
            }
        }
        else sprite = spriteIdle;
    }

    public void action() {
        depressed = !depressed;
        //if already holding, stop
        if (hand.held.equals(towerType)) hand.setHeld("null");
        //if not, do
        else if (depressed) hand.setHeld(towerType);
    }
}